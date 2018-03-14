package project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.ProjCollabRepository;
import project.Repository.ProjectsRepository;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

	@Autowired
	private ProjectsRepository projectsRepository;
	
	@Autowired
	ProjCollabRepository projectCollaboratorRepository; 
		
	/**
	 * Constructor that allows one to create a new Project Repository. There are no
	 * mandatory fields.
	 */
	public ProjectService() {}

	/**
	 * Constructor created for JPA purposes. It is not to be used in model context.
	 */
	public ProjectService (ProjectsRepository projectsRepository){
		this.projectsRepository = projectsRepository;
	}



	/**
	 * Creates an instance of Project and saves it in the database
	 * 
	 * @param name name to be given to the Project
	 * @param description description to be given to the Project
	 * @param projectManager what user will be this Project's manager
	 * 
	 * @return the project created
	 */
	public Project createProject(String name, String description, User projectManager) {

		Project newProject = new Project(name, description, projectManager);
		this.projectsRepository.save(newProject);
		return newProject;

	}

	/**
	 * Gets a project from the database by searching with the id
	 *
	 * @param id id to search for a specific project
	 *
	 * @return project found
	 */
	public Project getProjectById(long id) {
		return this.projectsRepository.findById(id);
	}

	/**
	 * This method tells the ProjectRepository to save a certain project in the database
	 * 
	 * @param project
	 *            Project added to the List of Projects
	 */
	public void addProjectToProjectContainer(Project project) {
			this.projectsRepository.save(project);
	}

	/**
	 * This method tells the projectRepository to call the method findAll in order to retrieve all projects from the database
	 * 
	 * @return allProjects This is a copy of he list of all Projects created
	 */
	public List<Project> getAllProjectsfromProjectsContainer() {

		List<Project> allProjects = new ArrayList<>();
		allProjects.addAll(this.projectsRepository.findAll());
		return allProjects;
	}

	/**
	 * Tells the projectRepository to call the method findByProjectStatus for each
	 * of the status that is considered as "Active" .
	 * ActiveStates 1 to 4
	 * 
	 * @return activeProjectsList The List of active Projects
	 */
	public List<Project> getActiveProjects() {

		List<Project> activeProjects = new ArrayList<>();
		
		for(Project project : getAllProjectsfromProjectsContainer()) {
			if(project.isProjectActive()) {
				activeProjects.add(project);
			}
		}
		return activeProjects;
	}

	/**
	 * Checks if the a project already exists
	 * 
	 * @return TRUE if the Project is in this project repository FALSE if not
	 */
	public boolean isProjectInProjectContainer(int projectId){

		return this.projectsRepository.exists(projectId);
	}

	/**
	 * This method returns a set of Projects where a certain user
	 *
	 * *
	 * @param user user whose projects are going to be found
	 * 
	 * @return List of Projects of a User
	 * 
	 */
	public List<Project> getProjectsFromUser(User user) {
		
		List<Project> projects = new ArrayList<>();
		List<ProjectCollaborator> userProjCollabs = new ArrayList<>();

		//Finds all projectCollaborators from a given user
		userProjCollabs.addAll(this.projectCollaboratorRepository.findAllByCollaborator(user));
		
		long projectId;

		//Compares de projectId of the projectCollaborator to the project id of the projects in the database
		for(ProjectCollaborator collaborator : userProjCollabs) {
			projectId = collaborator.getProject().getId(); 
			for(Project project : this.getAllProjectsfromProjectsContainer()) { 
				if(project.getId() == projectId) {
					projects.add(project); 
				}			
			}			
		}

		return projects;		
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

		List<Project> projectsOfPM = new ArrayList<>();
		projectsOfPM.addAll(this.projectsRepository.findAllByProjectManager(user));

		return projectsOfPM;
	}

	/**
	 * Thid method tells the projectRepository to call the method save, in order to update a certain project
	 *
	 * @param project Project to update
	 */
	public void updateProject(Project project) {
		this.projectsRepository.save(project);
	}


	/**
	 * Calls the projectCollaboratorRepository to save the projectCollaborator to the database
	 * The projectCollaborator is created by the Project
	 *
	 * @param projectCollaborator ProjectCollaborator to save
	 */
	public void addProjectCollaborator(ProjectCollaborator projectCollaborator) {

		this.projectCollaboratorRepository.save(projectCollaborator);
	}

	

	/**
	 * TODO This method is going to transinct to the TaskContainerService Class.
	 *
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
/*
	public List<Task> getLastMonthFinishedUserTaskList(User user) {

		List<Task> lastMonthFinishedTaskListByUser = new ArrayList<>();
		for (Project other : this.projectsContainer) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);

			//if (toCheck != null) {
			//	lastMonthFinishedTaskListByUser.addAll(
			//			other.getTaskRepository().getFinishedTasksFromProjectCollaboratorInGivenMonth(toCheck, 1));
			//}
		}
		return lastMonthFinishedTaskListByUser;
	} */

	/**
	 * TODO This method is going to transinct to the TaskContainerService Class.
	 *
	 * This method returns the sum of the time spent in all the tasks that were
	 * marked as finished during the last month. So it runs a cycle to get the time
	 * spent on every task finished on last month, and sum one by one.
	 *
	 * @param user
	 *            user to search the tasks in which it is included
	 *
	 * @return Returns total time spent doing tasks in the last month.
	 */
	/*public double getTotalTimeOfFinishedTasksFromUserLastMonth(User user) {

		double totalTime = 0;
		for (Project other : this.projectsContainer) {
			ProjectCollaborator toCheck = other.findProjectCollaborator(user);
			//totalTime = totalTime
					//+ other.getTaskRepository().getTimeSpentByProjectCollaboratorInAllTasksLastMonth(toCheck);

		}

		return totalTime;

	} */

	/**
	 * TODO This method is going to transinct to the TaskContainerService Class.
	 *
	 * This method returns the average time spent by task during last month. This
	 * method gets the total time spent on every task finished on last month. Then
	 * it will divide that time by the number of tasks.
	 *
	 * @param user user for which to get the average time spent on tasks that were finished last month
	 *
	 * @return Returns the average time spent by finished task in the last month.
	 */
	/*public double getAverageTimeOfFinishedTasksFromUserLastMonth(User user) {

		double totalTime = this.getTotalTimeOfFinishedTasksFromUserLastMonth(user);

		return totalTime / this.getLastMonthFinishedUserTaskList(user).size();

	}*/

	/**
	 * This method returns a list with the tasks finished last month by decreasing
	 * order.
	 *
	 * @param user user for which to get the tasks that were finished last month in decreasing order of finish date
	 *
	 * @return Returns a list with the tasks finished last month by decreasing
	 *         order.
	 */
	/*public List<Task> getFinishedUserTasksFromLastMonthInDecreasingOrder(User user) {

		List<Task> lastMonthFinishedUserTaskListDecreasingOrder = new ArrayList<>(this.getLastMonthFinishedUserTaskList(user));

		return this.sortTaskListDecreasingOrder(lastMonthFinishedUserTaskListDecreasingOrder);

	}*/

	/**
	 * TODO This method is going to transinct to the TaskContainerService Class.
	 *
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
	/*public List<Task> getAllFinishedUserTasksInDecreasingOrder(User user) {

		List<Task> finishedUserTaskListDecreasingOrder = new ArrayList<>(this.getAllFinishedTasksFromUser(user));

		return this.sortTaskListDecreasingOrder(finishedUserTaskListDecreasingOrder);
	}*/

	/**
	 * TODO This method is going to transinct to the TaskContainerService Class.
	 *
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
	/*public List<Task> getStartedNotFinishedUserTasksInIncreasingDeadlineOrder(User user) {

		List<Task> incompleteUserTaskListIncreasingOrder = new ArrayList<>(this.getStartedNotFinishedUserTaskList(user));

		return this.sortTaskListByDeadline(incompleteUserTaskListIncreasingOrder);
	}*/


}
