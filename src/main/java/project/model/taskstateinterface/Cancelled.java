package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

import java.util.Calendar;

public class Cancelled implements TaskStateInterface {


	/**
	 * Method that checks if a task that is currently in the cancelled state meets the conditions to change to the finished state
	 *
	 * @param task to check if it has the conditions to change to the finished state
	 */
	@Override
	public void doAction(Task task) {
		Finished finishedState = new Finished();
		if(finishedState.isValid(task)) {
			task.setTaskState(finishedState);
			task.setCurrentState(StateEnum.FINISHED);
			task.setFinishDate(Calendar.getInstance());
			task.removeAllCollaboratorsFromTaskTeam();
			
	}
	}

	/**
	 * Method that checks if a certain task meets the conditions to be in the Cancelled state
	 *
	 * @param task to check if it meets the conditions
	 *
	 * @return TRUE if it meets the conditions, FALSE if not
	 */
	public boolean isValid(Task task) {

		Boolean isValid = false;

		if((task.getCurrentState() == StateEnum.ONGOING) || (task.getCurrentState() == StateEnum.STANDBY)) {
			if (task.getFinishDate() == null && task.getStartDate() != null) {
				if (task.getCancelDate() != null) {
					isValid = true;
				}
			}
		}
		return isValid;
	}
}
