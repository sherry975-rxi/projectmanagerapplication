package main.java.project.model;

//
import java.util.ArrayList;
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

	private int idCode;
	private int status;
	private TaskRepository taskRepository;
	private User projectManager;
	private List<User> projectTeam;
	private String name;
	private String description;
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

	public Project(String name, String description, User projectManager) {

		this.idCode = Company.getTheInstance().getCounter();
		Company.getTheInstance().setCounter(Company.getTheInstance().getCounter() + 1);
		this.name = name;
		this.description = description;
		this.projectManager = projectManager;
		this.status = PLANNING;
		this.taskRepository = new TaskRepository();
		taskRepository.setProjId(IdCode);
		this.projectTeam = new ArrayList<User>();
	}

	/**
	 * Get the complete user list for this project's team
	 * 
	 * @return Project Team
	 */
	public List<User> getProjectTeam() {
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
	 */
	public void addUserToProjectTeam(User toAdd) {
		if (!this.projectTeam.contains(toAdd)) {
			this.projectTeam.add(toAdd);
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
			if (this.idCode == proj.idCode) {
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
	 * Checks if the user exist in the list of users
	 * 
	 * @param user
	 * @return TRUE if the user exists in the project team FALSE if the user does
	 *         not exist in the project team
	 */
	public boolean containsUser(User user) {
		for (User other : this.projectTeam) {
			if (user.equals(other)) {
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
		return this.idCode;
	}

}