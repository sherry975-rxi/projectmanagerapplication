package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class Cancelled implements TaskStateInterface {

	Task task;

	public Cancelled(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the State "Cancelled" requirements are suitable to a
	 * specif task.
	 * 
	 * @return true if is possible, false if not
	 */
	@Override
	public boolean isValid() {
		return task.getFinishDate() == null && task.getCancelDate() != null;
	}

	@Override
	public boolean changeToCreated() {
		return false;
	}

	@Override
	public boolean changeToPlanned() {
		return false;
	}

	@Override
	public boolean changeToAssigned() { return false; }

	@Override
	public boolean changeToReady() {
		return false;
	}

	@Override
	public boolean changeToOnGoing() {
		return false;
	}

	@Override
	public boolean changeToStandBy() {
		return false;
	}

	@Override
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task from "Cancelled" to the "Finished"
	 * state, if it's possible
	 */
	@Override
	public boolean changeToFinished() {
		boolean condition = false;
        TaskStateInterface finishedState = new Finished(task);
        if (finishedState.isValid()) {
            task.setTaskState(finishedState);
			task.setCurrentState(StateEnum.FINISHED);
            condition = true;
        }
		return condition;
	}
}
