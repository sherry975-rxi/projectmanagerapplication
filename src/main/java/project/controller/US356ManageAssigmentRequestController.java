package project.controller;

import java.util.List;

import project.model.Project;
import project.model.TaskTeamRequest;

public class US356ManageAssigmentRequestController {

	Project selectedProject;
	TaskTeamRequest selectedAdditionRequest;

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

		List<String> listOfPendingAssignmentRequests = selectedProject.viewPendingTaskAssignementRequests();

		return listOfPendingAssignmentRequests;
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
	}

	/**
	 * This method approves an assignment request: Gets the target request from the
	 * index number, adding its collaborator field to the task in its task field
	 * 
	 * @param int
	 *            Index - the index number of the selected request
	 * 
	 * @return returns true or false if the request was found and approved
	 *         successfully
	 */
	public boolean approveAssignmentRequest() {
		if (selectedAdditionRequest != null) {
			selectedAdditionRequest.getTask().addProjectCollaboratorToTask(selectedAdditionRequest.getProjCollab());
			updateTaskState();
			deleteRequest();
			return true;
		} else
			return false;
	}

	/**
	 * This method rejects an assignment request: Gets the target request from the
	 * index number, adding its collaborator field to the task in its task field
	 * 
	 * @param int
	 *            Index - the index number of the selected request
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

	// TODO this method may be REMOVED once UpdateState is implemented in TaskState
	// Interface
	public void updateTaskState() {
		switch (this.selectedAdditionRequest.getTask().viewTaskStateName()) {
		case "Planned":
			selectedAdditionRequest.getTask().getTaskState().changeToAssigned();
			selectedAdditionRequest.getTask().getTaskState().changeToReady();
			break;
		case "StandBy":
			selectedAdditionRequest.getTask().getTaskState().changeToOnGoing();
			break;
		}
	}

	/**
	 * This method deletes an addition request; to be called after the approve or
	 * reject methods have resolved
	 * 
	 */
	public void deleteRequest() {
		selectedProject.deleteTaskAssignementRequest(this.selectedAdditionRequest);
	}
}