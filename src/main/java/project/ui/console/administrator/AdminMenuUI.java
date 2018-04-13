package project.ui.console.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.model.User;

import java.util.Scanner;

@Component
public class AdminMenuUI {

    @Autowired
    private US130ListUsersUI listUsersUI;
    @Autowired
	private US135andUS136ListUsersUI searchUsersUI;
    @Autowired
	private US110andUS112SetUserProfileUI changeUserProfileUI;
	@Autowired
	private US115andUS116SetUserStateUI changeUserStateUI;

	User adminLoggedIn;

	User selectedUser;

	String options = "[1] - View all users \n" + "[2] - Search users by profile or email \n"
			+ "[3] - Manage selected user profile \n" + "[4] - Manage selected user state\n"
			+ "______________________________________________\n" + "[B] Back\n";

	String command;


	public AdminMenuUI() {
		//Empty constructor for JPA purposes
	}

	public void adminMenu() {

		Scanner input = new Scanner(System.in);

		boolean cycle = true;
		while (cycle) {
			System.out.println("");
			System.out.println(
					"--------------------------------------------------------------------------MENU ADMIN--------------------------------------------------------------------------");
			System.out.println("Welcome to admin menu, " + adminLoggedIn.getName());
			System.out.println("______________________________________________");

			if (selectedUser != null) {
				System.out.println("> User selected!");
				System.out.println(selectedUser.getIdNumber() + ": " + selectedUser.getName() + "("
						+ selectedUser.getEmail() + ")");
				System.out.println("(User management commands enabled!)");
				System.out.println("");
			}

			System.out.println("Please choose a command:");
			System.out.println(options);
			System.out.println("");

			command = input.nextLine().toLowerCase();

			switch (command) {
			case "1":
				selectedUser = listUsersUI.displayUsersList(selectedUser);
				break;
			case "2":
				selectedUser = searchUsersUI.displayUsersList(selectedUser);
				break;

			case "3":
				if (selectedUser != null)
					System.out.println("No user selected!");
				else {
					changeUserProfileUI.changeUserProfile(selectedUser);
				} break;

			case "4":
				if (selectedUser == null)
					System.out.println("No user selected!");
				else {
					changeUserStateUI.changeUserState(selectedUser);
				} break;

			case "b":
				System.out.println("Returning to main menu...");
				System.out.println("");
				cycle = false;
				break;

			default:
				System.out.println("Invalid input!");
				System.out.println("");
				break;

			}

		}

	}


	public void setAdminLoggedIn(User adminLoggedIn) {
		this.adminLoggedIn = adminLoggedIn;
	}

}
