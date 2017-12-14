package main.project.model;

import java.util.Calendar;

public class TaskWorker {

	private ProjectCollaborator projCollaborator;
	private Calendar startDate;
	private Calendar finishDate;
	// private Integer hoursSpent;
	// private Integer costPerEffort;
	private boolean status;

	/**
	 * Constructor to create a new task worker
	 * 
	 * Collaborator is set as the Project Collaborator provided. A start date is set
	 * automatically. Hours Spent and Cost are set from the Project Collaborator.
	 * Finish date is added after collaborator is removed.
	 * 
	 * @param projCollaborator
	 *            projectCollaborator to create the new TaskWorker
	 */
	public TaskWorker(ProjectCollaborator projCollaborator) {
		this.projCollaborator = projCollaborator;
		this.startDate = Calendar.getInstance();
		this.finishDate = null;
		// this.hoursSpent = null;
		// this.costPerEffort = projCollaborator.getCollaboratorCost();
		this.status = true;
	}

	/**
	 * Returns the user associated to the ProjectCollaborator of this Task Worker
	 * 
	 * @return user
	 */
	public User getTaskWorker() {
		return this.projCollaborator.getCollaboratorUserData();
	}

	/**
	 * Gets the ProjectCollaborator of this Task Worker
	 * 
	 * @return Returns the ProjectCollaborator of this Task Worker
	 */
	public ProjectCollaborator getProjectCollaboratorFromTaskWorker() {
		return this.projCollaborator;
	}

	/**
	 * Checks if the a project collaborator is in a task worker.
	 * 
	 * @return TRUE if the Project Collaborator is in this TaskWorker FALSE if not
	 */
	public boolean isProjectCollaboratorInTaskWorker(ProjectCollaborator projCollabToCheck) {

		return this.projCollaborator.equals(projCollabToCheck);

	}

	/**
	 * Returns the state of the TaskWorker. If the TaskWorker don't have a finish
	 * date, then it's Active, and returns True If the TaskWorker has a finish date,
	 * then it's Inactive, and returns False
	 * 
	 * @return TRUE if the taskWorker does not have a finish date(null) or FALSE if
	 *         the task worker has a finish date.
	 */
	public boolean isTaskWorkerActiveInTask() {

		return this.finishDate == null;

	}

	/**
	 * Adds a Finish Date to the task worker
	 */
	public void addFinishDateForTaskWorker() {
		this.finishDate = Calendar.getInstance();
		this.status = false;
	}

	/**
	 * Returns a specific Start Date
	 * 
	 * @return StartDate
	 */
	public Calendar getStartDate() {
		return this.startDate;
	}

	/**
	 * Returns a specific Finish Date
	 * 
	 * @return Finish Date
	 */
	public Calendar getFinishDate() {
		return this.finishDate;
	}

	// Override the Equals method. Compares only if the taskworker and the
	// finishDate are the same
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskWorker other = (TaskWorker) obj;
		if (finishDate == null) {
			if (other.finishDate != null)
				return false;
		} else if (!finishDate.equals(other.finishDate))
			return false;
		if (projCollaborator == null) {
			if (other.projCollaborator != null)
				return false;
		} else if (!projCollaborator.equals(other.projCollaborator))
			return false;
		return true;
	}

}

//
// /**
// * Returns the total hours spent by the collaborator
// *
// *
// * @return TotalHoursSpent
// */
// public int getTotalHoursSpent() {
//
// int result = 0;
//
// for (int indexHoursSpent = 0; indexHoursSpent < this.hoursSpent.size();
// indexHoursSpent++) {
//
// result = result + this.hoursSpent.get(indexHoursSpent);
// }
//
// return result;
//
// }
//
// /**
// * Returns the cost of the collaborator in a specific period
// *
// * @param i
// * index of the cost wanted
// *
// * @return cost
// */
// public int getCost(int i) {
// return cost.get(i);
// }
//
// /**
// * Sets the hours spent by the user in this task in this period
// *
// * @param hoursSpent
// */
// public void setHoursSpent(int hoursSpent) {
// this.hoursSpent.set(this.hoursSpent.size() - 1, hoursSpent);
// }
//
// /**
// * Gets the size of the cost list
// *
// */
// public int getCostListSize() {
// return this.cost.size();
// }
