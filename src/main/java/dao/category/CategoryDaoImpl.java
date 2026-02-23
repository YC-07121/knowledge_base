package dao.category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dao.category.CategoryDao;
import model.Category;
import util.Tool;

public class CategoryDaoImpl implements CategoryDao {

    Connection conn = Tool.ConnectDB();

    @Override
    public List<Category> findByUserId(int userId) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE user_id = ? ORDER BY parent_id ASC, name ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category cat = new Category();
                    cat.setId(rs.getInt("id"));
                    cat.setName(rs.getString("name"));

                    // 處理 parent_id 為 null 的情況
                    int parentId = rs.getInt("parent_id");
                    if (rs.wasNull()) {
                        cat.setParentId(null);
                    } else {
                        cat.setParentId(parentId);
                    }

                    cat.setUserId(rs.getInt("user_id"));
                    list.add(cat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int insert(Category cat) {
        String sql = "INSERT INTO categories (name, parent_id, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cat.getName());

            // 處理父分類可能為 null 的情況
            if (cat.getParentId() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, cat.getParentId());
            }

            ps.setInt(3, cat.getUserId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void update(int id, Category cat) {
        String sql = "UPDATE categories SET name = ?, parent_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cat.getName());

            if (cat.getParentId() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, cat.getParentId());
            }

            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}