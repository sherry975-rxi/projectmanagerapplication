package project.model.taskStateInterface;

import project.model.Task;

public class OnGoing implements TaskStateInterface {
	
	//On going task can transition to stand by, cancelled or finished	
	
	Task task;
	
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
			
		if(task.getTaskState() == new StandBy()) {
			return valid = true;
		}
				return valid;
			}
		
		/*	if(taskOngoing.getTasstate == new Cancelled())
			return true;
		
		if(taskOngoing.state == new Finished())
			return true;
		
*/
	
	public void changeToCreated() {
		
	}

	public void changeToPlanned() {
		
	}

	public void changeToAssigned() {
		
	}

	public void changeToReady() {
		
	}
	
	public void changeToOnGoing() {
		
	}

	public void changeToStandBy() {
		
		if (isTransitionToStandByPossible()) {
	            
	            TaskStateInterface stateStandBy = new StandBy(task);
	            if( stateStandBy.isValid() )
	                task.setTaskState( stateStandBy );
	        }
	    
	    }
		
	public void changeToCancelled() {
		
		if (isTransitionToCancelledPossible()) {
            
            TaskStateInterface stateCancelled = new Cancelled(task);
            if( stateCancelled.isValid() )
                task.setTaskState( stateCancelled );
        }
		
	}

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
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCreatedPossible() {
		return false;
		
	}

	/**
	 * This method verifies if the transition to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToPlannedPossible() {
		return false;
		
	}

	/**
	 * This method verifies if the transition to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToAssignedPossible() {
		return false;
		
	}

	/**
	 * This method verifies if the transition to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToReadyPossible() {
		return false;
		
	}

	/**
	 * This method verifies if the transition to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToOnGoingPossible() {
		return false;
		
	}

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToStandByPossible() {
		return true;
		
	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCancelledPossible() {
		return true;
		
	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToFinishedPossible() {
		return true;
		
	}
	
	

}
