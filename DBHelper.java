import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static final String URL = 
    "jdbc:sqlite:c:/Users/killi/OneDrive/Documents/Library Project/library.db";
    
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}