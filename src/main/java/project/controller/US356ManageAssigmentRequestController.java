package project.controller;

import project.model.Project;
import project.model.ProjectContainer;
import project.model.TaskTeamRequest;

import java.util.List;

public class US356ManageAssigmentRequestController {

	Project selectedProject;
	TaskTeamRequest selectedAdditionRequest;
	ProjectContainer projectContainer = new ProjectContainer();

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

		return selectedProject.viewPendingTaskAssignementRequests();
	}

	/**
	 * Sets the seletedAdditionRequest. The result is equal to the
	 * getPendingTaskAssigmentRequest().get(index)
	 * 
	 * @param index
	 *            Index of the request.
	 */
	public void setSelectedAdditionRequest(int index) {
		this.selectedAdditionRequest = selectedProject.getPendingTaskAssignementRequests().get(index);
		projectContainer.saveProjectInRepository(selectedProject);
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
			selectedAdditionRequest.getTask().addProjectCollaboratorToTask(selectedAdditionRequest.getProjCollab());
			deleteRequest();
			projectContainer.saveProjectInRepository(selectedProject);
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
			projectContainer.saveProjectInRepository(selectedProject);
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

		selectedProject.deleteTaskAssignementRequest(this.selectedAdditionRequest);
		projectContainer.saveProjectInRepository(selectedProject);
	}
}