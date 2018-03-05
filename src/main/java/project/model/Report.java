package project.model;

import javax.persistence.*;

/**
 * 
 * This Class Stores Reported Information associated with a Task Collaborator
 * 
 * @author Group3
 *
 */
@Entity
@Table(name = "Report")

public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int reportedTime;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TaskCollaborator_id")
	private TaskCollaborator taskCollaborator;
	private int cost;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Task_id")
	private Task task;




	public Report(){

	}

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


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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

	public void setTaskCollaborator(TaskCollaborator taskCollab) {
		this.taskCollaborator = taskCollab;

	}

}
