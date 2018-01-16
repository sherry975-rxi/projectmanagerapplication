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
		boolean valid = false;
		if (!toUpdate.hasActiveDependencies()) {
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
	public void changeToCreated() {
		if (isTransitionToCreatedPossible()) {

			TaskStateInterface stateCreated = new Created(toUpdate);
			if (stateCreated.isValid())
				toUpdate.setTaskState(stateCreated);
		}
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
		if (isTransitionToAssignedPossible()) {

			TaskStateInterface stateAssigned = new Assigned(toUpdate);
			if (stateAssigned.isValid())
				toUpdate.setTaskState(stateAssigned);
		}
	}

	/**
	 * This method changes the state of a Task to the "Ready" state. It does nothing
	 * in this state.
	 * 
	 * @return Void
	 */
	@Override
	public void changeToReady() {
		if (isTransitionToReadyPossible()) {

			TaskStateInterface stateReady = new Ready(toUpdate);
			if (stateReady.isValid())
				toUpdate.setTaskState(stateReady);
		}
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
		if (isTransitionToStandByPossible()) {

			TaskStateInterface stateStandBy = new StandBy(toUpdate);
			if (stateStandBy.isValid())
				toUpdate.setTaskState(stateStandBy);
		}
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state. It does
	 * nothing in this state.
	 * 
	 * @return TRUE if state was changed and FALSE if state was not changed
	 */
	public boolean changeToCancelled() {

		boolean condition = false;
		if (isTransitionToCancelledPossible()) {

			TaskStateInterface stateCancelled = new Cancelled(toUpdate);
			if (stateCancelled.isValid())
				toUpdate.setTaskState(stateCancelled);
			condition = true;
			return condition;
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state. It does
	 * nothing in this state.
	 * 
	 * @return Void
	 */
	@Override
	public void changeToFinished() {
		if (isTransitionToFinishedPossible()) {

			TaskStateInterface stateFinished = new Finished(toUpdate);
			if (stateFinished.isValid())
				toUpdate.setTaskState(stateFinished);
		}

	}

	/**
	 * This method verifies if the transition to the “Created” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToCreatedPossible() {
		boolean valid = false;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	@Override
	public boolean isTransitionToPlannedPossible() {
		boolean valid = true;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToAssignedPossible() {
		boolean valid = false;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToReadyPossible() {
		boolean valid = false;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	@Override
	public boolean isTransitionToOnGoingPossible() {
		boolean valid = true;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToStandByPossible() {
		boolean valid = false;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToCancelledPossible() {
		boolean valid = false;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	@Override
	public boolean isTransitionToFinishedPossible() {
		boolean valid = false;
		return valid;
	}
}