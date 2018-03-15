package project.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Entity
@Table(name = "TaskTeamRequest")
public class TaskTeamRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "ProjectCollaborator_id")
	private ProjectCollaborator projCollab;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "Task_id")
	private Task task;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "Project_id")
	private Project project;

	private Integer type;
	public static final int ASSIGNMENT = 0;
	public static final int REMOVAL = 1;


	/*
	 * 	Constructor of TaskTeamRequest
	 * 	Receives as Parameters the ProjectCollaborator who makes the request and the task that he wants to be associated to
	 */
	public TaskTeamRequest(ProjectCollaborator projCollab, Task task) {
		this.projCollab = projCollab;
		this.task = task;

	}

	/*
	 * 	Empty Constructor for TaskTeamRequest
	 */
	public TaskTeamRequest() {

	}

	/*
	 * Returns the id of the task
	 */
	public int getId() {
		return id;
	}

	/*
	 * Sets a task D
	 */
	public void setId(int id) {
		this.id = id;
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
		return projCollab;
	}

	/*
	 * This method returns the Task associated of the TaskTeamRequest
	 *
	 * @return Task
	 * 	 The Task associated to the TaskTeamRequest
	 */
	public Task getTask() {
		return task;
	}


	public void setProject(Project proj) {
		this.project=proj;
	}

	public Project getProject() {
		return project;
	}


	public String getType() {
	    String typeString ="N/A";
	    if (type==0) {
            typeString = "Assignment";
        } else if (type==1) {
            typeString = "Removal";
        }
	    return typeString;
    }

    public boolean isAssignmentRequest() {
	    return type==TaskTeamRequest.ASSIGNMENT;
    }

    public boolean isRemovalRequest() {
        return type==TaskTeamRequest.REMOVAL;
    }

    public void setType(int typ) {
	    this.type=typ;
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
		    if (other.type != null) {
		        return false;
            }
        } else if (type != other.type) {
		    return false;
        }
		return true;
	}

	/**
	 * Takes the attributes of the request and converts the name and email of the
	 * collaborator and the id and description of the task into a string
	 *
	 * @return The string representation
	 */
	public String viewStringRepresentation() {

		return this.projCollab.getUserFromProjectCollaborator().getName() + "\n"
				+ this.projCollab.getUserFromProjectCollaborator().getEmail() + "\n"
				+ this.task.getTaskID() + "\n" + this.task.getDescription();

	}

}
