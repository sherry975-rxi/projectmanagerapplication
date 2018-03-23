package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Report;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class US207CreateTaskReportController {

	private User username;
	private String email;
	private Task task;

	@Autowired
	private UserService userContainer;

	@Autowired
	private TaskService taskService;


	public US207CreateTaskReportController() {
        //  Empty controller created for JPA integration tests

    }

	/**
	 * This method returns a TaskCollaborator by its email
	 *
	 * @return
	 */
	public TaskCollaborator getTaskCollaboratorByEmail(String userEmail) {

		TaskCollaborator taskCollaboratorByEmail = null;

		for (TaskCollaborator other : this.task.getTaskTeam()) {
			if (other.getProjCollaborator().getUserFromProjectCollaborator().getEmail().equals(userEmail)
					&& other.getFinishDate() == null) {
				taskCollaboratorByEmail = other;
			}
		}

		return taskCollaboratorByEmail;
	}

	/**
	 * This method creates or updates a Report with a given time
	 *
	 * @param timeToReport
	 *            The time associated to the report
	 * @return TRUE if the report is created, FALSE if not
	 */
	public boolean createReportController(double timeToReport, Calendar dateOfReport) {

		boolean wasReportCreated = false;

		wasReportCreated = task.createReport(task.getTaskCollaboratorByEmail(email), dateOfReport, timeToReport);

		return wasReportCreated;

	}

	/**
	 * This method gets the report Index of a given user
	 *
	 * @return A List with the index of the reports of a given taskCollaborator
	 */
	public List<Integer> getReportsIndexByGivenUser() {

		List<Integer> reportsOfGivenUser;

		reportsOfGivenUser = task.getReportsIndexOfTaskCollaborator(username.getEmail());

		return reportsOfGivenUser;

	}

	/**
	 * @param newReportTime
	 *            The update reported time
	 * @param taskCollaborator
	 *            The TaskCollaborator associated to the Report
	 * @param reportToChange
	 *            The report index to change
	 * @return TRUE if the report was updated, FALSE if not
	 */
	public boolean updateTaskReport(double newReportTime, TaskCollaborator taskCollaborator, Integer reportToChange) {
		Boolean wasReportUpdated = false;
		wasReportUpdated = task.updateReportedTime(newReportTime, taskCollaborator, reportToChange);

		return wasReportUpdated;

	}

	/**
	 * This method returns the dates of when the reports were created
	 *
	 * @param taskCollaborator
	 *            The TaskCollaborator to search its reports for
	 * @return A List with the dates of the report creation dates
	 */
	public List<Calendar> getReportsCreationDateByGivenUser(TaskCollaborator taskCollaborator) {

		List<Calendar> reportsDate = new ArrayList<>();
		for (Report other : this.task.getReports()) {
			if (other.getTaskCollaborator().equals(taskCollaborator)) {
				reportsDate.add(other.getDateOfReport());
			}
		}

		return reportsDate;
	}

	/**
	 * This method returns the dates of when the reports were updated
	 *
	 * @param taskCollaborator
	 *            The TaskCollaborator to search its reports for
	 * @return A List with the dates of the report update dates
	 */
	public List<Calendar> getReportsUpdateDateByGivenUser(TaskCollaborator taskCollaborator) {

		List<Calendar> reportsDate = new ArrayList<>();
		for (Report other : this.task.getReports()) {
			if (other.getTaskCollaborator().equals(taskCollaborator)) {
				reportsDate.add(other.getDateOfUpdate());
			}
		}

		return reportsDate;
	}

	/**
	 * This method returns the reported times of a given TaskCollaborator
	 *
	 * @param taskCollaborator
	 *            The TaskCollaborator to search its reports for
	 * @return A List with the reported times in the reports by a given user
	 */
	public List<Double> getReportTimeByGivenUser(TaskCollaborator taskCollaborator) {

		List<Double> reportsTime = new ArrayList<>();
		for (Report other : this.task.getReports()) {
			if (other.getTaskCollaborator().equals(taskCollaborator)) {
				reportsTime.add(other.getReportedTime());
			}
		}

		return reportsTime;
	}

	public User getUsername() {
		return username;
	}

	public void setUsername(User username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
