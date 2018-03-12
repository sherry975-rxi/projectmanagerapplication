package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class OnGoing implements TaskStateInterface {

	/**
	 * Method that checks if a task that is currently in the OnGoing state meets the
	 * conditions to change to the standBy state, cancelled state or finished state
	 *
	 * @param task
	 *            to check if it has the conditions to change to the standBy state,
	 *            cancelled state or finished state
	 */
	@Override
	public void doAction(Task task) {
		StandBy standByState = new StandBy();
		if (standByState.isValid(task)) {
			task.setTaskState(standByState);
			task.setCurrentState(StateEnum.STANDBY);
		}

		Cancelled cancelledState = new Cancelled();
		if (cancelledState.isValid(task)) {
			task.setTaskState(cancelledState);
			task.setCurrentState(StateEnum.CANCELLED);
		}

		Finished finishedState = new Finished();
		if (finishedState.isValid(task)) {
			task.setTaskState(finishedState);
			task.setCurrentState(StateEnum.FINISHED);
			task.setFinishDate();
		}
	}

	/**
	 * Method that checks if a certain task meets the conditions to be in the
	 * OnGoing state
	 *
	 * @param task
	 *            to check if it meets the conditions
	 *
	 * @return TRUE if it meets the conditions, FALSE if not
	 */
	public boolean isValid(Task task) {
		return ((task.getTaskState() instanceof Ready) || (task.getTaskState() instanceof Finished)
				|| (task.getTaskState() instanceof StandBy)) && (task.getEstimatedTaskStartDate() != null)
				&& (task.getTaskDeadline() != null) && task.doesTaskTeamHaveActiveUsers()
				&& (task.getStartDate() != null) && (task.getFinishDate() == null) && (task.getCancelDate() == null)
				&& (task.getEstimatedTaskEffort() != 0) && (task.getTaskBudget() != 0);
	}

}
