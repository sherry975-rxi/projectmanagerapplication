package project.controller;

import java.util.List;

import project.model.Company;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

/**
 * US210 Controller that gets a list of all the finished tasks in a decreasing
 * order
 *
 */
public class GetAllFinishedUserTasksInDecreasingOrderController {

	Company myCompany;

	/**
	 * Returns the list of finished user tasks in decreasing order for a specific
	 * user
	 * 
	 * @param myUser
	 * @return Task List
	 * 
	 */
	public List<Task> getAllFinishedUserTasksInDecreasingOrder(User myUser) {
		ProjectRepository myProjRep = this.myCompany.getProjectsRepository();
		return myProjRep.getAllFinishedUserTasksInDecreasingOrder(myUser);
	}

	/**
	 * Sets the company used in this method
	 * 
	 * @param companyToSet
	 */
	public void setMyCompany(Company companyToSet) {
		this.myCompany = companyToSet;
	}
}
