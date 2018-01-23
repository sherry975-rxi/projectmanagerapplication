package project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.Task;
import project.model.TaskCollaborator;

public class PrintTaskInfoController {

	private Task task;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	private String taskID;
	private Integer projeID;
	private Project project;

	public PrintTaskInfoController(Task task) {
		this.task = task;
	}

	public PrintTaskInfoController(String taskID, Integer projeID) {
		this.taskID = taskID;
		this.projeID = projeID;
		setProjectAndTask();
	}

	public void setProjectAndTask() {
		this.project = Company.getTheInstance().getProjectsRepository().getProjById(this.projeID);
		this.task = Company.getTheInstance().getProjectsRepository().getProjById(this.projeID).getTaskRepository()
				.getTaskByID(this.taskID);
	}

	/**
	 * This method get the task's name and return it as a String
	 *
	 * @return String task's name
	 */
	public String printTaskNameInfo() {
		return this.task.getDescription();
	}

	/**
	 * This method get the task's ID and return it as a String
	 *
	 * @return String task's ID
	 */
	public String printTaskIDCodeInfo() {
		return this.task.getTaskID();
	}

	/**
	 * This method get the task's state and return it as a String
	 *
	 * @return String task's state
	 */
	public String printTaskStateInfo() {

		return this.task.viewTaskStateName();
	}

	/**
	 * This method get the task's estimated start date and return it as a String
	 *
	 * @return String task's estimated start date
	 */
	public String printTaskEstimatedStartDateInfo() {
		Calendar estimatedStartDate = this.task.getEstimatedTaskStartDate();
		String toPrint = "---";
		if (estimatedStartDate != null) {
			toPrint = this.dateFormat.format(estimatedStartDate.getTime());
		}
		return toPrint;
	}

	/**
	 * This method get the task's start date and return it as a String
	 *
	 * @return String task's start date
	 */
	public String printTaskStartDateInfo() {
		Calendar startDate = this.task.getStartDate();
		String toPrint = "---";
		if (startDate != null) {
			toPrint = this.dateFormat.format(startDate.getTime());
		}
		return toPrint;
	}

	/**
	 * This method get the task's deadline and return it as a String
	 *
	 * @return String task's deadline
	 */
	public String printTaskDeadlineInfo() {
		Calendar deadlineDate = this.task.getTaskDeadline();
		String toPrint = "---";
		if (deadlineDate != null) {
			toPrint = this.dateFormat.format(deadlineDate.getTime());
		}
		return toPrint;
	}

	/**
	 * This method get the task's finish date and return it as a String
	 *
	 * @return String task's finish date
	 */
	public String printTaskFinishDateInfo() {
		Calendar finishDate = this.task.getFinishDate();
		String toPrint = "---";
		if (finishDate != null) {
			toPrint = this.dateFormat.format(finishDate.getTime());
		}
		return toPrint;
	}

	/**
	 * This method get the task's team and return it as a String (team members
	 * separeted by commas)
	 *
	 * @return String task's team
	 */
	public String printTaskTeamInfo() {
		List<TaskCollaborator> projectTeam = this.task.getTaskTeam();
		List<String> team = new ArrayList<>();
		for (TaskCollaborator projectMember : projectTeam) {
			team.add(projectMember.getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator()
					.getName());
		}
		return String.join(", ", team);
	}

	/**
	 * This method get the task's budget and return it as a String
	 *
	 * @return String task's budget
	 */
	public String printTaskBudgetInfo() {
		return String.valueOf(this.task.getTaskBudget());
	}

	/**
	 * this method get the name of project associated with taskiD.
	 * 
	 * @return name of project associated with taskID
	 */
	public String printProjectNameInfo() {
		return this.project.getName();
	}
}
