package project.model.taskStateInterface;

import project.model.Task;

public class OnGoing implements TaskStateInterface {

	// On going task can transition to stand by, cancelled or finished

	Task task;

	public OnGoing(Task taskToUpdate) {

		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the transition to the “Standby”, "Cancelled" or
	 * "Finished" state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */

	@Override
	public boolean isValid() {
		boolean valid = false;

		if (task.getStartDate() != null && task.doesTaskTeamHaveActiveUsers() && task.getFinishDate() == null
				&& !task.hasActiveDependencies()) {
			valid = true;
		}
		return valid;
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
		return false;
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	@Override
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
	 * This method changes the state of a Task to the "Cancelled" state
	 * 
	 * @return Void
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
	 * @return Void
	 */
	@Override
	public boolean changeToFinished() {
		boolean condition = false;
		if (isTransitionToFinishedPossible()) {
			TaskStateInterface finishedState = new Finished(task);
			if (finishedState.isValid()) {
				task.setTaskState(finishedState);
				condition = true;
			}
		}
		return condition;
	}

	/**
	 * This method verifies if the transition to the “Created” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToCreatedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToPlannedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToAssignedPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToReadyPossible() {
		return false;

	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToOnGoingPossible() {
		return false;

	}

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	@Override
	public boolean isTransitionToStandByPossible() {
		return true;

	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	@Override
	public boolean isTransitionToCancelledPossible() {
		return true;

	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	@Override
	public boolean isTransitionToFinishedPossible() {
		return true;
	}

}
