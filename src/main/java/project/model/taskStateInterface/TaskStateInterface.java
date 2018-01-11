package project.model.taskStateInterface;

public interface TaskStateInterface {

	/**
	 * This method checks if a transition to a certain state is valid
	 * 
	 * @return True if valid, False if not
	 */
	public boolean isValid();

	/**
	 * This method changes the state of a Task to the "Created" state
	 * 
	 * @return Void
	 */
	public void changeToCreated();

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	public void changeToPlanned();

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 * @return Void
	 */
	public void changeToAssigned();

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 * @return Void
	 */
	public void changeToReady();

	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	public void changeToOnGoing();

	/**
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	public void changeToStandBy();

	/**
	 * This method changes the state of a Task to the "Cancelled" state
	 * 
	 * @return Void
	 */
	public void changeToCancelled();

	/**
	 * This method changes the state of a Task to the "Finished" state
	 * 
	 * @return Void
	 */
	public void changeToFinished();

	/**
	 * This method verifies if the transition to the “Created” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCreatedPossible();

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToPlannedPossible();

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToAssignedPossible();

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToReadyPossible();

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToOnGoingPossible();

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToStandByPossible();

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCancelledPossible();

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToFinishedPossible();

}
