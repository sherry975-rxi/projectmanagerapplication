package project.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Calendar;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double reportedTime;
    @ManyToOne(cascade = CascadeType.PERSIST)
	@LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "TaskCollaborator_id")
	private TaskCollaborator taskCollaborator;
	private double cost;
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "Task_id")
	private Task task;
	private Calendar dateOfReport;
	private Calendar dateOfUpdate;





	public Report(){

	}

	/**
	 * This method creates a report
	 * 
	 * @param taskCollaborator
	 *            Task Collaborator
	 */


	public Report(TaskCollaborator taskCollaborator, Calendar reportDate) {
		
		this.reportedTime = 0;
		this.taskCollaborator = taskCollaborator;
		this.cost = taskCollaborator.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		this.dateOfReport = reportDate;
		this.dateOfUpdate = reportDate;
	}

	/**
	 * This method returns the id of the Report
	 *
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method sets an ID to the Report
	 *
	 * @return void
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method returns the Task associated to the Report
	 *
	 * @return Task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * This method sets a Task to the Task attribute of the Report
	 *
	 * @return Void
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * This method returns the cost of a taskCollaborator during a certain period
	 * 
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Sets the time that a a Task Collaborator spent on a task
	 * 
	 * @param time
	 *            Time spent on task
	 */
	public void setReportedTime(double time) {
		this.reportedTime = time;

	}

	/**
	 * Sets the time that a a Task Collaborator spent on a task
	 *
	 * @param time Time spent on task
	 */
	public void updateReportedTime(double time) {
		this.reportedTime = time;
		this.dateOfUpdate = Calendar.getInstance();

	}

	/**
	 * This method returns the time that a Task Collaborator spent on a task
	 *
	 * @return Time spent on task by a Task Collaborator
	 */
	public double getReportedTime() {
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

	/**
	 * Sets the Task Collaborator to the report
	 *
	 * @return void
	 */
	public void setTaskCollaborator(TaskCollaborator taskCollab) {
		this.taskCollaborator = taskCollab;

	}

	/**
	 * Sets a date to the report
	 *
	 * @return void
	 */
	public void setDateOfReport(Calendar reportDate) {
		this.dateOfReport = reportDate;
	}


	/**
	 * Gets the date of the report
	 *
	 * @return void
	 */
	public Calendar getDateOfReport() {
		return this.dateOfReport;
	}

	/**
	 * Sets a cost to the Report
	 *
	 * @return void
	 */

	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Gets the date of when the Report was last updated
	 *
	 * @return LocalDate
	 */
	public Calendar getDateOfUpdate() {
		return dateOfUpdate;
	}

	/**
	 * Sets a date to the report
	 *
	 * @return LocalDate
	 */
	public void setDateOfUpdate(Calendar dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}


}
