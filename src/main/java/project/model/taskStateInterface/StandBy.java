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
	public StandBy(Task t) {
		this.task = t;
	}

	/**
	 * Empty Constructor of StandBy class
	 * 
	 */
	public StandBy() {
	}

	/**
	 * This method verifies if the transition to "StandBy" State is possible. If the
	 * state of the task is set to "OnGoing" and doesn't have any active users, the
	 * method returns true, else, returns false
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isValid() {

		return (!task.doesTaskTeamHaveActiveUsers());

	}

	/**
	 * This method changes the state of a Task to the "Created" state
	 * 
	 * @return Void
	 */
	public void changeToCreated() {

		if (isTransitionToCreatedPossible()) {

			TaskStateInterface stateCreated = new Created(task);
			if (stateCreated.isValid())
				task.setTaskState(stateCreated);
		}

	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	public void changeToPlanned() {

		if (isTransitionToPlannedPossible()) {

			TaskStateInterface statePlanned = new Planned(task);
			if (statePlanned.isValid())
				task.setTaskState(statePlanned);
		}

	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 * @return Void
	 */
	public void changeToAssigned() {

		if (isTransitionToAssignedPossible()) {

			TaskStateInterface stateAssigned = new Assigned(task);
			if (stateAssigned.isValid())
				task.setTaskState(stateAssigned);
		}

	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 * @return Void
	 */
	public void changeToReady() {

		if (isTransitionToReadyPossible()) {

			TaskStateInterface stateReady = new Ready(task);
			if (stateReady.isValid())
				task.setTaskState(stateReady);
		}

	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	public void changeToOnGoing() {

		if (isTransitionToOnGoingPossible()) {
			TaskStateInterface taskInterface = new OnGoing(task);
			if (taskInterface.isValid())
				task.setTaskState(taskInterface);

		}

	}

	/**
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	public void changeToStandBy() {

	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state
	 * 
	 * @return Void
	 */
	public void changeToCancelled() {

		if (isTransitionToCancelledPossible()) {
			TaskStateInterface taskInterface = new Cancelled(task);
			if (taskInterface.isValid())
				task.setTaskState(taskInterface);

		}

	}

	/**
	 * This method changes the state of a Task to the "Finished" state
	 * 
	 * @return Void
	 */
	public void changeToFinished() {

		if (isTransitionToFinishedPossible()) {
			TaskStateInterface taskInterface = new Finished(task);
			if (taskInterface.isValid())
				task.setTaskState(taskInterface);

		}

	}

	/**
	 * This method verifies if the transition to the “Created” state of a Task is
	 * possible
	 * 
	 * @return False
	 */
	public boolean isTransitionToCreatedPossible() {

		boolean valid = false;
		return valid;

	}

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToPlannedPossible() {

		boolean valid = false;
		return valid;

	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return False
	 */
	public boolean isTransitionToAssignedPossible() {

		boolean valid = false;
		return valid;

	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return False
	 */
	public boolean isTransitionToReadyPossible() {

		boolean valid = false;
		return valid;

	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return True
	 */
	public boolean isTransitionToOnGoingPossible() {

		boolean valid = true;
		return valid;

	}

	/**
	 * This method verifies if the transition to the “StandBy" state of a Task is
	 * possible
	 * 
	 * @return False
	 */
	public boolean isTransitionToStandByPossible() {

		boolean valid = false;
		return valid;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return True
	 */
	public boolean isTransitionToCancelledPossible() {

		boolean valid = true;
		return valid;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return True
	 */
	public boolean isTransitionToFinishedPossible() {

		boolean valid = true;
		return valid;
	}

}
