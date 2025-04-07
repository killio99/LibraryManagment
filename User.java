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
        if (fines > 0){
            System.out.println("You have overdue fines. Please pay them before borrowing books.");
        }
        if (borrowedBooks.size() >= getMaxCheckouts()){
            System.out.println("You have reached the maximum number of checkouts.");
            
            }
        if (b.borrowBook()){
            borrowedBooks.add(b);
            System.out.println("Check out: " + b.getTitle());
        }else {
            System.out.println("Book is currently unavailable.");
        }
    }

    public void returnBook(Book b){
        if (borrowedBooks.contains(b)){
            borrowedBooks.remove(b);
            b.returnBook();
            System.out.println("Returned: " + b.getTitle());
        }else{
            System.out.println("This book was not borrowed by the user.");
        }
    }
}