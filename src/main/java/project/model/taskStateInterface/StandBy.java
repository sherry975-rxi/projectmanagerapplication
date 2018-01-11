package project.model.taskStateInterface;

import project.model.Task;

public class StandBy implements TaskStateInterface {

	Task task;

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

	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	public void changeToPlanned() {

	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 * @return Void
	 */
	public void changeToAssigned() {

	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 * @return Void
	 */
	public void changeToReady() {

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

		return false;

	}

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToPlannedPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return False
	 */
	public boolean isTransitionToAssignedPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return False
	 */
	public boolean isTransitionToReadyPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return True
	 */
	public boolean isTransitionToOnGoingPossible() {

		return true;

	}

	/**
	 * This method verifies if the transition to the “StandBy" state of a Task is
	 * possible
	 * 
	 * @return False
	 */
	public boolean isTransitionToStandByPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return True
	 */
	public boolean isTransitionToCancelledPossible() {

		return true;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return True
	 */
	public boolean isTransitionToFinishedPossible() {

		return true;
	}

}
