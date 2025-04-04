import java.util.ArrayList;
import java.io.File;  // Import the File class
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
        


    };

    public int getMaxCheckouts(){ return MAX_CHECKOUTS;}
    
}