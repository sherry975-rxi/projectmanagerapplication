package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

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

		return (task.getTaskState() instanceof Planned) &&
				(task.getEstimatedTaskStartDate() != null) &&
				(task.getTaskDeadline() != null) &&
				task.doesTaskTeamHaveActiveUsers() &&
				(task.getStartDate() == null) &&
				(task.getFinishDate() == null) &&
				(task.getCancelDate() == null) &&
				(task.getEstimatedTaskEffort() != 0) &&
				(task.getTaskBudget() != 0) &&
				!task.hasActiveDependencies();
	}
}