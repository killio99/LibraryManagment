import java.util.ArrayList;

public class Library{
    private final int MAX_CHECKOUTS = 3;
    //all the books
    //dont add or remove on a checkout, just change the num 
    //available in the book object
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<User> users = new ArrayList<User>();

    //mass entry of books and users in construcotr
    public Library(){};

    public int getMaxCheckouts(){ return MAX_CHECKOUTS;}
    
}