package main.project.model;

import java.util.Calendar;


/**
 * 
 * This Class Stores Reported Information associated with a Task Collaborator
 * @author Group3
 *
 */
public class Report {

	private int reportedTime;
	private TaskWorker taskWorker;

	/**
	 * This method creates a report
	 * 
	 * @param taskWorker Task Collaborator
	 */
	public Report(TaskWorker taskWorker) {

		this.reportedTime = 0;
		this.taskWorker = taskWorker;
	}
	
	/**
	 * Sets the time that a a Task Collaborator spent on a task
	 *  
	 * @param time Time spent on task
	 */
	public void setReportedTime( int time) {
		this.reportedTime = time;
		
	}
	
	/**
	 * This method returns the time that a Task Worker spent on a task
	 * @return Time spent on task by a Task Collaborator
	 */
	public int getReportedTime() {
		return this.reportedTime;
	}

}
