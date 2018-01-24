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
	@Override
	public boolean isValid() {
		boolean validation = false;
		if (task.getFinishDate() == null && task.getCancelDate() == null) {
			validation = true;
		}
		return validation;
	}

	@Override
	public boolean changeToCreated() {
		return false;
	}

	@Override
	public boolean changeToPlanned() {
		return false;
	}

	@Override
	public boolean changeToAssigned() {
		return false;

	}

	@Override
	public boolean changeToReady() {
		return false;
	}

	@Override
	public boolean changeToOnGoing() {
		return false;
	}

	@Override
	public boolean changeToStandBy() {
		return false;
	}

	@Override
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task from "Cancelled" to the "Finished"
	 * state, if it's possible
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
	 * This method verifies if the transition from "Cancelled" to the “Created”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isTransitionToCreatedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Planned”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isTransitionToPlannedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Assigned”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isTransitionToAssignedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Ready” state
	 * of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isTransitionToReadyPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “OnGoing”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isTransitionToOnGoingPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “StandBy”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isTransitionToStandByPossible() {
		return false;
	}

	/**
	 * This method verifies if the "Cancelled" state of the task can continue to be
	 * "Cancelled".
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isTransitionToCancelledPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Finished”
	 * state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */
	@Override
	public boolean isTransitionToFinishedPossible() {
		return true;
	}
}
