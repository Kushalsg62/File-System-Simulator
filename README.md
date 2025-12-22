# 🗂️ Java File System Handler (with MySQL Persistence)

A modular, data-structure-oriented file system implementation in Java, with support for directory and file creation, deletion, and listing — all backed by a MySQL database for persistence.

## 🚀 Features

- Create and remove files and directories
- In-memory hierarchical structure (`FileNode`, `Directory`, `File`)
- Persistent storage using MySQL (mirrors the file tree)
- Path-based operations (`mkdir`, `touch`, `ls`, `rm`)
- Clean code structure following OOP and DSA principles

---

## 🏗️ Project Structure

```
src/
├── FileSystem.java # Core operations and tree management
├── FileNode.java # Abstract base node
├── Directory.java # Represents folders
├── File.java # Represents file nodes
├── pathResolver.java # Normalizes and resolves paths
├── Main.java # Entry point / CLI tester
└── db/
    ├── DatabaseConnection.java # JDBC connector
    ├── FileSystemEntity.java # DB model
    └── FileSystemDAO.java # DAO for DB access
```


---

## 🧠 Data Structure Overview

- `FileNode` — abstract base class with name and parent
- `Directory` — holds a list of child nodes
- `File` — contains file content (`write()`, `read()`)
- In-memory tree mimics actual file system hierarchy

---

## 🗃️ MySQL Integration

### 🧾 SQL Schema

```sql
CREATE TABLE file_system (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    type ENUM('FILE', 'FOLDER') NOT NULL,
    parent_id INT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES file_system(id) ON DELETE CASCADE
);
```

🛠 Setup Instructions
1. ✅ Install MySQL and create a database:

```sql
CREATE DATABASE java_filesystem;
USE java_filesystem;
```
2. ✅ Run the schema above to create the file_system table.

3. ✅ Insert a root entry (if not using auto-creation in code):

```sql
INSERT INTO file_system (id, name, type, parent_id) VALUES (1, '', 'FOLDER', NULL);
```

⚙️ Configuration
🔌 Set DB Credentials
Update dbConn.java:

```java
private static final String URL = "jdbc:mysql://localhost:3306/java_filesystem";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

▶️ How to Run
Using CLI / Main
Compile everything:

```java
javac src/**/*.java
```

Run
```java
java src/Main
```

Try CLI example in Main.java:
```java
fs.CreateDirectory("/docs");
fs.CreateDirectory("/docs/projects");
fs.CreateFile("/docs/projects/todo.txt", "Finish MySQL integration");
fs.List("/docs");
fs.RemoveFile("/docs/projects/todo.txt");
```
bash:

```
Creating structure...

Listing /docs:
projects
readme.txt

Removing /docs/readme.txt

Listing /docs again:
projects
```

## 💡 Design Goals
Keep in-memory tree fast and accurate
Use MySQL as persistence layer (survives reboot)
Keep modular DAO to support future migration (e.g., to PostgreSQL or file-based DB)
Designed for extensibility: add cd, search, or mv with ease

## 🔮 Future Enhancements
 cd command with current working directory context
 Move (mv) or rename nodes
 Full-text search
 Caching or syncing strategy
 GUI file explorer interface
 REST API for remote access
 
## 🤝 Contributing
Feel free to fork, improve, or open issues. PRs are welcome!

## 🪪 License
This project is open-source under the MIT License.

```yaml
---

## 🧠 Optional Extras

If you want to:
- Publish this on GitHub, I can help write `.gitignore`, `.editorconfig`, and `.github/workflows`.
- Bundle it as a `.jar` or run via Gradle/Maven.

Want those too?
```
