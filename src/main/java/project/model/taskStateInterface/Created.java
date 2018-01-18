package project.model.taskStateInterface;

import project.model.Task;

public class Created implements TaskStateInterface {

	Task task;

	/**
	 * Constructor to create State Created
	 * 
	 * @param task
	 *            Task to set State upon
	 */
	public Created(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the State "Created" requirements are fulfilled for a
	 * specific Task. The requirements are If the taskID and the description of the
	 * task is not null, the task is created.
	 * 
	 * @return TRUE if Valid, FALSE if it is Not Valid
	 */
	public boolean isValid() {
		boolean valid = false;

		if (task.getTaskID() != null && task.getDescription() != null) {
			valid = true;
			return valid;
		} else
			return valid;

	}

	/**
	 * This method changes the state of a Task to the "Created" state.
	 * 
	 */
	public boolean changeToCreated() {
		boolean condition = false;
		if (isTransitionToCreatedPossible()) {
			TaskStateInterface stateCreated = new Created(task);
			if (stateCreated.isValid()) {
				task.setTaskState(stateCreated);
				condition = true;
			}
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 */
	public boolean changeToPlanned() {
		boolean condition = false;
		if (isTransitionToPlannedPossible()) {
			TaskStateInterface statePlanned = new Planned(task);
			if (statePlanned.isValid()) {
				task.setTaskState(statePlanned);
				condition = true;
			}
		}
		return condition;
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
		boolean condition = false;
		if (isTransitionToReadyPossible()) {
			TaskStateInterface stateReady = new Ready(task);
			if (stateReady.isValid()) {
				task.setTaskState(stateReady);
				condition = true;
			}
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state.
	 * 
	 */
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
	 * This method changes the state of a Task to the "StandBy" state.
	 * 
	 */
	public boolean changeToStandBy() {
		boolean condition = false;
		if (isTransitionToStandByPossible()) {
			TaskStateInterface stateStandBy = new StandBy(task);
			if (stateStandBy.isValid()) {
				task.setTaskState(stateStandBy);
				condition = true;
			}
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state.
	 * 
	 */
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
	 * This method changes the state of a Task to the "Finished" state.
	 * 
	 */
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
		return true;
	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToAssignedPossible() {
		return false;
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
