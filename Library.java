import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors

public class Library{
    private final int MAX_CHECKOUTS = 3;
    //all the books
    //dont add or remove on a checkout, just change the num 
    //available in the book object
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<User> users = new ArrayList<User>();

    //mass entry of books and users in construcotr
    public Library(){
        //Mass Entry of Books
         try (BufferedReader br = new BufferedReader(new FileReader("booksList.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                //create a book
                
                //file format
                //ISBN,Author,Title,Num
                int indISBN = line.indexOf(",",0);
                int ISBN = Integer.parseInt(line.substring(0,indISBN));
                
                int indAuthor = line.indexOf(",", indISBN+1);
                String Author = line.substring(indISBN+1, indAuthor);

                int indTitle = line.indexOf(",", indAuthor+1);
                String Title = line.substring(indAuthor+1, indTitle);

                int NumCopies = Integer.parseInt(line.substring(indTitle+1));
                
                books.add(new Book(ISBN, Author, Title, NumCopies));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        //Mass Entry of Users
        try (BufferedReader br2 = new BufferedReader(new FileReader("userList.txt"))) {
            String line;
            while ((line = br2.readLine()) != null) {
                //create a user object
                int indUsername = line.indexOf(",", 0);
                String user = line.substring(0, indUsername);

                int indPassword = line.indexOf(",", indUsername+1);
                String pass = line.substring(indUsername+1, indPassword);

                double fines = Double.parseDouble(line.substring(indPassword+1));
                users.add(new User(user, pass, fines));
                
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        /* 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("This is the first line.");
            writer.newLine(); 
            writer.write("This is the second line.");
        } catch (IOException e) {
            System.err.println("An error occurred writing to the file: " + e.getMessage());
        }
            */
    };

    public int getMaxCheckouts(){ return MAX_CHECKOUTS;}
    
    public String getBookListTitles(){
        String rtn = "";
        for (int i = 0; i < books.size(); i++) {
            rtn += books.get(i).getTitle() + "\n";
        }
        return rtn;
    }
}