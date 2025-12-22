package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConn {
    private static final String url = "jdbc:mysql://localhost:3306/file_system";
    private static final String name = "root";
    private static final String pwd = "Kushalsg@21";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, name, pwd);
    }
}
