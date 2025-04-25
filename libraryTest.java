public class libraryTest {
    public static void main(String[] args) {

        
        

        


        //This code works
        
        //LOGGING IN LOGIC
        //fix that it calls the construcor and saves to db when assigning
        User testuser = User.getUserFromDB("tester");

        //ASSINGING BOOK TO AN OBJECT
        //Book book1 = Book.getBookFromDB("Book A");
        Book book1 = Book.getBookFromDB("Book A");

        testuser.returnBook(book1);


        }   
}
