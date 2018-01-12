package project.model.taskStateInterface;

import project.model.Task;

public class Planned implements TaskStateInterface {
	
	Task task;
	
	public Planned (Task t) {
		this.task = t;
	}

	/**
	 * This method verifies if the State "Planned" requirements are fulfilled for a specific Task. 
	 * It has to have estimated dates and no users working on it.
	 * If so, it returns true,
	 * If not, it returns false
	 * 
	 * @return true if is Valid, false if not
	 */
	public boolean isValid () {
		boolean validation = false;
		if(task.getEstimatedTaskStartDate()!=null && task.getTaskDeadline()!=null && !task.doesTaskTeamHaveActiveUsers()) {
			validation = true;
		}

		return validation;
	}

	
	/**
	 * This method changes the state of a Task to the "Created" state.
	 * 
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
	 */
	public void changeToReady() {
		if (isTransitionToReadyPossible()) {

			TaskStateInterface stateReady = new Ready(task);
			if (stateReady.isValid())
				task.setTaskState(stateReady);
		}
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state.
	 * 
	 */
	public void changeToOnGoing() {
		if (isTransitionToOnGoingPossible()) {

			TaskStateInterface stateOnGoing = new OnGoing(task);
			if (stateOnGoing.isValid())
				task.setTaskState(stateOnGoing);
		}
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state.
	 * 
	 */
	public void changeToStandBy() {
		if (isTransitionToStandByPossible()) {

			TaskStateInterface stateStandBy = new StandBy(task);
			if (stateStandBy.isValid())
				task.setTaskState(stateStandBy);
		}
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state.
	 * 
	 */
	public void changeToCancelled() {
		if (isTransitionToCancelledPossible()) {

			TaskStateInterface stateCancelled = new Cancelled(task);
			if (stateCancelled.isValid())
				task.setTaskState(stateCancelled);
		}
	}

	/**
	 * This method changes the state of a Task to the "Finished" state.
	 * 
	 */
	public void changeToFinished() {
		if (isTransitionToFinishedPossible()) {

			TaskStateInterface stateFinished = new Finished(task);
			if (stateFinished.isValid())
				task.setTaskState(stateFinished);
		}
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
		boolean transitionState = false;
		return transitionState;
	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToAssignedPossible() {
		boolean transitionState = true;
		return transitionState;
	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToReadyPossible() {
		boolean transitionState = false;
		return transitionState;
	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToOnGoingPossible() {
		boolean transitionState = false;
		return transitionState;
	}

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToStandByPossible() {
		boolean transitionState = false;
		return transitionState;
	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToCancelledPossible() {
		boolean transitionState = false;
		return transitionState;
	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToFinishedPossible() {
		boolean transitionState = false;
		return transitionState;
	}
	

}
