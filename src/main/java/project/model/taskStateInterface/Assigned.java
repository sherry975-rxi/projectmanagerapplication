package project.model.taskStateInterface;

import project.model.Task;

public class Assigned implements TaskStateInterface {

	private Task task;

	public Assigned(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method checks if a transition to a certain state is valid, checking if
	 * the task has active users
	 * 
	 * @return True if valid, False if not
	 */
	// TODO what happens when the finished dependency of a "ready" task reverts to
	// Unfinished?
	@Override
	public boolean isValid() {
		return task.doesTaskTeamHaveActiveUsers();
	}

	/**
	 * This method changes the state of a Task to the "Created" state - does nothing
	 * in Assigned state
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
	 * This method changes the state of a Task to the "Assigned" state - does
	 * nothing in Assigned state
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
	 * This method changes the state of a Task to the "OnGoing" state - does nothing
	 * in Assigned state
	 * 
	 * @return Void
	 */
	// TODO Can Task skip Ready State if it has no dependencies?
	@Override
	public boolean changeToOnGoing() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state - does nothing
	 * in Assigned state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToStandBy() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state - does
	 * nothing in Assigned state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state - does
	 * nothing in Assigned state
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
	 * @return true
	 */
	@Override
	public boolean isTransitionToReadyPossible() {
		return true;
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
