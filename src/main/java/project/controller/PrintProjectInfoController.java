package project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;

public class PrintProjectInfoController {

	private Project project;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	private Integer projID;

	public PrintProjectInfoController(Project project) {
		this.project = project;
	}

	public PrintProjectInfoController(Integer projID) {
		this.projID = projID;
		project = getProjectByProjectID();
	}

	public String printProjectNameInfo() {
		return this.project.getName().toUpperCase();
	}

	public String printProjectIDCodeInfo() {
		return String.valueOf(this.project.getIdCode());
	}

	public String printProjectStatusInfo() {
		return String.valueOf(this.project.getProjectStatus());
	}

	public String printProjectDescriptionInfo() {
		return this.project.getProjectDescription();
	}

	public String printProjectStartDateInfo() {
		Calendar startDate = this.project.getStartdate();
		return this.dateFormat.format(startDate.getTime());
	}

	public String printProjectFinishDateInfo() {
		Calendar finishDate = this.project.getFinishdate();
		return this.dateFormat.format(finishDate.getTime());
	}

	public String printProjectManagerInfo() {
		return this.project.getProjectManager().getName();
	}

	public String printProjectTeamInfo() {
		List<ProjectCollaborator> projectTeam = this.project.getProjectTeam();
		List<String> team = new ArrayList<>();
		for (ProjectCollaborator projectMember : projectTeam) {
			team.add(projectMember.getUserFromProjectCollaborator().getName());
		}
		return String.join(", ", team);
	}

	public String printProjectBudgetInfo() {
		return String.valueOf(this.project.getProjectBudget());
	}

	public List<String> getProjectTaskList() {
		List<Task> taskList = Company.getTheInstance().getProjectsRepository().getProjById(this.project.getIdCode())
				.getTaskRepository().getProjectTaskRepository();
		List<String> projectTaskList = new ArrayList<>();
		for (Task projectTask : taskList) {
			String[] stringList = projectTask.getTaskID().split("\\.");
			projectTaskList.add("[" + stringList[0] + "." + stringList[1] + "]" + " " + projectTask.getDescription());
		}
		return projectTaskList;
	}

	public List<String> getTasksIDs() {
		List<Task> taskList = Company.getTheInstance().getProjectsRepository().getProjById(this.project.getIdCode())
				.getTaskRepository().getProjectTaskRepository();
		List<String> projectTasksID = new ArrayList<>();
		for (Task projectTask : taskList) {
			projectTasksID.add(projectTask.getTaskID());
		}
		return projectTasksID;
	}

	public List<Task> getTasks() {
		return Company.getTheInstance().getProjectsRepository().getProjById(this.project.getIdCode())
				.getTaskRepository().getProjectTaskRepository();
	}

	private Project getProjectByProjectID() {

		this.project = Company.getTheInstance().getProjectsRepository().getProjById(this.projID);

		return project;
	}
}
