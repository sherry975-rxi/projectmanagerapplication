package project.model.taskstateinterface;

import org.springframework.hateoas.Link;
import project.model.StateEnum;
import project.model.Task;
import project.restcontroller.RestProjectController;

import java.util.ArrayList;
import java.util.List;

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
	 * Method that will get the actions available to the task depending of its state
	 *
	 * @return List of Actions
	 */
	@Override
	public List<String> getActions(){
		List<String> actions = new ArrayList<>();
		//Add actions
		return actions;
	}
}
