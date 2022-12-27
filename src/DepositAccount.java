

public class DepositAccount extends Account {
    private double interestRate;

    public DepositAccount(Owner owner, String accountNumber, String PIN, double balance) {
        super(owner, "Deposit", accountNumber, PIN, balance);
        this.interestRate = 0.05;
    }

    void addInterestToBalance(){
        double newBalance;
        newBalance = this.getBalance() + (this.getBalance() * interestRate);
        this.setBalance(newBalance);
    }

    @Override
    public void addBalance(double amount) {
        double newBalance;
        newBalance = this.getBalance() + amount;
        this.setBalance(newBalance);
    }

    @Override
    public boolean withdraw(double amount) {
        double total_balance;
        if (amount <= this.getBalance()) {
			total_balance = this.getBalance() - amount;
			this.setBalance(total_balance);
			System.out.println("YES");
			return true;
		} else {
			return false;
		}
    }
    
}
