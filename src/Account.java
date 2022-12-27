import java.util.ArrayList;

public abstract class Account {

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

    public void printTransactionHistory(){

        // Print header
        for(int j = 0; j < 49; j++){
            System.out.print("=");
        }
        System.out.println();
        System.out.printf("|%-17s|%-17s|%-17s|%-17s|\n", "Transaction ID", "Transaction Type", "Recipient Acc No.", "Amount");
        for(int j = 0; j < 49; j++){
            System.out.print("=");
        }
        System.out.println();

        // Print accounts
        for(int i = 0; i < getTransactionHistory().size(); i++){

            if(!getTransactionHistory().get(i).getTransactionType().equals("Deposit")){
                System.out.printf("|%-17s|%-17s|%-17s|- Rp %-13.2f|\n", getTransactionHistory().get(i).getTransactionID(), getTransactionHistory().get(i).getTransactionType(), getTransactionHistory().get(i).getDestinationAccountNumber(), getTransactionHistory().get(i).getAmount());
                continue;
            }

            System.out.printf("|%-17s|%-17s|%-17s| Rp %-13.2f|\n", getTransactionHistory().get(i).getTransactionID(), getTransactionHistory().get(i).getTransactionType(), getTransactionHistory().get(i).getDestinationAccountNumber(), getTransactionHistory().get(i).getAmount());
        }
        for(int j = 0; j < 49; j++){
            System.out.print("=");
        }
        System.out.println();
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

	public abstract void addBalance(double amount);
	public abstract boolean withdraw(double amount);

}
