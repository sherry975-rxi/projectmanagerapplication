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

	private int idCode;
	private int taskCounter;
	private int status;
	private List<Task> projectTaskList;
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
		this.projectTaskList = new ArrayList<Task>();
		this.projectTeam = new ArrayList<User>();
	}

	/**
	 * Creates an instance of Task
	 * 
	 * @param proj
	 * @param description
	 * 
	 * @return the task created
	 */
	public Task createTask(String description) {

		Task newTask = new Task(this, description);

		return newTask;

	}

	/**
	 * Get the complete task list for the project
	 * 
	 * @return Project Task List
	 */
	public List<Task> getProjectTaskList() {
		return this.projectTaskList;
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
	 * Add a task to the project tasks list
	 * 
	 * @param toAdd
	 *            Task to add to the Project Task List
	 */
	public void addProjectTask(Task toAdd) {
		this.projectTaskList.add(toAdd);
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

	/**
	 * This method returns only the unfinished tasks in a project.
	 * 
	 * @return UnfinishedTaskList The list if tasks that are not finished
	 */
	public List<Task> getUnFinishedTaskList(User user) {

		List<Task> unfinishedTaskList = new ArrayList<Task>();

		for (Task other : this.projectTaskList) {
			if (!other.isFinished()) {
				if (other.taskTeamContainsUser(user)) {
					unfinishedTaskList.add(other);
				}
			}

		}

		return unfinishedTaskList;

	}

	/**
	 * This method returns only the finished tasks of a user in a project.
	 * 
	 * @return FinishedTaskList The list if tasks that are finished
	 */
	public List<Task> getFinishedTaskListofUserInProject(User user) {

		List<Task> finishedTaskList = new ArrayList<Task>();

		for (Task other : this.projectTaskList) {
			if (other.isFinished()) {
				if (other.taskTeamContainsUser(user)) {
					finishedTaskList.add(other);

				}
			}
		}

		return finishedTaskList;
	}

	/**
	 * This method returns a list with all the tasks of a certain user in the
	 * project (finished + unfinished)
	 * 
	 * @param user
	 *            User (to be able to return its tasks)
	 * 
	 * @return AllTasksList List if all tasks from a user
	 */
	public List<Task> getAllTasks(User user) {
		List<Task> allTasks = new ArrayList<Task>();
		allTasks.addAll(this.getFinishedTaskListofUserInProject(user));
		allTasks.addAll(this.getUnFinishedTaskList(user));
		return allTasks;
	}

	/**
	 * This method returns a list with the finished tasks from the last month in
	 * regards to the month provided
	 * 
	 * @param thisMonth
	 *            Goes from 0 to 11 (0 - January, 1 - February, 2 - March [...] 11 -
	 *            December)
	 * @return lastMonthFinishedTaskList List of all tasks finished the previous
	 *         month, by the user
	 */
	public List<Task> getFinishedTaskListLastMonth(User user) {
		Calendar previousMonth = Calendar.getInstance();
		previousMonth.add(Calendar.MONTH, -1);
		List<Task> lastMonthFinishedTaskList = new ArrayList<Task>();

		for (Task other : this.getFinishedTaskListofUserInProject(user)) {
			if (other.getFinishDate() != null) {
				if (other.getFinishDate().get(Calendar.MONTH) == previousMonth.get(Calendar.MONTH)) {
					lastMonthFinishedTaskList.add(other);
				}
			}
		}
		return lastMonthFinishedTaskList;
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
	 * Checks if the task exist in the task list
	 * 
	 * @param task
	 *            to check
	 * @return TRUE if task exists in the task list FALSE if task does not exist in
	 *         the task list
	 */
	public boolean containsTask(Task task) {
		for (Task other : this.projectTaskList) {
			if (task.equals(other)) {
				return true;
			}
		}
		return false;
	}

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
	 * This method returns the total time spent by a user in tasks from a project
	 * Last month
	 * 
	 * @param user
	 * @return Time spent on last month project user tasks
	 */
	public double getTimeSpentOnLastMonthProjectUserTasks(User user) {
		List<Task> lastMonth = new ArrayList<Task>();
		lastMonth.addAll(this.getFinishedTaskListLastMonth(user));
		double totalTime = 0;
		for (Task test : lastMonth) {
			if (test.taskTeamContainsUser(user) && test.getFinishDate() != null) {
				totalTime = totalTime + test.getTimeSpentOnTask();

			}
		}
		return totalTime;
	}

	/**
	 * Get Project idCode
	 * 
	 * @return idCode of Project
	 */
	public int getIdCode() {
		return this.idCode;
	}

	/**
	 * Sets Counter of tasks
	 * 
	 * @param count
	 *            Integer to set the count of the task
	 */
	public void setTaskCounter(int count) {
		this.taskCounter = count;
	}

	/**
	 * Gets the counter of tasks
	 * 
	 * @return the count of the tasks
	 */
	public int getTaskCounter() {
		return this.taskCounter;
	}

}