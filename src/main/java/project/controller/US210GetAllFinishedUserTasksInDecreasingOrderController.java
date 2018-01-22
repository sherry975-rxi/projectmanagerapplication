package project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import project.model.Company;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

public class US210GetAllFinishedUserTasksInDecreasingOrderController {

	User myUser;
	ProjectRepository myProjRepo;

	/**
	 * Creator of the controller, receives a user
	 * @param user
	 */
	public US210GetAllFinishedUserTasksInDecreasingOrderController(User user) {
		this.myProjRepo = Company.getTheInstance().getProjectsRepository();
		this.myUser = user;
	}

	
	
	
	/**
	 * Method to return the user name in string
	 * @return string
	 */
	public String printUserNameInfo() {
		return this.myUser.getName();
	}
	
	public String convertCalendarToString(Calendar dateToConvert) {
	
			
			Date conversion = dateToConvert.getTime();
			SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dateConverted = newDateFormat.format(conversion).toString();
	
			return dateConverted;
	}
	
	/**
	 * Returns the list of user finished tasks in decreasing order. - US210
	 * 
	 * @param myUser
	 *            The User to search for it's finished tasks in decreasing order
	 * @return Task List
	 * 
	 */
	public List<String> getAllFinishedUserTasksInDecreasingOrder(){
		List<Task> taskList = this.myProjRepo.getAllFinishedUserTasksInDecreasingOrder(this.myUser);
		List<String> finishedTaskListDecreasingOrder = new ArrayList<>();
		for (Task finishedTask : taskList) {
			String[] stringList = finishedTask.getTaskID().split("\\.");
			finishedTaskListDecreasingOrder.add("[" + stringList[0] + "." + stringList[1] + "]" + " " + convertCalendarToString(finishedTask.getFinishDate()) + " - " +  finishedTask.getDescription());
		}
	return finishedTaskListDecreasingOrder;
	}
	
	
}
