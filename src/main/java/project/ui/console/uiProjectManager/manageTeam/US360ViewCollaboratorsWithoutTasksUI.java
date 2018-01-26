package project.ui.console.uiProjectManager.manageTeam;

import java.util.List;

import project.controller.US360ViewCollaboratorsWithoutTasksController;
import project.model.Project;
import project.model.User;
import project.ui.console.uiProjectManager.ProjectManagerMainMenuUI;

public class US360ViewCollaboratorsWithoutTasksUI {

	public void viewUnassignedCollaborators(Project selectedProject, User user) {

		List<String> idleCollaboratorsInfo;

		US360ViewCollaboratorsWithoutTasksController controller = new US360ViewCollaboratorsWithoutTasksController(
				selectedProject);

		idleCollaboratorsInfo = controller.showCollaboratorsWithoutTasks();

		if (idleCollaboratorsInfo.isEmpty())
			System.out.println("Every Team member has one or more tasks assigned!");
		else {
			System.out.println("The following Collaborators have no Tasks assigned");
			for (String other : idleCollaboratorsInfo) {
				System.out.println(other);
				System.out.println("");
			}
		}
		ProjectManagerMainMenuUI projectManagerMainMenuUI = new ProjectManagerMainMenuUI(user, selectedProject);
		projectManagerMainMenuUI.displayOptions();

	}
}
