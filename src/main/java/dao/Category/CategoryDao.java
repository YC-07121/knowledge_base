package dao.Category;

import java.util.List;

import model.Category;

public interface CategoryDao {

    List<Category> findByUserId(int userId);

    int insert(Category cat);

    void delete(int id);
}
