import java.sql.Connection;
import java.sql.Statement;

public class SchemaSetup {
    public static void main(String[] args) {
        setup(); // Call the setup method to create tables
    }
    public static void setup() {
        String createBooks = """
            CREATE TABLE IF NOT EXISTS Books (
                isbn TEXT UNIQUE,    
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                available_copies INTEGER,
                homeLibrary TEXT NOT NULL
            );
        """;

        String createUsers = """
            CREATE TABLE IF NOT EXISTS Users (
                username TEXT UNIQUE,
                password TEXT NOT NULL,
                fines REAL DEFAULT 0.0
            );
        """;

        String createTransactions = """
            CREATE TABLE IF NOT EXISTS Transactions (
                username TEXT,
                title TEXT,
                status TEXT CHECK(status IN ('checkout', 'return', 'reserve')),
                date DATE
            );
        """;

        String createActiveLoans = """
            CREATE TABLE IF NOT EXISTS ActiveLoans (
                username TEXT NOT NULL,
                title TEXT NOT NULL,
                due_date DATE NOT NULL,
                FOREIGN KEY (username) REFERENCES Users(username),
                FOREIGN KEY (title) REFERENCES Books(title)
            );
        """;

        String createActiveReserves = """
            CREATE TABLE IF NOT EXISTS ActiveReserves (
                username TEXT NOT NULL,
                title TEXT NOT NULL,
                FOREIGN KEY (username) REFERENCES Users(username),
                FOREIGN KEY (title) REFERENCES Books(title)
                );
            """;
            

        try (Connection conn = DBHelper.connect(); Statement stmt = conn.createStatement()) {
            
            System.out.println("Creating new tables...");
            stmt.execute(createBooks);
            stmt.execute(createUsers);
            stmt.execute(createTransactions);
            stmt.execute(createActiveLoans);
            stmt.execute(createActiveReserves);
            System.out.println("✅ Tables created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}