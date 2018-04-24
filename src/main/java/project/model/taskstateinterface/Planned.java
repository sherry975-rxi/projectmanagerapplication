package project.model.taskstateinterface;

import org.springframework.hateoas.Link;
import project.model.StateEnum;
import project.model.Task;
import project.restcontroller.RestProjectController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class Planned implements TaskStateInterface {

	/**
	 * Method that checks if a task that is currently in the planned state meets the conditions to change to the ready state.
	 *
	 * @param task to check if it has the conditions to change to the ready state
	 */
	@Override
	public void doAction(Task task) {

		Ready readyState = new Ready();
		if(readyState.isValid(task)) {
			task.setTaskState(readyState);
			task.setCurrentState(StateEnum.READY);
		}
	}

	/**
	 * Method that checks if a certain task meets the conditions to be in the planned state
	 *
	 * @param task to check if it meets the conditions
	 *
	 * @return TRUE if it meets the conditions, FALSE if not
	 */
	public boolean isValid(Task task) {

		if(task.getCurrentState() == StateEnum.CREATED) {
			return ((task.getEstimatedTaskStartDate() != null) || (task.getTaskDeadline() != null) ||
					task.doesTaskTeamHaveActiveUsers());
		} else if (task.getCurrentState() == StateEnum.READY) {
			return ((task.getEstimatedTaskStartDate() == null) || (task.getTaskDeadline() == null) ||
					!task.doesTaskTeamHaveActiveUsers());
		} else {
			return false;
		}
	}

	/**
	 * Method that creates the rels available to this state and adds the links to the task
	 *
	 * @param task Task to get links from
	 * @param projectId Id of the project to create the links
	 */
	@Override
	public void addRels(Task task, int projectId){

		//Not yet implemented - PATCH
		Link reference = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).withRel("Add Budget and Estimated Cost");
		task.add(reference);

		//Not yet implemented - POST
		Link reference2 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("team").withRel("Add Project Collaborator");
		task.add(reference2);

		Link reference3 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("requests/assignmentRequest").withRel("Create Assignment Request");
		task.add(reference3);

		Link reference4 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("requests/removalRequest").withRel("Create Removal Request");
		task.add(reference4);
	}
}
