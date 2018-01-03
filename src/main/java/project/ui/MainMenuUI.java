package project.ui;

import java.util.Scanner;

import project.model.User;

public class MainMenuUI {
	private static User user;

	public MainMenuUI() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.println("Choose a user story:");
		System.out.println("102");
		System.out.println("180");
		System.out.println("201");
		int choice = input.nextInt();
		
		switch (choice) {
		case 102:
			UserRegisterUI userRegister = new UserRegisterUI();
			userRegister.userRegister();
			break;
		/*case 180:
			TODO insert Login UI
			break;*/
		case 201:
			UpdateUserInfoUI updateUserInfo = new UpdateUserInfoUI(user);
			updateUserInfo.chooseWhatInfoToUpdate();
			break;
		}
	}
	
}
