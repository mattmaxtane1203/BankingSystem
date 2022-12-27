
import java.util.Random;
import java.util.Scanner;

public class CreateAccount {

    private static Scanner scan = new Scanner(System.in);
    private static Random random = new Random();

    public CreateAccount(){

        // Clear screen and print types of accounts
        Utility.clearScreen();
        Utility.printAccountTypes();

        // Ask user to input choice of account; Validate;
        String accountTypeChoice = "";
        boolean choiceIsValid = false;
        do{
            System.out.println("|| Choices are case sensitive ||");
            System.out.print("> ");
            accountTypeChoice = scan.nextLine();
            choiceIsValid = accountTypeChoice.equals("Deposit Account") || accountTypeChoice.equals("Savings Account") || accountTypeChoice.equals("Credit Account");

            if(choiceIsValid == false){
                System.out.print("Invalid choice. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
                Utility.printAccountTypes();
            }
        }while(choiceIsValid == false);
        
        // Ask user to input username and password; Validate;
        Utility.clearScreen();
        String tempPIN = "";
        boolean passwordIsValid = false;
        do{
            System.out.println("Account Type: " + accountTypeChoice);
            System.out.print("Input PIN [numeric, 6 numbers]: ");
            tempPIN = scan.nextLine();
            
            passwordIsValid = Utility.validatePIN(tempPIN);
            
            if(passwordIsValid == false){
                System.out.println("Password does not fit conditions. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(passwordIsValid == false);

        // Generate random account number
        String tempAccountNumber = "";
        boolean accountNumberIsValid = false;
        do{
            Integer randomizedNumber = -1;

            for(int numberNum = 1; numberNum <= 11; numberNum++){
                randomizedNumber = random.nextInt(10);

                if(numberNum == 1){
                    tempAccountNumber = randomizedNumber.toString();
                }

                tempAccountNumber = tempAccountNumber + randomizedNumber.toString();
            }
            
            // Check if account number is unique
            accountNumberIsValid = Utility.validateAccountNumber(tempAccountNumber);
        }while(accountNumberIsValid == false);

        // Review information that was inputted
        Utility.clearScreen();
        String confirmation = "-";
        boolean confirmationIsValid = false;
        do{
            System.out.println("Account Type: " + accountTypeChoice);
            System.out.println("Password: " + tempPIN);
            System.out.println("Account Number: " + tempAccountNumber);
            System.out.println("Are you sure you want to create a new bank account? [Yes / No]");
            System.out.print("> ");
            confirmation = scan.nextLine();
            confirmationIsValid = confirmation.equals("Yes") || confirmation.equals("No");

            if(confirmationIsValid == false){
                System.out.print("Please type either [Yes] or [No]. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(confirmationIsValid == false);

        if(confirmation.equals("Yes")){
            Account newAccount = null;

            switch(accountTypeChoice){
                case "Deposit Account":
                    newAccount = new DepositAccount(Main.currentUser, tempAccountNumber, tempPIN, 0);
                    break;
                case "Savings Account":
                    newAccount = new SavingsAccount(Main.currentUser, tempAccountNumber, confirmation, tempPIN);
                    break;
                case "Credit Account":
                    newAccount = new CreditAccount(Main.currentUser, "Credit", tempAccountNumber, tempPIN);
                    break;
            }

            // Add accounts to user's owned accounts and the bank's account list
            Main.currentUser.addAccount(newAccount);
            Main.Bank.add(newAccount);

            System.out.print(accountTypeChoice + " successfully created! ");
            Utility.promptEnterKey();
            Main.currentUser.ownerMainMenu();
        }

        new CreateAccount();
    }
    
}
