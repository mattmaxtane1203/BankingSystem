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
            int choice = -1;
            boolean choiceIsValid = false;
			do{
				System.out.println("No accounts exist in this bank. Would you like to create an account? [1 for YES / 0 for NO]");
				System.out.print("> ");
				choice = scan.nextInt();
                scan.nextLine();
                choiceIsValid = choice == 1 || choice == 0;

				if(!choiceIsValid){
					System.out.print("Please type either 1 for YES or 0 for NO. ");
					promptEnterKey();
					clearScreen();
				}
			}while(choiceIsValid == false);

			if(choice == 1){
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
            System.out.println("Type 0 to go back to main menu.");
			System.out.print("Username: ");
			username = scan.nextLine();
			accountIndex = usernameExists(username);

            if(username.equals("0")){
				new Main();
			}

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
            System.out.println("===================");
            System.out.println("Create User Account");
            System.out.println("===================");
            System.out.println("Type 0 to go back to main menu.");
            System.out.print("Input first and last name: ");
            String tempName = scan.nextLine();

            if(tempName.equals("0")){
				new Main();
			}

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
            System.out.println("===================");
            System.out.println("Create User Account");
            System.out.println("===================");
            System.out.println("Type 0 to go back to main menu.");
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.print("Input Username [must be unique]: ");
            tempUsername = scan.nextLine();

            if(tempUsername.equals("0")){
				new Main();
			}
            
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
            System.out.println("===================");
            System.out.println("Create User Account");
            System.out.println("===================");
            System.out.println("Type 0 to go back to main menu.");
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.print("Input Birth Date [dd/MM/yyyy]: ");
            tempBirthDateString = scan.nextLine();

            if(tempBirthDate.equals("0")){
				new Main();
			}

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
            System.out.println("===================");
            System.out.println("Create User Account");
            System.out.println("===================");
            System.out.println("Type 0 to go back to main menu.");
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.print("Input phone number [unique and starts with 08...]: ");
            tempPhoneNumber = scan.nextLine();

            if(tempPhoneNumber.equals("0")){
				new Main();
			}

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
            System.out.println("===================");
            System.out.println("Create User Account");
            System.out.println("===================");
            System.out.println("Type 0 to go back to main menu.");
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Input Password [8 - 20 characters | No whitespaces | At least one special character | At least one uppercase and lowercase letter]: ");
            tempPassword = scan.nextLine();

            if(tempPassword.equals("0")){
				new Main();
			}

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
            System.out.println("===================");
            System.out.println("Create User Account");
            System.out.println("===================");
            System.out.println("Type 0 to go back to main menu.");
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Password: " + tempPassword);
            System.out.print("Input email [unique]: ");
            tempEmail = scan.nextLine();

            if(tempEmail.equals("0")){
				new Main();
			}

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
            System.out.println("===================");
            System.out.println("Create User Account");
            System.out.println("===================");
            System.out.println("Type 0 to go back to main menu.");
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Password: " + tempPassword);
            System.out.println("Email: " + tempEmail);
            System.out.print("Input address: ");
            tempAddress = scan.nextLine();

            if(tempAddress.equals("0")){
				new Main();
			}

            addressIsValid = validateAddress(tempAddress);

            if(addressIsValid == false){
                System.out.print("Address is not in the correct format. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(addressIsValid == false);

        // Confirmation
        clearScreen();
        int choice = -1;
        boolean choiceIsValid = false;
        do{
            System.out.println("===================");
            System.out.println("Create User Account");
            System.out.println("===================");
            System.out.println("Type 0 to go back to main menu.");
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Password: " + tempPassword);
            System.out.println("Email: " + tempEmail);
            System.out.println("Address: " + tempAddress);
            System.out.println("Are you sure you want to create a new account? [1 for YES / 0 for NO]");
            System.out.print("> ");
            choice = scan.nextInt();
            scan.nextLine();

            choiceIsValid = choice == 1 || choice == 0;

            if(!choiceIsValid){
                System.out.print("Please type either 1 for YES or 0 for NO. Try again.");
                promptEnterKey();
                clearScreen();
            }
        }while(choiceIsValid == false);

        if(choice == 1){
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
        int choice;
        boolean choiceIsValid = false;
        do{
            System.out.println("Welcome " + currentUser.getFirstName() + "!");
            System.out.println("==========================");
            printAccountMenu();
            System.out.print("> ");
			choice = scan.nextInt();
            scan.nextLine();
            choiceIsValid = choice >= 1 || choice <= 4;

            if(choiceIsValid == false){
                System.out.print("Invalid choice. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(choiceIsValid == false);

        switch (choice) {
            case 1:
                CreateAccount();
                break;
            case 2:
                ownedAccountsMenu();
                break;
            case 3:
                pastTransactionsMenuUser();
                break;
            case 4:
                updateOwnerInfo();
            case 5:
                Main.currentUser = null;
                new Main();
                break;
        }
	}

    public static void updateOwnerInfo() {
        clearScreen();
        int choice;
        boolean choiceIsValid = false;
        System.out.println("================");
        System.out.println("User Information");
        System.out.println("================");
        System.out.printf("Name: %s %s\n", currentUser.getFirstName(), currentUser.getSurname());
        System.out.printf("Username: %s\n", currentUser.getUsername());
        System.out.printf("Birth Date: %s\n", currentUser.getBirthDate());
        System.out.printf("Phone Number: %s\n", currentUser.getPhoneNumber());
        System.out.printf("Email: %s\n", currentUser.getEmail());
        System.out.printf("Address: %s\n", currentUser.getAddress());
        System.out.println();
        do {
            updateOwnerInfoMenu();
            System.out.println("Choose information to update");
            System.out.print("> ");
            choice = scan.nextInt();
            scan.nextLine();
            choiceIsValid = choice >= 1 || choice <= 7;

            if(choice == 0){
                OwnerMainMenu();
            }

            if (choiceIsValid == false) {
                System.out.print("Invalid choice. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        } while(choiceIsValid == false);

        switch(choice) {
            case 1:
                clearScreen();
                String tempFirstName = "";
                String tempSurname = "";
                boolean namesAreValid = false;
                do{
                    clearScreen();
                    System.out.println("[Type 0 to cancel]");
                    System.out.print("Input new first and last name: ");
                    String tempName = scan.nextLine();

                    if(tempName.equals("0")){
                        updateOwnerInfo();
                    }

                    String[] names = tempName.split("\\s");
                    tempFirstName = names[0];
                    tempSurname = names[1];

                    namesAreValid = true;
                } while(namesAreValid == false);
                currentUser.setFirstName(tempFirstName);
                currentUser.setSurname(tempSurname);
                System.out.println("Name successfully changed.");
                promptEnterKey();
                OwnerMainMenu();
                break;
            case 2:
                clearScreen();
                Date tempBirthDate = new Date();
                String tempBirthDateString;
                boolean birthDateIsValid = false;
                do{
                    clearScreen();
                    System.out.println("[Type 0 to cancel]");
                    System.out.print("Input new Birth Date [dd/MM/yyyy]: ");
                    tempBirthDateString = scan.nextLine();

                    if(tempBirthDateString.equals("0")){
                        updateOwnerInfo();
                    }

                    try {
                        tempBirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(tempBirthDateString);
                        birthDateIsValid = true;
                    } catch (ParseException e) {
                        System.err.print("Invalid date/date format");
                        e.printStackTrace();
                    }
                } while(birthDateIsValid == false);
                currentUser.setBirthDate(tempBirthDate);
                System.out.println("Birth Date successfully changed.");
                promptEnterKey();
                OwnerMainMenu();
                break;
            case 3:
                clearScreen();
                String tempPhoneNumber = "";
                boolean phoneNumberIsValid = false;
                do{
                    clearScreen();
                    System.out.println("[Type 0 to cancel]");
                    System.out.print("Input new phone number [unique and starts with 08...]: ");
                    tempPhoneNumber = scan.nextLine();

                    if(tempPhoneNumber.equals("0")){
                        updateOwnerInfo();
                    }

                    phoneNumberIsValid = validatePhoneNumber(tempPhoneNumber);

                    if(phoneNumberIsValid == false){
                        System.out.print("An account with this phone number already exists or the phone number is in the wrong format. Try again. ");
                        promptEnterKey();
                        clearScreen();
                    }
                } while(phoneNumberIsValid == false);
                currentUser.setPhoneNumber(tempPhoneNumber);
                System.out.println("Phone number successfully changed.");
                promptEnterKey();
                OwnerMainMenu();
                break;
            case 4:
                clearScreen();
                String tempPassword = "";
                boolean passwordIsValid = false;
                do{
                    clearScreen();
                    System.out.println("[Type 0 to cancel]");
                    System.out.println("Input new Password [8 - 20 characters | No whitespaces | At least one special character | At least one uppercase and lowercase letter]: ");
                    tempPassword = scan.nextLine();

                    if(tempPassword.equals("0")){
                        updateOwnerInfo();
                    }

                    passwordIsValid = validatePassword(tempPassword);
                    
                    if(passwordIsValid == false){
                        System.out.println("Password does not fit conditions. Try again. ");
                        promptEnterKey();
                        clearScreen();
                    }
                } while(passwordIsValid == false);
                currentUser.setPassword(tempPassword);
                System.out.println("Password successfully changed.");
                promptEnterKey();
                OwnerMainMenu();
                break;
            case 5:
                System.out.println("Input new email: ");
                clearScreen();
                String tempEmail;
                boolean emailIsValid = false;
                do{
                    clearScreen();
                    System.out.println("[Type 0 to cancel]");
                    System.out.print("Input new email [unique]: ");
                    tempEmail = scan.nextLine();

                    if(tempEmail.equals("0")){
                        updateOwnerInfo();
                    }

                    emailIsValid = validateEmail(tempEmail);
                    System.out.println(emailIsValid);

                    if(emailIsValid == false){
                        System.out.print("Email already exists or email is in the wrong format. Try again. ");
                        promptEnterKey();
                        clearScreen();
                    }
                } while(emailIsValid == false);
                currentUser.setEmail(tempEmail);
                System.out.println("Email successfully changed.");
                promptEnterKey();
                OwnerMainMenu();
                break;
            case 6:
                System.out.println("Input new address: ");
                clearScreen();
                String tempAddress;
                boolean addressIsValid = false;
                do{
                    clearScreen();
                    System.out.println("[Type 0 to cancel]");
                    System.out.print("Input new address: ");
                    tempAddress = scan.nextLine();

                    if(tempAddress.equals("0")){
                        updateOwnerInfo();
                    }

                    addressIsValid = validateAddress(tempAddress);

                    if(addressIsValid == false){
                        System.out.print("Address is not in the correct format. Try again. ");
                        promptEnterKey();
                        clearScreen();
                    }
                } while(addressIsValid == false);
                currentUser.setAddress(tempAddress);
                System.out.println("Address successfully changed.");
                promptEnterKey();
                OwnerMainMenu();
                break;
            case 7:
                OwnerMainMenu();
                break;
        }
    }
	
	public static void ownedAccountsMenu(){
        clearScreen();

        // If the user does not have an account, ask if they want to create one
        if(currentUser.getOwnedAccounts().isEmpty()){
            int confirmation = -1;
            boolean confirmationIsValid = false;
            do{
                System.out.println("You do not have a bank account with us yet! Would you like to make one? [Type 1 for YES or 0 for NO]");
                System.out.print("> ");
                confirmation = scan.nextInt(); scan.nextLine();
                confirmationIsValid = confirmation == 1 || confirmation == 0;

                if(confirmationIsValid == false){
                    System.out.print("Please type either 1 for YES or 0 for NO.");
                    promptEnterKey();
                    clearScreen();
                }
            }while(confirmationIsValid == false);

            if(confirmation == 1){
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
            System.out.print("PIN [Input 0 to go back to menu]: ");
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
        int accountTypeChoice = -1;
        boolean choiceIsValid = false;
        do{
            System.out.print("> ");
            accountTypeChoice = scan.nextInt();
            scan.nextLine();
            choiceIsValid = accountTypeChoice >= 1 && accountTypeChoice <= 3;

            if(choiceIsValid == false){
                System.out.print("Invalid choice. Try again. ");
                promptEnterKey();
                clearScreen();
                printAccountTypes();
            }
        }while(choiceIsValid == false);
        
        String accountTypeChoiceString = "-";
        switch(accountTypeChoice){
            case 1:
                accountTypeChoiceString = "Deposit";
                break;
            case 2:
                accountTypeChoiceString = "Savings";
                break;
            case 3:
                accountTypeChoiceString = "Credit";
                break;
        }

        // Ask user to input username and password; Validate;
        clearScreen();
        String tempPIN = "";
        boolean passwordIsValid = false;
        do{
            System.out.println("==============");
            System.out.println("Create Account");
            System.out.println("==============");
            System.out.println("Type 0 to go back to main menu.");
            System.out.println("Account Type: " + accountTypeChoiceString);
            System.out.print("Input PIN [numeric, 6 numbers]: ");
            tempPIN = scan.nextLine();

            if(tempPIN.equals("0")){
                OwnerMainMenu();
            }

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
        int confirmation = -1;
        boolean confirmationIsValid = false;
        do{
            System.out.println("==============");
            System.out.println("Create Account");
            System.out.println("==============");
            System.out.println("Account Type: " + accountTypeChoiceString);
            System.out.println("Password: " + tempPIN);
            System.out.println("Account Number: " + tempAccountNumber);
            System.out.println("Are you sure you want to create a new bank account? [1 for YES / 0 for NO]");
            System.out.print("> ");
            confirmation = scan.nextInt();
            scan.nextLine();
            confirmationIsValid = confirmation == 0 || confirmation == 1;

            if(confirmationIsValid == false){
                System.out.print("Please type either 1 for YES or 0 for NO. ");
                promptEnterKey();
                clearScreen();
            }
        }while(confirmationIsValid == false);

        if(confirmation == 1){
            Account newAccount = null;

            switch(accountTypeChoice){
                case 1:
                    newAccount = new DepositAccount(currentUser, tempAccountNumber, tempPIN);
                    break;
                case 2:
                    newAccount = new SavingsAccount(currentUser, tempAccountNumber, tempPIN);
                    break;
                case 3:
                    newAccount = new CreditAccount(currentUser, tempAccountNumber, tempPIN);
                    break;
            }

            // Add accounts to user's owned accounts and the bank's account list
            currentUser.addAccount(newAccount);
            Bank.add(newAccount);

            System.out.print("Account successfully created! ");
            promptEnterKey();
            OwnerMainMenu();
        }

        CreateAccount();
    }

	public static void AccountMainMenu(){
        clearScreen();

        int choice;
        boolean choiceIsValid = false;
        do{
			System.out.println("Current Account: " + currentAccount.getAccountNumber());
            System.out.println("What would you like to do?");
            System.out.println("==========================");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View Past Transactions");
            System.out.println("5. Close Account");
            System.out.println("6. Log Out");
            System.out.print("> ");
            choice = scan.nextInt();
            scan.nextLine();
            choiceIsValid = choice >= 1 && choice <= 6;

            if(choiceIsValid == false){
                System.out.print("Invalid choice. Try again. ");
                promptEnterKey();
                clearScreen();
            }
        }while(choiceIsValid == false);

        switch(choice){
            case 1:
                depositMenu();
                break;
            case 2:
                withdrawMenu();
                break;
            case 3:
                transferMenu();
                break;
            case 4:
                pastTransactionsMenuAccount();
                break;
            case 5:
                closeAccountMenu();
                break;
            case 6:
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
            System.out.print("PIN [Type 0 to cancel]: ");
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
        clearScreen();
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
            System.out.print("PIN [Type 0 to cancel]: ");
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
            System.out.print("Destination Account Number [Type 0 to cancel]: ");
            destinationAccountNumber = scan.nextLine();
            destinationAccountNumberIndex = accountNumberExists(destinationAccountNumber, Bank);

            if(destinationAccountNumber.equals("0")){
                AccountMainMenu();
            }

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
            System.out.print("PIN [Type 0 to cancel]: ");
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

    public static void closeAccountMenu(){
            String deleteChoice = "-";
            boolean deleteChoiceIsValid = false;
            do {
                clearScreen();
                System.out.println("Current Account: " + currentAccount.getAccountNumber());
                System.out.print("Are you sure want to delete your account? [Yes | No]: ");
                deleteChoice = scan.nextLine();
                deleteChoiceIsValid = deleteChoice.equals("Yes") || deleteChoice.equals("No");

                if(!deleteChoiceIsValid){
                    System.out.println("Invalid choice. Please try again.");
                    promptEnterKey();
                }
            } while(!deleteChoiceIsValid);

            if(deleteChoice.equals("Yes")) {
                Account accountToDelete = currentAccount;
                currentAccount = null;
                currentUser.deleteAccount(accountToDelete);
                Bank.remove(accountToDelete);
                System.out.println("Account has been closed.");
                promptEnterKey();
                OwnerMainMenu();
            } else {
                AccountMainMenu();
            }
    }

	// Utilities
    public static void updateOwnerInfoMenu() {
        System.out.println("Choose information that you want to update:");
        System.out.println("1. Name");
        System.out.println("2. Birthdate");
        System.out.println("3. Phone Number");
        System.out.println("4. Password");
        System.out.println("5. Email");
        System.out.println("6. Address");
        System.out.println("7. Back to Owner Main Menu");
    }

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
        System.out.println("4. Update Owner Information");
		System.out.println("5. Log Out");
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
