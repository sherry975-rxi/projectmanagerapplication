package project.controller;

import java.util.List;

import project.model.Company;
import project.model.Task;
import project.model.User;

/**
 * US210 Controller that gets a list of all the finished tasks in a decreasing
 * order
 *
 */
public class GetAllFinishedUserTasksInDecreasingOrderController {

	Company myCompany;

	public List<Task> getAllFinishedUserTasksInDecreasingOrder(User myUser) {
		return this.myCompany.getProjectsRepository().getAllFinishedUserTasksInDecreasingOrder(myUser);
	}

	public void setMyCompany(Company companyToSet) {
		this.myCompany = companyToSet;
	}
}
