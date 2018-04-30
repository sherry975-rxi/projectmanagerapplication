package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

		return ((task.getCurrentState() == StateEnum.ONGOING)
				|| (task.getCurrentState() == StateEnum.STANDBY))
				&& hasRequiredDates(task);

	}

	/**
	 * This private method verifies if a task has the required dates to be in the StandBy state. It is called by the isValid method.
	 *
	 * @param task to verify dates
	 *
	 * @return TRUE if it has, FALSE if not
	 */
	private static boolean hasRequiredDates(Task task) {
		return (task.getStartDate() != null) && (task.getFinishDate() == null) && (task.getCancelDate() != null);
	}

	/**
	 * Method that will get the actions available to the task depending of its state
	 *
	 * @return List of Actions
	 */
	@Override
	public List<String> getActions(){
		List<String> actions = new ArrayList<>();
		actions.add("3");
		return actions;
	}
}
