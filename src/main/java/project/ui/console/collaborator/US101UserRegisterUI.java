package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US101RegisterUserController;

import java.util.Scanner;

/**
 * UI for register a user (US102)
 *
 */

@Component
public class US101UserRegisterUI {
	@Autowired
	private US101RegisterUserController registerUsercontroller1;

	public void userRegister() {
		String blank = "";
		Scanner scannerInput = new Scanner(System.in);

		System.out.println("USER REGISTRATION");
		System.out.println();

		// Terms and Conditions are exposed before login, depends on confirmation to
		// proceed for registration
		System.out.println("TERMS AND CONDITIONS: \r\n" + "\r\n"
				+ "By using this application, you agree to be bound by, and to comply with these Terms and Conditions.\r\n"
				+ "If you do not agree to these Terms and Conditions, please do not use this application.\r\n"
				+ "To proceed with registration you must accept access conditions (y to confirm; n to deny).");
		System.out.println();

		String answer = scannerInput.nextLine();

		// In case user writes something different from "y" or "n"
		while (!("n".equalsIgnoreCase(answer)) && !("y".equalsIgnoreCase(answer))) {
			System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
			answer = scannerInput.nextLine();
		}

		if ("y".equalsIgnoreCase(answer)) {
			System.out.println();

			System.out.println("Conditions accepted.");
			System.out.println("-------------");

		} else { // In case user choose "n".
			System.out.println();

			System.out.println("Conditions not accepted.");

			System.out.println();

			return;

		}

		System.out.println("Enter name: ");
		String name = scannerInput.nextLine();

		System.out.println("Name accepted: " + name + ".");
		System.out.println();

		System.out.println("Enter email: ");
		String email = scannerInput.nextLine();
		// It verifies if the email written by user is in a valid format and if not, the
		// user must try another valid email address.
		// When the email address is valid, email is accepted and the next field
		// (idNumber) is ready to be complete by the user.
		while (!(registerUsercontroller1.isEmailValidController(email))
				|| (registerUsercontroller1.isUserInUserRepository(email))) {

			if (!(registerUsercontroller1.isEmailValidController(email))) {
				System.out.println("Invalid email, try again.");
				email = scannerInput.nextLine();
			}

			else if (registerUsercontroller1.isUserInUserRepository(email)) {
				System.out.println("User already exists, try again.");
				email = scannerInput.nextLine();
			} else {
				break;

			}

		}
		System.out.println("Email accepted: " + email);
		System.out.println();
		System.out.println("Enter idNumber: ");
		String idNumber = scannerInput.nextLine();
		System.out.println("IdNumber accepted: " + idNumber);
		System.out.println();

		System.out.println("Enter function: ");
		String function = scannerInput.nextLine();
		System.out.println("Function accepted: " + function);
		System.out.println();

		System.out.println("Enter phone: ");
		String phone = scannerInput.nextLine();
		System.out.println("Phone accepted: " + phone);
		System.out.println();

		System.out.println("Enter password: ");
		String password = scannerInput.nextLine();
		System.out.println("Password accepted!");
		System.out.println();

		System.out.println("Enter street: ");
		String street = scannerInput.nextLine();
		System.out.println("Street accepted: " + street);
		System.out.println();

		System.out.println("Enter zipCode: ");
		String zipCode = scannerInput.nextLine();
		System.out.println("ZipCode accepted: " + zipCode);
		System.out.println();

		System.out.println("Enter city: ");
		String city = scannerInput.nextLine();
		System.out.println("City accepted: " + city);
		System.out.println();

		System.out.println("Enter district: ");
		String district = scannerInput.nextLine();
		System.out.println("District accepted: " + district);
		System.out.println();

		System.out.println("Enter country: ");
		String country = scannerInput.nextLine();
		System.out.println("Country accepted: " + country);
		System.out.println();

		System.out.println("Confirm if your data is correct: ");
		System.out.println();
		System.out.println(blank + "[1] Nome: " + name);
		System.out.println(blank + "[2] Email: " + email);
		System.out.println(blank + "[3] IdNumber: " + idNumber);
		System.out.println(blank + "[4] Function: " + function);
		System.out.println(blank + "[5] Phone: " + phone);
		System.out.println(blank + "[6] Street: " + street);
		System.out.println(blank + "[7] ZipCode: " + zipCode);
		System.out.println(blank + "[8] City: " + city);
		System.out.println(blank + "[9] District: " + district);
		System.out.println(blank + "[10] Country: " + country);

		System.out.println();
		System.out.println("Is your data correct? (y to confirm; n to deny)");

		String confirm = scannerInput.nextLine();

		if ("y".equalsIgnoreCase(confirm)) {
			registerUsercontroller1.addNewUser(name, email, idNumber, function, phone, password, street, zipCode, city,
					district, country);
			System.out.println();
			System.out.println("-----REGISTER SUCCESSFUL-----");
			System.out.println();

		} else { // In case user choose "n".
			System.out.println();
			System.out.println("-----REGISTER CANCELLED-----");
			System.out.println();

		}

	}
}