package dao.Note;

import java.util.List;

import model.Note;

public interface NoteDao {

    List<Note> findByUserId(int userId);

    Note getDetail(int noteId);

    void updateContent(int noteId, String content);

    int insert(Note note);

    void delete(int noteId);
}