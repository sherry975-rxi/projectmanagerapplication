package main.java.project.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskRepository {

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
	 * Add a task to the project tasks list
	 * 
	 * @param toAdd
	 *            Task to add to the Project Task List
	 */
	public void addProjectTask(Task toAdd) {
		this.projectTaskList.add(toAdd);
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
