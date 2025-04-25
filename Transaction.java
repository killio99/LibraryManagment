import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {
    private String username;
    private String title;
    private LocalDate date;
    private String status;
    //isCheckout is true if the book is being checkout out
    //false if it is being returned
    
    //manuel entry
    public Transaction(String username, String title, String status, LocalDate date){
        this.username = username;
        this.title = title;
        this.status = status;
        this.date = date;
        saveToDB(); // Save the transaction to the database when created
    }

    //auto entry
    public Transaction(String username, String title, String status){
        this.username = username;
        this.title = title;
        this.status = status;
        this.date = LocalDate.now();
        saveToDB(); // Save the transaction to the database when created
    }

    public static String listTransactions(){
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Transactions";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String title = rs.getString("title");
                String status = rs.getString("status");
                LocalDate date = rs.getDate("date").toLocalDate();

                sb.append("User: ").append(username)

                  .append(", Book: ").append(title)
                  .append(", Status: ").append(status)
                  .append(", Date: ").append(date).append("\n");
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve transactions from database:");
            e.printStackTrace();
        }

        return sb.toString();
    }

    private void saveToDB(){
        String insertUserSql = "INSERT INTO Transactions (username, title, status, date) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = DBHelper.connect();
             PreparedStatement insertStmt = conn.prepareStatement(insertUserSql)) {

    
            // Insert the user if they do not exist
            insertStmt.setString(1, username);
            insertStmt.setString(2, title);
            insertStmt.setString(3, status);
            insertStmt.setString(4, LocalDate.now().toString()); // Use LocalDate's toString() method to get the date in the correct format
            insertStmt.executeUpdate();
    
            System.out.println("Transaction saved to database");
    
        } catch (Exception e) {
            System.out.println("Failed to save Transaction");
            e.printStackTrace();
        }

    }

        //get how many days ago this transaction happened
    public long daysSinceTransaction(){
        return ChronoUnit.DAYS.between(date, LocalDate.now());
    }

}
