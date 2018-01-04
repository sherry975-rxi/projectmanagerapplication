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

		String oldName = getInfo.getName(user);
		String oldEmail = getInfo.getEmail(user);
		String oldPhone = getInfo.getPhone(user);
		// Presents the updatable fields
		System.out.println("Choose a field to update:");
		System.out.println("1. Name: " + oldName);
		System.out.println("2. Email: " + oldEmail);
		System.out.println("3. Phone: " + oldPhone);
		System.out.println("4. Address");
		System.out.println();
		// Selects the field according to user input
		int choice = Integer.parseInt(input.nextLine());
		switch (choice) {
		case 1:
			// Updates name
			String name = input.nextLine();
			System.out.println("New info: " + name);
			if (confirmInfo(input)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserName(user, name);
				System.out.println("UPDATE SUCCESSFUL");
				System.out.println();
			}
			break;
		case 2:
			// Updates email
			String email = input.nextLine();
			System.out.println("New info: " + email);
			if (confirmInfo(input)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				if (updater.updateUserEmail(user, email)) {
					System.out.println("UPDATE SUCCESSFUL");
				} else {
					System.out.println("Invalid email. Try again.");
				}
				System.out.println();
			}
			break;
		case 3:
			// Updates phone
			String phone = input.nextLine();
			System.out.println("New info: " + phone);
			if (confirmInfo(input)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserPhone(user, phone);
				System.out.println("UPDATE SUCCESSFUL");
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
			String oldStreet = updater.getStreet(chosen);
			String oldZipCode = updater.getZipCode(chosen);
			String oldCity = updater.getCity(chosen);
			String oldDistrict = updater.getDistrict(chosen);
			String oldCountry = updater.getCountry(chosen);
			System.out.println("Please select the number of the field to update:");
			System.out.println("1. Street: " + oldStreet);
			System.out.println("2. ZipCode: " + oldZipCode);
			System.out.println("3. City: " + oldCity);
			System.out.println("4. District: " + oldDistrict);
			System.out.println("5. Country: " + oldCountry);
			System.out.println();
			// Selects the field
			int nrField = Integer.parseInt(input.nextLine());
			switch (nrField) {
			case 1:
				// Updates street
				String newStreet = input.nextLine();
				System.out.println("New info: " + newStreet);
				if (confirmInfo(input)) {
					updater.updateUserStreet(user, oldStreet, newStreet);
					System.out.println("UPDATE SUCCESSFUL");
					System.out.println();
				}
				break;
			case 2:
				// Updates zip code
				String newZipCode = input.nextLine();
				System.out.println("New info: " + newZipCode);
				if (confirmInfo(input)) {
					updater.updateUserZipCode(user, oldStreet, newZipCode);
					System.out.println("UPDATE SUCCESSFUL");
					System.out.println();
				}
				break;
			case 3:
				// Updates city
				String newCity = input.nextLine();
				System.out.println("New info: " + newCity);
				if (confirmInfo(input)) {
					updater.updateUserCity(user, oldStreet, newCity);
					System.out.println("UPDATE SUCCESSFUL");
					System.out.println();
				}
				break;
			case 4:
				// Updates district
				String newDistrict = input.nextLine();
				System.out.println("New info: " + newDistrict);
				if (confirmInfo(input)) {
					updater.updateUserDistrict(user, oldStreet, newDistrict);
					System.out.println("UPDATE SUCCESSFUL");
					System.out.println();
				}
				break;
			case 5:
				// Updates country
				String newCountry = input.nextLine();
				System.out.println("New info: " + newCountry);
				if (confirmInfo(input)) {
					updater.updateUserCountry(user, oldStreet, newCountry);
					System.out.println("UPDATE SUCCESSFUL");
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
		if (yesOrNo.equalsIgnoreCase("y")) {
			result = true;
		}
		return result;
	}
}
