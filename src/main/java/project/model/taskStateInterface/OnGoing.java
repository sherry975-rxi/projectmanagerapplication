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
			
		if(task.getStartDate() != null && task.doesTaskTeamHaveActiveUsers()) {
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
	
	}
	
	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	public void changeToPlanned() {
		
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
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
	                task.setTaskState( stateStandBy );
	        }
	    
	    }
	
	/**
	 * This method changes the state of a Task to the "Cancelled" state
	 * 
	 * @return Void
	 */	
	public void changeToCancelled() {
		
		if (isTransitionToCancelledPossible()) {
            
            TaskStateInterface stateCancelled = new Cancelled(task);
            if( stateCancelled.isValid() )
                task.setTaskState( stateCancelled );
        }
		
	}
	
	/**
	 * This method changes the state of a Task to the "Finished" state
	 * 
	 * @return Void
	 */
	public void changeToFinished() {
		
		if (isTransitionToFinishedPossible()) {
			
			TaskStateInterface stateFinished = new Finished(task);
			
			if (stateFinished.isValid())
				task.setTaskState( stateFinished );
		}
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
