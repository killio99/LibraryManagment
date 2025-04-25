public class libraryTest {
    public static void main(String[] args) {

        //User me = User.createUser("Killiano95", "123");

        //Book b = Book.createBook(1, "Killian", "The Book of Killian", 5);

        //returns user not found if user does not exist in DB
        User me = User.getUserFromDB("Killiano95");
        Book t = Book.getBookFromDB("The Book of Killian");
        //me.checkout(t);
        //Book b1 = Book.createBook(25, "Harry Potter", "J.K. Rowling", 5);
        //Book b2 = Book.createBook(31, "The Hobbit", "J.R.R. Tolkien", 3);
        Book b1 = Book.getBookFromDB("Harry Potter");
        Book b2 = Book.getBookFromDB("The Hobbit");
        me.checkout(b1);

        
        

        //me.viewBorrowedBooks();

    }   
}
