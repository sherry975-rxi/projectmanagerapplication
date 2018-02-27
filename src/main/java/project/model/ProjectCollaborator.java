package project.model;

public class ProjectCollaborator {

	private User collaborator;
	private boolean status;
	private int costPerEffort;

	/**
	 * Constructor of the class ProjectCollaborator. This contains a Project
	 * Collaborator's user data, their status on the project, and their cost per
	 * effort.
	 * 
	 * @param collab
	 *            the Project Collaborator's user data
	 * @param costPerEffort
	 *            how much the Project Collaborator costs per unit of effort
	 */

	public ProjectCollaborator(User collab, int costPerEffort) {
		this.collaborator = collab;
		this.status = true;
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
	public void setState(boolean status) {
		this.status = status;
	}

	/**
	 * This method views whether the Project Collaborator is working or not on the
	 * project
	 * 
	 */
	public boolean isProjectCollaboratorActive() {
		return this.status;
	}

	/**
	 * This method views the Project Collaborator's cost per effort unit
	 * 
	 */
	public int getCollaboratorCost() {
		return this.costPerEffort;
	}

	/**
	 * This method views the Project Collaborator's User Data. THIS METHOD WILL BE
	 * PURGED
	 * 
	 * @return Returns the User Data
	 */
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
