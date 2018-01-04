package project.ui;

import java.util.Scanner;

import project.model.Company;
import project.model.User;
import project.model.UserRepository;

public class MainMenuUI {
	private static User user;

	public static void main(String[] args) {
		mainMenu();
	}
		
		
		
	public static void mainMenu() {
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
			mainMenu();
			break;
		/*case 180:
			TODO insert Login UI
			break;*/
		case 201:
			user = Company.getTheInstance().getUsersRepository().getAllUsersFromRepository().get(0);
			UpdateUserInfoUI updateUserInfo = new UpdateUserInfoUI(user);
			updateUserInfo.chooseWhatInfoToUpdate();
			break;
		}
	}
	
}
