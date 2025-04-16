public class libraryTest {
    public static void main(String[] args) {
        Library lib = new Library();

        User user = new User("john", "pass");
        lib.addUser(user);
        Book book = new Book(999, "Tester", "Test Book", 1);
        lib.addBook(book);

        System.out.println("Checking out book...");
        boolean result = user.checkout(book);
        System.out.println("Success? " + result);

        System.out.println("Returning book...");
        user.returnBook(book);
        System.out.println("Returned. Available: " + book.getCopiesAvailable());
    }   
}
