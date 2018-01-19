/**
 * 
 */
package project.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.controller.CollectProjectsFromUserController;
import project.model.Project;
import project.model.User;

/**
 * This UI shows all projects from a user
 * 
 * @author Group3
 *
 */
public class CollectProjectsFromUserUI {

	private User user;

	/**
	 * Constructor
	 * 
	 * @param user
	 */
	public CollectProjectsFromUserUI(User user) {
		this.user = user;
	}

	/**
	 * This method executes all options to execute through this UI
	 */
	public void collectProjectsFromUser() {

		System.out.println("\n                  PROJETOS ");
		System.out.println("___________________________________________________");

		Scanner input = new Scanner(System.in);

		CollectProjectsFromUserController collectProjectsFromUserController = new CollectProjectsFromUserController(
				user);

		for (int i = 0; i < collectProjectsFromUserController.getProjectsFromUserAndProjectManager().size(); i++) {
			System.out.println(collectProjectsFromUserController.getProjectsFromUserAndProjectManager().get(i));
		}
		// show all projects from a user

		System.out.println("___________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		String option = input.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");
		listOfOptionsToCompare.add("M");
		listOfOptionsToCompare.add("E");

		List<Project> listOfProjectsFromUser = new ArrayList<>();
		listOfProjectsFromUser.addAll(collectProjectsFromUserController.getProjectsFromUser());
		listOfProjectsFromUser.addAll(collectProjectsFromUserController.getProjectsFromProjectManager());

		for (Project ii : listOfProjectsFromUser) {
			int projectIDCode = ii.getIdCode();
			String projectIDCodeToString = String.valueOf(projectIDCode);
			if (option.equals(projectIDCodeToString)) {
				// TODO
				// ProjectViewMenuUI projectViewMenuUI = new ProjectViewMenuUI
				// (projectIDCode);
				// projectViewMenuUI.projectDataDisplay();
			} else if (option.equals("B")) {
				CollaboratorMainMenuUI menu = new CollaboratorMainMenuUI(user);
				menu.displayOptions();
			} else if (option.equals("M")) {
				MainMenuUI.mainMenu();
			} else if (option.equals("E")) {
				System.exit(0);
			}
			listOfOptionsToCompare.add(projectIDCodeToString);
		}

		// In case the user input is an invalid option, the console shows a message and
		// returns to the beginning of this same menu
		if (!(listOfOptionsToCompare.contains(option))) {
			System.out.println("Please choose a valid option: ");
			CollectProjectsFromUserUI collectProjectsFromUserUI = new CollectProjectsFromUserUI(user);
			collectProjectsFromUserUI.collectProjectsFromUser();
		}
	}

}
