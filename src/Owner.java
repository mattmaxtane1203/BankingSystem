import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

public class Owner {

    private static Scanner scan = new Scanner(System.in);

    private String firstName;
    private String surname;
    private String username;
    private Date birthDate;
    private String phoneNumber;
    private String password;
    private String email;
    private String address;
    private ArrayList<Account> ownedAccounts = new ArrayList<Account>();

    public Owner(String firstName, String surname, String username, Date birthDate, String phoneNumber, String password, String email, String address) {
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addAccount(Account account){
        ownedAccounts.add(account);
    }

    public void printOwnedAccounts(){

        // Print header
        for(int j = 0; j < 49; j++){
            System.out.print("=");
        }
        System.out.println();
        System.out.printf("|%-15s|%-15s|%-15s|\n", "Account Number", "Account Type", "Current Balance");
        for(int j = 0; j < 49; j++){
            System.out.print("=");
        }
        System.out.println();

        // Print accounts
        for(int i = 0; i < ownedAccounts.size(); i++){
            System.out.printf("|%-15s|%-15s|Rp. %-11.2f|\n", Main.currentUser.ownedAccounts.get(i).getAccountNumber(), Main.currentUser.ownedAccounts.get(i).getType(), Main.currentUser.ownedAccounts.get(i).getBalance());
        }
        for(int j = 0; j < 49; j++){
            System.out.print("=");
        }
        System.out.println();
    }

    // Menu
    public void ownerMainMenu(){
        Utility.clearScreen();
        String choice = "";
        boolean choiceIsValid = false;
        do{
            System.out.println("Welcome " + this.firstName + "!");
            System.out.println("==========================");
            Utility.printAccountMenu();
            System.out.print("> ");
            choice = scan.nextLine();
            choiceIsValid = choice.equals("Open New Account") || choice.equals("View Accounts") || choice.equals("Log Out");

            if(choiceIsValid == false){
                System.out.print("Invalid choice. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(choiceIsValid == false);

        switch (choice) {
            case "Open New Account":
                new CreateAccount();
                break;
            case "View Accounts":
                ownedAccountsMenu();
                break;
            case "Log Out":
                // Main.currentUser = null;
                new Main();
                break;
        }
    }

    public void ownedAccountsMenu(){
        Utility.clearScreen();

        // If the user does not have an account, ask if they want to create one
        if(Main.currentUser.ownedAccounts.isEmpty()){
            String confirmation = "";
            boolean confirmationIsValid = false;
            do{
                System.out.println("You do not have a bank account with us yet! Would you like to make one? [Yes / No]");
                System.out.print("> ");
                confirmation = scan.nextLine();
                confirmationIsValid = confirmation.equals("Yes") || confirmation.equals("No");

                if(confirmationIsValid == false){
                    System.out.print("Please type either [Yes] or [No]. Try again.");
                    Utility.promptEnterKey();
                    Utility.clearScreen();
                }
            }while(confirmationIsValid == false);

            if(confirmation.equals("Yes")){
                new CreateAccount();
            }
            
            ownerMainMenu();
        }
        
        // Ask to access account
        String accountNumberToAccess;
        int accountIndex = -1;
        do{
            printOwnedAccounts();
            System.out.print("Account number to access [Input 0 to go back to menu]: ");
            accountNumberToAccess = scan.nextLine();
            accountIndex = Utility.accountNumberExists(accountNumberToAccess, ownedAccounts);

            if(accountNumberToAccess.equals("0")){
                ownerMainMenu();
            }

            if(accountIndex == -1){
                System.out.print("Account does not exist. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(accountIndex == -1);

        // Ask to enter PIN
        Utility.clearScreen();
        Account accountToAccess = ownedAccounts.get(accountIndex);
        String PIN = null;
        do{
            printOwnedAccounts();
            System.out.println("Account number to access: " + accountNumberToAccess);
            System.out.print("PIN: ");
            PIN = scan.nextLine();

            if(PIN.equals("0")){
                ownerMainMenu();
            }

            if(!PIN.equals(accountToAccess.getPIN())){
                System.out.println("Incorrect PIN. Please try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(!PIN.equals(accountToAccess.getPIN()));

        // Redirect
        accountToAccess.accountMainMenu();
    }
}
