package project.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "ProjectCollaborator")
@Transactional
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "projectCollaboratorId", scope = ProjectCollaborator.class)
public class ProjectCollaborator extends ResourceSupport implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIdentityReference(alwaysAsId = true)
	private long projectCollaboratorId;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "User_id")
	private User collaborator;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Project_id")
	private Project project;
	private boolean status;
	private double costPerEffort;

    private Calendar startDate;
	private Calendar finishDate;
	static final long serialVersionUID = 51L;

	/**
	 * Empty Constructor for ProjectCollaborator
	 */
	public ProjectCollaborator() {
	}

	/**
	 * Constructor of the class ProjectCollaborator. This contains a Project
	 * Collaborator's user data, their status on the project, and their cost per
	 * effort.
	 * 
	 * @param collaborator
	 *            the Project Collaborator's user data
	 * @param costPerEffort
	 *            how much the Project Collaborator costs per unit of effort
	 */
	public ProjectCollaborator(User collaborator, double costPerEffort) {
		this.collaborator = collaborator;
		this.status = true;
		this.startDate = Calendar.getInstance();
		this.costPerEffort = costPerEffort;
	}

	public long getProjectCollaboratorId() {
		return projectCollaboratorId;
	}

    @JsonProperty("projectCollaboratorId")
    public void setProjectCollaboratorId(long projectCollaboratorId) {
        this.projectCollaboratorId = projectCollaboratorId;
    }

	public User getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(User collaborator) {
		this.collaborator = collaborator;
	}

	public boolean getStatus() {
		return status;
	}

    /**
     * This method views the Project Collaborator's cost per effort unit
     *
     */

	public double getCostPerEffort() {
		return costPerEffort;
	}

	public void setCostPerEffort(double costPerEffort) {
		this.costPerEffort = costPerEffort;
	}

	/**
	 * This method sets the Project Collaborator as working or not working on the
	 * project.
	 * 
	 * @param status
	 *            determines whether the Project Collaborator is still working on
	 *            the project (true by default)
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * This method views whether the Project Collaborator is working or not on the
	 * project
	 * 
	 */
	public boolean isProjectCollaboratorActive() {
		return this.finishDate==null;
	}

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Calendar finishDate) {
        this.finishDate = finishDate;
    }

	/**
	 * This method views the Project Collaborator's User Data. THIS METHOD WILL BE
	 * PURGED
	 * 
	 * @return Returns the User Data
	 */
	@JsonIgnore
	@Transactional
	public User getUserFromProjectCollaborator() {
		return this.collaborator;
	}

	/**
	 * This method compares a User with a Project Collaborator's User Data.
	 * 
	 * @return Returns true if its the same user
	 */
	public boolean isUser(User toCompare) {
		return toCompare.equals(this.collaborator);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 3;
		result = prime * result + collaborator.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectCollaborator other = (ProjectCollaborator) obj;
		return collaborator.equals(other.collaborator);
	}
}
