package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class Finished implements TaskStateInterface {

	/**
	 * /**
	 * Method that checks if a task that is currently in the finished state meets the conditions to change to the ongoing state
	 *
	 * @param task to check if it has the conditions to change to the ongoing state
	 *
	 */
	@Override
	public void doAction(Task task) {
		OnGoing ongoingState = new OnGoing();
		if(ongoingState.isValid(task)) {
			task.setTaskState(ongoingState);
			task.setCurrentState(StateEnum.ONGOING);
		}
	}

	/**
	 * Method that checks if a certain task meets the conditions to be in the Finished state
	 *
	 * @param task to check if it meets the conditions
	 *
	 * @return TRUE if it meets the conditions, FALSE if not
	 */
	public boolean isValid(Task task) {

		if(task.getCurrentState() == StateEnum.ONGOING || task.getCurrentState() == StateEnum.CANCELLED ||
				task.getCurrentState() == StateEnum.STANDBY)
			return hasRequiredDates(task);

		return false;

	}

	/**
	 * This private method verifies if a task has the required dates to be in the StandBy state. It is called by the isValid method.
	 *
	 * @param task to verify dates
	 *
	 * @return TRUE if it has, FALSE if not
	 */
	private static boolean hasRequiredDates(Task task) {
		return (task.getStartDate() != null) && (task.getFinishDate() != null);
	}

}
