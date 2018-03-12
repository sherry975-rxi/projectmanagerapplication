package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Repository.ProjectsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectContainer {

	@Autowired
	private ProjectsRepository projectsRepository;

	private List<Project> projectsContainer;
	private int projCounter = 1;



	/**
	 * Constructor created for JPA purposes. It is not to be used in model context.
	 */
	//@Deprecated
	public ProjectContainer (ProjectsRepository projectsRepository){
		this.projectsRepository = projectsRepository;
		this.projectsContainer = new ArrayList<>();
	}

	/**
	 * Constructor that allows one to create a new Project Repository. There are no
	 * mandatory fields.
	 */
	public ProjectContainer() {

		this.projectsContainer = new ArrayList<>();
	}

	/**
	 * Creates an instance of Project
	 * 
	 * @param name name to be given to the Project
	 * @param description description to be given to the Project
	 * @param projectManager what user will be this Project's manager
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
		for (Project proj : projectsContainer) {
			if (proj.getIdCode() == projCounter) {
				return proj;
			}
		}
		return null;
	}

	/**
	 * Takes an int to set the Project counter
	 * 
	 * @param projCounter the number of projects alreayd created. Used to define the Project with an unique number
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
	public void addProjectToProjectContainer(Project toAddProject) {
		if (!this.projectsContainer.contains(toAddProject)) {
			this.projectsContainer.add(toAddProject);
		}
	}

	/**
	 * This method returns a copy of the list of projects (projectsList)
	 * 
	 * @return allProjects This is a copy of he list of all Projects created
	 */
	public List<Project> getAllProjectsfromProjectsContainer() {

		return new ArrayList<>(this.projectsContainer);
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

		for (Project other : this.projectsContainer) {

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
	 * @param user
	 *            user to search the tasks in which it is included
	 * 
	 * @return Returns the user task list.
	 */
	public List<Task> getUserTasks(User user) {
		List<Task> tasksOfSpecificUser = new ArrayList<>();

		for (Project other : this.projectsContainer) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);

			if (toCheck != null) {
				tasksOfSpecificUser.addAll(other.getTaskRepository().getAllTasksFromProjectCollaborator(toCheck));
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
		for (Project other : this.projectsContainer) {
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
		for (Project other : this.projectsContainer) {
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
		for (Project other : this.projectsContainer) {
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
		for (Project other : this.projectsContainer) {
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
		for (Project other : this.projectsContainer) {
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
	 * @param user user for which to get the average time spent on tasks that were finished last month
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
	 * @param user user for which to get the tasks that were finished last month in decreasing order of finish date
	 * 
	 * @return Returns a list with the tasks finished last month by decreasing
	 *         order.
	 */
	public List<Task> getFinishedUserTasksFromLastMonthInDecreasingOrder(User user) {

		List<Task> lastMonthFinishedUserTaskListDecreasingOrder = new ArrayList<>(this.getLastMonthFinishedUserTaskList(user));

		return this.sortTaskListDecreasingOrder(lastMonthFinishedUserTaskListDecreasingOrder);

	}

	/**
	 * This method returns a list with the finished tasks of a certain user by
	 * decreasing order of date. First, this method creates a list which is a copy
	 * of the finished task list of the user. This method just reverses the initial
	 * order of the finishedTaskList. It does not runs a cycle to compare the tasks
	 * finish dates, neither analysis the finishedTaskList in any way.
	 * 
	 * @param user user for which to get the finished tasks in decreasing order of finish date
	 * 
	 * @return Returns a list with the all the user finished tasks sorted by
	 *         decreasing order.
	 */
	public List<Task> getAllFinishedUserTasksInDecreasingOrder(User user) {

		List<Task> finishedUserTaskListDecreasingOrder = new ArrayList<>(this.getAllFinishedTasksFromUser(user));

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
	 * @param user user for which to get the started but not finished tasks in increasing order of deadline
	 * 
	 * @return Returns a list with the all the user started, unfinished tasks sorted
	 *         by increasing Deadline order.
	 */
	public List<Task> getStartedNotFinishedUserTasksInIncreasingDeadlineOrder(User user) {

		List<Task> incompleteUserTaskListIncreasingOrder = new ArrayList<>(this.getStartedNotFinishedUserTaskList(user));

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
		List<Task> result = new ArrayList<>(toSort);
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
		List<Task> result = new ArrayList<>(toSort);
		boolean cycle = true;
		int i = 0;
		while (cycle) {
			cycle = false;

			for (int j = i + 1; j < result.size(); j++) {
				cycle = true;
				if (result.get(i).getTaskDeadline()!= null) {
					if (result.get(i).getTaskDeadline().after(result.get(j).getTaskDeadline())) {
						Task h = new Task(result.get(i));
						result.set(i, result.get(j));
						result.set(j, h);
					}
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
	public boolean isProjectInProjectContainer(Project project) {

		return this.projectsContainer.contains(project);
	}

	/**
	 * This method returns a set of Projects where a certain user
	 * 
	 * @param user user whose projects are going to be found
	 * 
	 * @return List of Projects of a User
	 * 
	 */
	public List<Project> getProjectsFromUser(User user) {

		List<Project> listOfProjectsOfUser = new ArrayList<>();

		for (Project other : this.projectsContainer) {
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
	 * @param user user for which to find the projects where it is the project manager
	 * 
	 * @return List of Projects of a User
	 * 
	 */
	public List<Project> getProjectsFromProjectManager(User user) {

		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();

		for (Project other : this.projectsContainer) {
			if (other.isProjectManager(user)) {
				listOfProjectsOfProjectManager.add(other);
			}
		}
		return listOfProjectsOfProjectManager;
	}


	/**
	 *  This method updates the project repository(DB) from the project container.
	 */
	public void updateProjectContainer(){

		this.projectsContainer.clear();
		projectsContainer.addAll(this.projectsRepository.findAll());
		projCounter=projectsContainer.size()+1;

	}
}
