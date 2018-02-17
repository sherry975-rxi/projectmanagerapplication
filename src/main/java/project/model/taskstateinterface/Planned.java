package project.model.taskstateinterface;

import project.model.Task;

public class Planned implements TaskStateInterface {

	Task task;

	public Planned(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the State "Planned" requirements are fulfilled for a
	 * specific Task. It has to have estimated dates and no users working on it. If
	 * so, it returns true, If not, it returns false
	 * 
	 * @return true if is Valid, false if not
	 */
	@Override
	public boolean isValid() {
		return task.getEstimatedTaskStartDate() != null && task.getTaskDeadline() != null
				&& !task.doesTaskTeamHaveActiveUsers();
	}

	/**
	 * This method changes the state of a Task to the "Created" state.
	 * 
	 */
	@Override
	public boolean changeToCreated() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Planned" state
	 * 
	 */
	@Override
	public boolean changeToPlanned() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 */
	@Override
	public boolean changeToAssigned() {
		boolean condition = false;
		TaskStateInterface stateAssigned = new Assigned(task);
		if (stateAssigned.isValid()) {
			task.setTaskState(stateAssigned);
			condition = true;
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Ready" state
	 * 
	 */
	@Override
	public boolean changeToReady() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state.
	 * 
	 */
	@Override
	public boolean changeToOnGoing() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state.
	 * 
	 */
	@Override
	public boolean changeToStandBy() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state.
	 * 
	 */
	@Override
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state.
	 * 
	 */
	@Override
	public boolean changeToFinished() {
		return false;

	}
}
