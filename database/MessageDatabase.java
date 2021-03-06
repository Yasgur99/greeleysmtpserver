package greeleysmtpserver.database;

import greeleysmtpserver.server.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author michaelmaitland
 */

public class MessageDatabase extends AbstractDatabase {

    private static MessageDatabase messageDatabase = null;
    private static String FILENAME = "greeleysmtp.db";
    private static String DOMAIN = "ccsd.ws";

    private MessageDatabase() {
        super(FILENAME);
        createTable();
    }

    public static void setFilename(String filename) {
        FILENAME = filename;
    }

    public static MessageDatabase getInstance() {
        if (messageDatabase == null)
            messageDatabase = new MessageDatabase();
        return messageDatabase;
    }

    @Override
    protected void createTable() {
        // SQL statement for creating a new table
        String query = "CREATE TABLE IF NOT EXISTS messages (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	mailFrom text NOT NULL,\n"
                + "	rcptTo text NOT NULL,\n"
                + "     date text,\n"
                + "     dataTo text,\n"
                + "     dataFrom text,\n"
                + "     subject text,\n"
                + "     inResponseTo integer,\n"
                + "     data text\n"
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
        String query = "SELECT id, mailFrom, rcptTo, date, dataTo, dataFrom, subject, inResponseTo, data FROM messages";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("mailFrom") + "\t"
                        + rs.getString("rcptTo") + "\t"
                        + rs.getString("date") + "\t"
                        + rs.getString("dataTo") + "\t"
                        + rs.getString("dataFrom") + "\t"
                        + rs.getString("subject") + "\t"
                        + rs.getString("inResponseTo") + "\t"
                        + rs.getString("data"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addMessage(Session session) {
        String query = "INSERT INTO messages(mailFrom, rcptTo, date, dataTo, dataFrom, subject, inResponseTo, data) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, session.getFrom());
            pstmt.setString(2, session.getRecipients().toString());//TODO: handle this better
            pstmt.setString(3, session.getDate());
            pstmt.setString(4, session.getTo());
            pstmt.setString(5, session.getFrom());
            pstmt.setString(6, session.getSubject());
            pstmt.setInt(7, -1); //TODO: implements messsage chains
            pstmt.setString(8, session.getData());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
