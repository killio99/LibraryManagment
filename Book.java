import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class Book{
    private int id;
    private String author;
    private String title;
    private int copies;

    //Constructor
    public Book(int id, String author, String title, int copies){
        this.id = id;
        this.author = author;
        this.title = title;
        this.copies = copies;

        saveNewToDB(); // Save the book to the database when created
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAvailable(int copies){
        this.copies = copies;
        updateCopiesInDB(); // Update the number of copies in the database
    }

    //getters    
    public int getID(){
        return id;
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
        String insertBookSql = "INSERT INTO Books (isbn, author, title, available_copies) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = DBHelper.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkBookSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertBookSql)) {
    
            // Check if the book already exists
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next(); // Move to the first row of the result set
            int count = rs.getInt(1);
    
            if (count > 0) {
                System.out.println("Book already exists in the database: " + title);
            } else {
                // Insert the new book
                insertStmt.setInt(1, id);
                insertStmt.setString(2, author);
                insertStmt.setString(3, title);
                insertStmt.setInt(4, copies);
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
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
    
            System.out.println("Book copies updated in database: " + title);
    
        } catch (Exception e) {
            System.out.println("Failed to update book copies:");
            e.printStackTrace();
        }
    }
    
    public void removeFromDB(){
        String sql = "DELETE FROM Books WHERE isbn = ?";
    
        try (Connection conn = DBHelper.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
    
            System.out.println("Book removed from database: " + title);
    
        } catch (Exception e) {
            System.out.println("Failed to remove book from database:");
            e.printStackTrace();
        }
    }
    
    public static Book getBookFromDB(String title) {
        String sql = "SELECT * FROM Books WHERE title = ?";
        Book book = null;

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book(
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getInt("available_copies")
                );
            } else {
                System.out.println("Book not found: " + title);
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
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getInt("available_copies")
                );
                books.add(book);
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to load books:");
            e.printStackTrace();
        }
        return books;
    }


}