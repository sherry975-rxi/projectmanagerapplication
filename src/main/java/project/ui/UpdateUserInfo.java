package project.ui;

import java.util.Scanner;

import project.controller.UpdateUserInfoController;
import project.model.User;

/**
 * UI for updating User info US201 v2
 *
 */
public class UpdateUserInfo {
	User user;

	/**
	 * Creates the UI
	 * 
	 * @param user
	 */
	public UpdateUserInfo(User user) {
		this.user = user;
	}

	public void chooseWhatInfoToUpdate() {
		Scanner input = new Scanner(System.in);

		System.out.println("Choose a field to update:");
		System.out.println("1.Name");
		System.out.println("2.Email");
		System.out.println("3.Phone");
		System.out.println("4.Address");
		int choice = input.nextInt();
		switch (choice) {
		case 1:
			String name = confirmInfo(input);
			if (!name.equals(null)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserName(user, name);
			}
			break;
		case 2:
			String email = confirmInfo(input);
			if (!email.equals(null)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserEmail(user, email);
			}
			break;
		case 3:
			String phone = confirmInfo(input);
			if (!phone.equals(null)) {
				UpdateUserInfoController updater = new UpdateUserInfoController();
				updater.updateUserPhone(user, phone);
			}
			break;
		case 4:
			System.out.println("Please choose which address to update:");
			input.nextLine();
			break;
		}
	}

	private String confirmInfo(Scanner input) {
		System.out.println("New info:");
		String info = input.nextLine();
		System.out.println("Is this info correct? (y to confirm)");
		System.out.println(info);
		String yesOrNo = input.nextLine();
		if (yesOrNo.equals("y")) {
			return info;
		}
		return null;
	}
}
