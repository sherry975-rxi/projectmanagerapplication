package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class StandBy implements TaskStateInterface {

	private Task task;

	/**
	 * Constructor of StandBy class
	 * 
	 * @param t
	 *            The task that will be associated to the parameter Task of the
	 *            instance of the object StandBy
	 */
	public StandBy(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the transition to "StandBy" State is possible. If the
	 * state of the task is set to "OnGoing" and doesn't have any active users and
	 * doesnt have any finish date, the method returns true, else, returns false
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isValid() {
		return !task.doesTaskTeamHaveActiveUsers() && task.getFinishDate() == null;
	}

	/**
	 * This method changes the state of a Task to the "Created" state
	 */
	@Override
	public boolean changeToCreated() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 */
	@Override
	public boolean changeToPlanned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 */
	@Override
	public boolean changeToAssigned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 */
	@Override
	public boolean changeToReady() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state
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
	 */
	@Override
	public boolean changeToStandBy() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state
	 */
	@Override
	public boolean changeToCancelled() {
		boolean condition = false;
		TaskStateInterface stateCancelled = new Cancelled(task);
		if (stateCancelled.isValid()) {
			task.setTaskState(stateCancelled);
			task.setCurrentState(StateEnum.CANCELLED);
			condition = true;
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state
	 * 
	 */
	@Override
	public boolean changeToFinished() {
		boolean condition = false;
		TaskStateInterface finishedState = new Finished(task);
		if (finishedState.isValid()) {
			task.setTaskState(finishedState);
			task.setCurrentState(StateEnum.FINISHED);
			condition = true;
		}
		return condition;
	}
}
