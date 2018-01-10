package project.model.taskStateInterface;

public class Cancelled implements TaskStateInterface {

	public boolean isValid() {
		return false; //TODO fazer quando a respectiva US estiver implementada no model 
	}

	public void changeToCreated() {}

	public void changeToPlanned(){}

	public void changeToAssigned(){}

	public void changeToReady(){}

	public void changeToOnGoing(){}

	public void changeToStandBy(){}

	public void changeToCancelled(){}

	public void changeToFinished(){}

	public boolean isTransitionToCreatedPossible(){
		return false;
	}

	public boolean isTransitionToPlannedPossible(){
		return false;
	}

	public boolean isTransitionToAssignedPossible(){
		return false;
	}

	public boolean isTransitionToReadyPossible(){
		return false;
	}

	public boolean isTransitionToOnGoingPossible(){
		return false;}

	public boolean isTransitionToStandByPossible(){
		return false;
	}

	public boolean isTransitionToCancelledPossible(){
		return false;
	}
	
	public boolean isTransitionToFinishedPossible(){
		return false;
	}
}
