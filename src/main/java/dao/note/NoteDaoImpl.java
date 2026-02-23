package dao.note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Note;
import util.Tool;

public class NoteDaoImpl implements NoteDao {

    Connection conn = Tool.ConnectDB();

    @Override
    public List<Note> findByUserId(int userId) {
        List<Note> list = new ArrayList<>();
        String sql = "SELECT id, category_id, title, user_id, create_time FROM notes WHERE user_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Note note = new Note();
                    note.setId(rs.getInt("id"));
                    note.setCategoryId(rs.getInt("category_id"));
                    note.setTitle(rs.getString("title"));
                    note.setUserId(rs.getInt("user_id"));
                    note.setCreateTime(rs.getTimestamp("create_time"));
                    list.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Note> searchNotes(int userId, String keyword) {
        List<Note> list = new ArrayList<>();
        String sql = "SELECT id, category_id, title, user_id, create_time FROM notes WHERE user_id = ? AND content LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Note note = new Note();
                    note.setId(rs.getInt("id"));
                    note.setCategoryId(rs.getInt("category_id"));
                    note.setTitle(rs.getString("title"));
                    note.setUserId(rs.getInt("user_id"));
                    note.setCreateTime(rs.getTimestamp("create_time"));
                    list.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Note getDetail(int noteId) {
        String sql = "SELECT * FROM notes WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, noteId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Note note = new Note();
                    note.setId(rs.getInt("id"));
                    note.setCategoryId(rs.getInt("category_id"));
                    note.setTitle(rs.getString("title"));
                    note.setContent(rs.getString("content"));
                    note.setUserId(rs.getInt("user_id"));
                    note.setCreateTime(rs.getTimestamp("create_time"));
                    return note;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateContent(int noteId, String content) {
        String sql = "UPDATE notes SET content = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, content);
            ps.setInt(2, noteId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int insert(Note note) {
        String sql = "INSERT INTO notes (category_id, title, content, user_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, note.getCategoryId());
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getContent() == null ? "" : note.getContent());
            ps.setInt(4, note.getUserId());

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
    public void delete(int noteId) {
        String sql = "DELETE FROM notes WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, noteId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}