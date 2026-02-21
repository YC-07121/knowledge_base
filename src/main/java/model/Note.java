package model;

import java.sql.Timestamp;

public class Note {
	private Integer id;          
    private Integer categoryId;  
    private String title;        
    private String content;      
    private Integer userId;      
    private Timestamp createTime;  

	public Note() {
	}

	public Note(Integer id, Integer categoryId, String title, String content, Integer userId, Timestamp createTime) {
		this.id = id;
		this.categoryId = categoryId;
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
    public String toString() {
        return "📄 " + title; // 加個圖示讓樹狀結構更直觀
    }
}
