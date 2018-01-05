package project.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskRepository {

	private int taskCounter;
	private int projId;
	private List<Task> projectTasks;

	public TaskRepository(int projId) {

		this.projectTasks = new ArrayList<>();
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

		Task newTask = new Task(this.taskCounter, this.projId, description, estimatedTaskEffort, estimatedTaskStartDate,
				taskDeadline, estimatedBudgetCostTask);
		taskCounter++;
		return newTask;
	}

	/**
	 * Get the complete task list for the project
	 * 
	 * @return Project Task List
	 */
	public List<Task> getProjectTaskRepository() {
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
	public List<Task> getUnfinishedTasksFromProjectCollaborator(ProjectCollaborator collab) {

		List<Task> unfinishedTaskList = new ArrayList<>();
		unfinishedTaskList.addAll(this.getAllTasksFromProjectCollaborator(collab));

		for (Task other : this.getAllTasksFromProjectCollaborator(collab)) {
			if (other.isTaskFinished()) {
				unfinishedTaskList.remove(other);
			}
		}

		return unfinishedTaskList;

	}

	/**
	 * This method returns only the started but not finished tasks assigned to a
	 * ProjectCollaborator.
	 * 
	 * @return incompleteTaskList The list if tasks that are started but not
	 *         finished
	 */
	public List<Task> getStartedNotFinishedTasksFromProjectCollaborator(ProjectCollaborator collab) {

		List<Task> incompleteTaskList = new ArrayList<>();
		incompleteTaskList.addAll(this.getAllTasksFromProjectCollaborator(collab));

		for (Task other : this.getAllTasksFromProjectCollaborator(collab)) {
			if (other.isTaskFinished() || other.getStartDate() == null) {
				incompleteTaskList.remove(other);
			}
		}

		return incompleteTaskList;

	}

	/**
	 * This method returns only the finished tasks of a user in a project.
	 * 
	 * @return FinishedTaskList The list if tasks that are finished
	 */
	public List<Task> getFinishedTaskListofUserInProject(ProjectCollaborator collab) {

		List<Task> finishedTaskList = new ArrayList<>();

		for (Task other : this.projectTasks) {
			if (other.isTaskFinished() && other.isProjectCollaboratorInTaskTeam(collab)) {
				finishedTaskList.add(other);
			}
		}

		return finishedTaskList;
	}

	/**
	 * This method returns a list of all tasks finished a number of months ago by given user.
	 * Given a negative "monthsAgo" input, Returns ALL finished tasks of said user
	 * 
	 * @param user
	 *            user who is in the tasks
	 * @param monthsAgo
	 *            how many months to subtract
	 * @return lastMonthFinishedTaskList List of all tasks finished the previous
	 *         month, by the user
	 */
	public List<Task> getFinishedTasksFromProjectCollaboratorInGivenMonth(ProjectCollaborator collab, int monthsAgo) {
		Calendar givenMonth = Calendar.getInstance();
		givenMonth.add(Calendar.MONTH, -monthsAgo);
		List<Task> lastMonthFinishedTaskList = new ArrayList<>();

		for (Task other : this.getAllTasksFromProjectCollaborator(collab)) {
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
	public boolean isTaskInRTaskRepository(Task task) {
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
	public double getTimeSpentByProjectCollaboratorInAllTasksLastMonth(ProjectCollaborator collab) {
		List<Task> lastMonth = new ArrayList<>();
		lastMonth.addAll(this.getFinishedTasksFromProjectCollaboratorInGivenMonth(collab, 1));
		double totalTime = 0;
		for (Task test : lastMonth) {
			totalTime = totalTime + test.getTimeSpentByProjectCollaboratorOntask(collab);
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
	public List<Task> getAllTasksFromProjectCollaborator(ProjectCollaborator collab) {
		List<Task> allTasks = new ArrayList<>();
		for (Task other : this.getProjectTaskRepository()) {
			if (other.isProjectCollaboratorInTaskTeam(collab)) {
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
	public boolean isCollaboratorActiveOnAnyTask(ProjectCollaborator collab) {
		for (Task otherTask : this.getProjectTaskRepository()) {
			if (otherTask.isProjectCollaboratorActiveInTaskTeam(collab))
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
	public List<Task> getAllTasksWithoutCollaboratorsAssigned() {

		List<Task> listOfTasksWithoutCollaboratorsAssigned = new ArrayList<>();

		for (Task other : this.getProjectTaskRepository()) {

			if (other.isTaskTeamEmpty()) {
				listOfTasksWithoutCollaboratorsAssigned.add(other);
			} else if (!other.doesTaskTeamHaveActiveUsers()) {
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
		List<Task> allFinishedTasks = new ArrayList<>();

		for (Task other : this.getProjectTaskRepository()) {
			if (other.isTaskFinished()) {
				allFinishedTasks.add(other);
			}
		}

		return allFinishedTasks;
	}

	/**
	 * This method returns a list with the tasks of a certain user by decreasing
	 * order of date. First, this method creates a list which is a copy of the task
	 * list of the user. This method just reverses the initial order of the
	 * TaskList. It does not runs a cycle to compare the tasks finish dates, neither
	 * analysis the TaskList in any way.
	 * 
	 * @param toSort
	 *            List of tasks to sort
	 * 
	 * @return sorted list
	 * 
	 */
	public List<Task> sortTaskListDecreasingOrder(List<Task> toSort) {
		List<Task> result = new ArrayList<>();
		result.addAll(toSort);
		for (int i = 0; i < result.size(); i++) {
			for (int j = i + 1; j < result.size(); j++) {
				if (result.get(i).getFinishDate().before(result.get(j).getFinishDate())) {
					Task h = new Task(result.get(i));
					result.set(i, result.get(j));
					result.set(j, h);
				}
			}

		}
		return result;
	}

	/**
	 * This method create a list of all tasks finished from project in decreasing
	 * order. First creates a empty list, then add all finished tasks from the
	 * project using method getFinishedTasks. At last, apply the sort by decreasing
	 * order to that list and return it.
	 * 
	 * @return a list of tasks finished by decreasing order
	 */
	public List<Task> getFinishedTasksInDecreasingOrder() {

		List<Task> finishedTaskListDecreasingOrder = new ArrayList<>();
		finishedTaskListDecreasingOrder.addAll(this.getFinishedTasks());

		return sortTaskListDecreasingOrder(finishedTaskListDecreasingOrder);
	}

	/**
	 * this method create a list whit all unfinished tasks in project.
	 * 
	 * @return allUnFinishedTasks
	 */
	public List<Task> getUnFinishedTasks() {
		List<Task> allUnFinishedTasks = new ArrayList<>();

		for (Task other : this.getProjectTaskRepository()) {
			if (!other.isTaskFinished() && other.getStartDate() != null) {
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
		List<Task> allUnstartedTasks = new ArrayList<>();

		for (Task other : this.getProjectTaskRepository()) {
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
		List<Task> expiredTasks = new ArrayList<>();
		for (Task other : this.projectTasks) {
			if (!other.isTaskFinished()) {
				if (other.getTaskDeadline().before(today)) {
					expiredTasks.add(other);

				}
			}
		}
		return expiredTasks;
	}

	/**
	 * This method returns the a Task by taskID
	 * 
	 * @param taskID 
	 * 
	 * @return A task by a Task ID
	 */
	public Task getTaskByID(String taskID) {
		
		for (Task other : projectTasks) {
			if(other.getTaskID().equals(taskID)) {
				return other;
			}
			
		}
		return null;
		
	}

}
