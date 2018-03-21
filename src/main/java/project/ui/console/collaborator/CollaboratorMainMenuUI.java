package project.ui.console.collaborator;

import org.springframework.stereotype.Component;
import project.model.User;

import java.util.Scanner;

/**
 * @author group3
 *
 */
@Component
public class CollaboratorMainMenuUI {

	private User user;

	/**
	 * Creates the UI
	 * 
	 * @param user
	 */
	public CollaboratorMainMenuUI() {

	}

	public void displayOptions() {
		boolean loop = true;
		while (loop) {
			loop = false;
		Scanner scannerInput = new Scanner(System.in);

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		System.out.println("\n");
		System.out.println("Please choose an option:");
		System.out.println("[1] Update User Register Info");
		System.out.println("[2] Projects");
		System.out.println("[3] Tasks\n");
		System.out.println("___________________________________________________");
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {
		case "1":
			US201and202UpdateUserInfoUI updateUserInfoUI = new US201and202UpdateUserInfoUI(this.user);
			updateUserInfoUI.chooseWhatInfoToUpdate();
			break;
		case "2":
			CollectProjectsFromUserUI collectProjectsFromUserUI = new CollectProjectsFromUserUI(this.user);
			collectProjectsFromUserUI.collectProjectsFromUser();
			break;
		case "3":
			UserTasksFunctionalitiesMenuUI tasksFunctionalities = new UserTasksFunctionalitiesMenuUI(user);
			tasksFunctionalities.displayFunctionalities();
			break;
		case "B":
			break;
		default:
			loop = true;
		}
	}}


	public void setUser(User user) {
		this.user = user;
	}
}
