import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class User{
    private String username;
    private String password;
    private double fines;
    private Map<String, LocalDate> borrowedBookTitles = new HashMap<>(); 
    //Map of book titles and due dates
    //assigns a due date to each book title
    private int booksBorrowed;

    private ArrayList<Book> reservedBooks = new ArrayList<Book>();

    public static User createUser(String u, String p){
        User newUser = new User(u, p); // Create a new user object
        newUser.saveNewToDB();
        
        return newUser; // Return the new user object
    }

    //called on a new user creation, and to load a user from getUserFromDB
    public User(String u, String p){
        username = u;
        password = p;
        fines = 0;
        //wouldnt load any for a new user call on the constructor
        loadBorrowedBooksFromDB(); // Load borrowed books from the database
    }

    public User(String u, String p, double f){
        username = u;
        password = p;
        fines = f;
        loadBorrowedBooksFromDB();
        //load borrowed books from DB into the map
    }
    public void setFines(double amount){
        fines = amount;
        updateFinesInDB(); // Update the fines in the database
    }
    //for user and book, create a loadfromDB function to load the user into a user object
    //and the book into a book object.
    //this will be used to load the user from the database when they log in
    
    //also make a create function, which calls the constructor, and calls saveNewToDB
    //That way, when we "log in" a user, which has to use the constructor, it doesn't call saveNewToDB
    //do the same for the book class

    



    private void saveNewToDB() {
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
                //prevents duplicate users from being created
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

    private void updateFinesInDB(){
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

    public void loadBorrowedBooksFromDB() {
        // This method loads the borrowed books from the database into the borrowedBookTitles map
        String sql = "SELECT * FROM ActiveLoans WHERE username = ?";
    
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            borrowedBookTitles.clear(); // Clear the map before loading
    
            while (rs.next()) {
                String bookTitle = rs.getString("isbn");
                String dueDateString = rs.getString("due_date"); // Get the date as a string
                LocalDate dueDate = LocalDate.parse(dueDateString); // Parse the date
                borrowedBookTitles.put(bookTitle, dueDate); // Add to the map
            }

            //System.out.println("books loaded for user: " + username);
    
        } catch (Exception e) {
            System.out.println("Failed to load borrowed books from database:");
            e.printStackTrace();
        }
    }
    public void updateBorrowedBooksInDB() {
        // This method updates the ActiveLoans table in the database for the user
        // It deletes all existing records for the user and re-inserts the current state of borrowedBookTitles
        String deleteSql = "DELETE FROM ActiveLoans WHERE username = ?";
        String insertSql = "INSERT INTO ActiveLoans (username, isbn, due_date) VALUES (?, ?, ?)";
    
        try (Connection conn = DBHelper.connect();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
    
            // Step 1: Delete all existing records for the user
            deleteStmt.setString(1, username);
            deleteStmt.executeUpdate();
    
            // Step 2: Re-insert the current state of borrowedBookTitles
            for (Map.Entry<String, LocalDate> entry : borrowedBookTitles.entrySet()) {
                String bookTitle = entry.getKey();
                LocalDate dueDate = entry.getValue();
    
                insertStmt.setString(1, username);
                insertStmt.setString(2, bookTitle);
                insertStmt.setObject(3, dueDate); // Use setObject for LocalDate
                insertStmt.executeUpdate();
            }
    
            //System.out.println("Borrowed books updated in database for user: " + username);
    
        } catch (Exception e) {
            System.out.println("Failed to update borrowed books in database:");
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
    //used to assign a user from the db to a user object
    public static User getUserFromDB(String username){
        String sql = "SELECT * FROM Users WHERE username = ?";
        User user = null;

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getDouble("fines")
                );
            } else {
                System.out.println("User not found: " + username);
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve user from database:");
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean checkout(Book b){
        //if the user has overdue fines
        if (b == null){
            System.out.println("Book is null.");
            return false;
        }
        if (fines > 0){
            System.out.println("You have overdue fines. Please pay them before borrowing books.");
            return false;
        }
        //if the user already has too many books borrowed
        if (borrowedBookTitles.size() >= Library.getMaxCheckouts()){
            System.out.println("You have reached the maximum number of checkouts.");
            return false;
        }
        //if its already borrowed by the user
        if (borrowedBookTitles.containsKey(b.getTitle())) {
            System.out.println("Book is already borrowed: " + b.getTitle());
            return false;
        }
        //if the book has copies available
        if (!b.isAvailable()){
            System.out.println("No copies available for checkout: " + b.getTitle());
            return false;
        }
        
        //checking out the book
        b.borrowBook(); //update book copies
        

        LocalDate dueDate = LocalDate.now().plusDays(14); // Set due date to 14 days from now

        borrowedBookTitles.put(b.getTitle(), dueDate);
        updateBorrowedBooksInDB(); //adds the book title to the user key in ActiveLoans table


        Transaction t = new Transaction(username, b.getTitle(), "checkout");

        System.out.println("Checked out: " + b.getTitle() + " | Due: " + dueDate);
        return true; //successful checkout

    }

    public void returnBook(Book b){
        if (b == null){
            System.out.println("Book is null.");
            return;
        }
        if (!borrowedBookTitles.containsKey(b.getTitle())){
            System.out.println("Book not borrowed: " + b.getTitle());
            return;
        }

        //if the book is overdue, add fines
        if (LocalDate.now().isAfter(borrowedBookTitles.get(b.getTitle()))){
            System.out.println("Book is overdue: " + b.getTitle());
            //add fines to user
            long daysOverdue = LocalDate.now().toEpochDay() - borrowedBookTitles.get(b.getTitle()).toEpochDay();
            fines += daysOverdue * Library.getFinesPerDay(); //add fine for each day overdue
            System.out.println("Fines added: $" + (daysOverdue * Library.getFinesPerDay()));
        }

        //increments copies in the book object
        b.returnBook();

        borrowedBookTitles.remove(b.getTitle()); //remove the book from the user
        updateBorrowedBooksInDB(); //update the ActiveLoans table in the database
        Transaction t = new Transaction(username, b.getTitle(), "return");
        //creates a transaction for the return of the book
        System.out.println("Returned: " + b.getTitle());
    }

    public void reserveBook(Book b){
    }

    public void viewBorrowedBooks(){
        //loops through every book checked out
        for (String tlt : borrowedBookTitles.keySet()){
            //finds the book object given the title stored with the user
            //this is used to get the book details from the database
           Book curBk = Book.getBookFromDB(tlt);
           
           System.out.println(curBk.getID() + " | " + curBk.getTitle() + " | " + curBk.getAuthor() +
            " | Due: " + borrowedBookTitles.get(tlt));
            //gets the due date of the book in the hashmap

        }
    }

    //Getters
    public String getUsername(){
        return username;
    }

    public double getFines(){
        return fines;
    }

    //set fines
    

    public void payFines(double amount){
        if(amount <= 0){
            System.out.println("Invalid payment amount.");
        }

        fines -= amount;
        if (fines < 0){
            fines = 0;
        }
        System.out.println("Paid fines. Remianing balance: $" + fines);
        updateFinesInDB();
    }


}