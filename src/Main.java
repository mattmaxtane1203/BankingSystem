// TODO: Fix the navigation of menus (change from typing full words to typing numbers only)
// TODO: Fix date and time format for birth date of users
// TODO: Fix table positioning when printing transaction history
// TODO: Work on Admin
// TODO: Work on writing all data to files
// TODO: Work on Multithreading to display date and time

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	// Imports
	public static Scanner scan = new Scanner(System.in);
	public static Random random = new Random();

	// Bank-related
	public static ArrayList<Account> Bank = new ArrayList<Account>();
	public static ArrayList<Owner> accountOwners = new ArrayList<Owner>();
    public static ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>();
	public static Owner currentUser = null;
	public static Account currentAccount = null;
	
    // Main Menu
	public Main() {
		clearScreen();
		
//		Print main menu
		printMainMenu();
		
//		Input choice
		int choice = 0;
		do {
			System.out.print("> ");
			choice = scan.nextInt(); scan.nextLine();
		}while(choice < 1 || choice > 3);
		
//		Switch choice
		switch(choice) {
		case 1:
			LoginMenu();
			break;
		case 2:
			CreateOwnerAccount();
			break;
		case 3:
			System.exit(0);
			break;
		}
	}
	
	// User-related menus
	public static void LoginMenu(){
		clearScreen();

		// Check if there are bank accounts in the list
		if(Main.accountOwners.isEmpty()){
			String confirmation = "-";
			boolean confirmationIsValid = false;
			do{
				System.out.println("No accounts exist in this bank. Would you like to create an account? [Yes / No]");
				System.out.print("> ");
				confirmation = scan.nextLine();
				confirmationIsValid = confirmation.equals("Yes") || confirmation.equals("No");

				if(confirmationIsValid == false){
					System.out.print("Please type either [Yes] or [No]. ");
					promptEnterKey();
					clearScreen();
				}
			}while(confirmationIsValid == false);

			if(confirmation.equals("Yes")){
				CreateOwnerAccount();
			}

			new Main();
		}
		
		// Enter username
		String username;
		int accountIndex = -1;
		do{
			System.out.println("=====");
			System.out.println("Login");
			System.out.println("=====");
			System.out.print("Username: ");
			username = scan.nextLine();
			accountIndex = usernameExists(username);

			if(accountIndex == -1){
				System.out.print("Username does not exist. Try again. ");
				promptEnterKey();
				clearScreen();
			}
		}while(accountIndex == -1);

		// Owner in question
		Owner currentOwner = accountOwners.get(accountIndex);

		// Enter password
		clearScreen();
		String password;
		do{
			System.out.println("=====");
			System.out.println("Login");
			System.out.println("=====");
			System.out.println("Username: " + username);
			System.out.print("Password: ");
			password = scan.nextLine();

			if(password.equals("0")){
				new Main();
			}
			
			if(!password.equals(currentOwner.getPassword())){
				System.out.print("Wrong password. Please try again. ");
				promptEnterKey();
				clearScreen();
			}
		}while(!password.equals(currentOwner.getPassword()));

		// Redirect to account page
		currentUser = currentOwner;
		OwnerMainMenu();
	}

	public static void CreateOwnerAccount(){
        clearScreen();

        // Ask user to input first and surname
        String tempFirstName = "";
        String tempSurname = "";
        boolean namesAreValid = false;
        do{
            System.out.print("Input first and last name: ");
            String tempName = scan.nextLine();

            String[] names = tempName.split("\\s");
            tempFirstName = names[0];
            tempSurname = names[1];

            namesAreValid = true;
        }while(namesAreValid == false);

        // Ask user to input username; Validate
        clearScreen();
        String tempUsername = "";
        boolean usernameIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.print("Input Username [must be unique]: ");
            tempUsername = scan.nextLine();
            usernameIsValid = validateUsername(tempUsername);
            if(usernameIsValid == false){
                System.out.print("Username entered already exists. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(usernameIsValid == false);

        // Ask user to input birth date; Validate
        clearScreen();
        Date tempBirthDate = new Date();
        String tempBirthDateString;
        boolean birthDateIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.print("Input Birth Date [dd/MM/yyyy]: ");
            tempBirthDateString = scan.nextLine();

            try {
                tempBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(tempBirthDateString);
                birthDateIsValid = true;
            } catch (ParseException e) {
                System.err.print("Invalid date/date format");
                e.printStackTrace();
            }
            
        } while(birthDateIsValid == false);

        // Ask user to input phone number
        clearScreen();
        String tempPhoneNumber = "";
        boolean phoneNumberIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.print("Input phone number [unique and starts with 08...]: ");
            tempPhoneNumber = scan.nextLine();
            phoneNumberIsValid = validatePhoneNumber(tempPhoneNumber);

            if(phoneNumberIsValid == false){
                System.out.print("An account with this phone number already exists or the phone number is in the wrong format. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(phoneNumberIsValid == false);

        // Ask user to input password; Validate;
        clearScreen();
        String tempPassword = "";
        boolean passwordIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Input Password [8 - 20 characters | No whitespaces | At least one special character | At least one uppercase and lowercase letter]: ");
            tempPassword = scan.nextLine();
            passwordIsValid = validatePassword(tempPassword);
            
            if(passwordIsValid == false){
                System.out.println("Password does not fit conditions. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(passwordIsValid == false);

        // Ask user to input email
        clearScreen();
        String tempEmail;
        boolean emailIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Password: " + tempPassword);
            System.out.print("Input email [unique]: ");
            tempEmail = scan.nextLine();
            emailIsValid = validateEmail(tempEmail);
            System.out.println(emailIsValid);

            if(emailIsValid == false){
                System.out.print("Email already exists or email is in the wrong format. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(emailIsValid == false);

        // Ask user to input address
        clearScreen();
        String tempAddress;
        boolean addressIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Password: " + tempPassword);
            System.out.println("Email: " + tempEmail);
            System.out.print("Input address: ");
            tempAddress = scan.nextLine();
            addressIsValid = validateAddress(tempAddress);

            if(addressIsValid == false){
                System.out.print("Address is not in the correct format. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(addressIsValid == false);

        // Confirmation
        clearScreen();
        String confirmation = "-";
        boolean confirmationIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Password: " + tempPassword);
            System.out.println("Email: " + tempEmail);
            System.out.println("Address: " + tempAddress);
            System.out.println("Are you sure you want to create a new account? [Yes / No]");
            System.out.print("> ");
            confirmation = scan.nextLine();
            confirmationIsValid = confirmation.equals("Yes") || confirmation.equals("No");

            if(confirmationIsValid == false){
                System.out.print("Please type either [Yes] or [No]. Try again.");
                promptEnterKey();
                clearScreen();
            }
        }while(confirmationIsValid == false);

        if(confirmation.equals("Yes")){
            currentUser = new Owner(tempFirstName, tempSurname, tempUsername, tempBirthDate, tempPhoneNumber, tempPassword, tempEmail, tempAddress);
            accountOwners.add(currentUser);
            System.out.print("Account created! ");
            promptEnterKey();
            OwnerMainMenu();
        }

        CreateOwnerAccount();
    }

	public static void OwnerMainMenu() {
		clearScreen();
        String choice = "";
        boolean choiceIsValid = false;
        do{
            System.out.println("Welcome " + currentUser.getFirstName() + "!");
            System.out.println("==========================");
            printAccountMenu();
            System.out.print("> ");
			choice = scan.nextLine();
            choiceIsValid = choice.equals("Open New Account") || choice.equals("View Accounts") || choice.equals("View Past Transactions") || choice.equals("Log Out");

            if(choiceIsValid == false){
                System.out.print("Invalid choice. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(choiceIsValid == false);

        switch (choice) {
            case "Open New Account":
                CreateAccount();
                break;
            case "View Accounts":
                ownedAccountsMenu();
                break;
            case "View Past Transactions":
                pastTransactionsMenuUser();
                break;
            case "Log Out":
                Main.currentUser = null;
                new Main();
                break;
        }
	}
	
	public static void ownedAccountsMenu(){
        clearScreen();

        // If the user does not have an account, ask if they want to create one
        if(currentUser.getOwnedAccounts().isEmpty()){
            String confirmation = "";
            boolean confirmationIsValid = false;
            do{
                System.out.println("You do not have a bank account with us yet! Would you like to make one? [Yes / No]");
                System.out.print("> ");
                confirmation = scan.nextLine();
                confirmationIsValid = confirmation.equals("Yes") || confirmation.equals("No");

                if(confirmationIsValid == false){
                    System.out.print("Please type either [Yes] or [No]. Try again.");
                    promptEnterKey();
                    clearScreen();
                }
            }while(confirmationIsValid == false);

            if(confirmation.equals("Yes")){
                CreateAccount();
            }
            
            OwnerMainMenu();
        }
        
     // Ask to access account
        String accountNumberToAccess;
        int accountIndex = -1;
        do{
            currentUser.printOwnedAccounts();
            System.out.print("Account number to access [Input 0 to go back to menu]: ");
            accountNumberToAccess = scan.nextLine();
            accountIndex = accountNumberExists(accountNumberToAccess, currentUser.getOwnedAccounts());

            if(accountNumberToAccess.equals("0")){
                OwnerMainMenu();
            }

            if(accountIndex == -1){
                System.out.print("Account does not exist. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(accountIndex == -1);

        // Ask to enter PIN
        clearScreen();
        Account accountToAccess = currentUser.getOwnedAccounts().get(accountIndex);
        String PIN = null;
        do{
            currentUser.printOwnedAccounts();
            System.out.println("Account number to access: " + accountNumberToAccess);
            System.out.print("PIN: ");
            PIN = scan.nextLine();

            if(PIN.equals("0")){
                OwnerMainMenu();
            }

            if(!PIN.equals(accountToAccess.getPIN())){
                System.out.println("Incorrect PIN. Please try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(!PIN.equals(accountToAccess.getPIN()));

        // Redirect
		currentAccount = accountToAccess;
        AccountMainMenu();
	}

    public static void pastTransactionsMenuUser(){
        clearScreen();

        if(currentUser.getTransactionHistory().isEmpty()){
            System.out.println("No transactions have been made yet. ");
            promptEnterKey();
            OwnerMainMenu();
        }

        currentUser.printTransactionHistory();
        promptEnterKey();
        OwnerMainMenu();
    }

	// Account-related menus
	public static void CreateAccount(){

        // Clear screen and print types of accounts
        clearScreen();
        printAccountTypes();

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
                promptEnterKey();
                clearScreen();
                printAccountTypes();
            }
        }while(choiceIsValid == false);
        
        // Ask user to input username and password; Validate;
        clearScreen();
        String tempPIN = "";
        boolean passwordIsValid = false;
        do{
            System.out.println("Account Type: " + accountTypeChoice);
            System.out.print("Input PIN [numeric, 6 numbers]: ");
            tempPIN = scan.nextLine();
            passwordIsValid = validatePIN(tempPIN);
            
            if(passwordIsValid == false){
                System.out.println("Password does not fit conditions. Try again. ");
                promptEnterKey();
                clearScreen();
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
            accountNumberIsValid = validateAccountNumber(tempAccountNumber);
        }while(accountNumberIsValid == false);

        // Review information that was inputted
        clearScreen();
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
                promptEnterKey();
                clearScreen();
            }
        }while(confirmationIsValid == false);

        if(confirmation.equals("Yes")){
            Account newAccount = null;

            switch(accountTypeChoice){
                case "Deposit Account":
                    newAccount = new DepositAccount(currentUser, tempAccountNumber, tempPIN, 0);
                    break;
                case "Savings Account":
                    newAccount = new SavingsAccount(currentUser, tempAccountNumber, confirmation, tempPIN);
                    break;
                case "Credit Account":
                    newAccount = new CreditAccount(currentUser, "Credit", tempAccountNumber, tempPIN);
                    break;
            }

            // Add accounts to user's owned accounts and the bank's account list
            currentUser.addAccount(newAccount);
            Bank.add(newAccount);

            System.out.print(accountTypeChoice + " successfully created! ");
            promptEnterKey();
            OwnerMainMenu();
        }

        CreateAccount();
    }

	public static void AccountMainMenu(){
        clearScreen();

        String choice;
        boolean choiceIsValid = false;
        do{
			System.out.println("Current Account: " + currentAccount.getAccountNumber());
            System.out.println("What would you like to do?");
            System.out.println("==========================");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View Past Transactions");
            System.out.println("5. Log Out");
            System.out.print("> ");
            choice = scan.nextLine();
            choiceIsValid = choice.equals("Deposit") || choice.equals("Withdraw") || choice.equals("Transfer") || choice.equals("View Past Transactions") || choice.equals("Log Out");

            if(choiceIsValid == false){
                System.out.print("Invalid choice. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(choiceIsValid == false);

        switch(choice){
            case "Deposit":
                depositMenu();
                break;
            case "Withdraw":
                withdrawMenu();
                break;
            case "Transfer":
                transferMenu();
                break;
            case "View Past Transactions":
                pastTransactionsMenuAccount();
                break;
            case "Log Out":
				currentAccount = null;
                ownedAccountsMenu();
                break;
        }
    }

    public static void depositMenu(){
        clearScreen();

        double amountToDeposit;
        System.out.print("Amount to deposit: Rp. ");
        amountToDeposit = scan.nextDouble(); scan.nextLine();
        
        clearScreen();
        String PIN = null;
        do{
            System.out.println("Amount to deposit: Rp. " + amountToDeposit);
            System.out.print("PIN: ");
            PIN = scan.nextLine();

            if(PIN.equals("0")){
                OwnerMainMenu();
            }

            if(!PIN.equals(currentAccount.getPIN())){
                System.out.println("Incorrect PIN. Please try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(!PIN.equals(currentAccount.getPIN()));

        // Generate transaction details
        Transaction newTransaction = generateTransaction("Deposit", null, amountToDeposit);

        currentUser.addTransaction(newTransaction);
        currentAccount.addTransaction(newTransaction);
        transactionHistory.add(newTransaction);

        currentAccount.addBalance(amountToDeposit);
        System.out.println("Funds deposited successfully!");
        System.out.println("Transaction ID: " + newTransaction.getTransactionID());
        System.out.println("Amount deposited: " + amountToDeposit);
        System.out.println("Current Balance: " + currentAccount.getBalance());
        promptEnterKey();
        AccountMainMenu();
    }

    public static void withdrawMenu(){
        clearScreen();

        double amountToWithdraw;
        do{
            System.out.print("Amount to withdraw: Rp. ");
            amountToWithdraw = scan.nextDouble(); scan.nextLine();

            if(amountToWithdraw > currentAccount.getBalance()){
                System.out.println("Not enough funds in your account. ");
                promptEnterKey();
                clearScreen();
            }
        }while(amountToWithdraw > currentAccount.getBalance());
        currentAccount.withdraw(amountToWithdraw);

        clearScreen();
        String PIN = null;
        do{
            System.out.println("Amount to deposit: Rp. " + amountToWithdraw);
            System.out.print("PIN: ");
            PIN = scan.nextLine();

            if(PIN.equals("0")){
                OwnerMainMenu();
            }

            if(!PIN.equals(currentAccount.getPIN())){
                System.out.println("Incorrect PIN. Please try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(!PIN.equals(currentAccount.getPIN()));

        // Generate transaction details
        Transaction newTransaction = generateTransaction("Withdraw", null, amountToWithdraw);

        currentUser.addTransaction(newTransaction);
        currentAccount.addTransaction(newTransaction);
        transactionHistory.add(newTransaction);
        
        clearScreen();
        System.out.println("Funds withdrawn successfully!");
        System.out.println("Transaction ID: " + newTransaction.getTransactionID());
        System.out.println("Amount withdrawn: " + amountToWithdraw);
        System.out.println("Current Balance: " + currentAccount.getBalance());
        promptEnterKey();
        AccountMainMenu();
    }

    public static void transferMenu(){
        clearScreen();
        String destinationAccountNumber;
        int destinationAccountNumberIndex = -1;
        do{
            System.out.print("Destination Account Number: ");
            destinationAccountNumber = scan.nextLine();
            destinationAccountNumberIndex = accountNumberExists(destinationAccountNumber, Bank);

            if(destinationAccountNumberIndex == -1){
                System.out.print("Account number does not exist. Please try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(destinationAccountNumberIndex == -1);

        clearScreen();
        double amountToTransfer;
        do{
            System.out.println("Destination Account Number: " + destinationAccountNumber);
            System.out.print("Amount to deposit: Rp. ");
            amountToTransfer = scan.nextDouble(); scan.nextLine();

            if(amountToTransfer > currentAccount.getBalance()){
                System.out.println("Not enough funds in your account. ");
                promptEnterKey();
                clearScreen();
            }
        }while(amountToTransfer > currentAccount.getBalance());
        currentAccount.withdraw(amountToTransfer);
        Bank.get(destinationAccountNumberIndex).addBalance(amountToTransfer);

        clearScreen();
        String PIN = null;
        do{
            System.out.println("Destination Account Number: " + destinationAccountNumber);
            System.out.println("Amount to deposit: Rp. " + amountToTransfer);
            System.out.print("PIN: ");
            PIN = scan.nextLine();

            if(PIN.equals("0")){
                OwnerMainMenu();
            }

            if(!PIN.equals(currentAccount.getPIN())){
                System.out.println("Incorrect PIN. Please try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(!PIN.equals(currentAccount.getPIN()));

        // Generate transaction details
        Transaction newTransaction = generateTransaction("Transfer", destinationAccountNumber, amountToTransfer);

        currentUser.addTransaction(newTransaction);
        currentAccount.addTransaction(newTransaction);
        transactionHistory.add(newTransaction);
        
        clearScreen();
        System.out.println("Funds transferred successfully!");
        System.out.println("Transaction ID: " + newTransaction.getTransactionID());
        System.out.println("Current Balance: " + currentAccount.getBalance());
        promptEnterKey();
        AccountMainMenu();
    }

    public static void pastTransactionsMenuAccount(){
        clearScreen();

        if(currentAccount.getTransactionHistory().isEmpty()){
            System.out.println("No transactions have been made yet. ");
            promptEnterKey();
            OwnerMainMenu();
        }

        currentAccount.printTransactionHistory();
        promptEnterKey();
        AccountMainMenu();
    }

	// Utilities
	public static void printMainMenu() {
		System.out.println("==========");
		System.out.println("SUNIB Bank");
		System.out.println("==========");
		System.out.println("1. Login");
		System.out.println("2. Open New Account");
		System.out.println("3. Exit");
	}

	public static void printAccountTypes(){
		System.out.println("===================");
		System.out.println("Choose Account Type");
		System.out.println("===================");
		System.out.println("1. Deposit Account");
		System.out.println("2. Savings Account");
		System.out.println("3. Credit Account");
	}

	public static void printAccountMenu(){
		System.out.println("1. Open New Account");
		System.out.println("2. View Accounts");
        System.out.println("3. View Past Transactions");
		System.out.println("4. Log Out");
	}

	// Validation
	public static boolean validateUsername(String tempUsername) {
		
		// If username is empty, return false
		if(tempUsername == null || tempUsername.isEmpty()) {
			return false;
		}

		// Search accounts if there are duplicate usernames
		for(int i = 0; i < Main.Bank.size(); i++){
			if(Main.Bank.get(i).getOwner().getUsername().equals(tempUsername)){
				return false;
			}
		}
		
		return true;
	}

	public static int usernameExists(String username){
		for(int i = 0; i < Main.accountOwners.size(); i++){
			if(Main.accountOwners.get(i).getUsername().equals(username)){
				return i;
			}
		}

		return -1;
	}

	public static boolean validatePassword(String tempPassword){

		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		Pattern pattern = Pattern.compile(regex);

		if(tempPassword == null || tempPassword.isEmpty()){
			return false;
		}

		Matcher matcher = pattern.matcher(tempPassword);

		return matcher.matches();
	}

	public static boolean validateAccountNumber(String tempAccountNumber){

		for(int i = 0; i < Main.Bank.size(); i++){
			if(Main.Bank.get(i).getAccountNumber().equals(tempAccountNumber)){
				return false;
			}
		}

		return true;
	}

	public static int accountNumberExists(String accountNumber, ArrayList<Account> accounts){

		for(int i = 0; i < accounts.size(); i++){
			if(accounts.get(i).getAccountNumber().equals(accountNumber)){
				return i;
			}
		}

		return -1;
	}

	public static boolean validateEmail(String tempEmail){

		for(int i = 0; i < Main.Bank.size(); i++){
			if(Main.Bank.get(i).getOwner().getEmail().equals(tempEmail)){
				return false;
			}
		}
		
    	String regexPattern = "^(.+)@(\\S+)$";
		Pattern pattern = Pattern.compile(regexPattern);

		if(tempEmail == null || tempEmail.isEmpty()){
			return false;
		}

		Matcher matcher = pattern.matcher(regexPattern);
    	
		return matcher.matches();
	}

	public static boolean validatePhoneNumber(String tempPhoneNumber){

		if(tempPhoneNumber.length() > 13 || tempPhoneNumber.length() < 10){
			return false;
		}

		if(tempPhoneNumber.charAt(0) != '0' || tempPhoneNumber.charAt(1) != '8'){
			return false;
		}

		// Check uniqueness
		for(int i = 0; i < Main.Bank.size(); i++){
			if(Main.Bank.get(i).getOwner().getPhoneNumber().equals(tempPhoneNumber)){
				return false;
			}
		}

		return true;
	}

	public static boolean validateAddress(String tempAddress){

		if(tempAddress.charAt(0) != 'J' || tempAddress.charAt(1) != 'l' || tempAddress.charAt(2) != '.' || tempAddress.charAt(3) != ' ' || tempAddress.charAt(4) == ' '){
			return false;
		}

		return true;
	}

	public static boolean validatePIN(String tempPIN){
		if(tempPIN.length() != 6){
			return false;
		}

		for(int i = 0; i < tempPIN.length(); i++){
			if(tempPIN.charAt(i) < 48 || tempPIN.charAt(i) > 57){
				return false;
			}
		}

		return true;
	}

    public static boolean validateTransactionID(String tempTransactionID){

        for(int i = 0; i < transactionHistory.size(); i++){
            if(transactionHistory.get(i).getTransactionID().equals(tempTransactionID)){
                return false;
            }
        }

        return true;
    }

    public static Transaction generateTransaction(String transactionType, String destinationAccountNumber, double amountInvolved){
        String transactionID = "";
        boolean transactionIDIsValid = false;
        do{
            Integer randomizedInteger = -1;
            for(int numberNum = 0; numberNum < 16; numberNum++){
                randomizedInteger = random.nextInt(10);

                if(numberNum == 0){
                    transactionID = randomizedInteger.toString();
                }

                transactionID = transactionID + randomizedInteger.toString();
            }

            // Check that transaction ID is unique
            transactionIDIsValid = validateTransactionID(transactionID);
        }while(transactionIDIsValid == false);

        if(transactionType.equals("Deposit")){
            return new Transaction(transactionID, LocalDateTime.now(), transactionType, currentAccount.getAccountNumber(), destinationAccountNumber, amountInvolved, currentAccount.getBalance() + amountInvolved);
        }

        return new Transaction(transactionID, LocalDateTime.now(), transactionType, currentAccount.getAccountNumber(), destinationAccountNumber, amountInvolved, currentAccount.getBalance() - amountInvolved);
    }

	// Other tools
	public static void clearScreen(){
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}

	public static void promptEnterKey(){
		System.out.print("Press Enter Key to continue...");
		scan.nextLine();
	}

	// Driver
	public static void main(String[] args) {
		new Main();
	}
}
