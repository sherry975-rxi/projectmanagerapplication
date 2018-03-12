package project.model;

import project.model.taskstateinterface.Cancelled;
import project.model.taskstateinterface.Finished;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Embeddable
public class TaskContainer implements Serializable{


	@javax.persistence.Transient
	private int taskCounter;
	@javax.persistence.Transient
	private int projId;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
	private List<Task> projectTasks;
	@javax.persistence.Transient
	private Project project;
	static final long serialVersionUID = 46L;


	public TaskContainer() {}

	public TaskContainer(int projId) {

		this.projectTasks = new ArrayList<>();
		this.taskCounter = 1;
		this.projId = projId;

	}


	public Task createTask(String description, int estimatedTaskEffort, Calendar estimatedTaskStartDate,
			Calendar taskDeadline, int estimatedBudgetCostTask) {

		Task newTask = new Task(this.taskCounter, this.projId, description, estimatedTaskEffort, estimatedTaskStartDate,
				taskDeadline, estimatedBudgetCostTask);
		taskCounter++;
		return newTask;
	}


	public Task createTask(String description) {

		Task newTask = new Task(this.taskCounter, this.projId, description);

		taskCounter++;
		return newTask;
	}


	public List<Task> getAllTasksfromProject() {
		return this.projectTasks;
	}


	public void addTaskToProject(Task toAdd) {
	    toAdd.setProject(this.project);
		this.projectTasks.add(toAdd);
	}


	public void setProject(Project project) {
		this.project=project;
	}


	public Project getProject() {
		return this.project;
	}

	/**
	 * This method returns only the unfinished tasks in a project.
	 *
	 * @param collaborator is a Project Collaborator
	 *
	 * @return UnfinishedTaskList The list if tasks that are not finished
	 */
	public List<Task> getUnfinishedTasksFromProjectCollaborator(ProjectCollaborator collaborator) {

		List<Task> unfinishedTaskList = new ArrayList<>(this.getAllTasksFromProjectCollaborator(collaborator));

		for (Task other : this.getAllTasksFromProjectCollaborator(collaborator)) {
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
	 * @param collaborator is a ProjectCollaborator
	 *
	 * @return incompleteTaskList The list if tasks that are started but not
	 *         finished
	 */
	public List<Task> getStartedNotFinishedTasksFromProjectCollaborator(ProjectCollaborator collaborator) {

		List<Task> incompleteTaskList = new ArrayList<>(this.getAllTasksFromProjectCollaborator(collaborator));

		for (Task other : this.getAllTasksFromProjectCollaborator(collaborator)) {
			if (other.isTaskFinished() || "Cancelled".equals(other.viewTaskStateName())
					|| other.getStartDate() == null) {
				incompleteTaskList.remove(other);
			}
		}
		return incompleteTaskList;

	}

	/**
	 * This method returns only the finished tasks of a user in a project.
	 *
	 * @param collaborator is the ProjectCollaborator
	 *
	 * @return FinishedTaskList The list if tasks that are finished
	 */
	public List<Task> getFinishedTaskListOfUserInProject(ProjectCollaborator collaborator) {

		List<Task> finishedTaskList = new ArrayList<>();

		for (Task other : this.projectTasks) {
			if (other.isTaskFinished() && other.isProjectCollaboratorInTaskTeam(collaborator)) {
				finishedTaskList.add(other);
			}
		}
		return finishedTaskList;
	}


	/**
	 * This method returns a list of all tasks finished a number of months ago by
	 * given user. Given a negative "monthsAgo" input, Returns ALL finished tasks of
	 * said user
	 * 
	 * @param collaborator
	 *            user who is in the tasks
	 * @param monthsAgo
	 *            how many months to subtract
	 * @return lastMonthFinishedTaskList List of all tasks finished the previous
	 *         month, by the user
	 */
	public List<Task> getFinishedTasksFromProjectCollaboratorInGivenMonth(ProjectCollaborator collaborator, int monthsAgo) {
		Calendar givenMonth = Calendar.getInstance();
		givenMonth.add(Calendar.MONTH, -monthsAgo);
		List<Task> lastMonthFinishedTaskList = new ArrayList<>();

		for (Task other : this.getAllTasksFromProjectCollaborator(collaborator)) {
			if (other.isTaskFinished()) {
				if (monthsAgo < 0) {
					lastMonthFinishedTaskList.add(other);
				} else if (other.getFinishDate().get(Calendar.MONTH) == givenMonth.get(Calendar.MONTH)) {
					lastMonthFinishedTaskList.add(other);
				}
			}
		}
		return lastMonthFinishedTaskList;
	}


	public boolean isTaskInTaskContainer(Task task) {
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
	 * @param collaborator is the ProjectCollaborator
	 * @return Time spent on last month project user tasks
	 */
	public double getTimeSpentByProjectCollaboratorInAllTasksLastMonth(ProjectCollaborator collaborator) {
		List<Task> lastMonth = new ArrayList<>(this.getFinishedTasksFromProjectCollaboratorInGivenMonth(collaborator, 1));
		double totalTime = 0;
		for (Task test : lastMonth) {
			totalTime = totalTime + test.getTimeSpentByProjectCollaboratorOntask(collaborator);
		}
		return totalTime;
	}


	public void setTaskCounter(int count) {
		this.taskCounter = count;
	}


	public int getTaskCounter() {
		return this.taskCounter;
	}


	public int getProjectId() {
		return projId;
	}


	public List<Task> getAllTasksFromProjectCollaborator(ProjectCollaborator collaborator) {
		List<Task> allTasks = new ArrayList<>();
		for (Task other : this.getAllTasksfromProject()) {
			if (other.isProjectCollaboratorInTaskTeam(collaborator)) {
				allTasks.add(other);
			}
		}
		return allTasks;
	}


	public boolean isCollaboratorActiveOnAnyTask(ProjectCollaborator collaborator) {
		for (Task otherTask : this.getAllTasksfromProject()) {
			if (otherTask.isProjectCollaboratorActiveInTaskTeam(collaborator))
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

		for (Task other : this.getAllTasksfromProject()) {

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

		for (Task other : this.getAllTasksfromProject()) {
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
		List<Task> result = new ArrayList<>(toSort);
		for (int i = 0; i < result.size(); i++) {
			for (int j = i + 1; j < result.size(); j++) {
				if (result.get(i).getFinishDate().before(result.get(j).getFinishDate())) {
					Task task = new Task(result.get(i));
					result.set(i, result.get(j));
					result.set(j, task);
				}
			}
		}
		return result;
	}


	public List<Task> getFinishedTasksInDecreasingOrder() {

		List<Task> finishedTaskListDecreasingOrder = new ArrayList<>(this.getFinishedTasks());

		return sortTaskListDecreasingOrder(finishedTaskListDecreasingOrder);
	}

	/**
	 * this method create a list whit all unfinished tasks in project.
	 * 
	 * @return allUnFinishedTasks
	 */
	public List<Task> getUnFinishedTasks() {
		List<Task> allUnFinishedTasks = new ArrayList<>();

		for (Task other : this.getAllTasksfromProject()) {
			if (!other.isTaskFinished() && !"Cancelled".equals(other.viewTaskStateName())
					&& other.getStartDate() != null) {
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

		for (Task other : this.getAllTasksfromProject()) {
			if (other.getStartDate() == null) {
				allUnstartedTasks.add(other);
			}
		}
		return allUnstartedTasks;
	}


	public List<Task> getExpiredTasks() {
		Calendar today = Calendar.getInstance();
		List<Task> expiredTasks = new ArrayList<>();
		for (Task other : this.projectTasks) {
			if (!other.isTaskFinished() && other.getTaskDeadline() != null && other.getTaskDeadline().before(today)) {
					expiredTasks.add(other);
			}
		}
		return expiredTasks;
	}


	public Task getTaskByID(String taskID) {

		for (Task other : projectTasks) {
			if (other.getTaskID().equals(taskID)) {
				return other;
			}
		}
		return null;
	}


	public boolean deleteTask(Task taskToDelete) {

		boolean wasTaskDeleted = false;

		switch (taskToDelete.viewTaskStateName()) {
		case "Assigned": case "Planned" : case "Created" : case "Ready":
 			this.projectTasks.remove(taskToDelete);
			wasTaskDeleted = true;
			break;
		default:
			break;
		}
		return wasTaskDeleted;

	}


	public List<Task> getCancelledTasksFromProject() {
		List<Task> cancelledTasksFromProject = new ArrayList<>();

		for (Task other : this.projectTasks) {
			if (other.getTaskState() instanceof Cancelled) {
				cancelledTasksFromProject.add(other);
			}
		}
		return cancelledTasksFromProject;
	}


	public List<String> getReportedCostOfEachTask() {
		List<String> reportTaskCost = new ArrayList<>();

		for (Task other : this.getAllTasksfromProject()) {
			reportTaskCost.add(String.valueOf(other.getTaskCost()));

		}

		return reportTaskCost;
	}

	/**
	 * This method returns a list of tasks that can be associated to
	 * TaskDependencies
	 * 
	 * @return A list of tasks that can be associated to a TaskDependency
	 */
	public List<Task> getTaskListOfWhichDependenciesCanBeCreated() {
		List<Task> validTasks = new ArrayList<>(projectTasks);
		for (Task other : this.projectTasks) {
			if (other.getTaskState() instanceof Finished) {
				validTasks.remove(other);
			}
			if (other.getTaskState() instanceof Cancelled) {
				validTasks.remove(other);
			}
		}
		return validTasks;
	}
}
