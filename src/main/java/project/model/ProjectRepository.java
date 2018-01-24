package project.model;

import java.util.ArrayList;
import java.util.List;

import project.model.taskStateInterface.OnGoing;

public class ProjectRepository {

	private List<Project> projectsRepository;
	private int projCounter = 1;

	/**
	 * Constructor that allows one to create a new Project Repository. There are no
	 * mandatory fields.
	 */
	public ProjectRepository() {

		this.projectsRepository = new ArrayList<>();
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

	public Project getProjById(int projCounter) {
		for (Project proj : projectsRepository) {
			if (proj.getIdCode() == projCounter) {
				return proj;
			}
		}
		return null;
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

		List<Project> allProjects = new ArrayList<>();
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

		List<Project> activeProjectsList = new ArrayList<>();

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
	 * @param User
	 *            user to search the tasks in which it is included
	 * 
	 * @return Returns the user task list.
	 */
	public List<Task> getUserTasks(User user) {
		List<Task> tasksOfSpecificUser = new ArrayList<>();

		for (Project other : this.projectsRepository) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);

			if (toCheck != null) {
				tasksOfSpecificUser.addAll(other.getTaskRepository().getAllTasksFromProjectCollaborator(toCheck));
			}
		}

		return tasksOfSpecificUser;
	}

	/**
	 * This method returns all the tasks from all the projects that are set to the
	 * state "OnGoing"
	 * 
	 * @param User
	 *            user to search the OnGoing tasks in which it is included
	 * 
	 * @return Returns the user OnGoing task list.
	 */
	public List<Task> getOnGoingUserTasks(User user) {
		List<Task> tasksOfSpecificUser = new ArrayList<>();

		for (Project other : this.projectsRepository) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);

			if (toCheck != null) {
				tasksOfSpecificUser.addAll(other.getTaskRepository().getAllTasksFromProjectCollaborator(toCheck));
			}
		}

		for (Task other : tasksOfSpecificUser) {
			if (!(other.getTaskState() instanceof OnGoing)) {
				tasksOfSpecificUser.remove(other);

			}
		}

		return tasksOfSpecificUser;
	}

	/**
	 * This method returns all the tasks from all the projects that are set to the
	 * state "OnGoing" and have a report by a given user
	 * 
	 * @param User
	 *            user to search the OnGoing tasks in which it is included
	 * 
	 * @return Returns the user OnGoing task list.
	 */
	public List<Task> getOnGoingUserReportedTasks(User user) {
		List<Task> tasksOfSpecificUser = new ArrayList<>();
		tasksOfSpecificUser = getOnGoingUserTasks(user);
		for (Task other : tasksOfSpecificUser) {
			if (!other.doesTaskHaveReportByGivenUser(user.getEmail())) {
				tasksOfSpecificUser.remove(other);
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
	 *            user to search the tasks in which it is included.
	 * 
	 * @return List of finished tasks of a specific user
	 */
	public List<Task> getAllFinishedTasksFromUser(User user) {

		List<Task> finishedTasksOfSpecificUser = new ArrayList<>();
		for (Project other : this.projectsRepository) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);

			if (toCheck != null) {
				finishedTasksOfSpecificUser.addAll(
						other.getTaskRepository().getFinishedTasksFromProjectCollaboratorInGivenMonth(toCheck, -1));
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
	 *            user to search the tasks in which it is included
	 * 
	 * @return List of unfinished tasks of a specific user
	 */
	public List<Task> getUnfinishedUserTaskList(User user) {

		List<Task> unfinishedTasksOfSpecificUser = new ArrayList<>();
		for (Project other : this.projectsRepository) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);

			if (toCheck != null) {
				unfinishedTasksOfSpecificUser
						.addAll(other.getTaskRepository().getUnfinishedTasksFromProjectCollaborator(toCheck));
			}
		}
		return unfinishedTasksOfSpecificUser;
	}

	/**
	 * This method returns all the Started tasks with state "unfinished" from all
	 * the projects, that has a specific user associated to that task.
	 * 
	 * @param user
	 *            user to search the tasks in which it is included
	 * 
	 * @return List of started but not finished tasks of a specific user
	 */
	public List<Task> getStartedNotFinishedUserTaskList(User user) {

		List<Task> unfinishedTasksOfSpecificUser = new ArrayList<>();
		for (Project other : this.projectsRepository) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);

			if (toCheck != null) {
				unfinishedTasksOfSpecificUser
						.addAll(other.getTaskRepository().getStartedNotFinishedTasksFromProjectCollaborator(toCheck));
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
	 *            user to search the tasks in which it is included
	 * 
	 * @return Returns a list with the all the user finished tasks sorted by
	 *         decreasing order.
	 */

	public List<Task> getLastMonthFinishedUserTaskList(User user) {

		List<Task> lastMonthFinishedTaskListByUser = new ArrayList<>();
		for (Project other : this.projectsRepository) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);

			if (toCheck != null) {
				lastMonthFinishedTaskListByUser.addAll(
						other.getTaskRepository().getFinishedTasksFromProjectCollaboratorInGivenMonth(toCheck, 1));
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
	 *            user to search the tasks in which it is included
	 * 
	 * @return Returns total time spent doing tasks in the last month.
	 */
	public double getTotalTimeOfFinishedTasksFromUserLastMonth(User user) {

		double totalTime = 0;
		for (Project other : this.projectsRepository) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);
			totalTime = totalTime
					+ other.getTaskRepository().getTimeSpentByProjectCollaboratorInAllTasksLastMonth(toCheck);

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
	public double getAverageTimeOfFinishedTasksFromUserLastMonth(User user) {

		double totalTime = this.getTotalTimeOfFinishedTasksFromUserLastMonth(user);

		return totalTime / this.getLastMonthFinishedUserTaskList(user).size();

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
	public List<Task> getFinishedUserTasksFromLastMonthInDecreasingOrder(User user) {

		List<Task> lastMonthFinishedUserTaskListDecreasingOrder = new ArrayList<>();
		lastMonthFinishedUserTaskListDecreasingOrder.addAll(this.getLastMonthFinishedUserTaskList(user));

		return this.sortTaskListDecreasingOrder(lastMonthFinishedUserTaskListDecreasingOrder);

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
	public List<Task> getAllFinishedUserTasksInDecreasingOrder(User user) {

		List<Task> finishedUserTaskListDecreasingOrder = new ArrayList<>();
		finishedUserTaskListDecreasingOrder.addAll(this.getAllFinishedTasksFromUser(user));

		return this.sortTaskListDecreasingOrder(finishedUserTaskListDecreasingOrder);
	}

	/**
	 * This method returns a list with the started but not finished tasks of a
	 * certain user by increasing order of deadline. First, this method creates a
	 * list which is a copy of the started, unfinished task list of the user. This
	 * method just sorts the list by increasing order of deadline. It does not runs
	 * a cycle to compare the tasks, neither analysis the finishedTaskList in any
	 * way.
	 * 
	 * @param user
	 * 
	 * @return Returns a list with the all the user started, unfinished tasks sorted
	 *         by increasing Deadline order.
	 */
	public List<Task> getStartedNotFinishedUserTasksInIncreasingDeadlineOrder(User user) {

		List<Task> incompleteUserTaskListIncreasingOrder = new ArrayList<>();
		incompleteUserTaskListIncreasingOrder.addAll(this.getStartedNotFinishedUserTaskList(user));

		return this.sortTaskListByDeadline(incompleteUserTaskListIncreasingOrder);
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
		boolean cycle = true;
		int i = 0;
		while (cycle) {
			cycle = false;

			for (int j = i + 1; j < result.size(); j++) {
				cycle = true;
				if (result.get(i).getFinishDate().before(result.get(j).getFinishDate())) {
					Task h = new Task(result.get(i));
					result.set(i, result.get(j));
					result.set(j, h);
				}
			}
			i++;

		}
		return result;
	}

	// TODO implement verification of Null Deadlines
	/**
	 * This method returns a list with the tasks of a certain user by decreasing
	 * order of date. First, this method creates a list which is a copy of the task
	 * list of the user. This method just sorts the Task List by Deadline,
	 * increasing order.
	 * 
	 * @param toSort
	 *            List of tasks to sort
	 * 
	 * @return sorted list
	 * 
	 */
	public List<Task> sortTaskListByDeadline(List<Task> toSort) {
		List<Task> result = new ArrayList<>();
		result.addAll(toSort);
		boolean cycle = true;
		int i = 0;
		while (cycle) {
			cycle = false;

			for (int j = i + 1; j < result.size(); j++) {
				cycle = true;
				if (result.get(i).getTaskDeadline().after(result.get(j).getTaskDeadline())) {
					Task h = new Task(result.get(i));
					result.set(i, result.get(j));
					result.set(j, h);
				}
			}
			i++;

		}
		return result;
	}

	/**
	 * Checks if the a project is in a project repository.
	 * 
	 * @return TRUE if the Project is in this project repository FALSE if not
	 */
	public boolean isProjectInProjectRepository(Project project) {

		return this.projectsRepository.contains(project);
	}

	/**
	 * This method returns a set of Projects where a certain user
	 * 
	 * @param User
	 * 
	 * @return List of Projects of a User
	 * 
	 */
	public List<Project> getProjectsFromUser(User user) {

		List<Project> listOfProjectsOfUser = new ArrayList<>();

		for (Project other : this.projectsRepository) {
			if (other.isUserInProjectTeam(user) || other.isProjectManager(user)) {
				listOfProjectsOfUser.add(other);
			}
		}
		return listOfProjectsOfUser;
	}

	/**
	 * This method returns a set of Projects where a certain user is the project
	 * manager
	 * 
	 * @param User
	 * 
	 * @return List of Projects of a User
	 * 
	 */
	public List<Project> getProjectsFromProjectManager(User user) {

		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();

		for (Project other : this.projectsRepository) {
			if (other.isProjectManager(user)) {
				listOfProjectsOfProjectManager.add(other);
			}
		}
		return listOfProjectsOfProjectManager;
	}

	/**
	 * This method checks if a task exist in any project by checking it's ID in
	 * every project
	 * 
	 * @param taskID
	 *            The id of the project to search for
	 * 
	 * @return TRUE if if finds a match, FALSE if not
	 */
	public boolean doesTaskIdExists(String taskID) {
		boolean doesTaskIdExist = false;
		for (Project p : this.projectsRepository) {
			if (p.getTaskRepository().getTaskByID(taskID) != null)
				doesTaskIdExist = true;
		}
		return doesTaskIdExist;
	}

}
