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
            return true;
        }
        return false;
    }

    public void returnBook(){
        copies++;
    }

    public boolean isAvailable(){
        return copies > 0;
    }

    public void saveToDB() {
        String sql = "INSERT INTO Books (id, author, title, available_copies) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBHelper.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, author);
            pstmt.setString(3, title);
            pstmt.setInt(4, copies);
            pstmt.executeUpdate();

            System.out.println("✅ Book saved to database: " + title);

        } catch (Exception e) {
            System.out.println("❌ Failed to save book:");
            e.printStackTrace();
        }
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
    public void updateCopiesInDB() {
        String sql = "UPDATE Books SET available_copies = ? WHERE id = ?";

        try (Connection conn = DBHelper.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, copies);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            System.out.println("🔄 Updated book copies in DB: " + title);

        } catch (Exception e) {
            System.out.println("❌ Failed to update copies:");
            e.printStackTrace();
        }
    }


}