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

                User newUserObj = new User(newUser, newPass);
                newUserObj.saveNewToDB();
            }
            
            System.out.print("Login: ");
            //ask to login or create a profile
            String userLogin = scanner.nextLine();
            System.out.print("Password: ");
            String userPass = scanner.nextLine();

            if (User.containsUserInDB(userLogin)){
                if (User.checkPasswordInDB(userLogin, userPass)){
                    //user exists and password matches
                    curUser = User.getUserFromDB(userLogin);
                    System.out.println("Welcome " + userLogin);
                    logInLoop = false;
                }
                else{
                    System.out.println();
                    System.out.println("Incorrect Password");
                }
                
            }
            else if(userLogin.toLowerCase().equals("admin")){
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
            else{
            
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

                        Book newBook = new Book(isbn, title, author, numCopies);
                        newBook.saveNewToDB();
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
            System.out.println("5. Lookup book details");
            System.out.println("6. Logout");
            System.out.println("Enter you choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice){
                case 1:
                    System.out.println("Enter book title to checkout: ");
                    String bookTitle = scanner.nextLine();
                    Book book = Book.getBookFromDB(bookTitle);
                    if (book == null){
                        System.out.println("Book not found in system.");
                        break;
                    }else{
                        boolean success = curUser.checkout(book);
                        if(success){
                            System.out.println("You have checked out " + book.getTitle() + " by " + book.getAuthor());
                        }
                    }
                    break;
                    //checkout book
                case 2:
                    System.out.println("Enter book title to return: ");
                    String returnBookTitle = scanner.nextLine();
                    Book returnBook = Book.getBookFromDB(returnBookTitle);
                    if (returnBook != null && curUser.borrowedBookTitles.contains(returnBook.getTitle())){
                        curUser.returnBook(returnBook);
                    }else {
                        System.out.println("This book was not borrowed");
                    }
                    break;
                    //return book
                case 3:
                    System.out.println("Enter book title to reserve: ");
                    String reserveBookTitle = scanner.nextLine();
                    Book reserveBook = Book.getBookFromDB(reserveBookTitle);
                    if(reserveBook != null){
                        curUser.reserveBook(reserveBook);
                    }else{
                        System.out.println("Book not found");
                    }
                    break;
                    //reserve book
                case 4:
                    System.out.println("Your borrowed books: ");
                    for(String borrowedBook : curUser.borrowedBookTitles){
                        System.out.println(borrowedBook);
                    }
                    break;
                    //view checkouts
                case 5:
                    System.out.println("Enter book title to lookup: ");
                    String lookupTitle = scanner.nextLine();
                    Book lookupBook = Book.getBookFromDB(lookupTitle);
                    if(lookupBook != null){
                        System.out.println("Book details: ");
                        System.out.println("Title: " + lookupBook.getTitle());
                        System.out.println("Author: " + lookupBook.getAuthor());
                        System.out.println("Available copies: " + lookupBook.getCopiesAvailable());
                    }else {
                        System.out.println("Book not found");
                    }
                    break;
                    //lookup book details
                case 6:
                    System.out.println("Logging out...");
                    userMenuLoop = false;
                    break;
                
                default:
                    System.out.println("Invalid choice");
                    break;      
        }
        }
    }
    scanner.close();
}
}