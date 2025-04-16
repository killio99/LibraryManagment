import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors

public class Library{
    private static final int MAX_CHECKOUTS = 3;
    //all the books
    //dont add or remove on a checkout, just change the num 
    //available in the book object
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<User> users = new ArrayList<User>();

    //mass entry of books and users in construcotr
    public Library(){
        
    };

    public static int getMaxCheckouts(){ 
        return MAX_CHECKOUTS;
    }
    
    public String getBookListTitles(){
        String rtn = "";
        for (int i = 0; i < books.size(); i++) {
            rtn += books.get(i).getTitle() + "\n";
        }
        return rtn;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    //testing
    public void addBook(Book b){
        //if the book already exists, add 1 to copies
        //if it doesnt, construct a new book
        books.add(b);
    }

}