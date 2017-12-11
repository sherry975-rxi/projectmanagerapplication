package main.java.project.model;

public class ProjectCollaborator {

	private User collaborator;
	private boolean inProject;
	private int costPerEffort;

	/**
	 * Constructor of the class ProjectCollaborator. This contains a collaborator's
	 * user data, their status on the project, and their cost per effort.
	 * 
	 * @param collab
	 *            the collaborator's user data
	 * @param cost
	 *            how much the collaborator costs per unit of effort
	 */

	public ProjectCollaborator(User collab, int cost) {
		this.collaborator = collab;
		this.inProject = true;
		this.costPerEffort = cost;
	}

	/**
	 * This method sets the Collaborator as working or not working on the project.
	 * 
	 * @param status
	 *            determines whether the collaborator is still working on the
	 *            project (true by default)
	 */
	public void setState(boolean status) {
		this.inProject = status;
	}

	/**
	 * This method views whether the Collaborator is working or not on the project
	 * 
	 */
	public boolean isCollaboratorInProject() {
		return this.inProject;
	}

	/**
	 * This method views the Collaborator's cost per effort unit
	 * 
	 */
	public int getCollaboratorCost() {
		return this.costPerEffort;
	}

	/**
	 * This method views the Collaborator's User Data. THIS METHOD WILL BE PURGED
	 * 
	 * @return Returns the User Data
	 */
	public User getCollaboratorUserData() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collaborator == null) ? 0 : collaborator.hashCode());
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
		if (collaborator == null) {
			if (other.collaborator != null)
				return false;
		} else if (!collaborator.equals(other.collaborator))
			return false;
		return true;
	}
}
