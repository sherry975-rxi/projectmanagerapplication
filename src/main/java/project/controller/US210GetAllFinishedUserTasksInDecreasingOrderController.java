package project.controller;

import project.model.ProjectContainer;
import project.model.Task;
import project.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class US210GetAllFinishedUserTasksInDecreasingOrderController {

	User myUser;
	ProjectContainer myProjRepo;

	/**
	 * Creator of the controller, receives a user
	 * @param user
	 */
	public US210GetAllFinishedUserTasksInDecreasingOrderController(User user) {
		this.myProjRepo = new ProjectContainer();
		this.myUser = user;
	}

	
	
	
	/**
	 * Method to return the user name in string
	 * @return string
	 */
	public String printUserNameInfo() {
		return this.myUser.getName();
	}
	
	/** Receives a gregorian calendar date
	 * @param dateToConvert
	 * @return date in string format
	 */
	public String convertCalendarToString(Calendar dateToConvert) {
		String dateConverted = "No date";
		if(dateToConvert != null) {
			Date conversion = dateToConvert.getTime();
			SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateConverted = newDateFormat.format(conversion);
		}
			return dateConverted;
	}
	
	/**
	 * Returns the list of user finished tasks in decreasing order. - US210
	 *
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
