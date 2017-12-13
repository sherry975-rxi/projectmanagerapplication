package main.project.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskRepository {

	private int taskCounter;
	private int projId;
	private List<Task> projectTasks;

	public TaskRepository(int projId) {

		this.projectTasks = new ArrayList<Task>();
		this.taskCounter = 1;
		this.projId = projId;

	}

	/**
	 * Creates an instance of Task
	 * 
	 * @param description
	 * 
	 * @return the task created
	 */

	public Task createTask(String description, int estimatedTaskEffort, Calendar estimatedTaskStartDate,
			Calendar taskDeadline, int estimatedBudgetCostTask) {

		Task newTask = new Task(this.taskCounter, this.projId, description, estimatedBudgetCostTask,
				estimatedTaskStartDate, taskDeadline, estimatedBudgetCostTask);
		taskCounter++;
		return newTask;
	}

	/**
	 * Get the complete task list for the project
	 * 
	 * @return Project Task List
	 */
	public List<Task> getProjectTaskList() {
		return this.projectTasks;
	}

	/**
	 * Add a task to the project tasks list
	 * 
	 * @param toAdd
	 *            Task to add to the Project Task List
	 */
	public void addProjectTask(Task toAdd) {
		this.projectTasks.add(toAdd);
	}

	/**
	 * This method returns only the unfinished tasks in a project.
	 * 
	 * @return UnfinishedTaskList The list if tasks that are not finished
	 */
	public List<Task> getUnFinishedTasksFromUser(User user) {

		List<Task> unfinishedTaskList = new ArrayList<Task>();
		unfinishedTaskList.addAll(this.getAllTasks(user));

		for (Task other : this.getAllTasks(user)) {
			if (other.isFinished()) {
				unfinishedTaskList.remove(other);
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

		for (Task other : this.projectTasks) {
			if (other.isFinished()) {
				if (other.taskTeamContainsUser(user)) {
					finishedTaskList.add(other);

				}
			}
		}

		return finishedTaskList;
	}

	/**
	 * This method returns a list with the finished tasks from the month provided by
	 * the user
	 * 
	 * @param user
	 *            user who is in the tasks
	 * @param monthsAgo
	 *            how many months to subtract
	 * @return lastMonthFinishedTaskList List of all tasks finished the previous
	 *         month, by the user
	 */
	public List<Task> getFinishedTasksGivenMonth(User user, int monthsAgo) {
		Calendar givenMonth = Calendar.getInstance();
		givenMonth.add(Calendar.MONTH, -monthsAgo);
		List<Task> lastMonthFinishedTaskList = new ArrayList<Task>();

		for (Task other : this.getAllTasks(user)) {
			if (other.getFinishDate() != null) {
				if (monthsAgo < 0) {
					lastMonthFinishedTaskList.add(other);
				} else if (other.getFinishDate().get(Calendar.MONTH) == givenMonth.get(Calendar.MONTH)) {
					lastMonthFinishedTaskList.add(other);
				}
			}
		}
		return lastMonthFinishedTaskList;
	}

	/**
	 * Checks if the task exist in the task list
	 * 
	 * @param task
	 *            to check
	 * @return TRUE if task exists in the task list FALSE if task does not exist in
	 *         the task list
	 */
	public boolean containsTask(Task task) {
		for (Task other : this.projectTasks) {
			if (task.equals(other)) {
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
		lastMonth.addAll(this.getFinishedTasksGivenMonth(user, 1));
		double totalTime = 0;
		for (Task test : lastMonth) {
			if (test.taskTeamContainsUser(user) && test.getFinishDate() != null) {
				totalTime = totalTime + test.getTimeSpentOntask(user);

			}
		}
		return totalTime;
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

	/**
	 * Gets the Project Id
	 * 
	 * @return the Project Id
	 */
	public int getProjId() {
		return projId;
	}

	/**
	 * This method returns a list with all the tasks of a certain user in the
	 * project
	 * 
	 * @param user
	 *            User (to be able to return its tasks)
	 * 
	 * @return AllTasksList List if all tasks from a user
	 */
	public List<Task> getAllTasks(User user) {
		List<Task> allTasks = new ArrayList<Task>();
		for (Task other : this.getProjectTaskList()) {
			if (other.taskTeamContainsUser(user)) {
				allTasks.add(other);
			}
		}
		return allTasks;
	}

	/**
	 * 
	 * This method checks if a given user doesnt have any task assigned to him
	 * 
	 * @param user
	 *            Project Collaborator
	 * @return true if the user doesnt have a task. False if he has at least one
	 *         task
	 */
	public boolean isCollaboratorActiveOnTasks(User user) {
		for (Task otherTask : this.getProjectTaskList()) {
			if (otherTask.taskTeamUserIsActive(user))
				return true;
		}
		return false;
	}

	/**
	 * This method tells every task within the project task repository to check if
	 * its team is empty.
	 * 
	 * @return listOfTasksWithoutCollaboratorsAssigned List with all the tasks with
	 *         no collaborators assigned.
	 */
	public List<Task> getListofTasksWithoutCollaboratorsAssigned() {

		List<Task> listOfTasksWithoutCollaboratorsAssigned = new ArrayList<Task>();

		for (Task other : this.getProjectTaskList()) {
			if (other.isTaskTeamEmpty()) {
				listOfTasksWithoutCollaboratorsAssigned.add(other);
			}
		}

		return listOfTasksWithoutCollaboratorsAssigned;
	}

	/**
	 * This method creates a list with all finished tasks in project.
	 * 
	 * @return allFinishedTasks
	 */
	public List<Task> getFinishedTasks() {
		List<Task> allFinishedTasks = new ArrayList<Task>();

		for (Task other : this.getProjectTaskList()) {
			if (other.isFinished()) {
				allFinishedTasks.add(other);
			}
		}

		return allFinishedTasks;
	}

	/**
	 * this method create a list whit all unfinished tasks in project.
	 * 
	 * @return allUnFinishedTasks
	 */
	public List<Task> getUnFinishedTasks() {
		List<Task> allUnFinishedTasks = new ArrayList<Task>();

		for (Task other : this.getProjectTaskList()) {
			if (!other.isFinished() && other.getStartDate() != null) {
				allUnFinishedTasks.add(other);
			}

		}
		return allUnFinishedTasks;
	}

	/**
	 * this method create a list with all unstarted tasks in project.
	 * 
	 * @return allUnstartedTasks
	 */
	public List<Task> getUnstartedTasks() {
		List<Task> allUnstartedTasks = new ArrayList<Task>();

		for (Task other : this.getProjectTaskList()) {
			if (other.getStartDate() == null) {
				allUnstartedTasks.add(other);
			}
		}
		return allUnstartedTasks;
	}

	/**
	 * Returns a list of the tasks which are unfinished but which deadline has
	 * already passed
	 * 
	 * @return expiredTasks
	 */
	public List<Task> getExpiredTasks() {
		Calendar today = Calendar.getInstance();
		List<Task> expiredTasks = new ArrayList<Task>();
		for (Task other : this.projectTasks) {
			if (!other.isFinished()) {
				if (other.getTaskDeadline().get(Calendar.YEAR) < today.get(Calendar.YEAR)) {
					expiredTasks.add(other);
				} else if (other.getTaskDeadline().get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR)
						&& other.getTaskDeadline().get(Calendar.YEAR) == today.get(Calendar.YEAR)) {

					expiredTasks.add(other);
				}
			}
		}
		return expiredTasks;
	}

}