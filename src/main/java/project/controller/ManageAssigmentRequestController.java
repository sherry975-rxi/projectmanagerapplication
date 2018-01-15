package project.controller;

import java.util.List;

import project.model.Project;
import project.model.TaskTeamRequest;

public class ManageAssigmentRequestController {

	/*
	 * This controller manages Addition Requests by Project Collaborators
	 * 
	 * respond to US 204v2
	 * 
	 * @param Project - selected project
	 * 
	 * @param int Index - the index number of the selected request
	 */

	/**
	 * This method shows a list of strings from all addition requests
	 * 
	 * @param project
	 *            - the selected Project
	 * 
	 * @return returns a list of strings with all addition requests
	 */
	public List<String> showAllAdditionRequests(Project project) {
		return project.viewPendingTaskAssignementRequests();
	}

	/**
	 * This method selects the addition request from the list, converting its index
	 * into a TaskTeamRequest to be handled by the controller
	 * 
	 * @param project
	 *            - the selected Project
	 * @param int
	 *            Index - the index number of the selected request
	 * 
	 * @return returns the TaskTeamRequest to be handled by the controller
	 */
	public TaskTeamRequest selectAdditionRequest(Project project, int index) {
		TaskTeamRequest selectedRequest = project.getAssignmentRequestsList().get(index);
		return selectedRequest;
	}

	/**
	 * This method approves an addition request Gets the target request from the
	 * index number, adding its collaborator field to the task in its task field
	 * 
	 * 
	 * @param project
	 *            - the selected Project
	 * @param int
	 *            Index - the index number of the selected request
	 * 
	 * @return returns a list of strings with all addition requests
	 */
	public void approveAdditionRequest(Project project, int index) {
		TaskTeamRequest toApprove = selectAdditionRequest(project, index);

		toApprove.getTask().addProjectCollaboratorToTask(toApprove.getProjCollab());

		deleteRequest(project, toApprove);
	}

	/**
	 * This method rejects an addition request, simply calling the delete method
	 * 
	 * @param project
	 * @param index
	 */
	public void rejectAdditionRequest(Project project, int index) {
		TaskTeamRequest toReject = selectAdditionRequest(project, index);

		deleteRequest(project, toReject);

	}

	/**
	 * This method deletes an addition request; to be called after the approve or
	 * reject methods have resolved
	 * 
	 * @param TaskTeamRequest
	 */
	public void deleteRequest(Project project, TaskTeamRequest toDelete) {

		project.deleteTaskAssignementRequest(toDelete);
	}

}
