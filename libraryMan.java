import java.util.Scanner;

public class libraryMan{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        Library messiahLibrary = new Library();
        User curUser;

        boolean logInLoop = true;
        String adminPassword = "admin";
        boolean adminLoggedIn = false;

        while (logInLoop){
            System.out.println();
            System.out.println("Welcome to the Library");

            System.out.println("Would you like to create a user (Y/N)?");
            String ynCreate = scanner.nextLine();
            if (ynCreate.equals("Y")){
                //create user
                //add to database
                //login in user
                //continue code logged in
            }
            

            System.out.print("Login: ");
            //ask to login or create a profile
            String userLogin = scanner.nextLine();
            System.out.print("Password: ");
            String userPass = scanner.nextLine();

            if (User.getAllUsers().indexOf(userLogin) != -1){
                if (User.getAllPasswords().indexOf(userPass) != -1){
                    //this user exists
                    //login in the user somehow
                    //curUser = 
                    logInLoop = false;
                }
                else{
                    System.out.println("Incorrect Password");
                }
            }

            else if(userLogin.toLowerCase().equals("admin"));
                //library admin
                if (userPass.equals(adminPassword)){
                    adminLoggedIn = true;
                    logInLoop = false;
                }
                
            else{
            
                System.out.println("No User found");
            }
        }
        //continues here
        System.out.println();
        //User controls
        if (!adminLoggedIn){
            //System.out.println("Welcome " + curUser.getUsername());
            System.out.println("1. Checkout book");
            System.out.println("2: Return book");
            System.out.println("3. Reserve book");
            //viewing will show due dates too. add red color for overdue?
            System.out.println("4. View checkouts");
            System.out.println("5. Lookup book details");
            int choice = scanner.nextInt();

        }
        else{
            System.out.println("Welcome admin");
            //would take in a user for some
            System.out.println("1. View user transactions");
            System.out.println("2. Pay user fines");
            int choice = scanner.nextInt();

            
        
        //admin controls
        }
    }
}