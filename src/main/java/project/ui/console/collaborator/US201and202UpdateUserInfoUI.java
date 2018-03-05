package project.ui.console.collaborator;

import project.controller.US201and202UpdateUserInfoController;
import project.model.Address;
import project.model.User;

import java.util.Scanner;

/**
 * UI for updating User info US201 v2
 *
 */
public class US201and202UpdateUserInfoUI {
	private User user;

	/**
	 * Creates the UI
	 * 
	 * @param user
	 */
	public US201and202UpdateUserInfoUI(User user) {
		this.user = user;
	}

	public void chooseWhatInfoToUpdate() {
		String processCancelled = "Process Cancelled";
		Scanner input = new Scanner(System.in);
		US201and202UpdateUserInfoController getInfo = new US201and202UpdateUserInfoController();

		String currentName = getInfo.getName(user);
		String currentEmail = getInfo.getEmail(user);
		String currentPhone = getInfo.getPhone(user);
		// Presents the updatable fields
		System.out.println("UPDATE USER INFO");
		System.out.println();

		System.out.println("PLEASE, SELECT THE NUMBER OF THE FIELD TO UPDATE:");
		System.out.println();

		System.out.println("[1] Name: " + currentName);
		System.out.println("[2] Email: " + currentEmail);
		System.out.println("[3] Phone: " + currentPhone);
		System.out.println("[4] Address List");
		System.out.println("[5] Add New Address");

		System.out.println("______________________________________________");
		System.out.println();

		System.out.println("[Any other key] BACK");
		System.out.println();

		// Selects the field according to user input
		String choice = input.nextLine().toUpperCase();
		
		final String updateSuccessful = "-----UPDATE SUCCESSFUL-----";
		switch (choice) {
		case "1":
			// Updates name
			System.out.println("Please, insert new name");
			String name = input.nextLine();
			System.out.println("New Name:" + name);
			confirmInfoToUpdateName(input, name, updateSuccessful);
			break;
		case "2":
			// Updates email
			System.out.println("Please, insert new email");
			String email = input.nextLine();
			System.out.println("New Email: " + email);
			confirmInfoToUpdateEmail(input, email, updateSuccessful);
			break;
		case "3":
			// Updates phone
			System.out.println("Please, insert new phone number");
			String phone = input.nextLine();
			System.out.println("New phone number: " + phone);
			confirmInfoToUpdatePhone(input, phone, updateSuccessful);
			break;
		case "4":
			// Updates address
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			// Shows all addresses
			System.out.println("Please select the number of the address to update:");
			int i = 0;

			for (Address address : updater.getAllAddresses(user)) {
				System.out.println();

				System.out.println("[" + (i + 1) + "].");
				System.out.println();

				System.out.println("Street:    " + updater.getStreet(address));
				System.out.println("ZipCode:   " + updater.getZipCode(address));
				System.out.println("City:      " + updater.getCity(address));
				System.out.println("District:  " + updater.getDistrict(address));
				System.out.println("Country:   " + updater.getCountry(address));
				System.out.println();
				i++;

			}
			System.out.println("___________________________________________________");
			System.out.println("If you select an invalid number the menu returns back");

			if (input.hasNextInt()) {
				int nrAddress = input.nextInt();

				if (nrAddress > updater.getAllAddresses(user).size() || nrAddress < 0) {
					System.out.println();
					System.out.println(processCancelled);
					System.out.println();

				} else {
					chooseAddressFieldAndUpdateIt(updater, input, nrAddress);
				}
			} else {
				System.out.println();
				System.out.println(processCancelled);
				System.out.println();
			}

			break;

		case "5":
			US201and202UpdateUserInfoController addAdress = new US201and202UpdateUserInfoController();
			// Adds new street
			System.out.println("Street: ");
			String newStreet = input.nextLine();
			System.out.println("ZipCode: ");

			String newZipCode = input.nextLine();

			System.out.println("City: ");
			String newCity = input.nextLine();
			System.out.println("District: ");

			String newDistrict = input.nextLine();
			System.out.println("Country: ");

			String newCountry = input.nextLine();

			Address newAddress = addAdress.createNewAddress(newStreet, newZipCode, newCity, newDistrict, newCountry);
			System.out.println();

			System.out.println("Verify if new adress is correct:");
			System.out.println();

			System.out.println("Street:     " + newStreet);
			System.out.println("ZipCode:    " + newZipCode);
			System.out.println("City:       " + newCity);
			System.out.println("District:   " + newDistrict);
			System.out.println("Country:    " + newCountry);
			System.out.println();

			confirmOrCancelAddNewAddress(input, newAddress, addAdress);
			break;
		default:
			System.out.println("The user cancelled the process");
			System.out.println();
			break;
		}

	}

	/**
	 * Method to check the answer Yes or No If yes returns true
	 * 
	 * @param input
	 * @return
	 */
	private static boolean confirmInfoYOrN(Scanner input) {
		boolean result = false;
		System.out.println("Press y to confirm change");
		String yesOrNo = input.nextLine();
		if ("y".equalsIgnoreCase(yesOrNo)) {
			result = true;
		} else {
			System.out.println("-----UPDATE DISCARDED-----\n");
		}
		return result;
	}

	/**
	 * Method to check the answer Yes or No to add new address If yes, adds new
	 * address to the address list If not goes back to main menu
	 * 
	 * @param input
	 * @param newAddress
	 * @param addAdress
	 */
	private void confirmOrCancelAddNewAddress(Scanner input, Address newAddress,
			US201and202UpdateUserInfoController addAdress) {
		System.out.println("Press [Y] to confirm");

		String confirmation = input.nextLine();
		if ("Y".equalsIgnoreCase(confirmation)) {
			addAdress.addNewAddress(user, newAddress);
			System.out.println("New Adress was added successfully");
			System.out.println();

		} else {
			System.out.println("The user cancelled the process of adding a new Address");
			System.out.println();
		}
	}

	/**
	 * Method to change email
	 * 
	 * @param input
	 * @param email
	 * @param updateSuccessful
	 *            (Message saying update successful)
	 */
	private void confirmInfoToUpdateEmail(Scanner input, String email, String updateSuccessful) {
		if (confirmInfoYOrN(input)) {
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			if (!updater.isEmailValid(email)) {
				System.out.println("Invalid email.");
			} else if (updater.isEmailAlreadyInUse(email)) {
				System.out.println("Email is already in use.");
			} else {
				updater.updateUserEmail(user, email);
				System.out.println(updateSuccessful);
			}
			System.out.println();
		}
	}

	/**
	 * Method to change phone number
	 * 
	 * @param input
	 * @param phone
	 * @param updateSuccessful
	 *            (Message saying update successful)
	 */
	private void confirmInfoToUpdatePhone(Scanner input, String phone, String updateSuccessful) {
		if (confirmInfoYOrN(input)) {
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			updater.updateUserPhone(user, phone);
			System.out.println(updateSuccessful);
			System.out.println();
		}
	}

	/**
	 * Method to change User Name
	 * 
	 * @param input
	 * @param name
	 * @param updateSuccessful
	 *            (Message saying update successful)
	 */
	private void confirmInfoToUpdateName(Scanner input, String name, String updateSuccessful) {
		if (confirmInfoYOrN(input)) {
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			updater.updateUserName(user, name);
			System.out.println(updateSuccessful);
			System.out.println();
		}
	}

	/**
	 * Method to change Street of an existing Address
	 * 
	 * @param input
	 * @param currentStreet
	 * @param newStreet
	 * @param updateSuccessful
	 *            (Message saying update successful)
	 */
	private void confirmInfoToUpdateStreet(Scanner input, String currentStreet, String newStreet,
			String updateSuccessful) {
		if (confirmInfoYOrN(input)) {
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			updater.updateUserStreet(user, currentStreet, newStreet);
			System.out.println(updateSuccessful);
			System.out.println();
		}
	}

	/**
	 * Method to change Zip Code of an existing Address
	 * 
	 * @param input
	 * @param currentStreet
	 * @param newZipCode
	 * @param updateSuccessful
	 *            (Message saying update successful)
	 */
	private void confirmInfoToUpdateZipCode(Scanner input, String currentStreet, String newZipCode,
			String updateSuccessful) {
		if (confirmInfoYOrN(input)) {
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			updater.updateUserZipCode(user, currentStreet, newZipCode);
			System.out.println(updateSuccessful);
			System.out.println();
		}
	}

	/**
	 * Method to change City of an existing Address
	 * 
	 * @param input
	 * @param currentStreet
	 * @param newCity
	 * @param updateSuccessful
	 *            (Message saying update successful)
	 */
	private void confirmInfoToUpdateCity(Scanner input, String currentStreet, String newCity, String updateSuccessful) {
		if (confirmInfoYOrN(input)) {
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			updater.updateUserCity(user, currentStreet, newCity);
			System.out.println(updateSuccessful);
			System.out.println();
		}
	}

	/**
	 * Method to change District of an existing Address
	 * 
	 * @param input
	 * @param currentStreet
	 * @param newDistrict
	 * @param updateSuccessful
	 *            (Message saying update successful)
	 */
	private void confirmInfoToUpdateDistrict(Scanner input, String currentStreet, String newDistrict,
			String updateSuccessful) {
		if (confirmInfoYOrN(input)) {
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			updater.updateUserDistrict(user, currentStreet, newDistrict);
			System.out.println(updateSuccessful);
			System.out.println();
		}
	}

	/**
	 * Method to change Country of an existing Address
	 * 
	 * @param input
	 * @param currentStreet
	 * @param newCountry
	 * @param updateSuccessful
	 *            (Message saying update successful)
	 */
	private void confirmInfoToUpdateCountry(Scanner input, String currentStreet, String newCountry,
			String updateSuccessful) {
		if (confirmInfoYOrN(input)) {
			US201and202UpdateUserInfoController updater = new US201and202UpdateUserInfoController();
			updater.updateUserCountry(user, currentStreet, newCountry);
			System.out.println(updateSuccessful);
			System.out.println();
		}
	}

	private void chooseAddressFieldAndUpdateIt(US201and202UpdateUserInfoController updater, Scanner input,
			int nrAddress) {

		String inputNewInfo = "Please insert the new info:";
		String newInfo = "New info: ";
		String updateSuccessful = "-----UPDATE SUCCESSFUL-----";

		// Chooses address
		Address chosen = updater.getAllAddresses(user).get(nrAddress - 1);
		// Shows fields of the address
		String currentStreet = updater.getStreet(chosen);
		String currentZipCode = updater.getZipCode(chosen);
		String currentCity = updater.getCity(chosen);
		String currentDistrict = updater.getDistrict(chosen);
		String currentCountry = updater.getCountry(chosen);
		System.out.println("Please select the number of the field to update:");
		System.out.println("[1] Street: " + currentStreet);
		System.out.println("[2] ZipCode: " + currentZipCode);
		System.out.println("[3] City: " + currentCity);
		System.out.println("[4] District: " + currentDistrict);
		System.out.println("[5] Country: " + currentCountry);
		System.out.println();
		// Selects the field
		String nrField = input.nextLine();
		switch (nrField) {
		case "1":
			// Updates street
			System.out.println(inputNewInfo);
			String newStreet = input.nextLine();
			System.out.println(newInfo + newStreet);
			confirmInfoToUpdateStreet(input, currentStreet, newStreet, updateSuccessful);
			break;
		case "2":
			// Updates zip code
			System.out.println(inputNewInfo);
			String newZipCode = input.nextLine();
			System.out.println(newInfo + newZipCode);
			confirmInfoToUpdateZipCode(input, currentStreet, newZipCode, updateSuccessful);
			break;
		case "3":
			// Updates city
			System.out.println(inputNewInfo);
			String newCity = input.nextLine();
			System.out.println(newInfo + newCity);
			confirmInfoToUpdateCity(input, currentStreet, newCity, updateSuccessful);
			break;
		case "4":
			// Updates district
			System.out.println(inputNewInfo);
			String newDistrict = input.nextLine();
			System.out.println(newInfo + newDistrict);
			confirmInfoToUpdateDistrict(input, currentStreet, newDistrict, updateSuccessful);
			break;
		case "5":
			// Updates country
			System.out.println(inputNewInfo);
			String newCountry = input.nextLine();
			System.out.println(newInfo + newCountry);
			confirmInfoToUpdateCountry(input, currentStreet, newCountry, updateSuccessful);
			break;
		default:
			System.out.println("");
			System.out.println("Process Cancelled");
			System.out.println("");
			break;
		}
	}
}
