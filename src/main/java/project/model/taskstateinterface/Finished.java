package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class Finished implements TaskStateInterface {

	Task task;

	public Finished(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method checks if a transition to a certain state is valid
	 * 
	 * @return True if valid, False if not
	 */
	@Override
	public boolean isValid() {
		return task.getFinishDate() != null;
	}

	/**
	 * This method changes the state of a Task to the "Created" state
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
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToAssigned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
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
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToStandBy() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToFinished() {
		return false;
	}

}
