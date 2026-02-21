package model;

public class Category {
    private Integer id;         
    private String name;        
    private Integer parentId;   
    private Integer userId;     

    public Category() {
    }

    public Category(Integer id, String name, Integer parentId, Integer userId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return name; 
    }
}