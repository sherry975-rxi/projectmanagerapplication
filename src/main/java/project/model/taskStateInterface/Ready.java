package project.model.taskStateInterface;

import project.model.Task;

public class Ready implements TaskStateInterface {

	private Task toUpdate;

	public Ready(Task toUpdateState) {
		this.toUpdate = toUpdateState;
	}

	@Override
	public boolean isValid() {
		if (toUpdate.getTaskDependency.isEmpty() && toUpdate.doesTaskTeamHaveActiveUsers() && toUpdate.getTaskDeadline() // TODO
																															// isto
																															// da
																															// task
																															// dependency
																															// tem
																															// de
																															// ser
																															// visto
				&& toUpdate.getEstimatedTaskStartDate()) {
			return true;
		}
		return false;
	}

	@Override
	public void changeToCreated() {
	}

	@Override
	public void changeToPlanned() {
		if (isTransitionToPlannedPossible()) {
			Planned plannedState = new Planned(toUpdate);

			if (plannedState.isValid())
				toUpdate.setTaskState(plannedState);
		}
	}

	@Override
	public void changeToAssigned() {
	}

	@Override
	public void changeToReady() {
	}

	@Override
	public void changeToOnGoing() {
		if (isTransitionToOnGoingPossible()) {
			OnGoing onGoingState = new OnGoing(toUpdate);// TODO falta fazer o constructor do estado OnGOing

			if (onGoingState.isValid())
				toUpdate.setTaskState(onGoingState);
		}
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
		return false;
	}

	@Override
	public boolean isTransitionToOnGoingPossible() {
		return true;
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
