package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileSystemDAO {

    // Create a file or folder
    public void create(FileSystemEntity entity) {
        String sql = "INSERT INTO file_sys (name, type, parent_id) VALUES (?, ?, ?)";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getType());
            if (entity.getParentId() != null) {
                stmt.setInt(3, entity.getParentId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a file/folder by ID
    public Optional<FileSystemEntity> getById(int id) {
        String sql = "SELECT * FROM file_sys WHERE id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Get children of a folder
    public List<FileSystemEntity> listChildren(int parentId) {
        List<FileSystemEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM file_sys WHERE parent_id = ?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, parentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Find by name under specific parent
    public Optional<FileSystemEntity> findByNameUnderParent(String name, Integer parentId) {
        String sql = "SELECT * FROM file_sys WHERE name = ? AND parent_id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, parentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Rename a file/folder
    public void rename(int id, String newName) {
        String sql = "UPDATE file_sys SET name = ? WHERE id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Move a file/folder to a new parent
    public void move(int id, Integer newParentId) {
        String sql = "UPDATE file_sys SET parent_id = ? WHERE id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (newParentId != null) {
                stmt.setInt(1, newParentId);
            } else {
                stmt.setNull(1, Types.INTEGER);
            }

            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a file/folder by ID
    public void delete(int id) {
        String sql = "DELETE FROM file_sys WHERE id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Utility: maps SQL result to entity
    private FileSystemEntity mapResultSet(ResultSet rs) throws SQLException {
        FileSystemEntity entity = new FileSystemEntity();
        entity.setId(rs.getInt("id"));
        entity.setName(rs.getString("name"));
        entity.setType(rs.getString("type"));
        int parentId = rs.getInt("parent_id");
        entity.setParentId(rs.wasNull() ? null : parentId);
        return entity;
    }

    public FileSystemEntity getByNameAndParentId(String name, Integer parentId) {
        if(name.equals("")){
            return null;
        }
        String sql = "SELECT * FROM file_sys WHERE name = ? AND parent_id = ?";
        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, parentId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                FileSystemEntity entity = new FileSystemEntity();
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
                entity.setType(rs.getString("type"));
                entity.setParentId(rs.getInt("parent_id"));
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
