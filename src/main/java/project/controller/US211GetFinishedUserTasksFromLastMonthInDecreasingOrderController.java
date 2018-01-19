package project.controller;

import java.util.List;

import project.model.Company;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

public class US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController {
	ProjectRepository myProjRepo;

	public US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController() {
		this.myProjRepo = Company.getTheInstance().getProjectsRepository();
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
		return this.myProjRepo.getFinishedUserTasksFromLastMonthInDecreasingOrder(myUser);
	}

}
