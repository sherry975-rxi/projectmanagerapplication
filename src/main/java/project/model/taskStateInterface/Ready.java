package project.model.taskStateInterface;

import project.model.Task;

public class Ready implements TaskStateInterface {

	private Task task;

	public Ready() {
	}

	public Ready(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method checks if a transition to a certain state is valid
	 * 
	 * @return True if valid, False if not
	 */
	@Override
	public boolean isValid() {
		boolean valid = false;
		if (!task.hasActiveDependencies()) {
			valid = true;
		}
		return valid;
	}

	/**
	 * This method changes the state of a Task to the "Created" state. It does
	 * nothing in this state.
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
	 * This method changes the state of a Task to the "Assigned" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToAssigned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Ready" state. It does nothing
	 * in this state.
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
	 * This method changes the state of a Task to the "StandBy" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToStandBy() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state. It does
	 * nothing in this state.
	 * 
	 * @return TRUE if state was changed and FALSE if state was not changed
	 */
	@Override
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToFinished() {
		return false;
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
	 * @return true
	 */
	@Override
	public boolean isTransitionToPlannedPossible() {
		return true;
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
	 * @return true
	 */
	@Override
	public boolean isTransitionToOnGoingPossible() {
		return true;
	}

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToStandByPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToCancelledPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToFinishedPossible() {
		return false;
	}
}