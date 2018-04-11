package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import project.controllers.US101RegisterUserController;
import project.model.SendSMS;
import project.ui.console.MainMenuUI;

import java.util.Scanner;

/**
 * UI for register a user (US102)
 *
 */

@Controller
public class US101UserRegisterUI {
	@Autowired
	private US101RegisterUserController us101RegisterUserController;

	public void userRegister() throws Exception {
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
		while (!(us101RegisterUserController.isEmailValidController(email))
				|| (us101RegisterUserController.isUserInUserRepository(email))) {

			if (!(us101RegisterUserController.isEmailValidController(email))) {
				System.out.println("Invalid email, try again.");
				email = scannerInput.nextLine();
			} else if (us101RegisterUserController.isUserInUserRepository(email)) {
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

		System.out.println("Please choose an identity verification question: ");
		String question1 = "What is the name of the first pet?";
		String question2 = "What elementary school did you attend?";
		String question3 = "Where did you go for your honeymoon?";
		System.out.println("[1] " + question1);
		System.out.println("[2] " + question2);
		System.out.println("[3] " + question3);
		String question = scannerInput.nextLine();
		while (!("1".equals(question)) && !("2".equals(question)) && !("3".equals(question))) {
			System.out.println("\nInvalid question. Try again");
			question = scannerInput.nextLine();
		}

		System.out.println("Please answer the selected question: \n");
		String questionAnswer = scannerInput.nextLine().toUpperCase();
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
			us101RegisterUserController.addNewUser(name, email, idNumber, function, phone, password, street, zipCode, city,
					district, country, "Question?", "Answer");
			System.out.println();
			System.out.println("-------- A numeric verification code will be sent to the email address or the phone number that you provided. -------");
			System.out.println();
			System.out.println("Please type the number of your choice:");
			System.out.println("[1] - Send verification code by SMS.");
			System.out.println("[2] - Send verification code by Email.");
			System.out.println("[3] - Exit Registration.");


			String option = scannerInput.nextLine().toUpperCase();

			Boolean loop = true;
			while (loop) {
				switch (option) {

					case "1":
						us101RegisterUserController.sendVerificationCode(email, "1");
						loop = false;

						System.out.println("------ Please visit your account and insert the numeric verification code you received : -----");
						System.out.println();
						String codeInsertByPhone = scannerInput.nextLine();

						if (us101RegisterUserController.doesCodeGeneratedMatch(codeInsertByPhone, email)) {
							System.out.println("---------------------------------------- REGISTER SUCCESSFUL-----------------------------------");
						} else {
							System.out.println("------------------------------ REGISTER CANCELLED -------------------------");
							System.out.println("----- The numeric verification code that you provided is not correct. -----");
						}

						break;
					case "2":

						us101RegisterUserController.sendVerificationCode(email, "2");
						loop = false;


						System.out.println("------ Please visit your account and insert the numeric verification code you received : -----");
						System.out.println();
						String codeInsertByMail = scannerInput.nextLine();

						if (us101RegisterUserController.doesCodeGeneratedMatch(codeInsertByMail, email)) {
							System.out.println("---------------------------------------- REGISTER SUCCESSFUL-----------------------------------");
						} else {
							System.out.println("------------------------------ REGISTER CANCELLED -------------------------");
							System.out.println("----- The numeric verification code that you provided is not correct. -----");
						}

						break;

					case "E":

						loop = false;
						System.out.println();
						System.out.println("----- REGISTER CANCELLED -----");
						System.out.println();
						break;

					default:
						System.out.println("Please, type a valid option");
						option = scannerInput.nextLine().toUpperCase();

				}

			}


		}
	}
}