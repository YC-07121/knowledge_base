package service.note;

import java.util.List;
import dao.note.NoteDao;
import dao.note.NoteDaoImpl;
import model.Note;

public class NoteServiceImpl implements NoteService {

    private NoteDao noteDao = new NoteDaoImpl();

    @Override
    public Note getNoteDetail(int noteId) {
        // 只有點擊時才去抓取完整的 Content
        return noteDao.getDetail(noteId);
    }

    @Override
    public void saveNoteContent(int noteId, String content) {
        noteDao.updateContent(noteId, content);
    }

    @Override
    public Note createNewNote(int categoryId, String title, int userId) {
        Note note = new Note();
        note.setCategoryId(categoryId);
        note.setTitle(title);
        note.setContent("");
        note.setUserId(userId);

        int id = noteDao.insert(note);
        note.setId(id);
        return note;
    }

    @Override
    public boolean deleteNote(int noteId) {
        noteDao.delete(noteId);
        return true;
    }

    @Override
    public List<Note> searchNotes(int userId, String keyword) {
        return noteDao.searchNotes(userId, keyword);
    }
}