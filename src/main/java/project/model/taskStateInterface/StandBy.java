package project.model.taskStateInterface;

import project.model.Task;

public class StandBy implements TaskStateInterface {

	Task task;

	public StandBy(Task t) {
		this.task = t;
	}

	/**
	 * This method verifies if the transition to "StandBy" State is possible. If the
	 * state of the task is set to "OnGoing", the transition is possible, else, the
	 * method returns false
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isValid() {

		return (!task.doesTaskTeamHaveActiveUsers());

	}

	public void changeToCreated() {

	}

	public void changeToPlanned() {

	}

	public void changeToAssigned() {

	}

	public void changeToReady() {

	}

	public void changeToOnGoing() {

	}

	public void changeToStandBy() {

		if (isTransitionToStandByPossible()) {

			TaskStateInterface taskInterface = new StandBy(task);
			if (taskInterface.isValid())
				task.setTaskState(taskInterface);
		}

	}

	public void changeToCancelled() {

	}

	public void changeToFinished() {

	}

	/**
	 * This method verifies if the transition to the “Created” state of a Task is
	 * possible
	 * 
	 * @return false
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
	 * @return false
	 */
	public boolean isTransitionToAssignedPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToReadyPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToOnGoingPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “StandBy" state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToStandByPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToCancelledPossible() {

		return false;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToFinishedPossible() {

		return false;
	}

}
