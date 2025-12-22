import java.time.LocalDateTime;

public class FileNode {
    protected String name;
    protected Directory parent;
    protected LocalDateTime CreatedAt;
    protected LocalDateTime ModifiedAt;

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }
}
