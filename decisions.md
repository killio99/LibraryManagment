# LibraryManagment

To do

Create reserved books feature
make the ISBN a string
Create HomeLibrary variable and in DB 

# Library class 
Variables
private static final int MAX_CHECKOUTS

Methods
public static int getMaxCheckouts()
public String getAllBooks()

public void addUser(User user) saves to database
public void RemoveUser(User user) saves to database
public void addBook(Book book) saves to database
public void RemoveBook(Book book) saves to database, removes all copies

findBook(ISBN) returns book object for all
findBook(author)
findBook(title)


# User class
Variables
private String username;
private String password;
private double fines;
list of borrowed books
list of reserved books



Methods
2 Constrcutors, 1 with fines set to 0 for a new user
- getters and setters for each variable
public void saveToDB()
public static ArrayList<User> getAllUsersFromDB()
public boolean checkout(Book b)
public void returnBook(Book b)
public void reserveBook(Book b), add user to the books list, add book to the users reserve list
public void payFines(double amount)


# Book class

Variables
ISBN Number
String title
String author
int numCopies
String Home library
Date return Date
List of users that have reserved the book

Methods
getters and setters for variables
public boolean isAvailable: Returns if there is a book available given the numCopies


# Transaction class

Variables
User
Book
Date