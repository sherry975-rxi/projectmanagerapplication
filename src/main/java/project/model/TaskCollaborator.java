package project.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "TaskCollaborator")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "taskCollabDbId", scope = TaskCollaborator.class)
public class TaskCollaborator extends ResourceSupport implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIdentityReference(alwaysAsId = true)
    private long taskCollabDbId;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
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

    public Long getDbId() {
        return taskCollabDbId;
	}

	public ProjectCollaborator getProjCollaborator() {
		return projCollaborator;
	}

	public void setProjCollaborator(ProjectCollaborator projCollaborator) {
		this.projCollaborator = projCollaborator;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public void setFinishDate(Calendar finishDate) {
		this.finishDate = finishDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setId(Long id) {
        this.taskCollabDbId = id;
    }

    public long getTaskCollabDbId() {
        return taskCollabDbId;
	}

	/**
	 * Returns the user associated to the ProjectCollaborator of this Task
	 * Collaborator
	 * 
	 * @return user
	 */
	@Transactional
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
    
    public double getCost() {
    	return this.getProjectCollaboratorFromTaskCollaborator().getCostPerEffort();
    }

    @JsonProperty("taskCollabDbId")
    public void setTaskCollabDbId(long taskCollabDbId) {
        this.taskCollabDbId = taskCollabDbId;
    }
}



