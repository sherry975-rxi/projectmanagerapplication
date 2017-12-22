package project.controller;

import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

/**
 * Controller for every US that asks for a specific filter of Task list
 *
 */
public class TasksFiltersController {

	Company myCompany;

	/**
	 * This method returns all the unfinished Tasks the User has. - US203
	 * 
	 * @param myUser
	 *            The User to search for it's unfinished tasks
	 * @return Task List
	 */
	public List<Task> getUserUnfinishedTaskList(User myUser) {
		ProjectRepository myProjRep = this.myCompany.getProjectsRepository();
		return myProjRep.getUnfinishedUserTaskList(myUser);
	}

	/**
	 * This method returns all the started not finished Tasks the User has. -
	 * US203v2
	 * 
	 * @param myUser
	 *            The User to search for it's unfinished tasks
	 * @return Task List
	 */
	public List<Task> getUserStartedNotFinishedTaskListInIncreasingOrder(User myUser) {
		ProjectRepository myProjRep = this.myCompany.getProjectsRepository();
		return myProjRep.getStartedNotFinishedUserTasksInIncreasingDeadlineOrder(myUser);
	}

	/**
	 * Returns the list of user finished tasks in decreasing order. - US210
	 * 
	 * @param myUser
	 *            The User to search for it's finished tasks in decreasing order
	 * @return Task List
	 * 
	 */
	public List<Task> getAllFinishedUserTasksInDecreasingOrder(User myUser) {
		ProjectRepository myProjRep = this.myCompany.getProjectsRepository();
		return myProjRep.getAllFinishedUserTasksInDecreasingOrder(myUser);
	}

	/**
	 * Returns the list of user finished tasks in decreasing order from the last
	 * month. - US211
	 * 
	 * @param myUser
	 *            The User to search for it's finished tasks in decreasing order in
	 *            the last month
	 * @return Task List
	 * 
	 */
	public List<Task> getFinishedUserTasksFromLastMonthInDecreasingOrder(User myUser) {
		ProjectRepository myProjRep = this.myCompany.getProjectsRepository();
		return myProjRep.getFinishedUserTasksFromLastMonthInDecreasingOrder(myUser);
	}

	/**
	 * Returns a list of tasks that have no collaborator assigned to them. - US360
	 * 
	 * @param proj
	 *            The project to search for its tasks without collaborators assigned
	 * @return Task List
	 */

	public List<Task> getProjectTasksWithoutCollaboratorsAssigned(Project proj) {

		return proj.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned();

	}

	/**
	 * Returns a list of tasks that belong to a Project and were marked as finished.
	 * - US370
	 * 
	 * @param proj
	 *            The project to search for its finished tasks
	 * @return Task List
	 */

	public List<Task> getProjectFinishedTaskList(Project proj) {

		return proj.getTaskRepository().getFinishedTasks();

	}

	/**
	 * Returns a list of tasks that belong to a Project and are not marked as
	 * finished. - US372
	 * 
	 * @param proj
	 *            The project to search for its unfinished tasks
	 * @return Task List
	 */

	public List<Task> getProjectUnfinishedTaskList(Project proj) {

		return proj.getTaskRepository().getUnFinishedTasks();

	}

	/**
	 * Returns a list of tasks that belong to a Project and have not been started. -
	 * US375
	 * 
	 * @param proj
	 *            The project to search for its unstarted Tasks
	 * @return Task List
	 */

	public List<Task> getProjectNotStartedTaskList(Project proj) {

		return proj.getTaskRepository().getUnstartedTasks();

	}

	/**
	 * Returns a list of tasks that belong to a Project and have expired. - US380
	 * 
	 * @param proj
	 *            The project to search for its expired Tasks
	 * @return Task List
	 */

	public List<Task> getProjectExpiredTaskList(Project proj) {

		return proj.getTaskRepository().getExpiredTasks();

	}

	/**
	 * Sets the company used in this method
	 * 
	 * @param companyToSet
	 */
	public void setMyCompany(Company companyToSet) {
		this.myCompany = companyToSet;
	}

	/**
	 * This method create a list of all tasks finished from project in decreasing
	 * order. First creates a empty list, then add all finished tasks from the
	 * project using method getFinishedTasks. At last, apply the sort by decreasing
	 * order to that list and return it.
	 * 
	 * @param project
	 * @return a list of tasks finished by decreasing order
	 */
	public List<Task> getFinishedTasksInDescreasingOrder(Project project) {

		return project.getTaskRepository().getFinishedTasksInDecreasingOrder();
	}
}
