package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.TaskTeamRequest;

public class ManageAssigmentRequestController {

	int projectID;
	TaskTeamRequest selectedAdditionRequest;

	/*
	 * This controller manages Addition Requests by Project Collaborators
	 * 
	 * respond to US 204v2
	 * 
	 * @param ProjectID - the ID of the selected Project
	 * 
	 */
	public ManageAssigmentRequestController(int selectedProjectID) {
		this.projectID = selectedProjectID;
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
		Project toViewRequests = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);

		if (toViewRequests == null) {
			System.out.println("Project Not found!");
			List<String> empty = new ArrayList<>();
			return empty;
		} else {
			return toViewRequests.viewPendingTaskAssignementRequests();
		}
	}

	/**
	 * This method selects the addition request from the list, converting its index
	 * into a TaskTeamRequest to be handled by the controller. If index is invalid,
	 * returns false
	 * 
	 * @param int
	 *            Index - the index number of the selected request
	 * 
	 * @return returns true if request us found, false if not
	 */
	public boolean selectAssignmentRequest(int index) {
		Project toSelectRequest = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);

		if (index >= 0 && index < toSelectRequest.getAssignmentRequestsList().size()) {
			this.selectedAdditionRequest = toSelectRequest.getAssignmentRequestsList().get(index);
			System.out.println("Selected: " + this.selectedAdditionRequest.viewStringRepresentation());
			System.out.println("");
			return true;
		} else {
			this.selectedAdditionRequest = null;
			System.out.println("Request not found!");
			System.out.println();
			return false;
		}

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

			deleteRequest();
			System.out.println("Request approved!");
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

			System.out.println("Request rejected!");
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
		Project toDeleteRequest = Company.getTheInstance().getProjectsRepository().getProjById(this.projectID);

		toDeleteRequest.deleteTaskAssignementRequest(this.selectedAdditionRequest);
	}

}
