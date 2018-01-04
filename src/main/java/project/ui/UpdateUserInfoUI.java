package project.ui;

import java.util.Scanner;

import project.controller.UpdateUserInfoController;
import project.model.User;

/**
 * UI for updating User info US201 v2
 *
 */
public class UpdateUserInfoUI {
	User user;

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

		String oldName = user.getName();
		String oldEmail = user.getEmail();
		String oldPhone = user.getPhone();
	

		System.out.println("Choose a field to update:");
		System.out.println("1. Name: " + oldName);
		System.out.println("2. Email: " + oldEmail);
		System.out.println("3. Phone: " + oldPhone);
		System.out.println("4. Address");
		int choice = input.nextInt();
		switch (choice) {
		case 1:
			String name = confirmInfo(input);
			if (!(name == null)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserName(user, name);
			}
			break;
		case 2:
			String email = confirmInfo(input);
			if (!(email == null)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserEmail(user, email);
			}
			break;
		case 3:
			String phone = confirmInfo(input);
			if (!(phone == null)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserPhone(user, phone);
			}
			break;
		case 4:
			UpdateUserInfoController updater = new UpdateUserInfoController();

			System.out.println("Please select the number of the address to update:");
			for (int i = 0; i < updater.getAllAddresses(user).size(); i++) {
				System.out.println((i + 1) + ". " + updater.getAllAddresses(user).get(i).toString());
			}
			int nrAddress = input.nextInt();
			String oldStreet = updater.getAllAddresses(user).get(nrAddress).getStreet();
			String oldZipCode = updater.getAllAddresses(user).get(nrAddress).getZipCode();
			String oldCity = updater.getAllAddresses(user).get(nrAddress).getCity();
			String oldDistrict = updater.getAllAddresses(user).get(nrAddress).getDistrict();
			String oldCountry = updater.getAllAddresses(user).get(nrAddress).getCountry();
			System.out.println("Please select the number of the field to update:");
			System.out.println("1. Street: " + oldStreet);
			System.out.println("2. ZipCode: " + oldZipCode);
			System.out.println("3. City: " + oldCity);
			System.out.println("4. District: " + oldDistrict);
			System.out.println("5. Country: " + oldCountry);
			int nrField = input.nextInt();
			// System.out.println("Please enter the new info:");
			switch (nrField) {
			case 1:
				String newStreet = confirmInfo(input);
				if (newStreet != null) {
					updater.updateUserStreet(user, oldStreet, newStreet);
				}
				break;
			case 2:
				String newZipCode = confirmInfo(input);
				if (newZipCode != null) {
					updater.updateUserZipCode(user, oldStreet, newZipCode);
				}
				break;
			case 3:
				String newCity = confirmInfo(input);
				if (newCity != null) {
					updater.updateUserCity(user, oldStreet, newCity);
				}
				break;
			case 4:
				String newDistrict = confirmInfo(input);
				if (newDistrict != null) {
					updater.updateUserDistrict(user, oldStreet, newDistrict);
				}
				break;
			case 5:
				String newCountry = confirmInfo(input);
				if (newCountry != null) {
					updater.updateUserCountry(user, oldStreet, newCountry);
				}
				break;
			}
			System.out.println("UPDATE SUCCESSFUL");

			break;
		}
	}

	private String confirmInfo(Scanner input) {
		System.out.println("New info:");
		String info = input.nextLine();
		System.out.println(info);
		System.out.println("Is this info correct? (y to confirm)");
		String yesOrNo = input.nextLine();
		if (yesOrNo.equalsIgnoreCase("y")) {
			return info;
		}
		return null;
	}
}
