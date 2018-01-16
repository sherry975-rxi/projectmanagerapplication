package project.model.taskStateInterface;

import project.model.Task;

public class OnGoing implements TaskStateInterface {
	
	//On going task can transition to stand by, cancelled or finished	
	
	Task task;
	
	public OnGoing() {
		
	}
	
	public OnGoing(Task t){
	  
		this.task = t;
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
	 * @return Void
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
	 * @return Void
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
	 * @return Void
	 */
	public void changeToReady() {
		if (isTransitionToReadyPossible()) {

			TaskStateInterface stateReady = new Ready(task);
			if (stateReady.isValid())
				task.setTaskState(stateReady);
		}
		
	}
	
	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	public void changeToOnGoing() {
		
	}
	
	/**
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	public void changeToStandBy() {
		
		if (isTransitionToStandByPossible()) {
	            
	            TaskStateInterface stateStandBy = new StandBy(task);
	            if( stateStandBy.isValid() )
	                task.setTaskState(stateStandBy);
	        }
	    
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
			return condition;
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
			
			TaskStateInterface stateFinished = new Finished(task);
			
			if (stateFinished.isValid()) {
				task.setTaskState( stateFinished );
				condition = true;
				return condition;
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
		boolean valid = false;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return  false
	 */
	public boolean isTransitionToPlannedPossible() {
		boolean valid = false;
		return valid;
	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return false 
	 */
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
	public boolean isTransitionToReadyPossible() {
		boolean valid = false;
		return valid;
		
	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return false
	 */
	public boolean isTransitionToOnGoingPossible() {
		boolean valid = false;
		return valid;
		
	}

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return true 
	 */
	public boolean isTransitionToStandByPossible() {
		boolean valid = true;
		return valid;
		
	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	public boolean isTransitionToCancelledPossible() {
		boolean valid = true;
		return valid;
		
	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return true
	 */
	public boolean isTransitionToFinishedPossible() {
		boolean valid = true;
		return valid;
	}
	
	

}
