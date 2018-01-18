package project.model.taskStateInterface;

import project.model.Task;

public class Cancelled implements TaskStateInterface {

	Task task;

	public Cancelled(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the State "Cancelled" requirements are suitable to a
	 * specif task.
	 * 
	 * @return true if is possible, false if not
	 */
	public boolean isValid() {
		boolean validation = false;
		if (task.getFinishDate() == null && task.getCancelDate() == null) {
			validation = true;
		}
		return validation;
	}

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
	 * This method changes the state of a Task from "Cancelled" to the "Finished"
	 * state, if it's possible
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
	 * This method verifies if the transition from "Cancelled" to the “Created”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCreatedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Planned”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToPlannedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Assigned”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToAssignedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Ready” state
	 * of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToReadyPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “OnGoing”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToOnGoingPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “StandBy”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToStandByPossible() {
		return false;
	}

	/**
	 * This method verifies if the "Cancelled" state of the task can continue to be
	 * "Cancelled".
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCancelledPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Finished”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToFinishedPossible() {
		return true;
	}
}
