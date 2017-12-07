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
	 * This method allows the projectManager to be changed
	 * 
	 * @param newProjectManager New Project Manager to Set
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
	 * Get the complete user Repository for this Project's Team
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
	 * Check if the user provided is the project manager or not. To add a user to a
	 * UserTeam, the user cannot be the project manager.
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
	 * 
	 * Add user to project team if is missing from the projectTeam.
	 * 
	 * @param toAdd
	 *            User to add to the Project Team
	 * 
	 * @param costPerEffort
	 *            Added User's cost per effort
	 */
	public void addUserToProjectTeam(User toAdd, int costPerEffort) {
		ProjectCollaborator newAddition = new ProjectCollaborator(toAdd, costPerEffort);
		if (!containsUser(toAdd)) {
			this.projectTeam.add(newAddition);
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

	/**
	 * Overrides equals comparing a project to this one, and if their idCode is the
	 * same, returns true, and therefore, they are equal.
	 * 
	 * @param toCompare
	 *            Project to compare to the current Project
	 * @return result TRUE if the projects are equal (equal idCode) FALSE if they
	 *         are not equal (different idCode)
	 * 
	 */
	@Override
	public boolean equals(Object toCompare) {
		boolean result = false;
		if (toCompare instanceof Project) {
			Project proj = (Project) toCompare;
			if (this.projectIdCode == proj.projectIdCode) {
				result = true;
			}
		}
		return result;
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
	 * Checks if the user exist in the User Repository
	 * 
	 * @param user User to add as ProjectCollaborator
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

}