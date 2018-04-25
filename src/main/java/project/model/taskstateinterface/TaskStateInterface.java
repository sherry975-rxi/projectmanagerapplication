package project.model.taskstateinterface;
import project.model.Task;

import java.util.List;

public interface TaskStateInterface {
	
	/**
	 * Method that verifies if a certain task can change its state
	 * 
	 * @param task Task to verify and possible change state
	 */
	void doAction(Task task);

	/**
	 * Method that will get the actions available to the task depending of its state

	 * @return List of Actions
	 */
	List<String> getActions();

}
