package project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "TaskTeamRequest")
public class TaskTeamRequest extends ResourceSupport implements Serializable {

	static final long serialVersionUID = 61L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int taskRequestDbId;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ProjectCollaborator_id")
	private ProjectCollaborator projCollab;

    @LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "Task_id")
	private Task task;

	@Enumerated(EnumType.STRING)
	private RequestType type;

	private Calendar approvalDate;
	private Calendar rejectDate;

	/*
	 * 	Empty Constructor for TaskTeamRequest
	 */
	public TaskTeamRequest() {

	}

	/*
	 * 	Constructor of TaskTeamRequest
	 * 	Receives as Parameters the ProjectCollaborator who makes the request and the task that he wants to be associated to
	 */
	public TaskTeamRequest(ProjectCollaborator projCollab, Task task) {
		this.projCollab = projCollab;
		this.task = task;
		this.type=RequestType.NOT_AVAILABLE;
		this.approvalDate=null;
		this.rejectDate=null;

	}

    public int getTaskRequestDbId() {
        return taskRequestDbId;
	}

	/*
	 * Sets a task D
	 */
    @JsonProperty("taskRequestDbId")
    public void setTaskRequestDbId(int taskRequestDbId) {
        this.taskRequestDbId = taskRequestDbId;
    }

    /*
     * Returns the taskRequestDbId of the task
     */
    public int getDbId() {
        return taskRequestDbId;
	}

	/*
	 *	Sets a Project Collaborator to Parameter projCollab of the TaskTeamRequest
	 */
	public void setProjCollab(ProjectCollaborator projCollab) {
		this.projCollab = projCollab;
	}

	/*
	 * Sets a Task to the Task Parameter of the TaskTeamRequest
	 */
	public void setTask(Task task) {
		this.task = task;
	}


	/*
	 * This method returns the projectCollaborator parameter of the TaskTeamRequest
	 *
	 * @return ProjectCollaborator
	 * 	 The Project Collaborator associated to the TaskTeamRequest
	 */
	public ProjectCollaborator getProjCollab() {
		return this.projCollab;
	}

	/*
	 * This method returns the Task associated of the TaskTeamRequest
	 *
	 * @return Task
	 * 	 The Task associated to the TaskTeamRequest
	 */
	public Task getTask() {
		return this.task;
	}


	public RequestType getType() {

		return this.type;
	}

	public boolean isAssignmentRequest() {
		return this.type==RequestType.ASSIGNMENT;
	}

	public boolean isRemovalRequest() {
		return this.type==RequestType.REMOVAL;
	}

	public void setType(RequestType newType) {
		this.type= newType;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 3;
		result = prime * result + ((projCollab == null) ? 0 : projCollab.hashCode());
		result = result + ((task == null) ? 0 : task.hashCode());
		return result;
	}

	/**
	 * Checks if two requests are the equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TaskTeamRequest)) {
			return false;
		}
		TaskTeamRequest other = (TaskTeamRequest) obj;
		if (projCollab == null) {
			if (other.projCollab != null) {
				return false;
			}
		} else if (!projCollab.equals(other.projCollab)) {
			return false;
		}
		if (task == null) {
			if (other.task != null) {
				return false;
			}
		} else if (!task.equals(other.task)) {
			return false;
		}
		if (type == null) {
			return other.type == null;
		} else return type == other.type;
	}

	/**
	 * Takes the attributes of the request and converts the name and email of the
     * collaborator and the taskRequestDbId and description of the task into a string
	 *
	 * @return The string representation
	 */
	public String viewStringRepresentation() {

		return this.projCollab.getUserFromProjectCollaborator().getName() + "\n"
				+ this.projCollab.getUserFromProjectCollaborator().getEmail() + "\n"
				+ this.task.getTaskID() + "\n" + this.task.getDescription();

	}

	public Calendar getApprovalDate() {
		return approvalDate;
	}

	public Calendar getRejectDate() {
		return rejectDate;
	}

	public void setApprovalDate(Calendar approvalDate) {
		this.approvalDate = approvalDate;
	}

	public void setRejectDate(Calendar rejectDate) {
		this.rejectDate = rejectDate;
	}
}

