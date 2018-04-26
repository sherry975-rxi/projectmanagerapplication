package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

import java.util.ArrayList;
import java.util.List;


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

		Boolean isValid = true;

		if(task.getCurrentState() != StateEnum.PLANNED)
			isValid = false;
		if(task.getEstimatedTaskStartDate() != null && task.getTaskDeadline() != null && !task.doesTaskTeamHaveActiveUsers())
			isValid = false;
		if((Double.compare(task.getEstimatedTaskEffort(),0.0) == 0) && (Double.compare(task.getTaskBudget(),0.0) == 0))
			isValid = false;
		if(task.hasActiveDependencies())
			isValid = false;

		return isValid;

	}

	/**
	 * Method that will get the actions available to the task depending of its state
	 *
	 * @return List of Actions
	 */
	@Override
	public List<String> getActions(){
		List<String> actions = new ArrayList<>();
		actions.add("7");
		actions.add("2");
		actions.add("8");
		actions.add("9");
		actions.add("5");
		actions.add("6");
		actions.add("3");
		return actions;
	}
}