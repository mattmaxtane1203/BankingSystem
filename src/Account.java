

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Account {
    
    private static Scanner scan = new Scanner(System.in);

    private Owner owner;
    private String type;
    private String accountNumber;
	private String PIN;
	private double balance;
    private ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>();
	
	public Account(Owner owner, String type, String accountNumber, String password, double balance) {
        this.owner = owner;
        this.type = type;
        this.accountNumber = accountNumber;
        this.PIN = password;
        this.balance = balance;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String password) {
		this.PIN = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

    public void addTransaction(Transaction transaction){
        transactionHistory.add(transaction);
    }
	
	public abstract void addBalance(double amount);
	public abstract void withdraw(double amount);

    // Menu
    public void accountMainMenu(){
        Utility.clearScreen();

        String choice;
        boolean choiceIsValid = false;
        do{
            System.out.println("What would you like to do?");
            System.out.println("==========================");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Log Out");
            System.out.print("> ");
            choice = scan.nextLine();
            choiceIsValid = choice.equals("Deposit") || choice.equals("Withdraw") || choice.equals("Transfer") || choice.equals("Log Out");

            if(choiceIsValid == false){
                System.out.print("Invalid choice. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
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
            case "Log Out":
                Main.currentUser.ownedAccountsMenu();
                break;
        }
    }

    public void depositMenu(){
        Utility.clearScreen();

        double amountToDeposit;
        System.out.print("Amount to deposit: Rp. ");
        amountToDeposit = scan.nextDouble(); scan.nextLine();
        addBalance(amountToDeposit);
        
        Utility.clearScreen();

        System.out.println("Funds deposited successfully!");
        System.out.println("Amount deposited: " + amountToDeposit);
        System.out.println("Current Balance: " + getBalance());
        Utility.promptEnterKey();
        accountMainMenu();
    }

    public void withdrawMenu(){
        Utility.clearScreen();

        double amountToWithdraw;
        do{
            System.out.print("Amount to withdraw: Rp. ");
            amountToWithdraw = scan.nextDouble(); scan.nextLine();

            if(amountToWithdraw > getBalance()){
                System.out.println("Not enough funds in your account. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(amountToWithdraw > getBalance());
        withdraw(amountToWithdraw);
        
        Utility.clearScreen();

        System.out.println("Funds withdrawn successfully!");
        System.out.println("Amount withdrawn: " + amountToWithdraw);
        System.out.println("Current Balance: " + getBalance());
        Utility.promptEnterKey();
        accountMainMenu();
    }

    public void transferMenu(){
        Utility.clearScreen();

        String destinationAccountNumber;
        int destinationAccountNumberIndex = -1;
        do{
            System.out.print("Destination Account Number: ");
            destinationAccountNumber = scan.nextLine();
            destinationAccountNumberIndex = Utility.accountNumberExists(destinationAccountNumber, Main.Bank);

            if(destinationAccountNumberIndex == -1){
                System.out.print("Account number does not exist. Please try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(destinationAccountNumberIndex == -1);

        Utility.clearScreen();

        double amountToTransfer;
        do{
            System.out.println("Destination Account Number: " + destinationAccountNumber);
            System.out.print("Amount to deposit: Rp. ");
            amountToTransfer = scan.nextDouble(); scan.nextLine();

            if(amountToTransfer > getBalance()){
                System.out.println("Not enough funds in your account. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(amountToTransfer > getBalance());
        withdraw(amountToTransfer);
        Main.Bank.get(destinationAccountNumberIndex).addBalance(amountToTransfer);
        
        Utility.clearScreen();

        System.out.println("Funds transferred successfully!");
        System.out.println("Current Balance: " + getBalance());
        Utility.promptEnterKey();
        accountMainMenu();
    }
}
