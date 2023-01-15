

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(Owner owner, String accountNumber, String PIN) {
        super(owner, "Savings", accountNumber, PIN, 0);
        this.interestRate = 0.025;
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
        double newBalance;
		if (amount <= this.getBalance()) {
			newBalance = this.getBalance() - amount;
			this.setBalance(newBalance);
			System.out.println("YES");
			return true;
		} else {
			return false;
		}
    }
    
}
