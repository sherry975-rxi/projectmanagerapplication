package project.ui;

import java.util.Scanner;

import project.controller.PrintUserInfoController;
import project.controller.RegisterUserController;
import project.model.User;

/**
 * UI for register a user (US102)
 *
 */
public class UserRegisterUI {

	/**
	 * These fields are the data entered by the user
	 */
	private String name;
	private String email;
	private String password;
	private String idNumber;
	private String function;
	private String phone;
	private String street;
	private String zipCode;
	private String city;
	private String district;
	private String country;

	public void userRegister() {

		Scanner scannerInput = new Scanner(System.in);
		RegisterUserController registerUsercontroller1 = new RegisterUserController();

		System.out.println("Enter name: ");
		name = scannerInput.nextLine();
		System.out.println("Name accepted " + name);

		System.out.println("Enter email: ");
		email = scannerInput.nextLine();
		while (!(registerUsercontroller1.isEmailValidController(email))) {
			System.out.println("Invalid email. try again.");
			email = scannerInput.nextLine();
			}
		
		System.out.println("Email accepted " + email);
	
		System.out.println("Enter idNumber: ");
		idNumber = scannerInput.nextLine();
		System.out.println("IdNumber accepted " + idNumber);

		System.out.println("Enter function: ");
		function = scannerInput.nextLine();
		System.out.println("Function accepted " + function);

		System.out.println("Enter phone: ");
		phone = scannerInput.nextLine();
		System.out.println("Phone accepted " + phone);

		System.out.println("Enter password: ");
		password = scannerInput.nextLine();
		System.out.println("Password accepted " + password);

		System.out.println("Enter street: ");
		street = scannerInput.nextLine();
		System.out.println("Street accepted " + street);

		System.out.println("Enter zipCode: ");
		zipCode = scannerInput.nextLine();
		System.out.println("ZipCode accepted " + zipCode);

		System.out.println("Enter city: ");
		city = scannerInput.nextLine();
		System.out.println("City accepted " + city);

		System.out.println("Enter district: ");
		district = scannerInput.nextLine();
		System.out.println("District accepted " + district);

		System.out.println("Enter country: ");
		country = scannerInput.nextLine();
		System.out.println("Country accepted " + country);

		System.out.println(
				"ACCESS CONDITIONS.\nTo proceed with registration you must accept access conditions (y to confirm; n to deny).");
		String answer = scannerInput.nextLine();

		if (answer.equalsIgnoreCase("y")) {
			PrintUserInfoController userController = new PrintUserInfoController();

			registerUsercontroller1.addNewUser(name, email, idNumber, function, phone, password, street, zipCode, city, district,
					country);

			if (userController.getUserByEmailController(email) instanceof User) {
				System.out.println("Register Successfully");
			} else {
				System.out.println("An error occurred in the registry");
			}

		} else if (answer.equalsIgnoreCase("n")) {
			System.out.println("Conditions not accepted");
		} else {
			System.out.println("Invalid answer");
		}
	}
}
