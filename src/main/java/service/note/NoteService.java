package service.note;

import java.util.List;
import model.Note;

public interface NoteService {

    // 當點擊樹節點時，獲取完整筆記內容
    Note getNoteDetail(int noteId);

    // 儲存/更新筆記內文
    void saveNoteContent(int noteId, String content);

    // 新增一則新筆記（可以預設標題）
    Note createNewNote(int categoryId, String title, int userId);

    // 重新命名筆記名稱
    void renameNote(int noteId, String newTitle);

    // 刪除筆記
    boolean deleteNote(int noteId);

    // 關鍵字搜尋筆記
    List<Note> searchNotes(int userId, String keyword);
}