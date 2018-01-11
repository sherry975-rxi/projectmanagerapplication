package project.model.taskStateInterface;

import project.model.Task;

public class Cancelled implements TaskStateInterface {

	Task task;
	
	public Cancelled (Task taskToUpdate) {
		this.task = taskToUpdate;
	}
	
	/**
	 * This method verifies if the State "Cancelled" requirements are suitable to a specif task.
	 * 
	 * @return true if is possible, false if not
	 */
	public boolean isValid() {
		boolean validation = false;
		if(task.getTaskState() == new OnGoing(task) || task.getTaskState() == new StandBy(task)) {
			validation = true;
		}
		return validation;
	}

	/**
	 * This method changes the task's state from "Cancelled" to "Created", if this transition is possible. 
	 */
	public void changeToCreated() {
		if (isTransitionToCreatedPossible()) {
			TaskStateInterface Created1 = new Created(task);
			if (Created1.isValid()) {
				task.setTaskState(Created1);
			}
		}
	}

	public void changeToPlanned(){
		if (isTransitionToPlannedPossible()) {
			TaskStateInterface Planned1 = new Planned(task);
			if (Planned1.isValid()) {
				task.setTaskState(Planned1);
			}
		}
	}

	public void changeToAssigned(){
		if (isTransitionToAssignedPossible()) {
			TaskStateInterface Assigned1 = new Assigned(task);
			if (Assigned1.isValid()) {
				task.setTaskState(Assigned1);
			}
		}
	}

	public void changeToReady(){
		if (isTransitionToReadyPossible()) {
			TaskStateInterface Ready1 = new Ready(task);
			if (Ready1.isValid()) {
				task.setTaskState(Ready1);
			}
		}
	}

	public void changeToOnGoing(){
		if (isTransitionToOnGoingPossible()) {
			TaskStateInterface OnGoing1 = new OnGoing(task);
			if (OnGoing1.isValid()) {
				task.setTaskState(OnGoing1);
			}
		}
	}

	public void changeToStandBy(){
		if (isTransitionToStandByPossible()) {
			TaskStateInterface StandBy1 = new StandBy(task);
			if (StandBy1.isValid()) {
				task.setTaskState(StandBy1);
			}
		}
	}

	public void changeToCancelled(){
		if (isTransitionToCancelledPossible()) {
			TaskStateInterface Cancelled1 = new Cancelled(task);
			if (Cancelled1.isValid()) {
				task.setTaskState(Cancelled1);
			}
		}
	}

	public void changeToFinished(){
		if (isTransitionToFinishedPossible()) {
			TaskStateInterface Finished1 = new Finished(task);
			if (Finished1.isValid()) {
				task.setTaskState(Finished1);
			}
		}
	}

	
	//TODO atualizar estes métodos qd as US estiverem implementadas
	
	/**
	 * This method verifies if the transition from "Cancelled" to the “Created” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCreatedPossible(){
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Planned” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToPlannedPossible(){
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Assigned” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToAssignedPossible(){
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “Ready” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToReadyPossible(){
		return false;
	}

	/**
	 * This method verifies if the transition from "Cancelled" to the “OnGoing” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToOnGoingPossible(){
		return false;}

	/**
	 * This method verifies if the transition from "Cancelled" to the “StandBy” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToStandByPossible(){
		return false;
	}

	/**
	 * This method verifies if the "Cancelled" state of the task can continue to be "Cancelled".
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToCancelledPossible(){
		return false;
	}
	
	/**
	 * This method verifies if the transition from "Cancelled" to the “Finished” state of a Task is
	 * possible
	 * 
	 * @return true if possible, false if not
	 */
	public boolean isTransitionToFinishedPossible(){
		return false;
	}
}
