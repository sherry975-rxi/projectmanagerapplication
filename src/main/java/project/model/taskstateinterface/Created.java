package project.model.taskstateinterface;

import project.model.StateEnum;
import project.model.Task;

public class Created implements TaskStateInterface {

	 /**
     * Method that checks if a task that is currently in the created state meets the conditions to change to the planned state.
     *
     * @param task to check if it has the conditions to change to the planned state
     */
	@Override
    public void doAction(Task task) {
		Planned plannedState = new Planned();
		if(plannedState.isValid(task)) {
			task.setTaskState(plannedState);
	    }
    }

    /**
     *
     * @param task to check if it has the conditions to be in this state
     *
     * @return TRUE if it meets the conditions, FALSE if not
     */
    public boolean isValid(Task task) {
		return (task.getTaskID() != null) &&
                (task.getDescription() != null) &&
                (task.getEstimatedTaskStartDate() == null) &&
                (task.getTaskDeadline() == null) &&
                (task.getStartDate() == null) &&
                (task.getFinishDate() == null) &&
                (task.getCancelDate() == null);
	}

}
