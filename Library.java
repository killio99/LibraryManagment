import java.util.ArrayList;

public class Library{
    private static final int MAX_CHECKOUTS = 3;

    //mass entry of books and users in construcotr
    public Library(){
        
    };

    public static int getMaxCheckouts(){ 
        return MAX_CHECKOUTS;
    }

    
    public ArrayList<Book> getAllBooks(){
        return Book.getAllBooks();
    }

    public void addUser(User user){
        user.saveToDB();
    }

    //testing
    public void addBook(Book book){
        book.saveToDB();
    }

}