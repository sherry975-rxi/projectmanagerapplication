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

		return (task.getCurrentState() == StateEnum.ONGOING) &&
				hasRequiredDates(task) &&
				!task.doesTaskTeamHaveActiveUsers();

	}

	/**
	 * This private method verifies if a task has the required dates to be in the StandBy state. It is called by the isValid method.
	 *
	 * @param task to verify dates
	 *
	 * @return TRUE if it has, FALSE if not
	 */
	private static boolean hasRequiredDates(Task task) {
		return (task.getStartDate() != null) && (task.getFinishDate() == null) && (task.getCancelDate() == null);
	}
}
