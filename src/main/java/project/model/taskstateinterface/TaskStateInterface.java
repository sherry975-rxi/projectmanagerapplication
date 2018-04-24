package project.model.taskstateinterface;
import project.model.Task;

public interface TaskStateInterface {
	
	/**
	 * Method that verifies if a certain task can change its state
	 * 
	 * @param task Task to verify and possible change state
	 */
	void doAction(Task task);

	/**
	 * Method that will add the links available to the task depending of its state
	 *
	 * @param task Task to get links from
	 * @param projectId Id of the project to create the links
	 *
	 * @return List of Links
	 */
	void addRels(Task task, int projectId);

}
