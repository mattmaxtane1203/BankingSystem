// TODO: Create getter for transaction history

import java.util.Date;
import java.util.ArrayList;

public class Owner {

    private String firstName;
    private String surname;
    private String username;
    private Date birthDate;
    private String phoneNumber;
    private String password;
    private String email;
    private String address;
    private ArrayList<Account> ownedAccounts = new ArrayList<Account>();
    private ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>();

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
        getOwnedAccounts().add(account);
    }

	public ArrayList<Account> getOwnedAccounts() {
		return ownedAccounts;
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
        for(int i = 0; i < getOwnedAccounts().size(); i++){
            System.out.printf("|%-15s|%-15s|Rp. %-11.2f|\n", getOwnedAccounts().get(i).getAccountNumber(), getOwnedAccounts().get(i).getType(), getOwnedAccounts().get(i).getBalance());
        }
        for(int j = 0; j < 49; j++){
            System.out.print("=");
        }
        System.out.println();
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
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

}
