package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

import javax.swing.plaf.nimbus.State;

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

		Boolean isValid = false;

		if(task.getCurrentState() == StateEnum.ONGOING || task.getCurrentState() == StateEnum.CANCELLED ||
				task.getCurrentState() == StateEnum.STANDBY) {
			if (task.getStartDate() != null && task.getFinishDate() != null) {
				isValid = true;
			}
		}
		return isValid;

	}

}
