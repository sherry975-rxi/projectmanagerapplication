package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.model.User;
import project.services.UserService;
import project.services.exceptions.ObjectNotFoundException;

import java.util.Scanner;

/**
 * @author group3
 *
 */
@Component
public class CollaboratorMainMenuUI {

	@Autowired
	private UserService userService; //TIRAR ISTO QUANDO PUDER SER!!!

	@Autowired
	private US201and202UpdateUserInfoUI updateUserInfoUI;

	@Autowired
	private CollectProjectsFromUserUI collectProjectsFromUserUI;

	@Autowired
	private UserTasksFunctionalitiesMenuUI tasksFunctionalities;

	private User user;

	/**
	 * Creates the UI
	 *
	 */
	public CollaboratorMainMenuUI() {

	}

	public void displayOptions() {
		Scanner scannerInput = new Scanner(System.in);

		boolean loopA = true;
		boolean loopB = true;




		while (loopA && user==null) {

            System.out.println("DEMO MODE ENABLED!");
			System.out.println("Please choose a User by inserting its email:");

			String email = scannerInput.nextLine();

			try {
				user = userService.getUserByEmail(email);

			} catch(ObjectNotFoundException o) {
				System.out.println("Invalid email inserted. Would you like to try again?");
				System.out.println("[Y] Yes");
				System.out.println("[Any Key] No");
				String yon = scannerInput.nextLine().toLowerCase();
				if (!("y".equals(yon))){
					loopA = false;
                    loopB=false;
				}
			}

		}

		while (loopB) {
			loopB = false;


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
                updateUserInfoUI.setUser(this.user);
                updateUserInfoUI.chooseWhatInfoToUpdate();
                break;
            case "2":
                collectProjectsFromUserUI.setUser(this.user);
                collectProjectsFromUserUI.collectProjectsFromUser();
                break;
            case "3":
                tasksFunctionalities.setUser(this.user);
                tasksFunctionalities.displayFunctionalities();
                break;
            case "B":
                break;
            default:
                loopB = true;
            }
	    }
	}


	public void setUser(User user) {
		this.user = user;
	}
}
