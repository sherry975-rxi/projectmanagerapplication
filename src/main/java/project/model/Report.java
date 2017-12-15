package project.model;

/**
 * 
 * This Class Stores Reported Information associated with a Task Collaborator
 * 
 * @author Group3
 *
 */
public class Report {

	private int reportedTime;
	private TaskCollaborator taskCollaborator;
	private int cost;

	/**
	 * This method creates a report
	 * 
	 * @param taskCollaborator
	 *            Task Collaborator
	 */
	public Report(TaskCollaborator taskCollaborator) {

		this.reportedTime = 0;
		this.taskCollaborator = taskCollaborator;
		this.cost = taskCollaborator.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
	}

	/**
	 * This method returns the cost of a taskCollaborator during a certain period
	 * 
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Sets the time that a a Task Collaborator spent on a task
	 * 
	 * @param time
	 *            Time spent on task
	 */
	public void setReportedTime(int time) {
		this.reportedTime = time;

	}

	/**
	 * This method returns the time that a Task Collaborator spent on a task
	 * 
	 * @return Time spent on task by a Task Collaborator
	 */
	public int getReportedTime() {
		return this.reportedTime;
	}

	/**
	 * Returns the Task Collaborator associated to this Report
	 * 
	 * @return Task Collaborator
	 */
	public TaskCollaborator getTaskCollaborator() {
		return this.taskCollaborator;
	}

}
