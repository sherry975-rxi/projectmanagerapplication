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

	@Override
	public boolean isTransitionToCreatedPossible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTransitionToPlannedPossible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTransitionToAssignedPossible() {
		return false;
	}

	@Override
	public boolean isTransitionToReadyPossible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTransitionToOnGoingPossible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTransitionToStandByPossible() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTransitionToCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTransitionToFinishedPossible() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isTransitionToCancelledPossible() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
