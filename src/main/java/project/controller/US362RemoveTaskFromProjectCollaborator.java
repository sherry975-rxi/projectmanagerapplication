/**
 * 
 */
package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.TaskRepository;

/**
 * @author Group3
 *
 */
public class US362RemoveTaskFromProjectCollaborator {

	int projectIDtoInstantiate;
	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private Project project;
	private Company company;

	/**
	 * Constructor
	 * 
	 */
	public US362RemoveTaskFromProjectCollaborator() {
	}

	/**
	 * Constructor
	 * 
	 * @param projectIDtoInstantiate
	 */
	public US362RemoveTaskFromProjectCollaborator(int projectIDtoInstantiate) {
		this.projectIDtoInstantiate = projectIDtoInstantiate;
		company = Company.getTheInstance();
		projectRepository = company.getProjectsRepository();
		project = projectRepository.getProjById(projectIDtoInstantiate);
		taskRepository = project.getTaskRepository();
	}

	/**
	 * This method returns the tasks from a specific project
	 * 
	 * @param userInputForProjectID
	 *            Project ID to get the tasks from
	 * 
	 * @return List of Tasks of the chosen Project
	 */
	public List<Task> getTasksFromAProject() {

		List<Task> tasksFromProject = new ArrayList<>();

		Project projectToGetTasks = projectRepository.getProjById(this.projectIDtoInstantiate);

		tasksFromProject.addAll(projectToGetTasks.getTaskRepository().getProjectTaskRepository());

		return tasksFromProject;
	}

	/**
	 * This method returns the List of Collaborators from a specific task
	 * 
	 * @param taskID
	 *            Task ID to get the collaborators associated with a specific task
	 * 
	 * @return List of Collaborators from a chosen Task
	 */
	public List<TaskCollaborator> getTaskCollaboratorsFromTask(String taskID) {

		Task taskToGetListOfCollaborator = Company.getTheInstance().getProjectsRepository()
				.getProjById(projectIDtoInstantiate).getTaskRepository().getTaskByID(taskID);

		return taskToGetListOfCollaborator.getTaskTeam();
	}

	/**
	 * This method removes a collaborator from the Task Team of a specific task
	 * 
	 * @param taskCollaboratorToRemoveFromTaskTeam
	 *            task Collaborator To Remove From TaskTeam of a specific task
	 * @param taskID
	 *            Task ID to get the collaborators associated with a specific task
	 * @return TRUE if collaborator is removed and FALSE if collaborator is not
	 *         removed
	 */
	public boolean removeCollaboratorFromTask(TaskCollaborator taskCollaboratorToRemoveFromTaskTeam, String taskID) {

		Task taskToGetListOfCollaborator = Company.getTheInstance().getProjectsRepository()
				.getProjById(projectIDtoInstantiate).getTaskRepository().getTaskByID(taskID);

		ProjectCollaborator projCollaboratorToRemoveFromTaskTeam = taskCollaboratorToRemoveFromTaskTeam
				.getProjectCollaboratorFromTaskCollaborator();

		boolean removed = false;
		if (taskToGetListOfCollaborator.removeProjectCollaboratorFromTask(projCollaboratorToRemoveFromTaskTeam)) {
			removed = true;
		}
		return removed;
	}

}
