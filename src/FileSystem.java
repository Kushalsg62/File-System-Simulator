import db.FileSystemDAO;
import db.FileSystemEntity;
import java.util.*;


class FileSystem {
    private Directory root;
    FileSystemDAO dao = new FileSystemDAO();


    public FileSystem() {
        this.root = new Directory("", null);
        if (dao.getByNameAndParentId("", null) == null) {
            FileSystemEntity rootEntity = new FileSystemEntity();
            rootEntity.setName("");
            rootEntity.setType("FOLDER");
            rootEntity.setParentId(null); // root has no parent

            dao.create(rootEntity);
        }
    }

    public void CreateFile(String path, String content) {
        Directory parent = ParentDirectoryResolver(path);
        String name = nameExtractor(path);

        File f = new File(name, parent);
        f.write(content);
        parent.add(f);

        // ⬇️ Sync to DB
        FileSystemEntity entity = new FileSystemEntity();
        entity.setName(name);
        entity.setType("FILE");
        entity.setParentId(getNodeId(parent)); // <-- You’ll implement this method
        dao.create(entity);
    }

    public void CreateDirectory(String path) {
        Directory parent = ParentDirectoryResolver(path);
        String name = nameExtractor(path);

        Directory dir = new Directory(name, parent);
        parent.add(dir);

        // ⬇️ Sync to DB
        FileSystemEntity entity = new FileSystemEntity();
        entity.setName(name);
        entity.setType("FOLDER");
        entity.setParentId(getNodeId(parent)); // <-- You’ll implement this method
        dao.create(entity);
    }

    public void RemoveFile(String path) {
        Directory parent = ParentDirectoryResolver(path);
        String name = nameExtractor(path);

        FileNode node = parent.getChild(name);
        if (node != null) {
            parent.remove(name);

            // ⬇️ Remove from DB too
            int dbId = getNodeId(node); // <-- You’ll implement this method
            dao.delete(dbId);
        }
    }

    public void List(String path) {
        Directory parent = DirectoryResolver(path);

        for (FileNode node : parent.list()) {
            System.out.println(node.getName());
        }
    }

    //this is to resolve directory related issues, check if the directory is valid or not.

    private Directory DirectoryResolver(String path) {
        path = pathResolver.Normalize(path);
        assert path != null;
        String[] parts = path.split("/");

        Directory current = root;

        for (String part : parts) {
            if (part == null || part.isEmpty()) continue; // fixed: part.equals(null) → part == null
            FileNode child = current.getChild(part);

            if (child == null || !(child instanceof Directory)) {
                throw new RuntimeException("invalid path" + path);
            }

            current = (Directory) child;
        }

        return current;
    }

    private Directory ParentDirectoryResolver(String path) {
        path = pathResolver.Normalize(path);
        int index = path.lastIndexOf('/');

        if (index <= 0) return root;
        String parentPath = path.substring(0, index);
        return DirectoryResolver(parentPath);
    }

    private String nameExtractor(String path) {
        int index = path.lastIndexOf('/');
        return path.substring(index + 1); // +1 to skip the slash itself
    }

    private Integer getNodeId(FileNode node) {
        if (node.getParent() == null) {
            // This is root
            return 1; // Hardcoded ID of root in DB
        }
        String name = node.getName();
        Directory parent = node.getParent();

        Integer parentId = null;
        if (parent != null) {
            parentId = getNodeId(parent);
        }

        FileSystemEntity entity = dao.getByNameAndParentId(name, parentId);
        return (entity != null) ? entity.getId() : null;
    }

}
