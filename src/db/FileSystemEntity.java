package db;

public class FileSystemEntity {
    private int id;
    private String name;
    private String type; // "FILE" or "FOLDER"
    private Integer parentId;

    // Getters, Setters, Constructors

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + name;
    }
}
