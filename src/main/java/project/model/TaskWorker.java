package main.java.project.model;

import java.util.Calendar;
import java.util.List;

public class TaskWorker {

	private User collaborator;
	private List<Calendar> dates;
	private int hoursSpent;

	/**
	 * Constructor to create a new task worker
	 * 
	 * Collaborator is set as the user provided Collaborator is defined as being in
	 * the task (inTask = true) Hours spent byt the Collaborator on this task is set
	 * as 0.
	 * 
	 * @param collaborator
	 *            user to set
	 */
	public TaskWorker(User collaborator) {
		this.collaborator = collaborator;
		this.dates.add(Calendar.getInstance());
		this.hoursSpent = 0;
	}

	/**
	 * Returns the user that identifies this collaborator
	 * 
	 * @return collaborator
	 */
	public User getTaskWorker() {
		return collaborator;
	}

	/**
	 * Returns the state of the collaborator in the task. inTask is true if user is
	 * in task, false if not
	 * 
	 * @return inTask
	 */
	public boolean isTaskWorkerActiveInTask() {
		if (this.dates.size() % 2 == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Adds Date to task worker list
	 */
	public void setDateForTaskWorker() {
		this.dates.add(Calendar.getInstance());
	}

	/**
	 * Returns the hours spent by the collaborator in this task
	 * 
	 * @return hoursSpent
	 */
	public int getHoursSpent() {
		return hoursSpent;
	}

	/**
	 * Sets the hours spent by the user in this task
	 * 
	 * @param hoursSpent
	 */
	public void setHoursSpent(int hoursSpent) {
		this.hoursSpent = hoursSpent;
	}

}
