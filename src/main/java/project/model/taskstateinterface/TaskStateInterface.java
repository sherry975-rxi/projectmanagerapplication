package project.model.taskstateinterface;

import project.model.Task;

public interface TaskStateInterface {
	
	/**
	 * Method that verifies if a certain task can change its state
	 * 
	 * @param task Task to verify and possible change state
	 */
	void doAction(Task task);

}
