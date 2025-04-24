public class libraryTest {
    public static void main(String[] args) {

        
        

        


        //This code works
        
        //LOGGING IN LOGIC
        //fix that it calls the construcor and saves to db when assigning
        User testuser = User.getUserFromDB("testuser");

        //ASSINGING BOOK TO AN OBJECT
        //beware, this increments the copies available in the db
        //Book book1 = Book.getBookFromDB("Book A");
        Book book2 = new Book(45, "Author A", "Book A", 1);

        //Book a = new Book(45, "Author A", "Book A", 1);
        

        //testuser.checkout(a);
        //testuser.getBorrowedBooks();
        

        //This code works
        /*
        Book book1 = new Book(1, "Author A", "Book A", 1);
        book1.saveNewToDB();
        book1.setAvailable(5);
        book1.updateCopiesInDB();
        book1.removeFromDB();
        */


        
        


        /* 
        Library lib = new Library();

        User user = new User("john", "pass");
        lib.addUser(user);
        Book book = new Book(999, "Tester", "Test Book", 3);
        lib.addBook(book);

        System.out.println("Checking out book...");
        boolean result = user.checkout(book);
        System.out.println("Success? " + result);

        System.out.println("Books now: " + book.getCopiesAvailable());

        System.out.println("Returning book...");
        user.returnBook(book);
        System.out.println("Returned. Available: " + book.getCopiesAvailable());
        */
        }   
}
