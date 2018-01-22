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
	private Project project;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	private String taskID;
	private Integer projeID;

	public PrintTaskInfoController(Task task) {
		this.task = task;
	}

	public PrintTaskInfoController(String taskID, Integer projeID) {
		this.taskID = taskID;
		this.projeID = projeID;
		this.project = Company.getTheInstance().getProjectsRepository().getProjById(this.projeID);
		this.task = project.getTaskRepository().getTaskByID(this.taskID);
	}

	public String printTaskNameInfo() {
		return this.task.getDescription().toUpperCase();
	}

	public String printProjectNameInfo() {
		return this.project.getName();
	}

	public String printTaskIDCodeInfo() {
		return this.task.getTaskID();
	}

	public String printTaskStateInfo() {
		return this.task.viewTaskStateName();
	}

	public String printTaskEstimatedStartDateInfo() {
		Calendar estimatedStartDate = this.task.getEstimatedTaskStartDate();
		String toPrint = "---";
		if (estimatedStartDate != null) {
			toPrint = this.dateFormat.format(estimatedStartDate.getTime());
		}
		return toPrint;
	}

	public String printTaskStartDateInfo() {
		Calendar startDate = this.task.getStartDate();
		String toPrint = "---";
		if (startDate != null) {
			toPrint = this.dateFormat.format(startDate.getTime());
		}
		return toPrint;
	}

	public String printTaskDeadlineInfo() {
		Calendar deadlineDate = this.task.getTaskDeadline();
		String toPrint = "---";
		if (deadlineDate != null) {
			toPrint = this.dateFormat.format(deadlineDate.getTime());
		}
		return toPrint;
	}

	public String printTaskFinishDateInfo() {
		Calendar finishDate = this.task.getFinishDate();
		String toPrint = "---";
		if (finishDate != null) {
			toPrint = this.dateFormat.format(finishDate.getTime());
		}
		return toPrint;
	}

	public String printTaskTeamInfo() {
		List<TaskCollaborator> projectTeam = this.task.getTaskTeam();
		List<String> team = new ArrayList<>();
		for (TaskCollaborator projectMember : projectTeam) {
			team.add(projectMember.getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator()
					.getName());
		}
		return String.join(", ", team);
	}

	public String printTaskBudgetInfo() {
		return String.valueOf(this.task.getTaskBudget());
	}

}
