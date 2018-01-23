package project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.model.Company;
import project.model.Project;
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
	public List<String> getFinishedUserTasksFromLastMonthInDecreasingOrder(User myUser) {
		List<Task> lastMonthFinishedTasks = this.myProjRepo.getFinishedUserTasksFromLastMonthInDecreasingOrder(myUser);
		List<String> lastMonthFinishedTasksString = new ArrayList<>();

		for (Task other : lastMonthFinishedTasks) {
			String finishDate = convertCalendarToString(other.getFinishDate());
			String projectName = viewProjectNameFromTaskID(other);

			String taskToString = finishDate + ": " + projectName + " - " + other.getDescription();
			lastMonthFinishedTasksString.add(taskToString);
		}

		return lastMonthFinishedTasksString;
	}

	/**
	 * This is a utility method to convert the necessary task Date into Strings as
	 * needed, to be returned to the User Interface
	 * 
	 * 
	 * @param toConvert
	 *            - the selected Calendar date
	 * @return - The selected Calendar date in "dd/MM/yyyy"
	 */
	public String convertCalendarToString(Calendar toConvert) {
		String dateString;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dateString = sdf.format(toConvert.getTime());
		return dateString;
	}

	/**
	 * This method receives a Task and uses its ID to discover which project it
	 * belongs to. If it finds no project, it instead returns a not found message
	 * 
	 * @param toSeeProject
	 *            - The task whose Project name should be viewed
	 * @return - the project's name as a string, or a "Not Found" message
	 */
	public String viewProjectNameFromTaskID(Task toSeeProject) {
		String projectName = "404 Project Not found!";
		String[] splitTaskID = toSeeProject.getTaskID().split("\\.");
		int projectID = Integer.parseInt(splitTaskID[0]);
		Project projectContainingTask = myProjRepo.getProjById(projectID);
		if (projectContainingTask != null) {
			projectName = projectContainingTask.getName();
		}
		return projectName;
	}

}