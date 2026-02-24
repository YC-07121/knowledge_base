package controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import controller.user.AccountUI;
import controller.user.LoginUI;
import model.Category;
import model.Note;
import model.User;
import service.category.CategoryService;
import service.category.CategoryServiceImpl;
import service.note.NoteService;
import service.note.NoteServiceImpl;
import util.Tool;

public class MainUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User user = Tool.readUser(); // 登入資訊

    // Service 宣告
    private CategoryService categoryService = new CategoryServiceImpl();
    private NoteService noteService = new NoteServiceImpl();

    // UI 組件
    private JTree tree;
    private JTextArea textArea;
    private JSplitPane splitPane;
    private JLabel statusLabel;
    private Note currentNote; // 紀錄目前正在編輯的物件

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainUI frame = new MainUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainUI() {
        setTitle("個人知識庫系統");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        Timer autoSaveTimer;

        // 初始化工具列
        JToolBar toolBar = new JToolBar();
        JButton saveBtn = new JButton("儲存筆記");
        toolBar.add(saveBtn);
        contentPane.add(toolBar, BorderLayout.NORTH);

        JButton logoutBtn = new JButton("登出");
        toolBar.add(logoutBtn);

        JButton accountBtn = new JButton("帳戶管理");
        toolBar.add(accountBtn);

        // 初始化筆記總覽區塊
        tree = new JTree();
        refreshTree(); // 呼叫 Service 載入資料庫資料
        JScrollPane treeScrollPane = new JScrollPane(tree);

        // 初始化文字編輯區
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textScrollPane = new JScrollPane(textArea);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, textScrollPane);
        splitPane.setDividerLocation(250);
        contentPane.add(splitPane, BorderLayout.CENTER);

        // 初始化狀態列
        statusLabel = new JLabel(" 請選擇一則筆記...");
        contentPane.add(statusLabel, BorderLayout.SOUTH);

        JTextField searchField = new JTextField(15);
        toolBar.add(new JLabel(" 搜尋: "));
        toolBar.add(searchField);

        // --- 事件監聽 ---
        initTreeListener();
        initPopupMenu();

        // 儲存按鈕事件
        saveBtn.addActionListener(e -> handleSave(statusLabel));

        autoSaveTimer = new Timer(1500, e -> autoSave(statusLabel)); // 停止打字 1.5 秒後執行
        autoSaveTimer.setRepeats(false);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                resetTimer();
            }

            public void removeUpdate(DocumentEvent e) {
                resetTimer();
            }

            public void changedUpdate(DocumentEvent e) {
                resetTimer();
            }

            private void resetTimer() {
                if (currentNote != null) {
                    autoSaveTimer.restart();
                    statusLabel.setText(" 編輯中...");
                }
            }
        });

        accountBtn.addActionListener(e -> {
            AccountUI accountUI = new AccountUI();
            accountUI.setVisible(true);
        });
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "確定要登出並回到登入畫面嗎？", "登出", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginUI().setVisible(true);
            }
        });

        searchField.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                handleSearch(keyword);
            }
        });
    }

    // 重新載入樹狀結構
    private void refreshTree() {
        if (user != null) {
            DefaultMutableTreeNode root = categoryService.getNoteTreeModel(user.getId());
            tree.setModel(new DefaultTreeModel(root));
        }
    }

    private void initTreeListener() {
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode == null)
                    return;

                Object userObj = selectedNode.getUserObject();

                if (userObj instanceof Note) {
                    Note summary = (Note) userObj;
                    // 取得完整內文
                    currentNote = noteService.getNoteDetail(summary.getId());
                    statusLabel.setText(" 目前編輯: " + currentNote.getTitle());
                    textArea.setText(currentNote.getContent());
                    textArea.setEditable(true);
                } else {
                    currentNote = null;
                    statusLabel.setText(" 分類: " + userObj.toString());
                    textArea.setText("");
                    textArea.setEditable(false);
                }
            }
        });
    }

    private void initPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem addCatItem = new JMenuItem("新增子分類");
        JMenuItem addNoteItem = new JMenuItem("新增筆記");
        JMenuItem renameItem = new JMenuItem("重新命名");
        JMenuItem deleteItem = new JMenuItem("刪除");

        popupMenu.add(addCatItem);
        popupMenu.add(addNoteItem);
        popupMenu.addSeparator();
        popupMenu.add(renameItem);
        popupMenu.add(deleteItem);

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tree.getClosestRowForLocation(e.getX(), e.getY());
                    tree.setSelectionRow(row);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // 監聽選單動作
        addCatItem.addActionListener(e -> handleAddCategory());
        addNoteItem.addActionListener(e -> handleAddNote());
        renameItem.addActionListener(e -> handleRename());
        deleteItem.addActionListener(e -> handleDelete());
    }

    private void handleSave(JLabel statusLabel) {
        if (currentNote != null) {
            noteService.saveNoteContent(currentNote.getId(), textArea.getText());
            statusLabel.setText("已儲存");
        } else {
            JOptionPane.showMessageDialog(this, "請先選取一則筆記");
        }
    }

    private void autoSave(JLabel statusLabel) {
        if (currentNote != null) {
            noteService.saveNoteContent(currentNote.getId(), textArea.getText());
            statusLabel.setText("已儲存");
        }
    }

    private void handleAddCategory() {
        String name = JOptionPane.showInputDialog(this, "請輸入新分類名稱:");
        if (name != null && !name.trim().isEmpty()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            Integer parentId = null;
            if (node != null && node.getUserObject() instanceof Category) {
                parentId = ((Category) node.getUserObject()).getId();
            }
            categoryService.createCategory(name, parentId, user.getId());
            refreshTree();
        }
    }

    private void handleAddNote() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node != null && node.getUserObject() instanceof Category) {
            int catId = ((Category) node.getUserObject()).getId();
            String title = JOptionPane.showInputDialog(this, "請輸入筆記標題:");
            if (title != null && !title.trim().isEmpty()) {
                noteService.createNewNote(catId, title, user.getId());
                refreshTree();
            }
        } else {
            JOptionPane.showMessageDialog(this, "請先選取一個分類後再新增筆記");
        }
    }


    private void handleRename() {
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    	if (node == null || node.isRoot()) return;

    	Object obj = node.getUserObject();
    	String oldName = obj.toString();
    

    	String newName = JOptionPane.showInputDialog(this, "請輸入新名稱:", oldName);

    	if (newName != null && !newName.trim().isEmpty() && !newName.equals(oldName)) {
    		if (obj instanceof Category) {
    			Category cat = (Category) obj;
    			categoryService.renameCategory(cat.getId(), newName);
            	cat.setName(newName); // 同步更新 UI 物件
    		} else if (obj instanceof Note) {
    			Note note = (Note) obj;
    			noteService.renameNote(note.getId(), newName);
    			note.setTitle(newName); // 同步更新 UI 物件
            
    			// 	如果目前正在編輯這則筆記，更新狀態列
    			if (currentNote != null && currentNote.getId() == note.getId()) {
    				statusLabel.setText(" 目前編輯: " + newName);
    			}
    		}
        
    		// 關鍵：通知 Tree 模型該節點資料已改變，UI 會立即重繪文字
    		((DefaultTreeModel) tree.getModel()).nodeChanged(node);
        
    		// 或者簡單暴力直接重新讀取整棵樹
    		//         refreshTree(); 
    	}
    }


    private void handleDelete() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null || node.isRoot())
            return;

        Object obj = node.getUserObject();
        int confirm = JOptionPane.showConfirmDialog(this, "確定要刪除「" + obj.toString() + "」嗎？");

        if (confirm == JOptionPane.YES_OPTION) {
            if (obj instanceof Category) {
                categoryService.deleteCategory(((Category) obj).getId());
            } else if (obj instanceof Note) {
                noteService.deleteNote(((Note) obj).getId());
            }
            refreshTree();
        }
    }

    private void handleSearch(String keyword) {
        List<Note> results = noteService.searchNotes(user.getId(), keyword);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "找不到相關筆記");
            return;
        }

        // 彈出清單讓使用者選取
        Note selected = (Note) JOptionPane.showInputDialog(
                this, "搜尋結果:", "搜尋筆記",
                JOptionPane.QUESTION_MESSAGE, null,
                results.toArray(), results.get(0));

        if (selected != null) {

            statusLabel.setText(" 搜尋跳轉: " + selected.getTitle());
            // 載入筆記內容
            currentNote = noteService.getNoteDetail(selected.getId());
            textArea.setText(currentNote.getContent());
        }
    }
}