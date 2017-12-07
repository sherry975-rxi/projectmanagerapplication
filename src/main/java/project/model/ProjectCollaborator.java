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
	 * This method views the Collaborator's User Data
	 * 
	 */
	public User getCollaboratorUserData() {
		return this.collaborator;
	}

	@Override
	public boolean equals(Object toCompare) {
		boolean result = false;
		if (toCompare instanceof ProjectCollaborator) {
			ProjectCollaborator collaborator1 = (ProjectCollaborator) toCompare;
			if (this.collaborator.equals(collaborator1.collaborator)) {
				result = true;
			}
		}
		return result;
	}
}
