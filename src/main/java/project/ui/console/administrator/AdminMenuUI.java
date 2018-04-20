package project.ui.console.administrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import project.model.User;
import project.ui.console.loadfiles.loaduser.UserReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
	@Autowired
	UserReader userReader;

	String options = "[1] - View all users \n" + "[2] - Search users by profile or email \n"
			+ "[3] - Manage selected user profile \n" + "[4] - Manage selected user state\n" + "[5] - Load Users from file\n"
			+ "______________________________________________\n" + "[B] Back\n";

	String command;


	public AdminMenuUI() {
		//Empty constructor for JPA purposes
	}

	public void adminMenu() {

		Scanner input = new Scanner(System.in);

		boolean cycle = true;
		while (cycle) {
            System.out.println();
			System.out.println(
					"--------------------------------------------------------------------------MENU ADMIN--------------------------------------------------------------------------");
			System.out.println("Welcome to admin menu, " + adminLoggedIn.getName());
			System.out.println("______________________________________________");

			if (selectedUser != null) {
				System.out.println("> User selected!");
				System.out.println(selectedUser.getIdNumber() + ": " + selectedUser.getName() + "("
						+ selectedUser.getEmail() + ")");
				System.out.println("(User management commands enabled!)");
                System.out.println();
			}

			System.out.println("Please choose a command:");
			System.out.println(options);
            System.out.println();

			command = input.nextLine().toLowerCase();

			switch (command) {
			case "1":
				selectedUser = listUsersUI.displayUsersList(selectedUser);
				break;
			case "2":
				selectedUser = searchUsersUI.displayUsersList(selectedUser);
				break;

			case "3":
                if (isUserSelected()) {
					changeUserProfileUI.changeUserProfile(selectedUser);
				}
				break;

			case "4":
				if (isUserSelected()) {
					changeUserStateUI.changeUserState(selectedUser);
				}
				break;

			case "5":
				System.out.println("Please insert file to load");
				String file = input.nextLine();
                loadUsers(file);
				break;

			case "b":
				System.out.println("Returning to main menu...");
                System.out.println();
				cycle = false;
				break;

			default:
				System.out.println("Invalid input!");
                System.out.println();
				break;

			}

		}

	}

	private boolean isUserSelected() {
	    if(selectedUser== null) {
            System.out.println("No user selected!");
	        return false;
        }
        return true;
    }

	public void setAdminLoggedIn(User adminLoggedIn) {
		this.adminLoggedIn = adminLoggedIn;
	}

    private void loadUsers(String file) {
        try {
            userReader.readFile(file);
            System.out.println("Users loaded successfully!");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Logger log = LoggerFactory.getLogger(UserReader.class);
            log.error("Error!", e);
            System.out.println("Something went wrong. Please review your input and try again.");
        }
    }
}
