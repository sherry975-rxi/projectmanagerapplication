package project.controller;

import java.util.List;

import project.model.Company;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

public class US210GetAllFinishedUserTasksInDecreasingOrderController {

	User myUser;
	ProjectRepository myProjRepo;

	public US210GetAllFinishedUserTasksInDecreasingOrderController(User user) {
		this.myProjRepo = Company.getTheInstance().getProjectsRepository();
		this.myUser = user;
	}

	/**
	 * Returns the list of user finished tasks in decreasing order. - US210
	 * 
	 * @param myUser
	 *            The User to search for it's finished tasks in decreasing order
	 * @return Task List
	 * 
	 */
	public List<Task> getAllFinishedUserTasksInDecreasingOrder() {
		return this.myProjRepo.getAllFinishedUserTasksInDecreasingOrder(this.myUser);
	}
}
