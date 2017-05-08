package greeleysmtpserver.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author michaelmaitland
 */
public abstract class AbstractDatabase {

    protected String url;

    public AbstractDatabase(String filename) {
        this.url = "jdbc:sqlite:" + System.getProperty("user.home") + "/Desktop/" + filename;
        createDatabase();
    }

    protected abstract void createTable();
    protected abstract void selectAll();
    
    private void createDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException ex) {
        }
    }
     
    protected Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
