package main.java.project.model;

//
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Class to build Projects.
 * 
 * From this class one can create a new Project(object), which is defined by its
 * name, email, idNumber, function, address list, phone number, and by its
 * profile.
 * 
 * @author Group 3
 *
 */

public class Project {

	private int projectIdCode;
	private int status;
	private TaskRepository taskRepository;
	private User projectManager;
	private List<ProjectCollaborator> projectTeam;
	private String name;
	private String description;
	private EffortUnit effortUnit;
	private int budget;
	private Calendar startdate;
	private Calendar finishdate;
	public static final int PLANNING = 0; // planeado
	public static final int INITIATION = 1; // arranque
	public static final int EXECUTION = 2; // execução
	public static final int DELIVERY = 3; // entrega
	public static final int REVIEW = 4; // garantia
	public static final int CLOSE = 5; // fecho

	/**
	 * Project Constructor that demands the fields name, description and the Project
	 * Manager and contains the other non mandatory fields idCode, status,
	 * projectTaskList, projectTeam
	 * 
	 * @param name
	 * @param description
	 * @param projectManager
	 */

	public Project(int projCounter, String name, String description, User projectManager) {

		this.projectIdCode = projCounter;
		this.name = name;
		this.description = description;
		this.projectManager = projectManager;
		this.effortUnit = EffortUnit.HOURS;
		this.budget = 0;
		this.status = PLANNING;
		this.startdate = null;
		this.finishdate = null;
		this.taskRepository = new TaskRepository(projectIdCode);
		this.projectTeam = new ArrayList<ProjectCollaborator>();
	}

	/**
	 * Creates an instance of ProjectCollaborator
	 * 
	 * @param collaborator
	 *            User to add to Project Collaborator
	 * @param costPerEffort
	 *            Cost per Effort of user In Project
	 * 
	 * @return The new Project Collaborator instantiated
	 */
	public ProjectCollaborator createProjectCollaborator(User collaborator, int costPerEffort) {

		ProjectCollaborator newProjectCollaborator = new ProjectCollaborator(collaborator, costPerEffort);

		return newProjectCollaborator;
	}

	/**
	 * This method allows the projectManager to be changed
	 * 
	 * @param newProjectManager
	 *            New Project Manager to Set
	 */
	public void setProjectManager(User newProjectManager) {
		this.projectManager = newProjectManager;
	}

	/**
	 * This method returns the Task Repository for this specific Project
	 * 
	 * @return taskRepository Task Repository for this Project
	 */
	public TaskRepository getTaskRepository() {
		return taskRepository;
	}

	/**
	 * Get the users that belong to this Project's Team
	 * 
	 * @return Project Team Team of the Project
	 */
	public List<ProjectCollaborator> getProjectTeam() {
		return this.projectTeam;
	}

	/**
	 * This method returns the state of the attribute status that can be planning,
	 * initiation, execution , delivery, review and close.
	 * 
	 * @return the status planning, initiation, execution , delivery, review or
	 *         close
	 */
	public int getProjectStatus() {
		return this.status;
	}

	/**
	 * This method allows the setting of the Project status, regardless of how it is
	 * previously instanced
	 * 
	 * @param newStatus
	 * @return the set status that can be planning, initiation, execution ,
	 *         delivery, review or close
	 */
	public void setProjectStatus(int newStatus) {
		this.status = newStatus;
	}

	/**
	 * Check if the user provided is the project manager or not. This method exists
	 * because when adding a user to a UserTeam, the user cannot be the project
	 * manager.
	 * 
	 * @param toCheck
	 *            User provided to check if he is the Project Manager
	 * @return TRUE if the user is the project manager False if the user is not the
	 *         project manager
	 */
	public boolean isProjectManager(User toCheck) {
		boolean result = false;
		if (toCheck.equals(this.projectManager)) {
			result = true;
		}
		return result;
	}

	/**
	 * Add Project Collaborator to project team if is missing from the projectTeam.
	 * 
	 * @param newAddedProjectCollaborator
	 *            Project Collaborator to add to the Project Team
	 */
	public void addUserToProjectTeam(ProjectCollaborator newAddedProjectCollaborator) {
		if (!containsUser(newAddedProjectCollaborator.getCollaboratorUserData())) {
			this.projectTeam.add(newAddedProjectCollaborator);
		}
	}

	/**
	 * 
	 * Add user to project team if is missing from the projectTeam. Checks if it's
	 * the project manager that wants to add the user to the team.
	 * 
	 * @param projectManager
	 *            User to compare to the Project's Manager
	 * @param toAdd
	 *            User to add to the Project Team
	 */
	// public void addUserToProjectTeam(User projectManager, User toAdd) {
	// if (this.isProjectManager(projectManager)) {
	// if (!this.projectTeam.contains(toAdd)) {
	// this.projectTeam.add(toAdd);
	// }
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + projectIdCode;
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
		Project other = (Project) obj;
		if (projectIdCode != other.projectIdCode)
			return false;
		return true;
	}

	// THIS METHOD SHOULD ONLY EXIST IN TASK AS IT IS THE TASK RESPONSIBILITY TO ADD
	// A USER TO ITS TASK
	// /**
	// * Adds user to task in project. Company calls this method to add user to
	// Task.
	// *
	// * @param user
	// * @param task
	// */
	// public void addUserToTaskInProject(User user, Task task) {
	// for (Task other : this.projectTaskList) {
	// if (task.equals(other)) {
	// task.addUserToTask(user);
	// }
	// }
	// }

	/**
	 * Checks if the user is in the Project Team. A project collaborator contains a
	 * User that is a collaborator and has a cost associated to him
	 * 
	 * @param user
	 *            User to add as ProjectCollaborator
	 * 
	 * @return TRUE if the user exists in the project team FALSE if the user does
	 *         not exist in the project team
	 */
	public boolean containsUser(User user) {
		for (ProjectCollaborator other : this.projectTeam) {
			if (user.equals(other.getCollaboratorUserData())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get Project idCode
	 * 
	 * @return idCode of Project
	 */
	public int getIdCode() {
		return this.projectIdCode;
	}

	/**
	 * Get Project effortUnit
	 * 
	 * @return EfforUnit of Project
	 */
	public EffortUnit getEffortUnit() {
		return this.effortUnit;
	}

	/**
	 * This method checks which ProjectCollaborators don't have tasks assigned.
	 * First, it copies the active project team; then, cycles each collaborator
	 * through each task and verifies if it's active on any task. If so, it's
	 * removed from the inactiveCollaborators list.
	 *
	 * @Return returns a List of Inactive Collaborators
	 *
	 */
	// public List<ProjectCollaborator> getCollaboratorsWithoutTasks() {
	// List<ProjectCollaborator> inactiveCollaborators = new
	// ArrayList<ProjectCollaborator>();
	//
	// inactiveCollaborators.addAll(this.getProjectTeam());
	//
	// for (ProjectCollaborator other : this.getProjectTeam()) {
	// for (Task otherTask : this.taskRepository.getProjectTaskList()) {
	// if (otherTask.taskTeamContainsUser(other.getCollaboratorUserData())) // needs
	// to check if collaborator
	// // is active
	// inactiveCollaborators.remove(other);
	// break;
	// }
	// }
	// return inactiveCollaborators;
	//
	// }

	public List<ProjectCollaborator> getCollaboratorsWithoutTasks() {
		List<ProjectCollaborator> inactiveCollaborators = new ArrayList<ProjectCollaborator>();
		inactiveCollaborators.addAll(this.getProjectTeam());
		for (ProjectCollaborator other : this.getProjectTeam()) {
			if (this.taskRepository.isCollaboratorActiveOnTasks(other.getCollaboratorUserData())) // needs to check if
																									// collaborator is
																									// active
				inactiveCollaborators.remove(other);
		}
		return inactiveCollaborators;
	}

	/**
	 * This method allows removing a Project Collaborator from a Project Team and
	 * includes removing that Project Collaborator from all Tasks in this Project
	 * 
	 * @param collaboratorToRemoveFromProjectTeam
	 *            Collaborator to remove from project
	 */

	public void removeCollaboratorFromProjectTeam(ProjectCollaborator collaboratorToRemoveFromProjectTeam) {

		if (this.projectTeam.contains(collaboratorToRemoveFromProjectTeam)) {
			collaboratorToRemoveFromProjectTeam.setState(false); // DELETE to be removed after deleting boolean
																	// inProject in Class ProjectCollaborator
			projectTeam.remove(collaboratorToRemoveFromProjectTeam); // removes collaborator from ProjectTeam

			for (Task otherTask : this.taskRepository
					.getAllTasks(collaboratorToRemoveFromProjectTeam.getCollaboratorUserData())) {
				otherTask.removeUserFromTask(collaboratorToRemoveFromProjectTeam.getCollaboratorUserData()); // Deactivates
																												// the
																												// Collaborator
																												// that
				// is removed from
				// ProjectCollaborator team
			}
		}

	}

	/**
	 * This method calculates the sum of the values reported to the task until the
	 * moment
	 * 
	 * @return The value reported to the project until the moment
	 */

	public double getTotalCostReportedToProjectUntilNow() {
		double reportedCost = 0.0;

		for (Task task : taskRepository.getProjectTaskList()) {
            reportedCost += task.getReportedBudgetToTheTask();
        }
		
		return reportedCost;
	}

}