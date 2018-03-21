package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Task;
import project.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class US210GetAllFinishedUserTasksInDecreasingOrderController {


	User user;

	@Autowired
	private ProjectService myProjRepo;

	@Autowired
	private TaskService taskService;


	/**
	 * Empty controller created for JPA integration tests
	 */
	public US210GetAllFinishedUserTasksInDecreasingOrderController() {

	}

	/**
	 * Creator of the controller, receives a user
	 * @param user
	 */
	public US210GetAllFinishedUserTasksInDecreasingOrderController(User user) {
		this.user = user;
	}


	/**
	 * Creator of the controller, receives a user
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}


	/**
	 * Method to return the user name in string
	 * @return string
	 */
	public String printUserNameInfo() {
		return this.user.getName();
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
		List<Task> taskList = taskService.getAllFinishedUserTasksInDecreasingOrder(this.user);
		List<String> finishedTaskListDecreasingOrder = new ArrayList<>();
		for (Task finishedTask : taskList) {
			String[] stringList = finishedTask.getTaskID().split("\\.");
			finishedTaskListDecreasingOrder.add("[" + stringList[0] + "." + stringList[1] + "]" + " " + convertCalendarToString(finishedTask.getFinishDate()) + " - " +  finishedTask.getDescription());
		}
	return finishedTaskListDecreasingOrder;
	}
	
	
}
