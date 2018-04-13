/**
 * 
 */
package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.CollectProjectsFromUserController;
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
@Component
public class CollectProjectsFromUserUI {
	@Autowired
	private CollectProjectsFromUserController collectProjectsFromUserController;

	@Autowired
	private ProjectManagerMainMenuUI pmMenu;

	@Autowired
	private ProjectViewMenuUI projectViewMenuUI;

	private User user;

	public CollectProjectsFromUserUI() {
		//Empty constructor for JPA purposes
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
		collectProjectsFromUserController.setUser(this.user);

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
                listOfOptionsToCompare.add(projectIDCodeToString);

                if (option.equals(projectIDCodeToString)) {
                    if(this.user.equals(project.getProjectManager())) {
                        System.out.print("TEST: " + listOfProjectsFromUser.size());
                        pmMenu.setProjectManager(user);
                        pmMenu.setProject(project);
                        pmMenu.displayOptions();
                        break;
                    }
                    else {
                    projectViewMenuUI.setProjectID(project.getIdCode());
                    projectViewMenuUI.setUser(user);
                    projectViewMenuUI.projectDataDisplay();
                    break;
                    }
                }
            }

            // In case the user input is an invalid option, the console shows a message and
            // returns to the beginning of this same menu
            if (!(listOfOptionsToCompare.contains(option))) {
                System.out.println("Please choose a valid option: ");
                loop = true;
            }
	    }
	}

	public void setUser(User user) {
		this.user = user;
	}
}
