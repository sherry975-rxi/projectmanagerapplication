package project.model.taskstateinterface;

import project.model.Task;

public class Assigned implements TaskStateInterface {

	private Task task;

	public Assigned(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the State "Assigned" requirements are fulfilled for a
	 * specific Task. It has to have at least one active user.
	 * 
	 * @return True if valid, False if not
	 */

	@Override
	public boolean isValid() {
		return task.doesTaskTeamHaveActiveUsers();
	}

	/**
	 * This method changes the state of a Task to the "Created" state - does nothing
	 * in Assigned state
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
		boolean condition = false;
        TaskStateInterface statePlanned = new Planned(task);
        if (statePlanned.isValid()) {
            task.setTaskState(statePlanned);
            condition = true;
        }
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state - does
	 * nothing in Assigned state
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
		boolean condition = false;
        TaskStateInterface stateReady = new Ready(task);
        if (stateReady.isValid()) {
            task.setTaskState(stateReady);
            condition = true;
        }
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "OnGoing" state - does nothing
	 * in Assigned state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToOnGoing() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "StandBy" state - does nothing
	 * in Assigned state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToStandBy() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Cancelled" state - does
	 * nothing in Assigned state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToCancelled() {
		return false;
	}

	/**
	 * This method changes the state of a Task to the "Finished" state - does
	 * nothing in Assigned state
	 * 
	 * @return Void
	 */
	@Override
	public boolean changeToFinished() {
		return false;
	}

}
