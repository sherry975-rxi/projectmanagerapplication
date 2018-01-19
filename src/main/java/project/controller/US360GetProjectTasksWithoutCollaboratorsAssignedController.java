package project.controller;

import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;

public class US360GetProjectTasksWithoutCollaboratorsAssignedController {

	ProjectRepository myProjRepo;

	public US360GetProjectTasksWithoutCollaboratorsAssignedController() {
		this.myProjRepo = Company.getTheInstance().getProjectsRepository();
	}

	/**
	 * Returns a list of tasks that have no collaborator assigned to them. - US360
	 * 
	 * @param proj
	 *            The project to search for its tasks without collaborators assigned
	 * @return Task List
	 */

	public List<Task> getProjectTasksWithoutCollaboratorsAssigned(Project proj) {
		return proj.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned();

	}

}
