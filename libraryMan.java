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
            System.out.println("1. View user transactions");
            System.out.println("2. Pay user fines");
            System.out.println("3. Add new book to system");
            int choice = scanner.nextInt();

            
        }
        else{
            System.out.println("1. Checkout book");
            System.out.println("2: Return book");
            System.out.println("3. Reserve book");
            //viewing will show due dates too. add red color for overdue?
            System.out.println("4. View checkouts");
            System.out.println("5. Lookup book details");
            System.out.println("6. Logout");
            int choice = scanner.nextInt();

            
            switch (choice){
                case 1:
                    System.out.println("Enter book title to checkout: ");
                    scanner.nextLine();
                    String bookTitle = scanner.nextLine();
                    Book book = Book.getBookFromDB(bookTitle);
                    if (book == null){
                        System.out.println("Book not found in system.");
                        break;
                    }
                    curUser.checkout(book);
                    System.out.println("You have checked out " + book.getTitle() + " by " + book.getAuthor());
                    //checkout book
                case 2:
                    //return book
                    
                case 3:
                    //reserve book
                    break;
                case 4:
                    //view checkouts
                    User.
                    break;
                case 5:
                    //lookup book details
                    break;
                
                
        }
        
        scanner.close();
    }
}
}