
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static Scanner scan = new Scanner(System.in);

	// Bank-related
	public static ArrayList<Account> Bank = new ArrayList<Account>();
	public static ArrayList<Owner> accountOwners = new ArrayList<Owner>();

	// Current user
	public static Owner currentUser = null;

	public static void main(String[] args) {
		// // Test data
		// currentUser = new Owner("Matthew", "Tane", "mattmaxtane", null, "081283762959", "matthew.tane@gmail.com", "Jl. Cempaka 1 no. 22");
		// currentUser.addAccount(new DepositAccount(currentUser, "00000000000", "000000", 0));
		// currentUser.printOwnedAccounts();

		new Main();
	}
	
	public Main() {
		Utility.clearScreen();
		
//		Print main menu
		Utility.printMainMenu();
		
//		Input choice
		int choice = 0;
		do {
			System.out.print("> ");
			choice = scan.nextInt(); scan.nextLine();
		}while(choice < 1 || choice > 3);
		
//		Switch choice
		switch(choice) {
		case 1:
			new Login();
			break;
		case 2:
			new CreateOwnerAccount();
			break;
		case 3:
			System.exit(0);
			break;
		}
	}
}
