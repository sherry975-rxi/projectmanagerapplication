package project.model.taskStateInterface;

import project.model.Task;

public class Finished implements TaskStateInterface {

	Task tarefa;

	public Finished(Task tarefa) {
		this.tarefa = tarefa;
	}

	public boolean isValid() {
		boolean valid = false;
		if (tarefa.getFinishDate() != null) {
			valid = true;
		}
		return valid;
	}

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
	}

	public void changeToCancelled() {
	}

	public void changeToFinished() {
	}

	public boolean isTransitionToCreatedPossible() {
		return false;
	}

	public boolean isTransitionToPlannedPossible() {
		return false;
	}

	public boolean isTransitionToAssignedPossible() {
		return false;
	}

	public boolean isTransitionToReadyPossible() {
		return false;
	}

	public boolean isTransitionToOnGoingPossible() {
		return false;
	}

	public boolean isTransitionToStandByPossible() {
		return false;
	}

	public boolean isTransitionToCancelledPossible() {
		return false;
	}

	public boolean isTransitionToFinishedPossible() {
		return false;
	}

}
