package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class StandBy implements TaskStateInterface {

	/**
	 * Method that checks if a task that is currently in the standBy state meets the
	 * conditions to change to the onGoing state, cancelled state or finished state
	 *
	 * @param task
	 *            to check if it has the conditions to change to the onGoing state,
	 *            cancelled state or finished state
	 * @param task
	 */
	@Override
	public void doAction(Task task) {
		OnGoing onGoingState = new OnGoing();
		if (onGoingState.isValid(task)) {
			task.setTaskState(onGoingState);
			task.setCurrentState(StateEnum.ONGOING);
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
			task.removeAllCollaboratorsFromTaskTeam();
		}
	}

	/**
	 * Method that checks if a certain task meets the conditions to be in the
	 * StandBy state
	 *
	 * @param task
	 *            to check if it meets the conditions
	 *
	 * @return TRUE if it meets the conditions, FALSE if not
	 */
	public boolean isValid(Task task) {
		Boolean isValid = true;

		if (task.getCurrentState() != StateEnum.ONGOING)
			isValid = false;
		if (task.getStartDate() == null && task.getFinishDate() != null && task.getCancelDate() != null)
			isValid = false;
		if (task.doesTaskTeamHaveActiveUsers())
			isValid = false;

		return isValid;

	}
}
