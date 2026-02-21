package service.category;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;
import model.Category;

public interface CategoryService {

    // 【核心】將資料庫所有資料轉換成 JTree 需要的根節點結構
    DefaultMutableTreeNode getNoteTreeModel(int userId);

    // 新增分類
    Category createCategory(String name, Integer parentId, int userId);

    // 修改分類名稱
    void renameCategory(int catId, String newName);

    // 刪除分類 (注意：這會連動刪除底下的所有筆記)
    void deleteCategory(int catId);
}