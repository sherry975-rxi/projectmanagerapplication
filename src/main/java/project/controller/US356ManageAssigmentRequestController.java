package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Project;
import project.model.Task;
import project.model.TaskTeamRequest;

public class US356ManageAssigmentRequestController {

	Project selectedProject;
	TaskTeamRequest selectedAdditionRequest;
	Task selectedTask;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	/*
	 * Default constructor
	 */

	public US356ManageAssigmentRequestController() {

	}

	/*
	 * Getters and Setters
	 */
	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}

	public TaskTeamRequest getSelectedAdditionRequest() {
		return selectedAdditionRequest;
	}

	public void setSelectedAdditionRequest(TaskTeamRequest selectedAdditionRequest) {
		this.selectedAdditionRequest = selectedAdditionRequest;
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	/*
	 * This controller manages Addition Requests by Project Collaborators * respond
	 * to US 356
	 * 
	 * @param ProjectID - the ID of the selected Project
	 * 
	 */
	public US356ManageAssigmentRequestController(Project project) {
		this.selectedProject = project;
		this.selectedAdditionRequest = null;
	}

	/**
	 * This method shows a list of strings from all addition requests from the
	 * selected project
	 * 
	 * 
	 * @return returns a list of strings with all addition requests, or an empty
	 *         list if the project is null
	 */
	public List<String> showAllAssignmentRequests() {

		return taskService.viewAllProjectTaskAssignmentRequests(selectedProject);
	}

	/**
	 * Sets the seletedAdditionRequest. The result is equal to the
	 * getPendingTaskAssigmentRequest().get(index)
	 * 
	 * @param index
	 *            Index of the request.
	 */
	public void setSelectedAdditionRequest(int index) {
		this.selectedAdditionRequest = taskService.getAllProjectTaskAssignmentRequests(selectedProject).get(index);
		this.selectedTask = selectedAdditionRequest.getTask();
	}

	/**
	 * This method approves an assignment request: Gets the target request from the
	 * index number, adding its collaborator field to the task in its task field
	 *
	 * 
	 * @return returns true or false if the request was found and approved
	 *         successfully
	 */
	public boolean approveAssignmentRequest() {
		if (selectedAdditionRequest != null) {
			selectedTask.addProjectCollaboratorToTask(selectedAdditionRequest.getProjCollab());
			deleteRequest();

			return true;
		} else
			return false;
	}

	/**
	 * This method rejects an assignment request: Gets the target request from the
	 * index number, adding its collaborator field to the task in its task field
	 *
	 * 
	 * @return returns true or false if the request was found and rejected
	 *         successfully
	 */
	public boolean rejectAssignmentRequest() {
		if (selectedAdditionRequest != null) {
			deleteRequest();

			return true;
		} else
			return false;
	}

	/**
	 * This method deletes an addition request; to be called after the approve or
	 * reject methods have resolved
	 * 
	 */
	public void deleteRequest() {

		selectedTask.deleteTaskAssignementRequest(selectedAdditionRequest.getProjCollab());
		taskService.saveTask(selectedTask);
	}
}