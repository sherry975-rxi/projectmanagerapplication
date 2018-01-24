package project.model.taskStateInterface;

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

		boolean isValid = false;

		if (!task.doesTaskTeamHaveActiveUsers() && task.getFinishDate() == null) {
			isValid = true;
		}

		return isValid;

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
		if (isTransitionToOnGoingPossible()) {
			TaskStateInterface stateOnGoing = new OnGoing(task);
			if (stateOnGoing.isValid()) {
				task.setTaskState(stateOnGoing);
				condition = true;
			}
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
		if (isTransitionToCancelledPossible()) {
			TaskStateInterface stateCancelled = new Cancelled(task);
			if (stateCancelled.isValid()) {
				task.setTaskState(stateCancelled);
				condition = true;
			}
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
		if (isTransitionToFinishedPossible()) {
			TaskStateInterface Finished1 = new Finished(task);
			if (Finished1.isValid()) {
				task.setTaskState(Finished1);
				condition = true;
			}
		}
		return condition;
	}

	/**
	 * This method verifies if the transition to the “Created” state of a Task is
	 * possible
	 */
	@Override
	public boolean isTransitionToCreatedPossible() {
		return false;

	}

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return FALSE
	 */
	@Override
	public boolean isTransitionToPlannedPossible() {
		return false;

	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return FALSE
	 */
	@Override
	public boolean isTransitionToAssignedPossible() {
		return false;

	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return FALSE
	 */
	@Override
	public boolean isTransitionToReadyPossible() {
		return false;

	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return TRUE
	 */
	@Override
	public boolean isTransitionToOnGoingPossible() {
		return true;

	}

	/**
	 * This method verifies if the transition to the “StandBy" state of a Task is
	 * possible
	 * 
	 * @return FALSE
	 */
	@Override
	public boolean isTransitionToStandByPossible() {
		return false;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return TRUE
	 */
	@Override
	public boolean isTransitionToCancelledPossible() {
		return true;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return TRUE
	 */
	@Override
	public boolean isTransitionToFinishedPossible() {
		return true;
	}

}
