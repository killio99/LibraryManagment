import java.util.Scanner;

public class libraryMan{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        Library messiahLibrary = new Library();
        User curUser = null;
        
        boolean logInLoop = true;
        String adminPassword = "admin";
        boolean adminLoggedIn = false;

        System.out.println("Welcome to the Library");
        while (logInLoop){
            System.out.println();
            

            System.out.println("Would you like to create a user (Y/N)?");
            String ynCreate = scanner.nextLine();

            if (ynCreate.equals("Y")){
                System.out.println("Enter a username: ");
                String newUser = scanner.nextLine();
                System.out.println("Enter a password: ");
                String newPass = scanner.nextLine();

                User newUserObj = User.createUser(newUser, newPass);
                //save to DB function prevents duplicate users
            }
            
            System.out.print("Login: ");
            //ask to login or create a profile
            String userLogin = scanner.nextLine();
            System.out.print("Password: ");
            String userPass = scanner.nextLine();

            if(userLogin.toLowerCase().equals("admin")){
                //library admin
                if (userPass.equals(adminPassword)){
                    System.out.println("Welcome Admin");
                    adminLoggedIn = true;
                    logInLoop = false;
                }
                else{
                    System.out.println("Incorrect Admin Password");
                }
            } 
            else if (User.containsUserInDB(userLogin)){
                if (User.checkPasswordInDB(userLogin, userPass)){
                    //user exists and password matches
                    //logging in
                    curUser = User.getUserFromDB(userLogin);
                    System.out.println();
                    System.out.println("Welcome " + userLogin);
                    logInLoop = false;
                }
                else{
                    System.out.println();
                    System.out.println("Incorrect Password");
                }
                
            }
            
            else{
                System.out.println();
                System.out.println("No User found");
                
            }
        }
        //continues here
        System.out.println();
        //User controls
        if (adminLoggedIn){
            System.out.println("Welcome admin");
            //would take in a user for some
            boolean adminMenuLoop = true;
            while(adminMenuLoop){
                System.out.println("1. View user transactions");
                System.out.println("2. Pay user fines");
                System.out.println("3. Add new book to system");
                System.out.println("4. Remove book from system");
                System.out.println("5. Logout");

                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println(Transaction.listTransactions());
                        break;
                    
                    case 2:
                        System.out.println("Enter username to pay fines: ");
                        String userToPay = scanner.nextLine();
                        User userToPayObj = User.getUserFromDB(userToPay);
                        //would be null if user does not exist

                        if (userToPayObj != null){
                            System.out.println("Enter amount to pay");
                            double payment = scanner.nextDouble();
                            userToPayObj.payFines(payment);
                        } else {
                            System.out.println("User not found");
                        }
                        break;
                    
                    case 3:
                        System.out.println("Enter book ISBN: ");
                        int isbn = scanner.nextInt();
                        System.out.println("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.println("Enter book author: ");
                        String author = scanner.nextLine();
                        System.out.println("Enter number of copies: ");
                        int numCopies = scanner.nextInt();

                        Book newBook = Book.createBook(isbn, author, title, numCopies);
                        if (newBook != null){
                            System.out.println("Book added successfully");
                        } else {
                            System.out.println("Book already exists in the system");
                        }
                        break;
                    
                    case 4:
                        System.out.println("Enter ISBN of the book to remove: ");
                        String removeIsbn = scanner.nextLine();
                        Book bookToRemove = Book.getBookFromDB(removeIsbn);
                        if (bookToRemove != null){
                            bookToRemove.removeFromDB();
                            System.out.println("Book removed successfully");
                        }else {
                            System.out.println("Book not found");
                        }
                        break;
                    
                    case 5:
                        System.out.println("Logging out...");
                        adminMenuLoop = false;
                        break;
                    
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
               
            }
        }        
        else{
            boolean userMenuLoop = true;

            while(userMenuLoop){
                System.out.println("1. Checkout book");
                System.out.println("2: Return book");
                System.out.println("3. Reserve book");
                //viewing will show due dates too. add red color for overdue?
                System.out.println("4. View checkouts");
                System.out.println("5. View reserves");
                System.out.println("6. Lookup book details");
                System.out.println("7. Logout");
                System.out.println("Enter you choice:");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice){
                    case 1:
                        System.out.println("Enter book title to checkout: ");
                        String bookTitle = scanner.nextLine();
                        Book book = Book.getBookFromDB(bookTitle);

                        if (book == null){
                            System.out.println();
                            System.out.println("Book not found in system.");
                            System.out.println();
                            break;
                        }else{
                            boolean success = curUser.checkout(book);
                            if(success){
                                System.out.println();
                                System.out.println("You have checked out " + book.getTitle() + " by " + book.getAuthor());
                                System.out.println();
                            }
                        }
                        break;
                        //checkout book
                    case 2:
                        System.out.println("Enter book title to return: ");
                        String returnBookTitle = scanner.nextLine();
                        Book returnBook = Book.getBookFromDB(returnBookTitle);
                        //returnBook confirms the user has it checkout out
                        if (returnBook != null){
                            curUser.returnBook(returnBook);
                        }else {
                            System.out.println();
                            System.out.println("This book was not borrowed");
                            System.out.println();
                        }
                        break;
                        //return book
                        
                    case 3:
                        //reserve book
                        /*
                        System.out.println("Enter book title to reserve: ");
                        String reserveBookTitle = scanner.nextLine();
                        Book reserveBook = Book.getBookFromDB(reserveBookTitle);
                        if(reserveBook != null){
                            curUser.reserveBook(reserveBook);
                        }else{
                            System.out.println("Book not found");
                        }
                        break;
                        */
                        //reserve book
                    case 4:
                        //view checkouts
                        System.out.println();
                        System.out.println("Your borrowed books: ");
                        System.out.println();
                        curUser.viewBorrowedBooks();
                        System.out.println();
                        break;
                        
                    case 5:
                        //view reserves
                        System.out.println();
                        System.out.println("Your reserved books: ");
                        System.out.println();
                        //curUser.viewReservedBooks();
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("Enter book title to lookup: ");
                        String lookupTitle = scanner.nextLine();
                        Book lookupBook = Book.getBookFromDB(lookupTitle);
                        if(lookupBook != null){
                            System.out.println();
                            System.out.println("Book details: ");
                            System.out.println("Title: " + lookupBook.getTitle());
                            System.out.println("Author: " + lookupBook.getAuthor());
                            System.out.println("Available copies: " + lookupBook.getCopiesAvailable());
                            System.out.println();
                        }else {
                            System.out.println();
                            System.out.println("Book not found");
                            System.out.println();
                        }
                        break;
                        //lookup book details
                    case 7:
                        System.out.println();
                        System.out.println("Logging out...");
                        System.out.println();
                        userMenuLoop = false;
                        break;
                    
                    default:
                        System.out.println();
                        System.out.println("Invalid choice");
                        System.out.println();
                        break;      
                }
            }
        }
        scanner.close();
    }
}