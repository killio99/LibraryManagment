public class libraryTest {
    public static void main(String[] args) {

        //Transaction t = new Transaction("testuser1", "Book A", "return");
        //t.saveToDB();
        //System.out.println(Transaction.listTransactions());

        Book book = new Book(1, "Author A", "Book A", 5);
        book.saveNewToDB();

        //This code works
        /*
        User testuser = new User("testuser1", "password123");
        testuser.saveNewToDB();
        testuser.setFines(10.0);
        testuser.updateFinesInDB();

        testuser.payFines(5);
        testuser.updateFinesInDB();
        testuser.removeUserInDB();
        */

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
