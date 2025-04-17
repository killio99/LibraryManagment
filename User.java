import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class User{
    private String username;
    private String password;
    private double fines;

    private ArrayList<Book> borrowedBooks = new ArrayList<Book>();
    private ArrayList<Book> reservedBooks = new ArrayList<Book>();

    //for mass import
    public User(String u, String p, double f){
        username = u;
        password = p;
        fines = f;
    }

    //for new user
    public User(String u, String p){
        username = u;
        password = p;
        fines = 0;
    }

    public void saveNewToDB() {
        String checkUserSql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        String insertUserSql = "INSERT INTO Users (username, password, fines) VALUES (?, ?, ?)";
    
        try (Connection conn = DBHelper.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkUserSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertUserSql)) {

            // Check if the username already exists
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next(); // Move to the first row of the result set
            int count = rs.getInt(1);
    
            if (count > 0) {
                System.out.println("User already exists in the database: " + username);
                return; // Exit the method without inserting
            }
    
            // Insert the user if they do not exist
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.setDouble(3, fines);
            insertStmt.executeUpdate();
    
            System.out.println("User saved to database: " + username);
    
        } catch (Exception e) {
            System.out.println("Failed to save user:");
            e.printStackTrace();
        }
    }

    public void updateFinesInDB(){
        String sql = "UPDATE Users SET fines = ? WHERE username = ?";

        try (Connection conn = DBHelper.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, fines);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

            System.out.println("User fines updated in database: " + username);

        } catch (Exception e) {
            System.out.println("Failed to update user fines:");
            e.printStackTrace();
        }
    }

    public void removeUserInDB(){
        String sql = "DELETE FROM Users WHERE username = ?";

        try (Connection conn = DBHelper.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.executeUpdate();

            System.out.println("User removed from database: " + username);

        } catch (Exception e) {
            System.out.println("Failed to remove user from database:");
            e.printStackTrace();
        }
    }

    public static String listUsers(){
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Users";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                //String password = rs.getString("password");
                double fines = rs.getDouble("fines");

                sb.append("User: ").append(username)
                  //.append(", Password: ").append(password)
                  .append(", Fines: $").append(fines).append("\n");
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve users from database:");
            e.printStackTrace();
        }

        return sb.toString();
    }


    public static boolean containsUserInDB(String username){
        String sql = "SELECT * FROM Users WHERE username = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true; // User exists in the database
            } else {
                System.out.println("User does not exist in the database: " + username);
            }
            return false;

        } catch (Exception e) {
            System.out.println("Failed to check user existence:");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkPasswordInDB(String username, String password){
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true; // Password matches for the user
            } 
            return false;

        } catch (Exception e) {
            System.out.println("Failed to check password:");
            e.printStackTrace();
        }
        return false;
    }

    public static User getUserFromDB(String username){

        String sql = "SELECT * FROM Users WHERE username = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String user = rs.getString("username");
                String pass = rs.getString("password");
                double fines = rs.getDouble("fines");

                return new User(user, pass, fines); // Return the user object
            } else {
                System.out.println("User does not exist in the database: " + username);
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve user from database:");
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean checkout(Book b){
        if (fines > 0){
            System.out.println("You have overdue fines. Please pay them before borrowing books.");
            return false;
        }
        if (borrowedBooks.size() >= Library.getMaxCheckouts()){
            System.out.println("You have reached the maximum number of checkouts.");
            return false;
        }
        if (b.borrowBook()){
            borrowedBooks.add(b);
            System.out.println("Check out: " + b.getTitle());
            return true;
        }else {
            System.out.println("Book is currently unavailable.");
            return false;
        }

    }

    public void returnBook(Book b){
        if (borrowedBooks.contains(b)){
            borrowedBooks.remove(b);
            b.returnBook();
            System.out.println("Returned: " + b.getTitle());
        }else{
            System.out.println("This book was not borrowed by the user.");
        }
    }

    public void reserveBook(Book b){
        if(!reservedBooks.contains(b)){
            reservedBooks.add(b);
            System.out.println("Reserved: " + b.getTitle());
        }else {
            System.out.println("Already reserved this book.");
        }
    }

    public void payFines(double amount){
        if(amount <= 0){
            System.out.println("Invalid payment amount.");
        }

        fines -= amount;
        if (fines < 0){
            fines = 0;
        }
        System.out.println("Paid fines. REmianing balance: $" + fines);
    }

    //Getters
    public String getUsername(){
        return username;
    }

    public double getFines(){
        return fines;
    }

    public ArrayList<Book> getBorrowedBooks(){
        return borrowedBooks;
    }

    public ArrayList<Book> getReservedBooks(){
        return reservedBooks;
    }

    //set fines
    public void setFines(double amount){
        fines = amount;
    }
}