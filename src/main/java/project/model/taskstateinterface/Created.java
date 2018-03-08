package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class Created implements TaskStateInterface {

	Task task;

	/**
	 * Constructor to create State Created
	 * 
	 * @param task
	 *            Task to set State upon
	 */
	public Created(Task taskToUpdate) {
		this.task = taskToUpdate;
	}

	/**
	 * This method verifies if the State "Created" requirements are fulfilled for a
	 * specific Task. The requirements are If the taskID and the description of the
	 * task is not null, the task is created.
	 * 
	 * @return TRUE if Valid, FALSE if it is Not Valid
	 */
	@Override
	public boolean isValid() {
		return task.getTaskID() != null && task.getDescription() != null;
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
		boolean condition = false;
		TaskStateInterface statePlanned = new Planned(task);
		if (statePlanned.isValid()) {
			task.setTaskState(statePlanned);
			task.setCurrentState(StateEnum.PLANNED);
			condition = true;
		}
		return condition;
	}

	/**
	 * This method changes the state of a Task to the "Assigned" state
	 * 
	 */
	@Override
	public boolean changeToAssigned() {
		return false;
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
