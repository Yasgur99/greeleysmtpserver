package greeleysmtpserver.database;

/**
 * @author michaelmaitland
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class UserDatabase extends AbstractDatabase {

    private static UserDatabase userDatabase = null;
    private static String FILENAME = "greeleysmtp.db";
    private static String DOMAIN = "ccsd.ws";

    private UserDatabase() {
        super(FILENAME);
        createTable();
    }

    public static void setFilename(String filename) {
        FILENAME = filename;
    }

    public static UserDatabase getInstance() {
        if (userDatabase == null)
            userDatabase = new UserDatabase();
        return userDatabase;
    }

    private final static Logger errorLogger = Logger.getLogger("server errors");

    @Override
    protected void createTable() {
        // SQL statement for creating a new table
        String query = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	username text NOT NULL UNIQUE,\n"
                + "	password text NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void selectAll() {
        String query = "SELECT id, username, password FROM users";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("username") + "\t"
                        + rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean add(String username, String password) {
        String query = "INSERT INTO users(username, password) VALUES(?,?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, toMd5(password));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
        } catch (NoSuchAlgorithmException nex) {
        }
        return false;
    }

    public boolean containsUser(String username) {
        String query = "SELECT username FROM users WHERE username = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            // set the value
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            // check to see if user exists is ResultSet
            while (rs.next())
                return username.equals(rs.getString("username"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static String toMd5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
    }

    public static String getDomain() {
        return DOMAIN;
    }
}
