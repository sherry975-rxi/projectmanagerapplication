package project.model.taskStateInterface;

import project.model.Task;

public class Finished implements TaskStateInterface {

	Task tarefa;

	public Finished(Task tarefa) {
		this.tarefa = tarefa;
	}

	/**
	 * This method checks if a transition to a certain state is valid
	 * 
	 * @return True if valid, False if not
	 */
	public boolean isValid() {
		boolean valid = false;
		if (tarefa.getFinishDate() != null) {
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
			TaskStateInterface Created1 = new OnGoing(tarefa);
			if (Created1.isValid()) {
				tarefa.setTaskState(Created1);
			}
		}
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	public void changeToPlanned() {
		if (isTransitionToPlannedPossible()) {
			TaskStateInterface Planned1 = new OnGoing(tarefa);
			if (Planned1.isValid()) {
				tarefa.setTaskState(Planned1);
			}
		}
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 * @return Void
	 */
	public void changeToAssigned() {
		if (isTransitionToAssignedPossible()) {
			TaskStateInterface Assigned1 = new OnGoing(tarefa);
			if (Assigned1.isValid()) {
				tarefa.setTaskState(Assigned1);
			}
		}
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 * @return Void
	 */
	public void changeToReady() {
		if (isTransitionToReadyPossible()) {
			TaskStateInterface Ready1 = new OnGoing(tarefa);
			if (Ready1.isValid()) {
				tarefa.setTaskState(Ready1);
			}
		}
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	public void changeToOnGoing() {
		if (isTransitionToOnGoingPossible()) {
			TaskStateInterface OnGoing1 = new OnGoing(tarefa);
			if (OnGoing1.isValid()) {
				tarefa.setTaskState(OnGoing1);
			}
		}

	}

	/**
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	public void changeToStandBy() {
		if (isTransitionToStandByPossible()) {
			TaskStateInterface StandBy1 = new StandBy(tarefa);
			if (StandBy1.isValid()) {
				tarefa.setTaskState(StandBy1);
			}
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

			TaskStateInterface stateCancelled = new Cancelled(tarefa);
			if (stateCancelled.isValid())
				tarefa.setTaskState(stateCancelled);
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
	public void changeToFinished() {
		if (isTransitionToFinishedPossible()) {
			TaskStateInterface Finished1 = new OnGoing(tarefa);
			if (Finished1.isValid()) {
				tarefa.setTaskState(Finished1);
			}
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
		return true;
	}

	/**
	 * This method verifies if the transition to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToStandByPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Cancelled” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCancelledPossible() {
		return false;
	}

	/**
	 * This method verifies if the transition to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToFinishedPossible() {
		return false;
	}

}
