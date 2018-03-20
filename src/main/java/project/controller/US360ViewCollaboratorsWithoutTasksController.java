package project.controller;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

public class US360ViewCollaboratorsWithoutTasksController {

	private Project toSearch;
	private ProjectService projectService;
	private TaskService taskService;


	/**
	 * This controller receives a project and stores it in a private field
	 * 
	 * @param the
	 *            selected Project
	 */
	public US360ViewCollaboratorsWithoutTasksController(Project selectedProject) {
		toSearch = selectedProject;
	}

	/**
	 * This method takes the selected project stored upon instanciating the
	 * controller and copies the list of active Project collaborators. Then, it
	 * checks if each of the active team members have any tasks assigned. If not, it
	 * converts their data into a string to be displayed in the UI
	 * 
	 * 
	 * @return A list of all idle team members as a String
	 */
	public List<String> showCollaboratorsWithoutTasks() {
		List<String> idleProjectCollaborators = new ArrayList<>();

		List<ProjectCollaborator> activeProjectTeam = projectService.getActiveProjectTeam(toSearch);

		Integer index = 1;

		for (ProjectCollaborator other : activeProjectTeam) {
			if (!taskService.isCollaboratorActiveOnAnyTask(other)) {



				String idleProjCollabInfo = collaboratorDataAsString(other);

				idleProjectCollaborators.add("[" + index + "] \n" + idleProjCollabInfo);
				index++;
			}

		}

		return idleProjectCollaborators;
	}

	/**
	 * This is a utility method that converts a Project Collaborator object into a
	 * string of data to be displayed in the UI.
	 * 
	 * @param The
	 *            chosen ProjectCollaborator
	 * @return A string containing the User's ID number, Name, Email, Phone,
	 *         Function and Cost Per effort in project
	 */

	public String collaboratorDataAsString(ProjectCollaborator toView) {
		User collabUserData = toView.getUserFromProjectCollaborator();

		String dataOutput = collabUserData.getIdNumber();
		dataOutput += ": " + collabUserData.getName();
		dataOutput += " (" + collabUserData.getEmail();
		dataOutput += "; " + collabUserData.getPhone();
		dataOutput += ") - " + collabUserData.getFunction();
		dataOutput += " [COST/EFFORT: " + toView.getCollaboratorCost() + "]";

		return dataOutput;
	}
}
