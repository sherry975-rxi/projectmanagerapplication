package project.ui;

import java.util.Scanner;

import project.controller.RegisterUserController;

/**
 * UI for register a user (US102)
 *
 */
public class UserRegisterUI {

	public void userRegister() {

		Scanner scannerInput = new Scanner(System.in);
		RegisterUserController registerUsercontroller1 = new RegisterUserController();

		System.out.println("Enter name: ");
		String name = scannerInput.nextLine();
		System.out.println("Name accepted " + name);

		System.out.println("Enter email: ");
		String email = scannerInput.nextLine();
		//It verifies if the email written by user is in a valid format and if not, the user must try another valid email address. 
		//When the email address is valid, email is accepted and the next field (idNumber) is ready to be complete by the user.
		while (!(registerUsercontroller1.isEmailValidController(email))) {
			System.out.println("Invalid email. try again.");
			email = scannerInput.nextLine();
			}
		System.out.println("Email accepted " + email);
	
		System.out.println("Enter idNumber: ");
		String idNumber = scannerInput.nextLine();
		System.out.println("IdNumber accepted " + idNumber);

		System.out.println("Enter function: ");
		String function = scannerInput.nextLine();
		System.out.println("Function accepted " + function);

		System.out.println("Enter phone: ");
		String phone = scannerInput.nextLine();
		System.out.println("Phone accepted " + phone);

		System.out.println("Enter password: ");
		String password = scannerInput.nextLine();
		System.out.println("Password accepted " + password);

		System.out.println("Enter street: ");
		String street = scannerInput.nextLine();
		System.out.println("Street accepted " + street);

		System.out.println("Enter zipCode: ");
		String zipCode = scannerInput.nextLine();
		System.out.println("ZipCode accepted " + zipCode);

		System.out.println("Enter city: ");
		String city = scannerInput.nextLine();
		System.out.println("City accepted " + city);

		System.out.println("Enter district: ");
		String district = scannerInput.nextLine();
		System.out.println("District accepted " + district);

		System.out.println("Enter country: ");
		String country = scannerInput.nextLine();
		System.out.println("Country accepted " + country);

		System.out.println(
				"ACCESS CONDITIONS.\nTo proceed with registration you must accept access conditions (y to confirm; n to deny).");
		String answer = scannerInput.nextLine();

		//In case user writes something different from "y" or "n"
		while (!("n".equalsIgnoreCase(answer)) && !("y".equalsIgnoreCase(answer))) {
			System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
			answer = scannerInput.nextLine();
			}
		
		if ("y".equalsIgnoreCase(answer)) {
			registerUsercontroller1.addNewUser(name, email, idNumber, function, phone, password, street, zipCode, city, district,
					country);
			System.out.println("Conditions accepted. Sucessfully register!");
			
		} else { //In case user choose "n".
			System.out.println("Conditions not accepted");
		}
		
	}
}
