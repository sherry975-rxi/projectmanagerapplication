package project.ui;

import java.util.Scanner;

import project.controller.UpdateUserInfoController;
import project.model.Address;
import project.model.User;

/**
 * UI for updating User info US201 v2
 *
 */
public class UpdateUserInfoUI {
	private User user;

	/**
	 * Creates the UI
	 * 
	 * @param user
	 */
	public UpdateUserInfoUI(User user) {
		this.user = user;
	}

	public void chooseWhatInfoToUpdate() {
		Scanner input = new Scanner(System.in);
		UpdateUserInfoController getInfo = new UpdateUserInfoController();

		String currentName = getInfo.getName(user);
		String currentEmail = getInfo.getEmail(user);
		String currentPhone = getInfo.getPhone(user);
		// Presents the updatable fields
		System.out.println("Please select the number of the field to update:");
		System.out.println("1. Name: " + currentName);
		System.out.println("2. Email: " + currentEmail);
		System.out.println("3. Phone: " + currentPhone);
		System.out.println("4. Address");
		System.out.println();
		// Selects the field according to user input
		int choice = Integer.parseInt(input.nextLine());
		final String inputNewInfo = "Please insert the new info:";
		final String newInfo = "New info: ";
		final String updateSuccessful = "-----UPDATE SUCCESSFUL-----";
		switch (choice) {
		case 1:
			// Updates name
			System.out.println(inputNewInfo);
			String name = input.nextLine();
			System.out.println(newInfo + name);
			if (confirmInfo(input)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserName(user, name);
				System.out.println(updateSuccessful);
				System.out.println();
			}
			break;
		case 2:
			// Updates email
			System.out.println(inputNewInfo);
			String email = input.nextLine();
			System.out.println(newInfo + email);
			if (confirmInfo(input)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				if (updater.updateUserEmail(user, email)) {
					System.out.println(updateSuccessful);
				} else {
					System.out.println("Invalid email. Try again.");
				}
				System.out.println();
			}
			break;
		case 3:
			// Updates phone
			System.out.println(inputNewInfo);
			String phone = input.nextLine();
			System.out.println(newInfo + phone);
			if (confirmInfo(input)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserPhone(user, phone);
				System.out.println(updateSuccessful);
				System.out.println();
			}
			break;
		case 4:
			// Updates address
			UpdateUserInfoController updater = new UpdateUserInfoController();
			// Shows all addresses
			System.out.println("Please select the number of the address to update:");
			updater.printAddressListWithIndex(user);
			int nrAddress = Integer.parseInt(input.nextLine());
			// Chooses address
			Address chosen = updater.getAllAddresses(user).get(nrAddress - 1);
			// Shows fields of the address
			String currentStreet = updater.getStreet(chosen);
			String currentZipCode = updater.getZipCode(chosen);
			String currentCity = updater.getCity(chosen);
			String currentDistrict = updater.getDistrict(chosen);
			String currentCountry = updater.getCountry(chosen);
			System.out.println("Please select the number of the field to update:");
			System.out.println("1. Street: " + currentStreet);
			System.out.println("2. ZipCode: " + currentZipCode);
			System.out.println("3. City: " + currentCity);
			System.out.println("4. District: " + currentDistrict);
			System.out.println("5. Country: " + currentCountry);
			System.out.println();
			// Selects the field
			int nrField = Integer.parseInt(input.nextLine());
			switch (nrField) {
			case 1:
				// Updates street
				System.out.println(inputNewInfo);
				String newStreet = input.nextLine();
				System.out.println(newInfo + newStreet);
				if (confirmInfo(input)) {
					updater.updateUserStreet(user, currentStreet, newStreet);
					System.out.println(updateSuccessful);
					System.out.println();
				}
				break;
			case 2:
				// Updates zip code
				System.out.println(inputNewInfo);
				String newZipCode = input.nextLine();
				System.out.println(newInfo + newZipCode);
				if (confirmInfo(input)) {
					updater.updateUserZipCode(user, currentStreet, newZipCode);
					System.out.println(updateSuccessful);
					System.out.println();
				}
				break;
			case 3:
				// Updates city
				System.out.println(inputNewInfo);
				String newCity = input.nextLine();
				System.out.println(newInfo + newCity);
				if (confirmInfo(input)) {
					updater.updateUserCity(user, currentStreet, newCity);
					System.out.println(updateSuccessful);
					System.out.println();
				}
				break;
			case 4:
				// Updates district
				System.out.println(inputNewInfo);
				String newDistrict = input.nextLine();
				System.out.println(newInfo + newDistrict);
				if (confirmInfo(input)) {
					updater.updateUserDistrict(user, currentStreet, newDistrict);
					System.out.println(updateSuccessful);
					System.out.println();
				}
				break;
			case 5:
				// Updates country
				System.out.println(inputNewInfo);
				String newCountry = input.nextLine();
				System.out.println(newInfo + newCountry);
				if (confirmInfo(input)) {
					updater.updateUserCountry(user, currentStreet, newCountry);
					System.out.println(updateSuccessful);
					System.out.println();
				}
				break;
			}

			break;
		}
	}

	private boolean confirmInfo(Scanner input) {
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
}
