package project.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "TaskCollaborator")
public class TaskCollaborator implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ProjectCollaborator_id")
	private ProjectCollaborator projCollaborator;
	private Calendar startDate;
	private Calendar finishDate;
	private boolean status;
	static final long serialVersionUID = 52L;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Task_id")
	private Task task;

	/**
	 * Empty Constructor for TaskCollaborator
	 */
	protected TaskCollaborator(){
	}

	/**
	 * Constructor to create a new task collaborator
	 *
	 * Collaborator is set as the Project Collaborator provided. A start date is set
	 * automatically. Finish date is added after collaborator is removed.
	 *
	 * @param projCollaborator
	 *            projectCollaborator to create the new TaskCollaborator
	 */
	public TaskCollaborator(ProjectCollaborator projCollaborator) {
		this.projCollaborator = projCollaborator;
		this.startDate = Calendar.getInstance();
		this.finishDate = null;
		this.status = true;
	}

	public TaskCollaborator setId(long id) {
		this.id = id;
		return this;
	}

	public ProjectCollaborator getProjCollaborator() {
		return projCollaborator;
	}

	public TaskCollaborator setProjCollaborator(ProjectCollaborator projCollaborator) {
		this.projCollaborator = projCollaborator;
		return this;
	}

	public TaskCollaborator setStartDate(Calendar startDate) {
		this.startDate = startDate;
		return this;
	}

	public TaskCollaborator setFinishDate(Calendar finishDate) {
		this.finishDate = finishDate;
		return this;
	}

	public boolean isStatus() {
		return status;
	}

	public TaskCollaborator setStatus(boolean status) {
		this.status = status;
		return this;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the user associated to the ProjectCollaborator of this Task
	 * Collaborator
	 * 
	 * @return user
	 */
	public User getTaskCollaborator() {
		return this.projCollaborator.getUserFromProjectCollaborator();
	}

	/**
	 * Gets the ProjectCollaborator of this Task Collaborator
	 * 
	 * @return Returns the ProjectCollaborator of this Task Collaborator
	 */
	public ProjectCollaborator getProjectCollaboratorFromTaskCollaborator() {
		return this.projCollaborator;
	}

	/**
	 * Checks if the a project collaborator is in a task collaborator.
	 * 
	 * @return TRUE if the Project Collaborator is in this TaskCollaborator FALSE if
	 *         not
	 */
	public boolean isProjectCollaboratorInTaskCollaborator(ProjectCollaborator projCollabToCheck) {

		return this.projCollaborator.equals(projCollabToCheck);

	}

	/**
	 * Returns the state of the TaskCollaborator. If the TaskCollaborator don't have
	 * a finish date, then it's Active, and returns True If the TaskCollaborator has
	 * a finish date, then it's Inactive, and returns False
	 * 
	 * @return TRUE if the taskCollaborator does not have a finish date(null) or
	 *         FALSE if the task collaborator has a finish date.
	 */
	public boolean isTaskCollaboratorActiveInTask() {

		return this.finishDate == null;

	}

	/**
	 * Adds a Finish Date to the task Collaborator
	 */
	public void addFinishDateForTaskCollaborator() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	// Override the Equals method. Compares only if the taskCollaborator and the
	// finishDate are the same
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskCollaborator other = (TaskCollaborator) obj;
		if (finishDate == null) {
			if (other.finishDate != null)
				return false;
		} else if (!finishDate.equals(other.finishDate))
			return false;
		return projCollaborator.equals(other.projCollaborator);
	}


    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}


 /**
	 * Returns the total hours spent by the collaborator
	 *
	 *
	 * @return TotalHoursSpent
	 */

 /*public int getTotalHoursSpent() {
  
  int result = 0;
  
  for (int indexHoursSpent = 0; indexHoursSpent < this.hoursSpent.size();
  indexHoursSpent++) {
  
  result = result + this.hoursSpent.get(indexHoursSpent); }
  
  return result;
 
  }*/
  
 /**
	 * Returns the cost of the collaborator in a specific period
	 *
	 * @param i
	 *            index of the cost wanted
	 *
	 * @return cost
	 */

 /* public int getCost(int i) { return cost.get(i); }*/
 
 /**
	 * Sets the hours spent by the user in this task in this period
	 *
	 * @param hoursSpent
	 */

  /*public void setHoursSpent(int hoursSpent) {
  this.hoursSpent.set(this.hoursSpent.size() - 1, hoursSpent); }
  */
 /**
	 * Gets the size of the cost list
	 *
	 */
/*		  public int getCostListSize() { return this.cost.size(); }*/
		 
