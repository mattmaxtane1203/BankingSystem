import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

	private static Scanner scan = new Scanner(System.in);
	
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
		System.out.println("3. Log Out");
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

	// Other tools
	public static void clearScreen(){
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}

	public static void promptEnterKey(){
		System.out.print("Press Enter Key to continue...");
		scan.nextLine();
	}
}
