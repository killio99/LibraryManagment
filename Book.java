import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class Book{
    private String isbn;
    private String author;
    private String title;
    private int copies;
    private String homeLibrary;

    //Constructor
    //used for creating a new book
    public static Book createBook(String isbn, String author, String title, int copies, String homeLibrary) {
        Book newBook = new Book(isbn, author, title, copies, homeLibrary);
        newBook.saveNewToDB(); // Save the book to the database when created
        return newBook;
    }

    //used for logging in and assigning a book to an object
    public Book(String isbn, String author, String title, int copies, String homeLibrary) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.copies = copies;
        this.homeLibrary = homeLibrary;
    }

    //Setters

    public void setAvailable(int copies){
        this.copies = copies;
        updateCopiesInDB(); // Update the number of copies in the database
    }

    //getters    
    public String getISBN(){
        return isbn;
    }

    public String getAuthor(){
        return author;
    }

    public String getTitle(){
        return title;
    }

    public int getCopiesAvailable(){
        return copies;
    }

    public boolean borrowBook(){
        if(copies > 0){
            copies --;
            updateCopiesInDB();
            return true;
        }
        return false;
    }

    public void returnBook(){
        copies++;
        updateCopiesInDB();
    }

    public boolean isAvailable(){
        return copies > 0;
    }

    private void saveNewToDB() {
        String checkBookSql = "SELECT COUNT(*) FROM Books WHERE isbn = ?";
        String insertBookSql = "INSERT INTO Books (isbn, author, title, available_copies, homeLibrary) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DBHelper.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkBookSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertBookSql)) {
    
            // Check if the book already exists
            checkStmt.setString(1, isbn);
            ResultSet rs = checkStmt.executeQuery();
            rs.next(); // Move to the first row of the result set
            int count = rs.getInt(1);
    
            if (count > 0) {
                System.out.println("Book already exists in the database: " + title);
                //increment book?
            } else {
                // Insert the new book
                insertStmt.setString(1, isbn);
                insertStmt.setString(2, author);
                insertStmt.setString(3, title);
                insertStmt.setInt(4, copies);
                insertStmt.setString(5, homeLibrary);
                insertStmt.executeUpdate();
                System.out.println("Book saved to database: " + title);
            }
    
        } catch (Exception e) {
            System.out.println("Failed to save book:");
            e.printStackTrace();
        }
    }
    //update copies in databse
    private void updateCopiesInDB() {

        String sql = "UPDATE Books SET available_copies = ? WHERE isbn = ?";
    
        try (Connection conn = DBHelper.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, copies);
            pstmt.setString(2, isbn);
            pstmt.executeUpdate();
    
            //System.out.println("Book copies updated in database: " + title);
    
        } catch (Exception e) {
            System.out.println("Failed to update book copies:");
            e.printStackTrace();
        }
    }
    
    public void removeFromDB(){
        String sql = "DELETE FROM Books WHERE isbn = ?";
    
        try (Connection conn = DBHelper.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
    
            System.out.println("Book removed from database: " + title);
    
        } catch (Exception e) {
            System.out.println("Failed to remove book from database:");
            e.printStackTrace();
        }
    }
    
    //Used to assign a book from the db to a book object
    public static Book getBookFromDB(String title) {
        String sql = "SELECT * FROM Books WHERE title = ?";
        Book book = null;

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book(
                    rs.getString("isbn"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getInt("available_copies"),
                    rs.getString("homeLibrary")
                );


            } else {
                System.out.println("Book not found in system: " + title);
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve book from database:");
            e.printStackTrace();
        }
        return book;
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";

        try (Connection conn = DBHelper.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                    rs.getString("isbn"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getInt("available_copies"),
                    rs.getString("homeLibrary")
                );
                books.add(book);
            }

        } catch (Exception e) {
            System.out.println("Failed to load books:");
            e.printStackTrace();
        }
        return books;
    }


}