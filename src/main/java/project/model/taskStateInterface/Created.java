package project.model.taskStateInterface;


import project.model.Task;

public class Created implements TaskStateInterface {

	Task task;

	/**
	 * Constructor to create State Created
	 * 
	 * @param task Task to set State upon
	 */
	public Created(Task task) {
		this.task = task;
	}
	
	/**
	 * This method verifies if the State "Created" requirements are fulfilled for a specific Task. The requirements are If the taskID and the description of the task is not null, the task is created.
	 * 
	 * @return TRUE if Valid, FALSE if it is Not Valid
	 */
	public boolean isValid() {
		boolean valid = false; 
		
		if( task.getTaskID() != null && task.getDescription() != null)
			return valid;
		else
			return valid;
	
	}

	/**
	 * This method changes the state of a Task to the "Created" state.
	 * 
	 */
	public void changeToCreated() {
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
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 */
	public void changeToReady() {	
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state.
	 * 
	 */
	public void changeToOnGoing() {
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state.
	 * 
	 */
	public void changeToStandBy() {
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state.
	 * 
	 */
	public void changeToCancelled() {
	}

	/**
	 * This method changes the state of a Task to the "Finished" state.
	 * 
	 */
	public void changeToFinished() {
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
		boolean transitionState = true;
		return transitionState;
	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return TRUE if possible, FALSE if not
	 */
	public boolean isTransitionToAssignedPossible() {
		boolean transitionState = false;
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
