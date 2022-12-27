
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CreateOwnerAccount {

    private static Scanner scan = new Scanner(System.in);

    public CreateOwnerAccount(){
        Utility.clearScreen();

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
        Utility.clearScreen();
        String tempUsername = "";
        boolean usernameIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.print("Input Username [must be unique]: ");
            tempUsername = scan.nextLine();
            usernameIsValid = Utility.validateUsername(tempUsername);
            if(usernameIsValid == false){
                System.out.print("Username entered already exists. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(usernameIsValid == false);

        // Ask user to input birth date; Validate
        Utility.clearScreen();
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
        Utility.clearScreen();
        String tempPhoneNumber = "";
        boolean phoneNumberIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.print("Input phone number [unique and starts with 08...]: ");
            tempPhoneNumber = scan.nextLine();
            phoneNumberIsValid = Utility.validatePhoneNumber(tempPhoneNumber);

            if(phoneNumberIsValid == false){
                System.out.print("An account with this phone number already exists or the phone number is in the wrong format. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(phoneNumberIsValid == false);

        // Ask user to input password; Validate;
        Utility.clearScreen();
        String tempPassword = "";
        boolean passwordIsValid = false;
        do{
            System.out.println("Name: " + tempFirstName + " " + tempSurname);
            System.out.println("Username: " + tempUsername);
            System.out.println("Birth Date: " + tempBirthDate.toString());
            System.out.println("Phone Number: " + tempPhoneNumber);
            System.out.println("Input Password [8 - 20 characters | No whitespaces | At least one special character | At least one uppercase and lowercase letter]: ");
            tempPassword = scan.nextLine();
            passwordIsValid = Utility.validatePassword(tempPassword);
            
            if(passwordIsValid == false){
                System.out.println("Password does not fit conditions. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(passwordIsValid == false);

        // Ask user to input email
        Utility.clearScreen();
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
            emailIsValid = Utility.validateEmail(tempEmail);
            System.out.println(emailIsValid);

            if(emailIsValid == false){
                System.out.print("Email already exists or email is in the wrong format. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(emailIsValid == false);

        // Ask user to input address
        Utility.clearScreen();
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
            addressIsValid = Utility.validateAddress(tempAddress);

            if(addressIsValid == false){
                System.out.print("Address is not in the correct format. Try again. ");
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(addressIsValid == false);

        // Confirmation
        Utility.clearScreen();
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
                Utility.promptEnterKey();
                Utility.clearScreen();
            }
        }while(confirmationIsValid == false);

        if(confirmation.equals("Yes")){
            Main.currentUser = new Owner(tempFirstName, tempSurname, tempUsername, tempBirthDate, tempPhoneNumber, tempPassword, tempEmail, tempAddress);
            Main.accountOwners.add(Main.currentUser);
            System.out.print("Account created! ");
            Utility.promptEnterKey();
            Main.currentUser.ownerMainMenu();
        }

        new CreateOwnerAccount();
    }  
}
