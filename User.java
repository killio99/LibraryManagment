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

    /* 
    public void checkout(Book b){
        if (fines == 0){
            if (borrowedBooks.size() < getMaxCheckouts()){
                //if ()
            }
            else{
                //display "too many checkouts"
            }
        }
        else{
            //display "overdue books with swing/javaFX"
        }
    }
    */
}