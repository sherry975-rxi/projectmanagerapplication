package project.model;

//

import project.model.taskstateinterface.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * Class that allows building and accessing Task attributes.
 * 
 * @author Group 3
 *
 */
@Entity
@Table(name = "Task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String taskID;
	private String description;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "task")
	@Column(columnDefinition = "LONGBLOB")
	private List<TaskCollaborator> taskTeam;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "task")
	@Column(columnDefinition = "LONGBLOB")
	private List<Report> reports;
	@Enumerated(EnumType.STRING)
	private StateEnum currentState;

	@OneToMany (fetch = FetchType.EAGER, cascade = ALL, mappedBy = "task")
	private List<TaskTeamRequest> pendingTaskTeamRequests;

	private Calendar creationDate;
	private Calendar startDate;
	private Calendar finishDate;
	@javax.persistence.Transient
	private TaskStateInterface taskState;
	private Integer estimatedTaskEffort;
	private Calendar estimatedTaskStartDate;
	private Calendar taskDeadline;
	private Integer taskBudget;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Task> taskDependency;

	private Integer startDateInterval;
	private Integer deadlineInterval;
	private Calendar cancelDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Project_id")
	private Project project;

	public Task() {
	}
	
	/**
	 * This constructor creates a task with the mandatory fields description 
	 * and the project where it will be associated.
	 * The other fields are automatically introduced by the
	 * creator pattern of the task.
	 * 
	 * @param description
	 *            Description of the task set by the user
	 * @param selectedProject
	 *            Id of the project to which the task belongs to

	 */
	public Task(String description, Project selectedProject) {
		this.description = description;
		this.creationDate = Calendar.getInstance();
		this.startDate = null;
		this.finishDate = null;
		this.taskTeam = new ArrayList<>();
		this.reports = new ArrayList<>();
		this.estimatedTaskEffort = 0;
		this.estimatedTaskStartDate = null;
		this.taskDeadline = null;
		this.taskBudget = 0;
		this.startDateInterval = null;
		this.deadlineInterval = null;
		this.taskDependency = new ArrayList<>();
		this.taskState = new Created();
		this.cancelDate = null;
		this.currentState = StateEnum.CREATED;
		this.project = selectedProject;
		this.pendingTaskTeamRequests = new ArrayList<>();
	}

	/**
	 * This constructor creates a task with the mandatory fields taskCounter, projId
	 * and description. However, the description is the only parameter that will be
	 * set by the user. The other two fields are automatically introduced by the
	 * creator pattern of the task.
	 * 
	 * @param taskCounter
	 *            Task counter in relation to the project
	 * @param projId
	 *            Id of the project to which the task belongs to
	 * @param description
	 *            Description of the task set by the user
	 */
	public Task(int taskCounter, int projId, String description) {
		Integer taskNumber = taskCounter;
		Integer projCode = projId;
		this.taskID = projCode.toString() + "." + taskNumber.toString();
		this.description = description;
		this.creationDate = Calendar.getInstance();
		this.startDate = null;
		this.finishDate = null;
		this.taskTeam = new ArrayList<>();
		this.reports = new ArrayList<>();
		this.estimatedTaskEffort = 0;
		this.estimatedTaskStartDate = null;
		this.taskDeadline = null;
		this.taskBudget = 0;
		this.startDateInterval = null;
		this.deadlineInterval = null;
		this.taskDependency = new ArrayList<>();
		this.taskState = this.getTaskState();
		this.cancelDate = null;
		this.currentState = StateEnum.CREATED;
		this.pendingTaskTeamRequests = new ArrayList<>();
	}

	/**
	 * This constructor is going to be deleted soon.
	 * 
	 * This Constructor creates a Task object with the mandatory parameters taskID
	 * and description and non mandatory parameters creation date, start date,
	 * finish date, task state (finished or unfinished) and task team
	 * @param description
	 *            Description of Task.
	 * @param estimatedTaskEffort
	 *            Value that corresponds to the effort associated with this Task.
	 * @param estimatedTaskStartDate
	 *            This value may have dependences if this Task has dependences.
	 * @param taskDeadline
	 *            Estimated finish Task date.
	 * @param estimatedBudgetCostTask
	 *            Value for the estimated cost of the Task.
	 */
	public Task(String description, int estimatedTaskEffort,
			Calendar estimatedTaskStartDate, Calendar taskDeadline, int estimatedBudgetCostTask) {
		this.description = description;
		this.creationDate = Calendar.getInstance();
		this.startDate = null;
		this.finishDate = null;
		this.taskTeam = new ArrayList<>();
		this.reports = new ArrayList<>();
		this.estimatedTaskEffort = estimatedTaskEffort;
		this.estimatedTaskStartDate = estimatedTaskStartDate;
		this.taskDeadline = taskDeadline;
		this.taskBudget = estimatedBudgetCostTask;
		this.startDateInterval = null;
		this.deadlineInterval = null;
		this.taskDependency = new ArrayList<>();
		this.taskState = new Created();
		this.currentState = StateEnum.CREATED;
		this.pendingTaskTeamRequests = new ArrayList<>();
	}
	
	/**
	 * This constructor is going to be deleted soon.
	 * 
	 * This Constructor creates a Task object with the mandatory parameters taskID
	 * and description and non mandatory parameters creation date, start date,
	 * finish date, task state (finished or unfinished) and task team
	 * 
	 * @param taskCounter
	 *            The Task counter in the Project in which it is included. This
	 *            value is generated in the Creator of Task.
	 * @param projId
	 *            This is the Project ID to which this Task belongs to.
	 * @param description
	 *            Description of Task.
	 * @param estimatedTaskEffort
	 *            Value that corresponds to the effort associated with this Task.
	 * @param estimatedTaskStartDate
	 *            This value may have dependences if this Task has dependences.
	 * @param taskDeadline
	 *            Estimated finish Task date.
	 * @param estimatedBudgetCostTask
	 *            Value for the estimated cost of the Task.
	 */
	public Task(int taskCounter, int projId, String description, int estimatedTaskEffort,
			Calendar estimatedTaskStartDate, Calendar taskDeadline, int estimatedBudgetCostTask) {
		Integer taskNumber = taskCounter;
		Integer projCode = projId;
		this.taskID = projCode.toString() + "." + taskNumber.toString();
		this.description = description;
		this.creationDate = Calendar.getInstance();
		this.startDate = null;
		this.finishDate = null;
		this.taskTeam = new ArrayList<>();
		this.reports = new ArrayList<>();
		this.estimatedTaskEffort = estimatedTaskEffort;
		this.estimatedTaskStartDate = estimatedTaskStartDate;
		this.taskDeadline = taskDeadline;
		this.taskBudget = estimatedBudgetCostTask;
		this.startDateInterval = null;
		this.deadlineInterval = null;
		this.taskDependency = new ArrayList<>();
		this.taskState = this.getTaskState();
		this.currentState = StateEnum.CREATED;
		this.pendingTaskTeamRequests = new ArrayList<>();
	}

	/**
	 * This constructor creates a Task from another Task, guaranteeing that the task
	 * created has the same field values as the original Task. Allows copying a
	 * task.
	 * 
	 * @param task
	 *            Task that will be used to create a new one.
	 */
	public Task(Task task) {
		this.description = task.description;
		this.creationDate = task.creationDate;
		this.startDate = task.getStartDate();
		this.finishDate = task.getFinishDate();
		this.taskState = task.getTaskState();
		this.taskTeam = task.copyListOfTaskCollaboratorsInTask();
		this.reports = task.getReports();
		this.estimatedTaskEffort = task.getEstimatedTaskEffort();
		this.estimatedTaskStartDate = task.getEstimatedTaskStartDate();
		this.taskDeadline = task.getTaskDeadline();
		this.taskBudget = task.getTaskBudget();
		this.taskDependency = task.taskDependency;
		this.taskState = task.getTaskState();
		if (task.startDateInterval != null) {
			this.startDateInterval = task.getStartDateInterval();
		} else {
			this.startDateInterval = null;
		}
		if (task.deadlineInterval != null) {
			this.deadlineInterval = task.getDeadlineInterval();
		} else {
			this.deadlineInterval = null;
		}
		this.currentState = StateEnum.CREATED;
		this.project = task.getProject();
		this.pendingTaskTeamRequests = new ArrayList<>();
	}

	/**
	 * Gets the currentState from the currentStateEnum variable
	 *
	 * @return the current state
	 */
	public StateEnum getCurrentState() {
		return currentState;
	}

	/**
	 * Sets the currentStateEnum
	 *
	 * @param currentState
	 *            Enum to set
	 */
	public void setCurrentState(StateEnum currentState) {
		this.currentState = currentState;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return this.project;
	}

	/**
	 * Returns the interval between the start date of the project and the estimated
	 * start date for the task.
	 * 
	 * @return startDateInterval
	 */
	public Integer getStartDateInterval() {
		return startDateInterval;
	}

	/**
	 * Defines the interval between the start date of the project and the estimated
	 * start date for the task to be a number provided.
	 * 
	 * @param newStartDateInterval
	 *            the amount of days between the start of the mother task and this
	 *            task
	 */
	public void setStartDateInterval(int newStartDateInterval) {
		this.startDateInterval = newStartDateInterval;
		this.taskState.doAction(this);
	}

	/**
	 * Returns the interval between the start date of the project and the estimated
	 * finish date for the task.
	 * 
	 * @return finishDateInterval
	 */
	public Integer getDeadlineInterval() {
		return deadlineInterval;
	}

	/**
	 * Defines the interval between the start date of the project and the estimated
	 * finish date for the task to be a number provided.
	 * 
	 * @param newFinishDateInterval
	 *            the amount of days between the end of the mother task and this
	 *            task
	 */
	public void setDeadlineInterval(int newFinishDateInterval) {
		this.deadlineInterval = newFinishDateInterval;
		this.taskState.doAction(this);
	}

	/**
	 * This method when called returns the Estimated Task Effort
	 * 
	 * @return estimatedTaskEffort
	 */
	public int getEstimatedTaskEffort() {
		return this.estimatedTaskEffort;
	}

	/**
	 * This method when called update the estimated task effort
	 * 
	 * @param newEstimatedTaskEffort
	 *            the effort estimated for a task
	 */
	public void setEstimatedTaskEffort(int newEstimatedTaskEffort) {
		this.estimatedTaskEffort = newEstimatedTaskEffort;
		this.taskState.doAction(this);
	}

	/**
	 * This method when called returns the Estimated Task Start Date if there is no
	 * start date interval (that happens by default), returns the initial estimated
	 * task start date. else, adds the interval to the project start date and
	 * returns the result (new date).
	 * 
	 * The task ID has the projID and so it can get the start date of the project
	 * where the task is in.
	 * 
	 * @return estimatedTaskStartDate
	 */
	public Calendar getEstimatedTaskStartDate() {
		if (this.startDateInterval == null) {
			return this.estimatedTaskStartDate;
		}
		Calendar newEstimatedStartDate = (Calendar) project.getStartdate().clone();
		newEstimatedStartDate.add(Calendar.DAY_OF_YEAR, this.startDateInterval);
		return newEstimatedStartDate;

	}

	/**
	 * This method when called update the Estimated Task Start Date
	 * 
	 * @param newEstimatedTaskStartDate
	 *            an estimated start date for the task
	 */
	public void setEstimatedTaskStartDate(Calendar newEstimatedTaskStartDate) {
		this.estimatedTaskStartDate = newEstimatedTaskStartDate;
		this.taskState.doAction(this);
	}

	/**
	 * This method when called returns the Deadline Date if there is no finish date
	 * interval (that happens by default), returns the initial Deadline date. else,
	 * adds the interval to the project start date and returns the result (new
	 * date).
	 * 
	 * The task ID is the same of the projID and so it can get the start date of the
	 * project where the task is in.
	 * 
	 * @return Deadline
	 */
	public Calendar getTaskDeadline() {
		if (this.deadlineInterval == null) {
			return this.taskDeadline;
		}
		Calendar newDeadline = (Calendar) project.getStartdate().clone();
		newDeadline.add(Calendar.DAY_OF_YEAR, this.deadlineInterval);
		return newDeadline;

	}

	/**
	 * This method when called update the task Dead line
	 * 
	 * @param newTaskDeadline
	 *            an estimated deadline for the task
	 */
	public void setTaskDeadline(Calendar newTaskDeadline) {
		this.taskDeadline = newTaskDeadline;
		this.taskState.doAction(this);
	}

	/**
	 * This method when called returns the estimated Budget Cost Task
	 * 
	 * @return estimatedBudgetCostTask
	 */
	public int getTaskBudget() {
		return this.taskBudget;
	}

	/**
	 * This method when called update the estimated Budget Cost Task
	 * 
	 * @param newEstimatedBudgetCostTask
	 *            an estimated budget for the task
	 */
	public void setTaskBudget(int newEstimatedBudgetCostTask) {
		this.taskBudget = newEstimatedBudgetCostTask;
		this.taskState.doAction(this);
	}

	/**
	 * This method when called returns the taskID
	 * 
	 * @return taskID the id of the task
	 * 
	 */
	public String getTaskID() {
		return this.taskID;
	}

	/**
	 * This method when called returns the task StartDate
	 * 
	 * @return startDate task start date
	 */
	public Calendar getStartDate() {
		return this.startDate;
	}

	/**
	 * This method when called let us define a startDate of our choice
	 * 
	 * @param c
	 *            Calendar date to input in start date
	 */
	public void setStartDate(Calendar c) {
		this.startDate = c;
		this.taskState.doAction(this);
	}

	/**
	 * This method returns the task description
	 * 
	 * @return description description of the task
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * This method returns the finish date
	 * 
	 * @return finishDate date of when the task finished
	 */
	public Calendar getFinishDate() {
		return this.finishDate;
	}

	/**
	 * This method sets the finish date to the current date (this instance) on the
	 * Calendar
	 * 
	 */
	public void setFinishDate() {
		this.finishDate = Calendar.getInstance();
	}

	/**
	 * This method gets the list of assigned users to a certain Task
	 * 
	 * @return taskTeam List of assigned users to a certain Task
	 */
	public List<TaskCollaborator> getTaskTeam() {
		return taskTeam;
	}

	public void setTaskTeam(List<TaskCollaborator> taskTeam) {
		this.taskTeam = taskTeam;
	}

	/**
	 * This method gets the list of reports from the task
	 * 
	 * @return reports List of reports in the task
	 */
	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public List<Task> getTaskDependency() {
		return this.taskDependency;
	}

	public void setTaskDependency(List<Task> taskDependency) {
		this.taskDependency = taskDependency;
	}

	public List<TaskTeamRequest> getPendingTaskTeamRequests() {
		return pendingTaskTeamRequests;
	}

	public void setPendingTaskTeamRequests(List<TaskTeamRequest> pendingTaskTeamRequests) {
		this.pendingTaskTeamRequests = pendingTaskTeamRequests;
	}
	/**
	 * This method confirms if the task state is Finished
	 * 
	 * @return TRUE if task's state is finished FALSE if task's state isn't finished
	 */
	public boolean isTaskFinished() {
		boolean isFinished = false;
		if (this.getTaskState() instanceof Finished) {
			isFinished = true;
		}
		return isFinished;
	}

	/**
	 * This method tries to change the task state to finished.
	 *
	 * @return TRUE if it state is successfully finished, FALSE if not
	 * 
	 */
	public boolean markTaskAsFinished() {
		Boolean changed = true;
		this.setFinishDate();

		this.taskState.doAction(this);
		
		if (!(this.taskState instanceof Finished)) {
			changed = false;
			this.finishDate = null;
		}

		return changed;
	}

	/**
	 * This Method adds a Project Collaborator to a Task, and creates a New Task
	 * Collaborator from this Project Collaborator.
	 * 
	 * @param projCollaborator
	 *            project collaborator to add to the task team
	 */
	public boolean addProjectCollaboratorToTask(ProjectCollaborator projCollaborator) {
		boolean addProjectCollaboratorToTask = false;

		addProjectCollaboratorToTask = addTaskCollaboratorToTask(createTaskCollaborator(projCollaborator));

		this.taskState.doAction(this);
		return addProjectCollaboratorToTask;
	}

	/**
	 * This method checks if the user is missing from the task team (List of users
	 * in Task), and if it is missing from the list, the user is added to the team.
	 * If it is already already added to the the list it is reactivated, and its
	 * attributes are copied into the existing task Collaborator
	 * 
	 * @param taskCollaborator
	 *            Task Collaborator to add to the task team
	 * 
	 * 
	 */
	public boolean addTaskCollaboratorToTask(TaskCollaborator taskCollaborator) {
		boolean wasTheTaskAddedToCollaborator = false;
		if (!isProjectCollaboratorInTaskTeam(taskCollaborator.getProjectCollaboratorFromTaskCollaborator())) {
			this.taskTeam.add(taskCollaborator);
			wasTheTaskAddedToCollaborator = true;
		} else if (!isProjectCollaboratorActiveInTaskTeam(
				taskCollaborator.getProjectCollaboratorFromTaskCollaborator())) {
			this.taskTeam.add(taskCollaborator);
			wasTheTaskAddedToCollaborator = true;

		}

		this.taskState.doAction(this);
		return wasTheTaskAddedToCollaborator;
	}

	/**
	 * Creates a Task Collaborator from a Project Collaborator
	 * 
	 * @param projCollaborator
	 *            a project collaborator for which a task collaborator will be
	 *            created
	 * 
	 * @return TaskCollaborator
	 */
	public TaskCollaborator createTaskCollaborator(ProjectCollaborator projCollaborator) {

		TaskCollaborator newTaskCollab = new TaskCollaborator(projCollaborator);
		newTaskCollab.setTask(this);

		return newTaskCollab;
	}

	/**
	 * Creates and adds a Report of a Specific Task Collaborator associated to a
	 * Specific Project Collaborator
	 * 
	 * @param taskCollaborator
	 *            a project collaborator for which a report will be created/updated
	 * @param dateOfReport
	 *            date of the hours worked in task
	 * 
	 * @return report
	 */

	public boolean createReport(TaskCollaborator taskCollaborator, Calendar dateOfReport, double timeToReport) {
		boolean wasReportCreated = true;

		if (this.getTaskState() instanceof Finished) {
			wasReportCreated = false;

		} else if (this.getTaskState() instanceof Cancelled) {
			wasReportCreated = false;

		} else if (this.getTaskState() instanceof StandBy) {
			wasReportCreated = false;

		} else if (!this.taskTeam.contains(taskCollaborator) || taskCollaborator.getFinishDate() != null) {
			wasReportCreated = false;
		} else {
			Report report = new Report(taskCollaborator, dateOfReport);
			report.setTask(this);
			report.setReportedTime(timeToReport);
			this.reports.add(report);

		}

		return wasReportCreated;

	}

	/**
	 * This method returns the index number of the reports associated to a Task
	 * Collaborator
	 *
	 * @param email
	 *            The Task Collaborator to search it's reports index number
	 * @return reportsIndex Returns a List with the index numbers of the reports by
	 *         a given taskCollaborator
	 */
	public List<Integer> getReportsIndexOfTaskCollaborator(String email) {

		List<Integer> reportsIndex = new ArrayList<>();

		for (int reportNumber = 0; reportNumber < this.reports.size(); reportNumber++) {
			if (this.reports.get(reportNumber).getTaskCollaborator().getProjCollaborator()
					.getUserFromProjectCollaborator().getEmail().equals(email)
					&& this.reports.get(reportNumber).getTaskCollaborator().getFinishDate() == null) {
				reportsIndex.add(reportNumber);
			}
		}

		return reportsIndex;

	}

	/**
	 *
	 * This method changes the reportedTime of a Task by a given TaskCollaborator
	 *
	 * @param newTime
	 *            The updated time of the report
	 * @param taskCollaborator
	 *            The userEmail to update it's reportedTime
	 * @return TRUE if the Report is updated, FALSE if Not
	 */
	public boolean updateReportedTime(double newTime, TaskCollaborator taskCollaborator, int reportToChange) {
		boolean wasReportUpdated = false;
		Report reportToUpdate;
		if (reportToChange >= 0 && reportToChange < this.reports.size()) {

			reportToUpdate = this.reports.get(reportToChange);

			if (reportToUpdate.getTaskCollaborator().equals(taskCollaborator)
					&& taskCollaborator.getFinishDate() == null) {
				reportToUpdate.setReportedTime(newTime);
				wasReportUpdated = true;
			}
		}

		return wasReportUpdated;
	}

	/**
	 * @param userEmail
	 *            The user to search for
	 * @return TRUE if the task already has a report by a given user, FALSE, if not
	 */
	public boolean doesTaskHaveReportByGivenUser(String userEmail) {
		boolean hasUserReport = false;
		for (Report other : this.reports) {
			if (other.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator()
					.getUserFromProjectCollaborator().getEmail().equals(userEmail)) {
				hasUserReport = true;
			}
		}

		return hasUserReport;

	}

	public TaskCollaborator getTaskCollaboratorByEmail(String email) {
		TaskCollaborator taskCollaborator = null;
		for (TaskCollaborator other : this.getTaskTeam()) {
			if (other.getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getEmail()
					.equals(email)) {
				taskCollaborator = other;
			}

		}

		return taskCollaborator;
	}

	/**
	 * This method returns the Active Task Collaborator by Email
	 *
	 * @param email
	 *            The Email to search for
	 * @return TaskCollaborator that is active in the TaskTeam
	 */
	public TaskCollaborator getActiveTaskCollaboratorByEmail(String email) {
		TaskCollaborator taskCollaborator = null;
		for (TaskCollaborator other : this.getTaskTeam()) {
			if (other.getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getEmail()
					.equals(email) && other.getFinishDate() == null) {
				taskCollaborator = other;
			}

		}

		return taskCollaborator;
	}

	/**
	 * @param userEmail
	 *            The UserEmail that the method will look for it's report
	 * @return The reported time by a task collaborator
	 */
	public double getReportedTimeByTaskCollaborator(String userEmail) {
		double reportedTime = 0;
		for (Report other : this.reports) {
			if (other.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator()
					.getUserFromProjectCollaborator().getEmail().equals(userEmail)) {
				reportedTime = other.getReportedTime();
			}
		}
		return reportedTime;
	}

	/**
	 * This method return the name of task Collaborator from the list of reports using a given email
	 * 
	 * @param userEmail
	 * 
	 * @return reporterName 
	 */
	public String getReporterName(String userEmail) {
		String reporterName = "";
		for (Report other : this.reports) {
			if (other.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator()
					.getUserFromProjectCollaborator().getEmail().equals(userEmail)) {
				reporterName = other.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator()
						.getUserFromProjectCollaborator().getName();
			}
		}
		return reporterName;
	}

	/**
	 * This method removes the user from a task. It checks first if the user is in
	 * the task team (List of users in Task), and deactivates it from the team.
	 * 
	 * @param projCollaborator
	 *            project collaborator to remove from the list of users in a task
	 * 
	 */
	public boolean removeProjectCollaboratorFromTask(ProjectCollaborator projCollaborator) {

		boolean removed = false;

		for (TaskCollaborator other : taskTeam) {
			if (other.getProjectCollaboratorFromTaskCollaborator().equals(projCollaborator)
					&& (other.isTaskCollaboratorActiveInTask())) {
				other.addFinishDateForTaskCollaborator();

				removed = true;
			}
		}
		this.taskState.doAction(this);
		return removed;
	}

	/**
	 * This method returns the time that all Task Collaborators spent on this
	 * specific task
	 * 
	 * @return Time spent on task
	 */
	public double getTimeSpentOntask() {

		double timeSpentOnTask = 0.0;

		for (Report report : this.reports) {
			timeSpentOnTask += report.getReportedTime();
		}

		return timeSpentOnTask;
	}

	/**
	 * This method returns the time that a specific Project Collaborator spent on
	 * this Task
	 * 
	 * @param toCheck
	 *            a project collaborator for which to get the time spent on task
	 * 
	 * @return Time spent on task
	 */
	public double getTimeSpentByProjectCollaboratorOntask(ProjectCollaborator toCheck) {

		double timeSpentByCollaboratorOnTask = 0.0;

		for (Report report : this.reports) {
			if (report.getTaskCollaborator().isProjectCollaboratorInTaskCollaborator(toCheck)) {
				timeSpentByCollaboratorOnTask += report.getReportedTime();
			}
		}

		return timeSpentByCollaboratorOnTask;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 3;
		result = prime * result + taskID.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;

		return taskID.equals(other.taskID);
	}

	/**
	 * This PRIVATE method checks if a Project Collaborator is already on the task
	 * team.
	 * 
	 * @param projCollaborator
	 *            Collaborator Project Collaborator to check
	 * @return True if task team contains Project Collaborator, FALSE if the task
	 *         team does not have the Project Collaborator to check
	 */
	public boolean isProjectCollaboratorInTaskTeam(ProjectCollaborator projCollaborator) {
		for (TaskCollaborator other : taskTeam) {
			if (other.getProjectCollaboratorFromTaskCollaborator().equals(projCollaborator)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This PRIVATE method checks if a Project Collaborator is active on the task
	 * team.
	 * 
	 * @param projCollaborator
	 *            Collaborator Project Collaborator to check
	 * @return True if task team Project Collaborator is active, FALSE if the task
	 *         team does not have the Project Collaborator active
	 */
	public boolean isProjectCollaboratorActiveInTaskTeam(ProjectCollaborator projCollaborator) {
		boolean isActive = false;
		for (TaskCollaborator other : taskTeam) {
			if (other.getTaskCollaborator().equals(projCollaborator.getUserFromProjectCollaborator())
					&& other.isTaskCollaboratorActiveInTask()) {
				isActive = true;
			}
		}
		return isActive;
	}

	/**
	 * This PRIVATE method checks if a Project Collaborator is active on the task
	 * team.
	 * 
	 * @return True if task team Project Collaborator is active, FALSE if the task
	 *         team does not have the Project Collaborator active
	 */
	public boolean doesTaskTeamHaveActiveUsers() {
		for (TaskCollaborator other : taskTeam) {
			if (other.isTaskCollaboratorActiveInTask()) {
				return true;
			}
		}
		return false;
	}

	// TODO What does copyListOfUsersInTask do that getUserList doesn't?
	// Why does it need to receive an empty list input?
	/**
	 * @return Returns a list of users copied from another task.
	 */
	private ArrayList<TaskCollaborator> copyListOfTaskCollaboratorsInTask() {

		return new ArrayList<>(this.getTaskTeam());
	}

	/**
	 * @return TRUE if task team is empty, FALSE if not.
	 */
	public boolean isTaskTeamEmpty() {
		return this.getTaskTeam().isEmpty();
	}

	/**
	 * This method returns the total cost of a task. The value that is returned is
	 * associated with a task with all its Task Collaborator. It is calculated
	 * multiplying the cost of each task Collaborator with the time that each
	 * Collaborator spent on this particular task.
	 * 
	 * @return Returns a double with the total cost of the task
	 * 
	 */
	public double getTaskCost() {
		double taskCost = 0.0;

		for (Report reported : this.reports) {
			taskCost += reported.getReportedTime() * reported.getCost();
		}
		return taskCost;
	}

	/**
	 * This method creates a dependence between tasks. It determines from which task
	 * the dependence is being created. Checks if the estimated this Task estimated
	 * start date is after the one on which this task depends, and adds it if this
	 * is confirmed
	 * 
	 * @param taskToEstablishDependenceUpon
	 *            Parent Task from which dependence is established
	 * @return TRUE if the task dependency was created, FALSE if not
	 */

	public boolean createTaskDependence(Task taskToEstablishDependenceUpon, int daysToPostpone) {
		boolean wasDependencyCreated = false;
		if (this.isCreatingTaskDependencyValid(taskToEstablishDependenceUpon)) {

			this.estimatedTaskStartDate = (Calendar) taskToEstablishDependenceUpon.taskDeadline.clone();
			if (daysToPostpone >= 0) {
				this.estimatedTaskStartDate.add(Calendar.DATE, daysToPostpone);
			}
			this.taskDependency.add(taskToEstablishDependenceUpon);
			wasDependencyCreated = true;

		}
		this.getTaskState().doAction(this);
		return wasDependencyCreated;

	}

	/**
	 * This method removes a dependency of a task
	 * 
	 * @param taskToEstablishDependenceUpon
	 *            The task to remove the task dependency from the TaskDependency
	 *            List
	 * 
	 * @return
	 * 
	 * 		TRUE if the dependency is removed, FALSE if not
	 */
	public boolean removeTaskDependence(Task taskToEstablishDependenceUpon) {
		boolean wasDependencyRemoved = false;
		for (Task other : this.taskDependency) {
			if (taskToEstablishDependenceUpon.equals(other)) {
				this.taskDependency.remove(taskToEstablishDependenceUpon);
				wasDependencyRemoved = true;
				break;
			}
		}
		return wasDependencyRemoved;
	}

	/**
	 * @param taskToEstablishDependenceUpon
	 *            The other task to check if its possible to create a task
	 *            dependence
	 * @return TRUE is it's possible to create a task Dependency, FALSE if not
	 */
	public boolean isCreatingTaskDependencyValid(Task taskToEstablishDependenceUpon) {

		boolean isDependencyValid = true;

		for (Task other : this.taskDependency) {
			if (other.equals(taskToEstablishDependenceUpon)) {
				isDependencyValid = false;
			}
		}

		/*
		 * Checks the state of this task
		 */
		if (this.getTaskState() instanceof OnGoing)
			isDependencyValid = false;
		if (this.getTaskState() instanceof Cancelled)
			isDependencyValid = false;
		if (this.getTaskState() instanceof Finished)
			isDependencyValid = false;
		if (this.getTaskState() instanceof StandBy)
			isDependencyValid = false;

		if (this.getTaskState() instanceof Ready)
			isDependencyValid = false;

		if (this.equals(taskToEstablishDependenceUpon))
			isDependencyValid = false;

		/*
		 * Checks the state of the other task
		 */
		if (taskToEstablishDependenceUpon.getTaskState() instanceof Cancelled)
			isDependencyValid = false;
		if (taskToEstablishDependenceUpon.taskDeadline == null)
			isDependencyValid = false;

		return isDependencyValid;

	}

	/**
	 * Checks if the task has active dependencies
	 * 
	 * @return True if there are active dependencies, false if not
	 */
	public boolean hasActiveDependencies() {
		boolean result = false;
		if (this.hasDependencies()) {
			for (Task other : this.taskDependency) {
				if (!"Finished".equals(other.viewTaskStateName()) && !"Cancelled".equals(other.viewTaskStateName())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * This method returns the current state of the task.
	 * 
	 * @return taskState
	 */
	public TaskStateInterface getTaskState() {
		return this.taskState;
	}

	/**
	 * This method returns the name of the task's current state.
	 * 
	 * @return String taskState
	 */
	public String viewTaskStateName() {
		return this.taskState.getClass().getSimpleName();
	}

	/**
	 * This method returns the name of the task's current state from the enum
	 *
	 * @return String taskState
	 */
	public String viewTaskStateNameFromEnum() {
		return this.currentState.getClass().toString();
	}

	/**
	 * This method defines the state of the task.
	 */
	public void setTaskState(TaskStateInterface newStateTask) {
		taskState = newStateTask;
	}

	/**
	 * Checks if the task has any dependencies, active or not
	 * 
	 * @return True if there are any dependencies, false if not
	 */
	public boolean hasDependencies() {
		return !this.taskDependency.isEmpty();
	}

	/**
	 * This method removes the finish date from the task
	 * 
	 */
	public void removeFinishDate() {
		if (this.finishDate != null) {
			this.finishDate=null;
			this.getTaskState().doAction(this);
		}

	}

	/**
	 * This method cancels a task
	 *
	 * @return TRUE if the state was successfully cancelled, FALSE if not
	 */
	public boolean cancelTask() {
		Boolean cancelled = true; 
		this.setCancelDate();
		this.taskState.doAction(this);
		if(!(this.getTaskState() instanceof Cancelled)) {
			cancelled = false; 
			this.cancelledDateClear();
		}
		
		return cancelled; 
	}

	/**
	 * This method sets the cancel date for the task
	 * 
	 */
	public void setCancelDate() {
		this.cancelDate = Calendar.getInstance();
	}

	/**
	 * This method gets the cancel date of the task
	 * 
	 */
	public Calendar getCancelDate() {
		return cancelDate;
	}

	/**
	 * This method removes all users from a task.
	 * 
	 */
	public void removeAllCollaboratorsFromTaskTeam() {
		for (TaskCollaborator other : taskTeam) {
			if (other.isTaskCollaboratorActiveInTask()) {
				other.addFinishDateForTaskCollaborator();
			}
		}
		this.getTaskState().doAction(this);
	}

	public void cancelledDateClear() {
		this.cancelDate = null;
		this.getTaskState().doAction(this);
	}

	/**
	 * Deletes the finish date of the task and changed its state to Ongoing. If the
	 * state machine does not let the task change its state, the finish date is set
	 * to its previous value.
	 */
	public void UnfinishTask() {
		int year = finishDate.get(Calendar.YEAR);
		int month = finishDate.get(Calendar.MONTH);
		int date = finishDate.get(Calendar.DAY_OF_MONTH);
		Calendar finishDateCopy = Calendar.getInstance(); 
		finishDate.set(year, month, date);
		
		this.finishDate = null;
		this.taskState.doAction(this);

		if (!(this.taskState instanceof OnGoing)) {
			this.finishDate = finishDateCopy;
		}
	}

	/**
	 * Creates a new assignment request, and adds the request to the list of pending
	 * task assignment requests if it isn't already created
	 *
	 * @param projCollab
	 *            projCollab to create Request
	 * @return True if it adds, false if there is already an equal request
	 */
	public boolean createTaskAssignementRequest(ProjectCollaborator projCollab) {// uso de if incorreto?
		TaskTeamRequest newReq = new TaskTeamRequest(projCollab, this);
		newReq.setType(TaskTeamRequest.ASSIGNMENT);
		if (!this.isAssignmentRequestAlreadyCreated(projCollab)) {
			 this.pendingTaskTeamRequests.add(newReq);
		}
		return false;
	}

	/**
	 * Creates a new removal request, and adds the request to the list of pending
	 * task removal requests if it isn't already created
	 *
	 * @param projCollab
	 *            projCollab to create Request
	 * @return True if it adds, false if there is already an equal request
	 */
	public boolean createTaskRemovalRequest(ProjectCollaborator projCollab) {
		TaskTeamRequest newReq = new TaskTeamRequest(projCollab, this);
		newReq.setType(TaskTeamRequest.REMOVAL);
		if (!this.isRemovalRequestAlreadyCreated(projCollab)) {
			this.pendingTaskTeamRequests.add(newReq);
		}
		return false;
	}

	/**
	 * Removes request to add a certain project collaborator to a specific task
	 * team.
	 *
	 * @param request
	 *            Request to remove from the list
	 */

	public void deleteTaskAssignementRequest(TaskTeamRequest request) {
		this.pendingTaskTeamRequests.remove(request);
	}

	/**
	 * Removes the removal request of a certain project collaborator to a specific
	 * task team.
	 *
	 * @param projCollab
	 *
	 * @return TRUE if deleted FALSE if not
	 */

	public boolean deleteTaskRemovalRequest(ProjectCollaborator projCollab) {
		TaskTeamRequest request = this.getRemovalTaskTeamRequest(projCollab);
		request.setType(TaskTeamRequest.REMOVAL);
		return this.pendingTaskTeamRequests.remove(request);
	}

	/**
	 * Returns the relevant info in the form of a list of strings
	 *
	 * @return toString List of strings which contains the info about the task and
	 *         the project collaborator for each request
	 */
	public List<String> viewPendingTaskAssignementRequests() {
		List<String> toString = new ArrayList<>();
		for (TaskTeamRequest req : this.pendingTaskTeamRequests) {
			if(req.isAssignmentRequest()) {
				toString.add(req.viewStringRepresentation());
			}
		}
		return toString;
	}

	/**
	 * Returns the relevant info in the form of a list of strings of the pending
	 * task removal requests
	 *
	 * @return toString List of strings which contains the info about the task and
	 *         the project collaborator for each request
	 */
	public List<String> viewPendingTaskRemovalRequests() {
		List<String> toString = new ArrayList<>();
		for (TaskTeamRequest req : this.pendingTaskTeamRequests) {
			if(req.isRemovalRequest()) {
				toString.add(req.viewStringRepresentation());
			}
		}
		return toString;
	}

	/**
	 * This method receives a Project Collaborator and a Task, creates a new
	 * TaskTeamRequest with those objects and searches if there's a removal request
	 * equal to the created one, in the pending removal requests list.
	 *
	 * @param projCollaborator
	 *            Project Collaborator to create the request
	 *
	 * @return Returns the removal request within the list if it exists or NULL if
	 *         it does not
	 */
	public TaskTeamRequest getRemovalTaskTeamRequest(ProjectCollaborator projCollaborator) {

		TaskTeamRequest removalRequestToFind = new TaskTeamRequest(projCollaborator, this);
		removalRequestToFind.setType(TaskTeamRequest.REMOVAL);
		for (TaskTeamRequest other : this.pendingTaskTeamRequests) {
			if (removalRequestToFind.equals(other)) {
				return other;
			}
		}
		return null;
	}

	/**
	 * Returns the list of Task assigment requests by collaborators, to be handled
	 * by the model or controller
	 *
	 * @return List of TaskTeamRequest Objects from all users asking to be assigned
	 *         to a certain task
	 */

	public List<TaskTeamRequest> getPendingTaskAssignementRequests() {

		List<TaskTeamRequest> assignmentRequests = new ArrayList<>();

		for (TaskTeamRequest req : this.pendingTaskTeamRequests) {
			if (req.isAssignmentRequest()) {
				assignmentRequests.add(req);
			}
		}
		return assignmentRequests;
	}

	// Do we use this method give the Removal requests to the controller, or
	// create a method in Project that handles the approvals/rejections by receiving
	// index numbers from the controller?
	/**
	 * Returns the list of Task removal requests by collaborators, to be handled by
	 * the model or controller
	 *
	 * @return List of TaskTeamRequest Objects from all users asking to be assigned
	 *         to a certain task
	 */

	public List<TaskTeamRequest> getPendingTaskRemovalRequests() {
		List<TaskTeamRequest> removalRequests = new ArrayList<>();

		for (TaskTeamRequest req : this.pendingTaskTeamRequests) {
			if (req.isRemovalRequest()) {
				removalRequests.add(req);
			}
		}
		return removalRequests;
	}

	/**
	 * Checks if a certain request already exists
	 *
	 * @param projectCollaborator
	 *            Projector collaborator that wants to create the request

	 * @return True if request already exists, false if not
	 */
	public boolean isAssignmentRequestAlreadyCreated(ProjectCollaborator projectCollaborator) {
		TaskTeamRequest request = new TaskTeamRequest(projectCollaborator, this);
		request.setType(TaskTeamRequest.ASSIGNMENT);
		return this.pendingTaskTeamRequests.contains(request);
	}

	/**
	 * Checks if a certain request already exists
	 *
	 * @param projCollab
	 *            Projector collaborator that wants to create the request
	 * @return True if request already exists, false if not
	 */
	public boolean isRemovalRequestAlreadyCreated(ProjectCollaborator projCollab) {
		TaskTeamRequest request = new TaskTeamRequest(projCollab, this);
		request.setType(TaskTeamRequest.REMOVAL);
		return this.pendingTaskTeamRequests.contains(request);
	}

	/**
	 * Gets the request associated with the project collaborator and task provided
	 *
	 * @param projCollaborator
	 *            Project Collaborator to search
	 * @return The request associated with the data provided, if it exists, else
	 *         return null.
	 */
	public TaskTeamRequest getAssignementTaskTeamRequest(ProjectCollaborator projCollaborator) {
		TaskTeamRequest result = null;
		TaskTeamRequest assignementRequestToFind = new TaskTeamRequest(projCollaborator, this);
		assignementRequestToFind.setType(TaskTeamRequest.ASSIGNMENT);
		for (TaskTeamRequest other : this.pendingTaskTeamRequests) {
			if (assignementRequestToFind.equals(other)) {
				result = other;
			}
		}
		return result;
	}

	/**
	 * Searches request lists for the task selected. If it finds any request
	 * with this task, removes it from the list.
	 */
	public void removeAllRequestsWithASpecificTask() {
		this.pendingTaskTeamRequests.clear();;
	}
}