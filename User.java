import java.util.ArrayList;

public class User{
    private String username;
    private String password;
    private double fines;

    //TRY TO FIND A MORE SECURE WAY?
    //ex, index 1 in both refers to the same user
    private static ArrayList<String> allUsernames = new ArrayList<String>();
    private static ArrayList<String> allPasswords = new ArrayList<String>();

    private ArrayList<Book> borrowedBooks = new ArrayList<Book>();
    private ArrayList<Book> reservedBooks = new ArrayList<Book>();

    //for mass import
    public User(String u, String p, double f){
        username = u;
        password = p;
        fines = f;
        allUsernames.add(u);
        allPasswords.add(p);
    }

    //for new user
    public User(String u, String p){
        username = u;
        password = p;
        fines = 0;
        allUsernames.add(u);
        allPasswords.add(p);
    }


    public boolean checkout(Book b){
        if (fines > 0){
            System.out.println("You have overdue fines. Please pay them before borrowing books.");
            return false;
        }
        if (borrowedBooks.size() >= Library.getMaxCheckouts()){
            System.out.println("You have reached the maximum number of checkouts.");
            return false;
        }
        if (b.borrowBook()){
            borrowedBooks.add(b);
            System.out.println("Check out: " + b.getTitle());
            return true;
        }else {
            System.out.println("Book is currently unavailable.");
            return false;
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

    public void reserveBook(Book b){
        if(!reservedBooks.contains(b)){
            reservedBooks.add(b);
            System.out.println("Reserved: " + b.getTitle());
        }else {
            System.out.println("Already reserved this book.");
        }
    }

    public void payFines(double amount){
        if(amount <= 0){
            System.out.println("Invalid payment amount.");
        }

        fines -= amount;
        if (fines < 0){
            fines = 0;
        }
        System.out.println("Paid fines. REmianing balance: $" + fines);
    }

    //Getters
    public String getUsername(){
        return username;
    }

    public double getFines(){
        return fines;
    }

    public ArrayList<Book> getBorrowedBooks(){
        return borrowedBooks;
    }

    public ArrayList<Book> getReservedBooks(){
        return reservedBooks;
    }

    //set fines
    public void setFines(double amount){
        fines = amount;
    }

    public static ArrayList<String> getAllUsers(){
        return allUsernames;
    }

    public static ArrayList<String> getAllPasswords(){
        return allPasswords;
    }
}