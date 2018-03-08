package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class Ready implements TaskStateInterface {

	private Task task;

	public Ready() {
	}

	public Ready(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method checks if a transition to a certain state is valid
	 * 
	 * @return True if valid, False if not
	 */
	@Override
	public boolean isValid() {
		return !task.hasActiveDependencies();
	}

	/**
	 * This method changes the state of a Task to the "Created" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToCreated() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToPlanned() {
		boolean condition = false;
		TaskStateInterface statePlanned = new Planned(task);
		if (statePlanned.isValid()) {
			task.setTaskState(statePlanned);
			task.setCurrentState(StateEnum.PLANNED);
			condition = true;
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToAssigned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Ready" state. It does nothing
	 * in this state.
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToReady() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToOnGoing() {
		boolean condition = false;
		TaskStateInterface stateOnGoing = new OnGoing(task);
		if (stateOnGoing.isValid()) {
			task.setTaskState(stateOnGoing);
			task.setCurrentState(StateEnum.ONGOING);
			condition = true;
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToStandBy() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state. It does
	 * nothing in this state.
	 * 
	 * @return TRUE if state was changed and FALSE if state was not changed
	 */
	@Override
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToFinished() {
		return false;
	}
}