package service.category;

import java.util.List;
import java.util.Objects;
import javax.swing.tree.DefaultMutableTreeNode;
import dao.category.CategoryDao;
import dao.category.CategoryDaoImpl;
import dao.note.NoteDao;
import dao.note.NoteDaoImpl;
import model.Category;
import model.Note;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();
    private NoteDao noteDao = new NoteDaoImpl();

    @Override
    public DefaultMutableTreeNode getNoteTreeModel(int userId) {
        // 一次性從 DAO 撈出所有資料，避免在遞迴中反覆查詢資料庫
        List<Category> allCats = categoryDao.findByUserId(userId);
        List<Note> allNotes = noteDao.findByUserId(userId);

        // 建立 UI 根節點
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("我的知識庫");

        // 呼叫遞迴方法組裝結構 (從頂層 parentId 為 null 的分類開始)
        assemble(root, null, allCats, allNotes);

        return root;
    }

    // 遞迴組裝方法
    private void assemble(DefaultMutableTreeNode parentNode, Integer parentId, List<Category> allCats,
            List<Note> allNotes) {
        // 遍歷所有分類
        for (Category cat : allCats) {
            if (Objects.equals(cat.getParentId(), parentId)) {
                // 如果是此層級的分類，建立節點
                DefaultMutableTreeNode catNode = new DefaultMutableTreeNode(cat);
                parentNode.add(catNode);

                // 遞迴：找這個分類底下的子分類
                assemble(catNode, cat.getId(), allCats, allNotes);

                // 掛載：找隸屬於這個分類的所有筆記
                for (Note note : allNotes) {
                    if (Objects.equals(note.getCategoryId(), cat.getId())) {
                        catNode.add(new DefaultMutableTreeNode(note));
                    }
                }
            }
        }
    }

    @Override
    public Category createCategory(String name, Integer parentId, int userId) {
        Category cat = new Category();
        cat.setName(name);
        cat.setParentId(parentId);
        cat.setUserId(userId);
        int id = categoryDao.insert(cat);
        cat.setId(id);
        return cat;
    }

    @Override
    public void renameCategory(int catId, String newName) {
        Category cat = new Category();
        cat.setName(newName);
        categoryDao.update(catId, cat);
    }

    @Override
    public void deleteCategory(int catId) {
        categoryDao.delete(catId);
    }
}