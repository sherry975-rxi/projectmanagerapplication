package main.java.project.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

	private List<Project> projectsRepository;
	private int projCounter;

	/**
	 * Constructor that allows one to create a new Project Repository. There are no
	 * mandatory fields.
	 */
	public ProjectRepository() {

		this.projectsRepository = new ArrayList<Project>();

	}

	/**
	 * Creates an instance of Project
	 * 
	 * @param name
	 * @param description
	 * @param projectManager
	 *            (User Object)
	 * 
	 * @return the project created
	 */
	public Project createProject(String name, String description, User projectManager) {

		Project newProject = new Project(projCounter, name, description, projectManager);
		projCounter++;
		return newProject;

	}

	/**
	 * This method returns the Project Counter
	 * 
	 * @return Project Counter
	 */
	public int getProjCounter() {
		return projCounter;
	}

	/**
	 * Takes an int to set the Project counter
	 * 
	 * @param projCounter
	 */
	public void setProjCounter(int projCounter) {
		this.projCounter = projCounter;
	}

	/**
	 * This method adds project to projectsList if project doesn't exit
	 * 
	 * @param toAddProject
	 *            Project added to the List of Projects
	 */
	public void addProjectToProjectRepository(Project toAddProject) {
		if (!this.projectsRepository.contains(toAddProject)) {
			this.projectsRepository.add(toAddProject);
		}
	}

	/**
	 * This method returns a copy of the list of projects (projectsList)
	 * 
	 * @return allProjects This is a copy of he list of all Projects created
	 */
	public List<Project> getAllProjects() {

		List<Project> allProjects = new ArrayList<Project>();
		allProjects.addAll(this.projectsRepository);

		return allProjects;
	}

	/**
	 * This method returns a list of active Projects. For each Project in the list
	 * of projects (projectList on the user task list), this method will check if
	 * the Project is active. If active equals true (the project is active) the
	 * projectsList list adds that Project.
	 * 
	 * @return activeProjectsList The List of active Projects
	 */
	public List<Project> getActiveProjects() {

		List<Project> activeProjectsList = new ArrayList<Project>();

		for (Project other : this.projectsRepository) {

			if (other.getProjectStatus() == 1 || other.getProjectStatus() == 2 || other.getProjectStatus() == 3
					|| other.getProjectStatus() == 4) {
				activeProjectsList.add(other);
			}

		}
		return activeProjectsList;
	}

	/**
	 * This method returns all the tasks from all the projects, that has a specific
	 * user associated to that task, no matter if the project is active or not, if
	 * the task is finished or not.
	 * 
	 * @param specificUser
	 *            user to search the tasks in which it is included
	 * 
	 * @return Returns the user task list.
	 */
	public List<Task> getUserTasks(User specificUser) {
		List<Task> tasksOfSpecificUser = new ArrayList<Task>();

		for (int indexProject = 0; indexProject < this.projectsRepository.size(); indexProject++) {
			if (this.projectsRepository.get(indexProject).containsUser(specificUser)) {
				tasksOfSpecificUser.addAll(
						this.projectsRepository.get(indexProject).getTaskRepository().getAllTasks(specificUser));
			}
		}

		return tasksOfSpecificUser;
	}

	/**
	 * This method returns all the tasks with state "finished" from all the
	 * projects, that has a specific user associated to that task, no matter if the
	 * project is active or not.
	 * 
	 * @param user
	 * 
	 * @return List of finished tasks of a specific user
	 */
	public List<Task> getFinishedUserTaskList(User user) {

		List<Task> finishedTasksOfSpecificUser = new ArrayList<Task>();
		for (Project test : this.projectsRepository) {
			if (test.containsUser(user)) {
				finishedTasksOfSpecificUser.addAll(test.getTaskRepository().getFinishedTasksGivenMonth(user, -1));
			}
		}
		return finishedTasksOfSpecificUser;
	}

	/**
	 * This method returns all the tasks with state "unfinished" from all the
	 * projects, that has a specific user associated to that task, no matter if the
	 * project is active or not.
	 * 
	 * @param user
	 * @return List of unfinished tasks of a specific user
	 */
	public List<Task> getUnfinishedUserTaskList(User user) {

		List<Task> unfinishedTasksOfSpecificUser = new ArrayList<Task>();
		for (Project test : this.projectsRepository) {
			if (test.containsUser(user)) {
				unfinishedTasksOfSpecificUser.addAll(test.getTaskRepository().getUnFinishedTasks(user));
			}
		}
		return unfinishedTasksOfSpecificUser;
	}

	/**
	 * This method returns a list with the finished tasks of a certain user by
	 * decreasing order of date. First, this method creates a list which is a copy
	 * of the finished task list of the user. This method just reverses the initial
	 * order of the finishedTaskList. It does not runs a cycle to compare the tasks
	 * finish dates, neither analysis the finishedTaskList in any way.
	 * 
	 * @param user
	 * 
	 * @return Returns a list with the all the user finished tasks sorted by
	 *         decreasing order.
	 */

	public List<Task> getLastMonthFinishedUserTaskList(User user) {

		List<Task> lastMonthFinishedTaskListByUser = new ArrayList<Task>();
		for (Project test : this.projectsRepository) {
			if (test.containsUser(user)) {
				lastMonthFinishedTaskListByUser.addAll(test.getTaskRepository().getFinishedTasksGivenMonth(user, 1));
			}
		}
		return lastMonthFinishedTaskListByUser;
	}

	/**
	 * This method returns the sum of the time spent in all the tasks that were
	 * marked as finished during the last month. So it runs a cycle to get the time
	 * spent on every task finished on last month, and sum one by one.
	 * 
	 * @param user
	 * 
	 * @return Returns total time spent doing tasks in the last month.
	 */
	public double getTotalTimeLastMonthFinishedTasksByUser(User user) {

		double totalTime = 0;
		for (Project test : this.projectsRepository) {
			if (test.containsUser(user)) {
				totalTime = totalTime + test.getTaskRepository().getTimeSpentOnLastMonthProjectUserTasks(user);
			}
		}

		return totalTime;

	}

	/**
	 * This method returns the average time spent by task during last month. This
	 * method gets the total time spent on every task finished on last month. Then
	 * it will divide that time by the number of tasks.
	 * 
	 * @param user
	 * 
	 * @return Returns the average time spent by finished task in the last month.
	 */
	public double getAverageTimeLastMonthFinishedTasksUser(User user) {

		double totalTime = this.getTotalTimeLastMonthFinishedTasksByUser(user);

		double average = totalTime / this.getLastMonthFinishedUserTaskList(user).size();

		return average;
	}

	/**
	 * This method returns a list with the tasks finished last month by decreasing
	 * order.
	 * 
	 * @param user
	 * 
	 * @return Returns a list with the tasks finished last month by decreasing
	 *         order.
	 */
	public List<Task> getLastMonthFinishedUserTaskListDecreasingOrder(User user) {

		List<Task> LastMonthFinishedUserTaskListDecreasingOrder = new ArrayList<Task>();
		LastMonthFinishedUserTaskListDecreasingOrder.addAll(this.getLastMonthFinishedUserTaskList(user));

		return this.sortTaskListDecreasingOrder(LastMonthFinishedUserTaskListDecreasingOrder);

	}

	/**
	 * This method returns a list with the finished tasks of a certain user by
	 * decreasing order of date. First, this method creates a list which is a copy
	 * of the finished task list of the user. This method just reverses the initial
	 * order of the finishedTaskList. It does not runs a cycle to compare the tasks
	 * finish dates, neither analysis the finishedTaskList in any way.
	 * 
	 * @param user
	 * 
	 * @return Returns a list with the all the user finished tasks sorted by
	 *         decreasing order.
	 */
	public List<Task> getFinishedTaskListByDecreasingOrder(User user) {

		List<Task> FinishedUserTaskListDecreasingOrder = new ArrayList<Task>();
		FinishedUserTaskListDecreasingOrder.addAll(this.getFinishedUserTaskList(user));

		return this.sortTaskListDecreasingOrder(FinishedUserTaskListDecreasingOrder);
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
		for (int i = 0; i < result.size() - 1; i++) {
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

}
