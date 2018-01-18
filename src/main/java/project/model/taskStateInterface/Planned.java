package project.model.taskStateInterface;

import project.model.Task;

public class Planned implements TaskStateInterface {

	Task task;

	public Planned(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the State "Planned" requirements are fulfilled for a
	 * specific Task. It has to have estimated dates and no users working on it. If
	 * so, it returns true, If not, it returns false
	 * 
	 * @return true if is Valid, false if not
	 */
	public boolean isValid() {
		boolean validation = false;
		if (task.getEstimatedTaskStartDate() != null && task.getTaskDeadline() != null
				&& !task.doesTaskTeamHaveActiveUsers()) {
			validation = true;
		}

		return validation;
	}

	/**
	 * This method changes the state of a Task to the "Created" state.
	 * 
	 */
	public boolean changeToCreated() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 */
	public boolean changeToPlanned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 */
	public boolean changeToAssigned() {
		boolean condition = false;
		if (isTransitionToAssignedPossible()) {
			TaskStateInterface stateAssigned = new Assigned(task);
			if (stateAssigned.isValid()) {
				task.setTaskState(stateAssigned);
				condition = true;
			}
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 */
	public boolean changeToReady() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state.
	 * 
	 */
	public boolean changeToOnGoing() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state.
	 * 
	 */
	public boolean changeToStandBy() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state.
	 * 
	 */
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state.
	 * 
	 */
	public boolean changeToFinished() {
		return false;

	}

	/**
	 * This method verifies if the transition to the “Created” state of a Task is
	 * possible.
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToCreatedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible.
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToPlannedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToAssignedPossible() {
		return true;
	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToReadyPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToOnGoingPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToStandByPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToCancelledPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToFinishedPossible() {
		return false;
	}

}
