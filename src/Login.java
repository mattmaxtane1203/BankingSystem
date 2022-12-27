
import java.util.Scanner;

public class Login {
	
	private static Scanner scan = new Scanner(System.in);

	public Login() {
		Utility.clearScreen();

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
					Utility.promptEnterKey();
					Utility.clearScreen();
				}
			}while(confirmationIsValid == false);

			if(confirmation.equals("Yes")){
				new CreateOwnerAccount();
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
			accountIndex = Utility.usernameExists(username);

			if(accountIndex == -1){
				System.out.print("Username does not exist. Try again. ");
				Utility.promptEnterKey();
				Utility.clearScreen();
			}
		}while(accountIndex == -1);

		// Owner in question
		Owner currentOwner = Main.accountOwners.get(accountIndex);

		// Enter password
		Utility.clearScreen();
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
				Utility.promptEnterKey();
				Utility.clearScreen();
			}
		}while(!password.equals(currentOwner.getPassword()));

		// Redirect to account page
		Main.currentUser = currentOwner;
		Main.currentUser.ownerMainMenu();
	}
}
