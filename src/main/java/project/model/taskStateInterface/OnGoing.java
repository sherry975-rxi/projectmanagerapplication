package project.model.taskStateInterface;

import project.model.Task;

public class OnGoing implements TaskStateInterface {
	
	//On going task can transition to stand by, cancelled or finished	
	
	Task task;
	
	public OnGoing() {
		
	}
	
	public OnGoing(Task taskToUpdate){
	  
		this.task = taskToUpdate;
	}
	
	/**
	 * This method verifies if the transition to the “Standby”, "Cancelled" or "Finished" state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
		
	public boolean isValid() {
		boolean valid = false;
			
		if(task.getStartDate() != null && task.doesTaskTeamHaveActiveUsers() && task.getFinishDate() == null) {
			valid = true;
		}
				return valid;
			}
		
	/**
	 * This method changes the state of a Task to the "Created" state
	 * 
	 * @return Void
	 */		
	public boolean changeToCreated() {
		boolean condition = false;
		if (isTransitionToCreatedPossible()) {
			TaskStateInterface stateCreated = new Created(task);
			if (stateCreated.isValid())
				task.setTaskState(stateCreated);
				condition = true;
		}
		return condition;
	}
	
	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	public boolean changeToPlanned() {
		boolean condition = false;
		if (isTransitionToPlannedPossible()) {
			TaskStateInterface statePlanned = new Planned(task);
			if (statePlanned.isValid())
				task.setTaskState(statePlanned);
				condition = true;
		}
		return condition; 
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 * @return Void
	 */
	public boolean changeToAssigned() {
		boolean condition = false;
		if (isTransitionToAssignedPossible()) {
			TaskStateInterface stateAssigned = new Assigned(task);
			if (stateAssigned.isValid())
				task.setTaskState(stateAssigned);
				condition = true;
		}
		return condition; 
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 * @return Void
	 */
	public boolean changeToReady() {
		boolean condition = false;
		if (isTransitionToReadyPossible()) {
			TaskStateInterface stateReady = new Ready(task);
			if (stateReady.isValid())
				task.setTaskState(stateReady);
				condition = true;
		}
		return condition; 
	}
	
	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	public boolean changeToOnGoing() {
		boolean condition = false;
		if (isTransitionToOnGoingPossible()) {
			TaskStateInterface stateOnGoing = new OnGoing(task);
			if (stateOnGoing.isValid())
				task.setTaskState(stateOnGoing);
				condition = true;
		}
		return condition; 
	}
	
	/**
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	public boolean changeToStandBy() {
		boolean condition = false;
		if (isTransitionToStandByPossible()) {
			TaskStateInterface stateStandBy = new StandBy(task);
			if (stateStandBy.isValid())
				task.setTaskState(stateStandBy);
				condition = true;
		}
		return condition;
	    }
	
	/**
	 * This method changes the state of a Task to the "Cancelled" state
	 * 
	 * @return Void
	 */	
	public boolean changeToCancelled() {
		boolean condition = false;
		if (isTransitionToCancelledPossible()) {
			TaskStateInterface stateCancelled = new Cancelled(task);
			if (stateCancelled.isValid())
				task.setTaskState(stateCancelled);
			condition = true;
		}
		return condition;
	}
	
	/**
	 * This method changes the state of a Task to the "Finished" state
	 * 
	 * @return Void
	 */
	public boolean changeToFinished() {
		boolean condition = false;
		if (isTransitionToFinishedPossible()) {
			TaskStateInterface Finished1 = new Finished(task);
			if (Finished1.isValid()) {
				task.setTaskState(Finished1);
				condition = true;
			}
		}
		return condition;
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
	 * @return  false
	 */
	public boolean isTransitionToPlannedPossible() {
		return false;
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
	 * @return false 
	 */
	public boolean isTransitionToReadyPossible() {
		return false;
		
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
	 * @return true 
	 */
	public boolean isTransitionToStandByPossible() {
		return true;
		
	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	public boolean isTransitionToCancelledPossible() {
		return true;
		
	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	public boolean isTransitionToFinishedPossible() {
		return true;
	}
	
	

}
