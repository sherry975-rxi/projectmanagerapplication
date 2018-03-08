package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class OnGoing implements TaskStateInterface {

	// On going task can transition to stand by, cancelled or finished

	Task task;

	public OnGoing(Task taskToUpdate) {

		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the transition to the “Standby”, "Cancelled" or
	 * "Finished" state of a Task is possible
	 * 
	 * @return true if possible, false if not
	 */

	@Override
	public boolean isValid() {
		return task.getStartDate() != null && task.doesTaskTeamHaveActiveUsers() && task.getFinishDate() == null
				&& !task.hasActiveDependencies();
	}

	/**
	 * This method changes the state of a Task to the "Created" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToCreated() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToPlanned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToAssigned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToReady() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToOnGoing() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToStandBy() {
		boolean condition = false;
		TaskStateInterface stateStandBy = new StandBy(task);
		if (stateStandBy.isValid()) {
			task.setTaskState(stateStandBy);
			task.setCurrentState(StateEnum.StandBy);
			condition = true;
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToCancelled() {
		boolean condition = false;
		TaskStateInterface stateCancelled = new Cancelled(task);
		if (stateCancelled.isValid()) {
			task.setTaskState(stateCancelled);
			task.setCurrentState(StateEnum.Cancelled);
			condition = true;
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToFinished() {
		boolean condition = false;
		TaskStateInterface finishedState = new Finished(task);
		if (finishedState.isValid()) {
			task.setTaskState(finishedState);
			task.setCurrentState(StateEnum.Finished);
			condition = true;
		}
		return condition;
	}
}
