package project.model.taskStateInterface;

import project.model.Task;

public class Assigned implements TaskStateInterface {

	private Task toUpdate;

	public Assigned(Task toUpdateState) {
		this.toUpdate = toUpdateState;
	}

	@Override
	public boolean isValid() {
		if (!toUpdate.getTaskState().isTransitionToAssignedPossible())// Isto não está a confirmar nada de relevante...
			return false;
		else
			return toUpdate.doesTaskTeamHaveActiveUsers();
	}

	@Override
	public void changeToCreated() {
	}

	// TODO create Created State Task Constructor!
	@Override
	public void changeToPlanned() {
		// Created toValidateTask = new Created(toUpdate);
		//
		// if(toValidateTask.isValid())
		// toUpdate.setTaskState(new Created());
	}

	@Override
	public void changeToAssigned() {
	}

	// TODO create Ready State Task Constructor!
	@Override
	public void changeToReady() {
		// Ready toValidateTask = new Ready(toUpdate);
		//
		// if(toValidateTask.isValid())
		// toUpdate.setTaskState(new Ready());
	}

	// TODO Can Task skip Ready State if it has no dependencies?
	@Override
	public void changeToOnGoing() {
	}

	@Override
	public void changeToStandBy() {
	}

	@Override
	public void changeToCancelled() {
	}

	@Override
	public void changeToFinished() {

	}

	@Override
	public boolean isTransitionToCreatedPossible() {
		return false;
	}

	@Override
	public boolean isTransitionToPlannedPossible() {
		return true;
	}

	@Override
	public boolean isTransitionToAssignedPossible() {
		return false;
	}

	@Override
	public boolean isTransitionToReadyPossible() {
		return true;
	}

	@Override
	public boolean isTransitionToOnGoingPossible() {
		return false;
	}

	@Override
	public boolean isTransitionToStandByPossible() {
		return false;
	}

	@Override
	public boolean isTransitionToCancelledPossible() {
		return false;
	}

	@Override
	public boolean isTransitionToFinishedPossible() {
		return false;
	}

}
