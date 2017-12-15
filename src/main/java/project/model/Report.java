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
	private TaskCollaborator taskWorker;
	private int cost;

	/**
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * This method creates a report
	 * 
	 * @param taskWorker
	 *            Task Collaborator
	 */
	public Report(TaskCollaborator taskWorker) {

		this.reportedTime = 0;
		this.taskWorker = taskWorker;
		this.cost = taskWorker.getProjectCollaboratorFromTaskWorker().getCollaboratorCost();
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
	 * This method returns the time that a Task Worker spent on a task
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
	public TaskCollaborator getTaskWorker() {
		return this.taskWorker;
	}

}
