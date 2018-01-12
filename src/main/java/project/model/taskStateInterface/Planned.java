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

	
	public void changeToCreated() {
		// TODO Auto-generated method stub
		
	}

	
	public void changeToPlanned() {
		// TODO Auto-generated method stub
		
	}

	
	public void changeToAssigned() {
		
		if (isTransitionToAssignedPossible()) {

			TaskStateInterface stateAssigned = new Assigned(task);
			if (stateAssigned.isValid())
				task.setTaskState(stateAssigned);
		}
		
	}

	
	public void changeToReady() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeToOnGoing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeToStandBy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeToCancelled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeToFinished() {
		// TODO Auto-generated method stub
		
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
