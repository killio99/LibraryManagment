import java.sql.Connection;
import java.sql.Statement;

public class SchemaSetup {
    public static void setup() {
        String createBooks = """
            CREATE TABLE IF NOT EXISTS Books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                isbn TEXT UNIQUE,
                available_copies INTEGER
            );
        """;

        String createUsers = """
            CREATE TABLE IF NOT EXISTS Users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE
            );
        """;

        String createTransactions = """
            CREATE TABLE IF NOT EXISTS Transactions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                book_id INTEGER,
                borrow_date TEXT,
                return_date TEXT,
                FOREIGN KEY(user_id) REFERENCES Users(id),
                FOREIGN KEY(book_id) REFERENCES Books(id)
            );
        """;

        try (Connection conn = DBHelper.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createBooks);
            stmt.execute(createUsers);
            stmt.execute(createTransactions);
            System.out.println("✅ Tables created successfully (or already exist).");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}