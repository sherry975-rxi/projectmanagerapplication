package project.ui;

import java.util.Scanner;

import project.controller.US342Controller;
import project.model.User;

/**
 * @author Utilizador
 *
 */
public class DefineDependenciesBetweenTasksUI {

	private User user;

	/**
	 * @param user
	 */
	public DefineDependenciesBetweenTasksUI(User user) {
		this.user = user;
	}

	/**
	 * 
	 */
	public void chooseProject() {

		Scanner input = new Scanner(System.in);

		System.out.println("Choose a project: ");

		US342Controller us342Controller = new US342Controller();

		int projectListSize = us342Controller.getProjectsFromUser(user).size();

		for (int indexProject = 0; indexProject < projectListSize; indexProject++) {

			String projectName = us342Controller.getProjectsFromUser(user).get(indexProject).getName();
			int projectID = us342Controller.getProjectsFromUser(user).get(indexProject).getIdCode();
			String projectIDString = Integer.toString(projectID);
			int caseNumber = indexProject + 1;

			System.out.println(caseNumber + "." + " " + projectName + " " + "ProjectID: " + projectIDString);

		}

	}

}
