import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteTest {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:library.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connection to SQLite has been established.");
            }
        } catch (Exception e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
        }
    }
}