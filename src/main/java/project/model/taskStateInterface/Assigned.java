package project.model.taskStateInterface;

import project.model.Task;

public class Assigned implements TaskStateInterface {

	private Task toUpdate;

	public Assigned(Task toUpdateState) {
		this.toUpdate = toUpdateState;
	}

	/**
	 * This method checks if a transition to a certain state is valid, checking if the task has active users
	 * 
	 * @return True if valid, False if not
	 */
	public boolean isValid() {
		return (toUpdate.hasActiveDependencies() && toUpdate.doesTaskTeamHaveActiveUsers());
	}

	/**
	 * This method changes the state of a Task to the "Created" state - does nothing in Assigned state
	 * 
	 * @return Void
	 */
	public void changeToCreated() {
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	public void changeToPlanned() {
		 
			 TaskStateInterface updatedState = new Planned(toUpdate);
			
			 if(updatedState.isValid() && isTransitionToPlannedPossible())
			 toUpdate.setTaskState(updatedState);
		 
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state - does nothing in Assigned state
	 * 
	 * @return Void
	 */
	public void changeToAssigned() {
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 * @return Void
	 */
	public void changeToReady() {
		 TaskStateInterface updatedState = new Ready(toUpdate);
		
		 if(updatedState.isValid() && isTransitionToReadyPossible())
		 toUpdate.setTaskState(updatedState);
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state - does nothing in Assigned state
	 * 
	 * @return Void
	 */
	// TODO Can Task skip Ready State if it has no dependencies?
	public void changeToOnGoing() {
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state - does nothing in Assigned state
	 * 
	 * @return Void
	 */
	public void changeToStandBy() {
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state - does nothing in Assigned state
	 * 
	 * @return Void
	 */
	public void changeToCancelled() {
	}

	/**
	 * This method changes the state of a Task to the "Finished" state - does nothing in Assigned state
	 * 
	 * @return Void
	 */
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
	 * @return true
	 */
	public boolean isTransitionToPlannedPossible() {
		return true;
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
	 * @return true
	 */
	public boolean isTransitionToReadyPossible() {
		return true;
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
	 * This method verifies if the transition to the “StandBy” state of a Task is
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
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToFinishedPossible() {
		return false;
	}

}
