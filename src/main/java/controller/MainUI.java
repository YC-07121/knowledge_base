import java.awt.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class MainUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User user; // 登入資訊
    
    // UI 組件
    private JTree tree;
    private JTextArea textArea;
    private JSplitPane splitPane;
    private JLabel statusLabel; // 顯示目前選取的標題

    // 模擬 Service (後續請替換為你的 DAO 邏輯)
    // private NoteService noteService = new NoteServiceImpl();

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

        // 1. 初始化左側：JTree (放在 ScrollPane 裡)
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("我的知識庫");
        tree = new JTree(root);
        JScrollPane treeScrollPane = new JScrollPane(tree);
        
        // 2. 初始化右側：JTextArea (放在 ScrollPane 裡)
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);      // 自動換行
        textArea.setWrapStyleWord(true); // 以單字為單位換行
        JScrollPane textScrollPane = new JScrollPane(textArea);

        // 3. 建立 JSplitPane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, textScrollPane);
        splitPane.setDividerLocation(250); // 設定分割線初始位置
        contentPane.add(splitPane, BorderLayout.CENTER);

        // 4. 狀態列 (下方顯示資訊)
        statusLabel = new JLabel(" 請選擇一則筆記...");
        contentPane.add(statusLabel, BorderLayout.SOUTH);

        // 5. 註冊 JTree 點擊監聽器
        initTreeListener();

        // 6. 載入資料 (暫時先載入測試資料)
        loadMockData();
    }

    private void initTreeListener() {
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                // 取得目前選取的節點
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                
                if (selectedNode == null) return;

                Object userObj = selectedNode.getUserObject();
                
                // 判斷是否為 Note 物件
                if (userObj instanceof Note) {
                    Note note = (Note) userObj;
                    statusLabel.setText(" 目前編輯: " + note.getTitle());
                    textArea.setText(note.getContent());
                    textArea.setEditable(true);
                } else {
                    // 如果點到的是分類 (Category)
                    statusLabel.setText(" 分類: " + userObj.toString());
                    textArea.setText(""); // 清空內容
                    textArea.setEditable(false);
                }
            }
        });
    }

    private void loadMockData() {
        // 這裡未來會替換成你的 DAO 遞迴邏輯
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("我的知識庫");
        
        // 測試分類 1
        Category cat1 = new Category(1, "Java 學習", null);
        DefaultMutableTreeNode catNode1 = new DefaultMutableTreeNode(cat1);
        
        // 測試筆記 1
        Note n1 = new Note(101, 1, "什麼是 MVC?", "MVC 是一種軟體架構模式...", null);
        catNode1.add(new DefaultMutableTreeNode(n1));
        
        root.add(catNode1);
        
        // 更新 TreeModel
        tree.setModel(new DefaultTreeModel(root));
    }
}