package project.model.taskstateinterface;

import org.springframework.hateoas.Link;
import project.model.StateEnum;
import project.model.Task;
import project.restcontroller.RestProjectController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class Ready implements TaskStateInterface {

	/**
	 * Method that checks if a task that is currently in the ready state meets the conditions to change to the planned state,
	 * or ongoing state.
	 *
	 * @param task to check if it has the conditions to change to the planned state or to the ongoing state
	 */
	@Override
	public void doAction(Task task) {

		//Checks if the task can change to the planned state
		Planned plannedState = new Planned();
		if(plannedState.isValid(task)) {
			task.setTaskState(plannedState);
			task.setCurrentState(StateEnum.PLANNED);
		}

		//Checks if the task can change to the ongoing state
		OnGoing ongoingState = new OnGoing();
		if(ongoingState.isValid(task)) {
			task.setTaskState(ongoingState);
			task.setCurrentState(StateEnum.ONGOING);
		}
	}

	/**
	 * Method that checks if a certain task meets the conditions to be in the ready state
	 *
	 * @param task to check if it meets the conditions
	 *
	 * @return TRUE if it meets the conditions, FALSE if not
	 */
	public boolean isValid(Task task) {

		Boolean isValid = true;

		if(task.getCurrentState() != StateEnum.PLANNED)
			isValid = false;
		if(task.getEstimatedTaskStartDate() != null && task.getTaskDeadline() != null && !task.doesTaskTeamHaveActiveUsers())
			isValid = false;
		if((Double.compare(task.getEstimatedTaskEffort(),0.0) == 0) && (Double.compare(task.getTaskBudget(),0.0) == 0))
			isValid = false;
		if(task.hasActiveDependencies())
			isValid = false;

		return isValid;

	}

	/**
	 * Method that creates the rels available to this state and adds the links to the task
	 *
	 * @param task Task to get links from
	 * @param projectId Id of the project to create the links
	 */
	@Override
	public void addRels(Task task, int projectId){

        //Not implemented yet - PATCH
		Link reference1 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).withRel("Add Start Date");
		task.add(reference1);

		//Not implemented yet - POST
		Link reference2 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("team").withRel("Add Project Collaborator");
		task.add(reference2);

		//Not implemented yet - DELETE
		Link reference3 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("team").withRel("Remove all collaborators");
		task.add(reference3);

		Link reference4 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("requests/assignmentRequest").withRel("Create Assignment Request");
		task.add(reference4);

		Link reference5 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("requests/removalRequest").withRel("Create Removal Request");
		task.add(reference5);
	}
}