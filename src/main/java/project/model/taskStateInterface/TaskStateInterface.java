package project.model.taskStateInterface;

public interface TaskStateInterface {

	public boolean isValid();

	public void changeToCreated();

	public void changeToPlanned();

	public void changeToAssigned();

	public void changeToReady();

	public void changeToOnGoing();

	public void changeToStandBy();

	public void changeToCancelled();

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
