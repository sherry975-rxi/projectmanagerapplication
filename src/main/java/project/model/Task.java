package project.model;

//
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.model.taskStateInterface.Created;
import project.model.taskStateInterface.TaskStateInterface;

/**
 * Class that allows building and accessing Task attributes.
 * 
 * @author Group 3
 *
 */

public class Task {

	private String taskID;
	private String description;
	private List<TaskCollaborator> taskTeam;
	private List<Report> reports;
	private Calendar creationDate;
	private Calendar startDate;
	private Calendar finishDate;
	private boolean taskStatus; // is going to be deleted.
	private TaskStateInterface taskState;
	private Integer estimatedTaskEffort;
	private Calendar estimatedTaskStartDate;
	private Calendar cancelledDate;
	private Calendar taskDeadline;
	private Integer taskBudget;
	private List<Task> taskDependency;
	private Integer startDateInterval;
	private Integer deadlineInterval;

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
		Integer taskNumber = (Integer) taskCounter;
		Integer projCode = (Integer) projId;
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
		this.taskState = new Created(this);
		this.cancelledDate = null;
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
		Integer taskNumber = (Integer) taskCounter;
		Integer projCode = (Integer) projId;
		this.taskID = projCode.toString() + "." + taskNumber.toString();
		this.description = description;
		this.creationDate = Calendar.getInstance();
		this.startDate = null;
		this.finishDate = null;
		this.taskStatus = false;
		this.taskTeam = new ArrayList<>();
		this.reports = new ArrayList<>();
		this.estimatedTaskEffort = estimatedTaskEffort;
		this.estimatedTaskStartDate = estimatedTaskStartDate;
		this.taskDeadline = taskDeadline;
		this.taskBudget = estimatedBudgetCostTask;
		this.startDateInterval = null;
		this.deadlineInterval = null;
		this.taskDependency = new ArrayList<>();
		this.taskState = new Created(this);
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
		this.taskID = task.taskID;
		this.description = task.description;
		this.creationDate = task.creationDate;
		this.startDate = task.getStartDate();
		this.finishDate = task.getFinishDate();
		this.taskState = task.getTaskState();
		this.taskTeam = task.copyListOfTaskCollaboratorsInTask(this.taskTeam);
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
	 */
	public void setStartDateInterval(int newStartDateInterval) {
		this.startDateInterval = newStartDateInterval;
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
	 */
	public void setDeadlineInterval(int newFinishDateInterval) {
		this.deadlineInterval = newFinishDateInterval;
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
	 */
	public void setEstimatedTaskEffort(int newEstimatedTaskEffort) {
		this.estimatedTaskEffort = newEstimatedTaskEffort;
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
		double d = Double.parseDouble(taskID);
		int projId = (int) d;
		Project proj = Company.getTheInstance().getProjectsRepository().getProjById(projId);
		Calendar newEstimatedStartDate = (Calendar) proj.getStartdate().clone();
		newEstimatedStartDate.add(Calendar.DAY_OF_YEAR, this.startDateInterval);
		return newEstimatedStartDate;

	}

	/**
	 * This method when called update the Estimated Task Start Date
	 * 
	 * @param newEstimatedTaskStartDate
	 */
	public void setEstimatedTaskStartDate(Calendar newEstimatedTaskStartDate) {
		this.estimatedTaskStartDate = newEstimatedTaskStartDate;
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
		double d = Double.parseDouble(taskID);
		int projId = (int) d;
		Project proj = Company.getTheInstance().getProjectsRepository().getProjById(projId);
		Calendar newDeadline = (Calendar) proj.getStartdate().clone();
		newDeadline.add(Calendar.DAY_OF_YEAR, this.deadlineInterval);
		return newDeadline;

	}

	/**
	 * This method when called update the task Dead line
	 * 
	 * @param newTaskDeadline
	 */
	public void setTaskDeadline(Calendar newTaskDeadline) {
		this.taskDeadline = newTaskDeadline;
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
	 */
	public void setTaskBudget(int newEstimatedBudgetCostTask) {
		this.taskBudget = newEstimatedBudgetCostTask;
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
	 * @parameter c Calendar date to input in start date
	 */
	public void setStartDate(Calendar c) {
		this.startDate = c;
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
	 * This method sets the finish date to a chosen date on the Calendar. Created to
	 * be able to test this class
	 * 
	 */
	public void setFinishDate(Calendar c) { // REMOVE AND CORRECT TESTS
		this.finishDate = (Calendar) c.clone();
	}

	/**
	 * This method gets the list of assigned users to a certain Task
	 * 
	 * @return taskTeam List of assigned users to a certain Task
	 */
	public List<TaskCollaborator> getTaskTeam() {
		return taskTeam;
	}

	/**
	 * This method gets the list of reports from the task
	 * 
	 * @return reports List of reports in the task
	 */
	public List<Report> getReports() {
		return reports;
	}

	/**
	 * This method returns the task state.
	 * 
	 * @return TRUE if finished FALSE if unfinished
	 */
	public boolean isTaskFinished() {
		return this.taskStatus;
	}

	/**
	 * This method changes the task state to finished by altering the task boolean
	 * to TRUE
	 * 
	 */
	public void markTaskAsFinished() {
		this.taskStatus = true;
	}

	/**
	 * This Method adds a Project Collaborator to a Task, and creates a New Task
	 * Collaborator from this Project Collaborator.
	 * 
	 * @param projCollaborator
	 */
	public void addProjectCollaboratorToTask(ProjectCollaborator projCollaborator) {
		addTaskCollaboratorToTask(createTaskCollaborator(projCollaborator));

	}

	/**
	 * This method checks if the user is missing from the task team (List of users
	 * in Task), and if it is missing from the list, the user is added to the team.
	 * If it is already already added to the the list it is reactivated, and its
	 * attributes are copied into the existing task Collaborator
	 * 
	 * @param TaskCollaborator
	 *            Task Collaborator to add to the task team
	 * 
	 * 
	 */
	public void addTaskCollaboratorToTask(TaskCollaborator taskCollaborator) {
		if (!isProjectCollaboratorInTaskTeam(taskCollaborator.getProjectCollaboratorFromTaskCollaborator())) {
			this.taskTeam.add(taskCollaborator);
		} else if (!isProjectCollaboratorActiveInTaskTeam(
				taskCollaborator.getProjectCollaboratorFromTaskCollaborator())) {
			this.taskTeam.add(taskCollaborator);
		}

	}

	/**
	 * Creates a Task Collaborator from a Project Collaborator
	 * 
	 * @param ProjectCollaborator
	 * 
	 * @return TaskCollaborator
	 */
	public TaskCollaborator createTaskCollaborator(ProjectCollaborator projCollaborator) {

		return new TaskCollaborator(projCollaborator);
	}

	/**
	 * Creates and adds a Report of a Specific Task Collaborator associated to a
	 * Specific Project Collaborator
	 * 
	 * @param taskCollaborator
	 * 
	 * @return report
	 */
	public void createReport(TaskCollaborator taskCollaborator) {

		Report report = new Report(taskCollaborator);
		this.reports.add(report);

	}

	/**
	 * This method removes the user from a task. It checks first if the user is in
	 * the task team (List of users in Task), and deactivates it from the team.
	 * 
	 * @param user
	 *            User to remove from the list of users in a task
	 * 
	 */
	public void removeProjectCollaboratorFromTask(ProjectCollaborator projCollaborator) {
		for (TaskCollaborator other : taskTeam) {
			if (other.getProjectCollaboratorFromTaskCollaborator().equals(projCollaborator)
					&& (other.isTaskCollaboratorActiveInTask())) {
				other.addFinishDateForTaskCollaborator();
			}
		}
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
	 * @param ProjectCollaborator
	 *            to check
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
	 * /** This method returns the total amount of time spent on a task. It takes in
	 * consideration the task startDate and finishDate, that a working day has 8h
	 * and that Sundays, Saturdays and Holidays are not considered for the
	 * calculation.
	 *
	 * @return intervalHoursFullDays + intervalHoursPartialDays Total hours of work
	 * spent in a task
	 *//*
		 * public double getTimeSpentOnTask() {
		 * 
		 * int intervalDays = this.finishDate.get(Calendar.DAY_OF_YEAR) -
		 * this.startDate.get(Calendar.DAY_OF_YEAR) - 1; // 1 is subtracted to guarantee
		 * I only work with full days here.
		 * 
		 * Calendar referenceDate = (Calendar) this.startDate.clone();
		 * 
		 * // To make sure that the interval of dates doesn't consider Saturdays,
		 * Sundays // and holidays. Subtract 24 hours for each weekend day and holiday
		 * while (referenceDate.before(this.finishDate)) { if
		 * (referenceDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
		 * referenceDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
		 * isItHoliday(referenceDate)) { intervalDays = intervalDays - 1; }
		 * referenceDate.add(Calendar.DAY_OF_YEAR, 1);// TODO NEED DOUBLE CHECK because
		 * it may fail while changing // year }
		 * 
		 * int intervalHoursFullDays = intervalDays * 8; // find useful hours (8)
		 * 
		 * // Sets start working hour of the day to 9 am Calendar startHour = (Calendar)
		 * this.finishDate.clone(); startHour.set(Calendar.HOUR_OF_DAY, 9);
		 * startHour.set(Calendar.MINUTE, 0); startHour.set(Calendar.SECOND, 0);
		 * 
		 * // Sets finish working hour of the day to 6 pm Calendar finishHour =
		 * (Calendar) this.startDate.clone(); finishHour.set(Calendar.HOUR_OF_DAY, 18);
		 * finishHour.set(Calendar.MINUTE, 0); finishHour.set(Calendar.SECOND, 0);
		 * 
		 * double intervalHoursFirstDay = (double) (finishHour.getTimeInMillis() -
		 * this.startDate.getTimeInMillis()); intervalHoursFirstDay =
		 * intervalHoursFirstDay / 3600000; if (intervalHoursFirstDay >= 5) {
		 * intervalHoursFirstDay--; }
		 * 
		 * double intervalHoursLastDay = (this.finishDate.getTimeInMillis() -
		 * startHour.getTimeInMillis()); intervalHoursLastDay = intervalHoursLastDay /
		 * 3600000; if (intervalHoursLastDay >= 5) { intervalHoursLastDay--; } double
		 * intervalHoursPartialDays = intervalHoursFirstDay + intervalHoursLastDay; //
		 * partial work hours in // first and last days of // work
		 * 
		 * return intervalHoursFullDays + intervalHoursPartialDays; // total hours
		 * working days }
		 * 
		 *//**
			 * This is a private method that checks if a date is a holiday (non working day)
			 *
			 * @param date
			 *
			 * @return TRUE if the day is a holiday FALSE if the day is not a holiday
			 *//*
				 * private boolean isItHoliday(Calendar date) { int currentDay =
				 * date.get(Calendar.DAY_OF_MONTH); int currentMonth = date.get(Calendar.MONTH);
				 * int currentYear = date.get(Calendar.YEAR); int[] specialDays =
				 * getSpecialHolidaysDate(currentYear);
				 * 
				 * int dayEaster = specialDays[1]; int monthEaster = specialDays[0];
				 * 
				 * int dayCarnival = specialDays[3]; int monthCarnival = specialDays[2];
				 * 
				 * int dayCorpusChrist = specialDays[5]; int monthCorpusChrist = specialDays[4];
				 * 
				 * int dayHolyFriday = specialDays[7]; int monthHolyFriday = specialDays[6];
				 * 
				 * switch (currentDay) { case 1: switch (currentMonth) { case 0: return true;
				 * case 10: return true; case 11: return true; }
				 * 
				 * case 25: switch (currentMonth) { case 3: return true; case 11: return true;
				 * 
				 * }
				 * 
				 * case 10: switch (currentMonth) { case 5: return true; }
				 * 
				 * case 15: switch (currentMonth) { case 7: return true; }
				 * 
				 * case 5: switch (currentMonth) { case 9: return true; }
				 * 
				 * case 8: switch (currentMonth) { case 11: return true; } }
				 * 
				 * if (currentDay == dayEaster && currentMonth == monthEaster) return true; //
				 * Feriado Páscoa - Feriado Móvel if (currentDay == dayCarnival && currentMonth
				 * == monthCarnival) return true; // Feriado Carnaval - Feriado Móvel if
				 * (currentDay == dayCorpusChrist && currentMonth == monthCorpusChrist) return
				 * true; // Corpo Cristo - Feriado Móvel if (currentDay == dayHolyFriday &&
				 * currentMonth == monthHolyFriday) return true; // Sexta-Feira Santa - Feriado
				 * Móvel
				 * 
				 * return false; }
				 * 
				 * private int[] getSpecialHolidaysDate(int year) {
				 * 
				 * int[] specialHolidays = new int[8];
				 * 
				 * GregorianCalendar easterDate = new GregorianCalendar(); GregorianCalendar
				 * carnivalDate = new GregorianCalendar(); GregorianCalendar corpusChristDate =
				 * new GregorianCalendar(); GregorianCalendar holyFridayDate = new
				 * GregorianCalendar();
				 * 
				 * int a = year % 19; int b = year / 100; int c = year % 100; int d = b / 4; int
				 * e = b % 4; int f = (b + 8) / 25; int g = (b - f + 1) / 3; int h = (19 * a + b
				 * - d - g + 15) % 30; int i = c / 4; int k = c % 4; int l = (32 + 2 * e + 2 * i
				 * - h - k) % 7; int m = (a + 11 * h + 22 * l) / 451; int month = (h + l - 7 * m
				 * + 114) / 31; int day = ((h + l - 7 * m + 114) % 31) + 1;
				 * 
				 * easterDate.set(Calendar.MONTH, month - 1);
				 * easterDate.set(Calendar.DAY_OF_MONTH, day);
				 * 
				 * specialHolidays[0] = easterDate.get(Calendar.MONTH); specialHolidays[1] =
				 * easterDate.get(Calendar.DAY_OF_MONTH);
				 * 
				 * // Carnival - 47 Days Before Easter
				 * carnivalDate.setTimeInMillis(easterDate.getTimeInMillis()); if (k != 0) {
				 * carnivalDate.add(Calendar.DAY_OF_MONTH, -47); } else {
				 * carnivalDate.add(Calendar.DAY_OF_MONTH, -46); } specialHolidays[2] =
				 * carnivalDate.get(Calendar.MONTH); specialHolidays[3] =
				 * carnivalDate.get(Calendar.DAY_OF_MONTH);
				 * 
				 * // CorpusChristi 60 days after Easter
				 * corpusChristDate.setTimeInMillis(easterDate.getTimeInMillis());
				 * corpusChristDate.add(Calendar.DAY_OF_MONTH, 60); specialHolidays[4] =
				 * corpusChristDate.get(Calendar.MONTH); specialHolidays[5] =
				 * corpusChristDate.get(Calendar.DAY_OF_MONTH);
				 * 
				 * holyFridayDate.setTimeInMillis(easterDate.getTimeInMillis());
				 * holyFridayDate.add(Calendar.DAY_OF_MONTH, -2); specialHolidays[6] =
				 * holyFridayDate.get(Calendar.MONTH); specialHolidays[7] =
				 * holyFridayDate.get(Calendar.DAY_OF_MONTH);
				 * 
				 * return specialHolidays; }
				 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (!taskID.equals(other.taskID))
			return false;
		return true;
	}

	/**
	 * This PRIVATE method checks if a Project Collaborator is already on the task
	 * team.
	 * 
	 * @param Project
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
	 * @param Project
	 *            Collaborator Project Collaborator to check
	 * @return True if task team Project Collaborator is active, FALSE if the task
	 *         team does not have the Project Collaborator active
	 */
	public boolean isProjectCollaboratorActiveInTaskTeam(ProjectCollaborator projCollaborator) {
		for (TaskCollaborator other : taskTeam) {
			if (other.getTaskCollaborator().equals(projCollaborator.getUserFromProjectCollaborator())) {
				if (other.isTaskCollaboratorActiveInTask()) {
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * This PRIVATE method checks if a Project Collaborator is active on the task
	 * team.
	 * 
	 * @param Project
	 *            Collaborator Project Collaborator to check
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
	 * @param emptyListOfUsersInTask
	 *            Empty list that will be filled with the users from another task.
	 * @return Returns a list of users copied from another task.
	 */
	public List<TaskCollaborator> copyListOfTaskCollaboratorsInTask(List<TaskCollaborator> emptyListOfUsersInTask) {

		emptyListOfUsersInTask = new ArrayList<>();

		for (int iUser = 0; iUser < this.getTaskTeam().size(); iUser++) {
			emptyListOfUsersInTask.add(this.getTaskTeam().get(iUser));
		}

		return emptyListOfUsersInTask;
	}

	/**
	 * @return TRUE if task team is empty, FALSE if not.
	 */
	public boolean isTaskTeamEmpty() {

		if (this.getTaskTeam().isEmpty()) {
			return true;
		}

		return false;
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

		for (Report reports : this.reports) {
			taskCost += reports.getReportedTime() * reports.getCost();
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
	 */
	public void createTaskDependence(Task taskToEstablishDependenceUpon) {

		// Este if talvez estivesse melhor num controller...
		if (this.estimatedTaskStartDate.after(taskToEstablishDependenceUpon.getEstimatedTaskStartDate())) {
			this.taskDependency.add(taskToEstablishDependenceUpon);
		}
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
				if (!other.viewTaskStateName().equals("Finished") && !other.viewTaskStateName().equals("Cancelled")) {
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
		this.finishDate = null;
	}
}