package project.model.taskStateInterface;

import project.model.Task;

public class Ready implements TaskStateInterface {

	private Task toUpdate;

	public Ready() {
	}

	public Ready(Task toUpdateState) {
		this.toUpdate = toUpdateState;
	}

	/**
	 * This method checks if a transition to a certain state is valid
	 * 
	 * @return True if valid, False if not
	 */
	@Override
	public boolean isValid() {
		// TODO isto da task dependency tem de ser visto
		// if (toUpdate.getTaskDependency.isEmpty()) {
		// return true;
		// }
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Created" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public void changeToCreated() {
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	@Override
	public void changeToPlanned() {
		if (isTransitionToPlannedPossible()) {
			TaskStateInterface plannedState = new Planned(toUpdate);
			if (plannedState.isValid())
				toUpdate.setTaskState(plannedState);
		}
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public void changeToAssigned() {
	}

	/**
	 * This method changes the state of a Task to the "Ready" state. It does nothing
	 * in this state.
	 * 
	 * @return Void
	 */
	@Override
	public void changeToReady() {
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	@Override
	public void changeToOnGoing() {
		if (isTransitionToOnGoingPossible()) {
			TaskStateInterface onGoingState = new OnGoing(toUpdate);
			if (onGoingState.isValid())
				toUpdate.setTaskState(onGoingState);
		}
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public void changeToStandBy() {
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public void changeToCancelled() {
	}

	/**
	 * This method changes the state of a Task to the "Finished" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public void changeToFinished() {

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
