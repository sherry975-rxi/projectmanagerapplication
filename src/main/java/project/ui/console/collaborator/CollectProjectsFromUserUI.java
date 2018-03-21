/**
 * 
 */
package project.ui.console.collaborator;

import project.controller.CollectProjectsFromUserController;
import project.model.Project;
import project.model.User;
import project.ui.console.projectmanager.ProjectManagerMainMenuUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		boolean loop = true;
		while (loop) {
			loop = false;
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
		System.out.println("[B] Back \n");

		String option = input.nextLine().toUpperCase();

		// creation of a list with the options B,E and M
		List<String> listOfOptionsToCompare = new ArrayList<>();
		listOfOptionsToCompare.add("B");
		listOfOptionsToCompare.add("M");
		listOfOptionsToCompare.add("E");

		List<Project> listOfProjectsFromUser = new ArrayList<>();
		listOfProjectsFromUser.addAll(collectProjectsFromUserController.getProjectsFromUser());
		listOfProjectsFromUser.addAll(collectProjectsFromUserController.getProjectsFromProjectManager());

		for (Project project : listOfProjectsFromUser) {
			int projectIDCode = project.getIdCode();
			String projectIDCodeToString = String.valueOf(projectIDCode);

			if (option.equals(projectIDCodeToString)) {
				if(this.user.equals(project.getProjectManager())) {
					ProjectManagerMainMenuUI pmMenu = new ProjectManagerMainMenuUI(this.user, project);
					pmMenu.displayOptions();
				}
				else {
				ProjectViewMenuUI projectViewMenuUI = new ProjectViewMenuUI(project.getIdCode(), user);
				projectViewMenuUI.projectDataDisplay();
				}
			} else if ("B".equals(option)) {
				//da maneira que isto esta, nem precisa desta op�ao...
				
			}
			listOfOptionsToCompare.add(projectIDCodeToString);
		}

		// In case the user input is an invalid option, the console shows a message and
		// returns to the beginning of this same menu
		if (!(listOfOptionsToCompare.contains(option))) {
			System.out.println("Please choose a valid option: ");
			loop = true;
		}
	}
	}
}
