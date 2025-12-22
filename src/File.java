//The file does the write and read part, basically makes a file.

import java.time.LocalDateTime;

class File extends FileNode{
    private String content;

    public File(String name,Directory parent){
        this.name = name;
        this.parent = parent;
        this.content = "";
        this.ModifiedAt = LocalDateTime.now();
        this.CreatedAt = LocalDateTime.now();
    }

    public void write(String content){
        this.content = content;
        this.ModifiedAt = LocalDateTime.now();
    }

    public String read(){
        return this.content;
    }

}