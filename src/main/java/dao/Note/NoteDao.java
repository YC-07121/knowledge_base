package dao.note;

import java.util.List;

import model.Note;

public interface NoteDao {

    List<Note> findByUserId(int userId);

    List<Note> searchNotes(int userId, String keyword);

    Note getDetail(int noteId);

    void updateContent(int noteId, String content);

    int insert(Note note);

    void delete(int noteId);

}