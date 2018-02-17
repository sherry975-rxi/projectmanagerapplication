package project.model.taskstateinterface;

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
	public boolean changeToCreated();

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	public boolean changeToPlanned();

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 * @return Void
	 */
	public boolean changeToAssigned();

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 * @return Void
	 */
	public boolean changeToReady();

	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	public boolean changeToOnGoing();

	/**
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	public boolean changeToStandBy();

	/**
	 * This method changes the state of a Task to the "Cancelled" state
	 * 
	 * @return Void
	 */
	public boolean changeToCancelled();

	/**
	 * This method changes the state of a Task to the "Finished" state
	 * 
	 * @return Void
	 */
	public boolean changeToFinished();

}
