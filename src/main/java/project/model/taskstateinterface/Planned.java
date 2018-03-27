package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

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
}
