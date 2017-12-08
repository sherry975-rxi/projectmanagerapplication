package main.java.project.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskWorker {

	private User collaborator;
	private List<Calendar> startDates;
	private List<Calendar> finishDates;
	private List<Integer> hoursSpent;
	private List<Integer> cost;

	/**
	 * Constructor to create a new task worker
	 * 
	 * Collaborator is set as the Project Collaborator provided. A start date is set
	 * automatically. Hours Spent and Cost are set from the Project Collaborator.
	 * Finish date is added after collaborator is removed.
	 * 
	 * @param collaborator
	 *            user to set
	 */
	public TaskWorker(ProjectCollaborator collaborator) {
		this.collaborator = collaborator.getCollaboratorUserData();
		this.startDates = new ArrayList<Calendar>();
		this.startDates.add(Calendar.getInstance());
		this.finishDates = new ArrayList<Calendar>();
		this.cost = new ArrayList<Integer>();
		this.cost.add(collaborator.getCollaboratorCost());
		this.hoursSpent = new ArrayList<Integer>();
		this.hoursSpent.add(0);
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
		if (this.startDates.size() != this.finishDates.size()) {
			return false;
		}
		return true;
	}

	/**
	 * Adds Finish Date to task worker list
	 */
	public void addFinishDateForTaskWorker() {
		this.finishDates.add(Calendar.getInstance());
	}

	/**
	 * Returns a specific Start Date
	 * 
	 * @param i
	 *            index of the the date wanted
	 * 
	 * @return StartDate
	 */
	public Calendar getStartDate(int i) {
		return startDates.get(i);
	}

	/**
	 * Returns a specific Finish Date
	 * 
	 * @param i
	 *            index of the date wanted
	 * 
	 * @return Finish Date
	 */
	public Calendar getFinishDate(int i) {
		return finishDates.get(i);
	}

	/**
	 * Returns the hours spent by the collaborator in a specific period
	 * 
	 * @param i
	 *            index of hours spent
	 * 
	 * @return hoursSpent
	 */
	public int getHoursSpent(int i) {
		return hoursSpent.get(i);
	}

	/**
	 * Returns the cost of the collaborator in a specific period
	 * 
	 * @param i
	 *            index of the cost wanted
	 * 
	 * @return cost
	 */
	public int getCost(int i) {
		return cost.get(i);
	}

	/**
	 * Sets the hours spent by the user in this task in this period
	 * 
	 * @param hoursSpent
	 */
	public void setHoursSpent(int hoursSpent) {
		this.hoursSpent.set(this.hoursSpent.size() - 1, hoursSpent);
	}

}
