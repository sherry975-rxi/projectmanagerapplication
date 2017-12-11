package main.java.project.model;

//
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Class that allows building and accessing Task attributes.
 * 
 * @author Group 3
 *
 */

public class Task {

	private String taskID;
	private String description;
	private List<TaskWorker> taskTeam;
	private Calendar creationDate;
	private Calendar startDate;
	private Calendar finishDate;
	private boolean taskState;
	private int estimatedTaskEffort;
	private Calendar estimatedTaskStartDate;
	private Calendar taskDeadline;
	private int estimatedBudgetCostTask;

	/**
	 * This Constructor creates a Task object with the mandatory parameters taskID
	 * and description and non mandatory parameters creation date, start date,
	 * finish date, task state (finished or unfinished) and task team
	 * 
	 * @param taskCounter
	 * @param projId
	 * @param description
	 * 
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
		this.taskState = false;
		this.taskTeam = new ArrayList<TaskWorker>();
		this.estimatedTaskEffort = estimatedTaskEffort;
		this.estimatedTaskStartDate = estimatedTaskStartDate;
		this.taskDeadline = taskDeadline;
		this.estimatedBudgetCostTask = estimatedBudgetCostTask;

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
		this.taskState = task.isFinished();
		this.taskTeam = task.copyListOfUsersInTask(this.taskTeam);
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
	 * This method when called returns the Estimated Task Start Date
	 * 
	 * @return estimatedTaskStartDate
	 */
	public Calendar getEstimatedTaskStartDate() {
		return this.estimatedTaskStartDate;
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
	 * This method when called returns the task Dead line
	 * 
	 * @return taskDeadline
	 */
	public Calendar getTaskDeadline() {
		return this.taskDeadline;
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
	public int getEstimatedBudgetCostTask() {
		return this.estimatedBudgetCostTask;
	}

	/**
	 * This method when called update the estimated Budget Cost Task
	 * 
	 * @param newEstimatedBudgetCostTask
	 */
	public void setEstimatedBudgetCostTask(int newEstimatedBudgetCostTask) {
		this.estimatedBudgetCostTask = newEstimatedBudgetCostTask;
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
		this.startDate = (Calendar) c.clone();
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
	public void setFinishDate(Calendar c) {
		this.finishDate = (Calendar) c.clone();
	}

	/**
	 * This method gets the list of assigned users to a certain Task
	 * 
	 * @return taskTeam List of assigned users to a certain Task
	 */
	public List<TaskWorker> getTaskTeam() {
		return taskTeam;
	}

	/**
	 * This method returns the task state.
	 * 
	 * @return TRUE if finished FALSE if unfinished
	 */
	public boolean isFinished() {
		return this.taskState;
	}

	/**
	 * This method changes the task state to finished by altering the task boolean
	 * to TRUE
	 * 
	 */
	public void markTaskAsFinished() {
		this.taskState = true;
	}

	/**
	 * This method checks if the user is missing from the task team (List of users
	 * in Task), and if it is missing from the list, the user is added to the team.
	 * If it is already already added to the the list it is reactivated
	 * 
	 * @param user
	 *            User to add to users list
	 * 
	 * 
	 */
	public void addUserToTask(ProjectCollaborator user) {
		if (!taskTeamContainsUser(user)) {
			this.taskTeam.add(new TaskWorker(user));
		}

		else if (taskTeamUserIsActive(user)) {
			for (TaskWorker other : taskTeam) {
				if (other.getTaskWorker().equals(user.getCollaboratorUserData())) {
					other.addCostForTaskWorker(user.getCollaboratorCost());
					other.addHoursSpentForTaskWorker();
					other.addStartDateForTaskWorker();
				}
			}

		}

	}

	/**
	 * This method removes the user from a task. It checks first if the user is in
	 * the task team (List of users in Task), and deactivates it from the team.
	 * 
	 * @param user
	 *            User to remove from the list of users in a task
	 * 
	 */
	public void removeUserFromTask(User user) {
		for (TaskWorker other : taskTeam) {
			if (other.getTaskWorker().equals(user) && other.isTaskWorkerActiveInTask()) {
				other.addFinishDateForTaskWorker();
			}
		}
	}

	public int getTimeSpentOntask(ProjectCollaborator user) {

		for (TaskWorker other : taskTeam) {
			if (other.getTaskWorker().equals(user)) {

				return other.getHoursSpent();
			}
		}

		return 0;
	}

	// /**
	// * This method returns the total amount of time spent on a task. It takes in
	// * consideration the task startDate and finishDate, that a working day has 8h
	// * and that Sundays, Saturdays and Holidays are not considered for the
	// * calculation.
	// *
	// * @return intervalHoursFullDays + intervalHoursPartialDays Total hours of
	// work
	// * spent in a task
	// */
	// public double getTimeSpentOnTask() {
	//
	// int intervalDays = this.finishDate.get(Calendar.DAY_OF_YEAR) -
	// this.startDate.get(Calendar.DAY_OF_YEAR) - 1;
	// // 1 is subtracted to guarantee I only work with full days here.
	//
	// Calendar referenceDate = (Calendar) this.startDate.clone();
	//
	// // To make sure that the interval of dates doesn't consider Saturdays,
	// Sundays
	// // and holidays. Subtract 24 hours for each weekend day and holiday
	// while (referenceDate.before(this.finishDate)) {
	// if (referenceDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
	// || referenceDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
	// isItHoliday(referenceDate)) {
	// intervalDays = intervalDays - 1;
	// }
	// referenceDate.add(Calendar.DAY_OF_YEAR, 1);// TODO NEED DOUBLE CHECK because
	// it may fail while changing
	// // year
	// }
	//
	// int intervalHoursFullDays = intervalDays * 8; // find useful hours (8)
	//
	// // Sets start working hour of the day to 9 am
	// Calendar startHour = (Calendar) this.finishDate.clone();
	// startHour.set(Calendar.HOUR_OF_DAY, 9);
	// startHour.set(Calendar.MINUTE, 0);
	// startHour.set(Calendar.SECOND, 0);
	//
	// // Sets finish working hour of the day to 6 pm
	// Calendar finishHour = (Calendar) this.startDate.clone();
	// finishHour.set(Calendar.HOUR_OF_DAY, 18);
	// finishHour.set(Calendar.MINUTE, 0);
	// finishHour.set(Calendar.SECOND, 0);
	//
	// double intervalHoursFirstDay = (double) (finishHour.getTimeInMillis() -
	// this.startDate.getTimeInMillis());
	// intervalHoursFirstDay = intervalHoursFirstDay / 3600000;
	// if (intervalHoursFirstDay >= 5) {
	// intervalHoursFirstDay--;
	// }
	//
	// double intervalHoursLastDay = (this.finishDate.getTimeInMillis() -
	// startHour.getTimeInMillis());
	// intervalHoursLastDay = intervalHoursLastDay / 3600000;
	// if (intervalHoursLastDay >= 5) {
	// intervalHoursLastDay--;
	// }
	// double intervalHoursPartialDays = intervalHoursFirstDay +
	// intervalHoursLastDay; // partial work hours in
	// // first and last days of
	// // work
	//
	// return intervalHoursFullDays + intervalHoursPartialDays; // total hours
	// working days
	// }

	/**
	 * This is a private method that checks if a date is a holiday (non working day)
	 * 
	 * @param date
	 * 
	 * @return TRUE if the day is a holiday FALSE if the day is not a holiday
	 */
	private boolean isItHoliday(Calendar date) {
		int currentDay = date.get(Calendar.DAY_OF_MONTH);
		int currentMonth = date.get(Calendar.MONTH);
		int currentYear = date.get(Calendar.YEAR);
		int[] specialDays = getSpecialHolidaysDate(currentYear);

		int dayEaster = specialDays[1];
		int monthEaster = specialDays[0];

		int dayCarnival = specialDays[3];
		int monthCarnival = specialDays[2];

		int dayCorpusChrist = specialDays[5];
		int monthCorpusChrist = specialDays[4];

		int dayHolyFriday = specialDays[7];
		int monthHolyFriday = specialDays[6];

		switch (currentDay) {
		case 1:
			switch (currentMonth) {
			case 0:
				return true;
			case 10:
				return true;
			case 11:
				return true;
			}

		case 25:
			switch (currentMonth) {
			case 3:
				return true;
			case 11:
				return true;

			}

		case 10:
			switch (currentMonth) {
			case 5:
				return true;
			}

		case 15:
			switch (currentMonth) {
			case 7:
				return true;
			}

		case 5:
			switch (currentMonth) {
			case 9:
				return true;
			}

		case 8:
			switch (currentMonth) {
			case 11:
				return true;
			}
		}

		if (currentDay == dayEaster && currentMonth == monthEaster)
			return true; // Feriado Páscoa - Feriado Móvel
		if (currentDay == dayCarnival && currentMonth == monthCarnival)
			return true; // Feriado Carnaval - Feriado Móvel
		if (currentDay == dayCorpusChrist && currentMonth == monthCorpusChrist)
			return true; // Corpo Cristo - Feriado Móvel
		if (currentDay == dayHolyFriday && currentMonth == monthHolyFriday)
			return true; // Sexta-Feira Santa - Feriado Móvel

		return false;
	}

	private int[] getSpecialHolidaysDate(int year) {

		int[] specialHolidays = new int[8];

		GregorianCalendar easterDate = new GregorianCalendar();
		GregorianCalendar carnivalDate = new GregorianCalendar();
		GregorianCalendar corpusChristDate = new GregorianCalendar();
		GregorianCalendar holyFridayDate = new GregorianCalendar();

		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		int month = (h + l - 7 * m + 114) / 31;
		int day = ((h + l - 7 * m + 114) % 31) + 1;

		easterDate.set(Calendar.MONTH, month - 1);
		easterDate.set(Calendar.DAY_OF_MONTH, day);

		specialHolidays[0] = easterDate.get(Calendar.MONTH);
		specialHolidays[1] = easterDate.get(Calendar.DAY_OF_MONTH);

		// Carnival - 47 Days Before Easter
		carnivalDate.setTimeInMillis(easterDate.getTimeInMillis());
		if (k != 0) {
			carnivalDate.add(Calendar.DAY_OF_MONTH, -47);
		} else {
			carnivalDate.add(Calendar.DAY_OF_MONTH, -46);
		}
		specialHolidays[2] = carnivalDate.get(Calendar.MONTH);
		specialHolidays[3] = carnivalDate.get(Calendar.DAY_OF_MONTH);

		// CorpusChristi 60 days after Easter
		corpusChristDate.setTimeInMillis(easterDate.getTimeInMillis());
		corpusChristDate.add(Calendar.DAY_OF_MONTH, 60);
		specialHolidays[4] = corpusChristDate.get(Calendar.MONTH);
		specialHolidays[5] = corpusChristDate.get(Calendar.DAY_OF_MONTH);

		holyFridayDate.setTimeInMillis(easterDate.getTimeInMillis());
		holyFridayDate.add(Calendar.DAY_OF_MONTH, -2);
		specialHolidays[6] = holyFridayDate.get(Calendar.MONTH);
		specialHolidays[7] = holyFridayDate.get(Calendar.DAY_OF_MONTH);

		return specialHolidays;
	}

	/**
	 * This method overrides the equals to be able to compare tasks (two tasks are
	 * equal id the taskID is the same)
	 * 
	 * @return TRUE if the tasks are equal FALSE if the task is not equal
	 */

	@Override
	public boolean equals(Object other) {
		if (other instanceof Task) {
			Task t1 = (Task) other;
			if (t1.taskID.equals(this.taskID)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This PRIVATE method checks if a user is already on the task team.
	 * 
	 * @param user
	 *            User to check
	 * @return True if task team contains user, FALSE if the task team does not have
	 *         the user to check
	 */
	public boolean taskTeamContainsUser(ProjectCollaborator user) {
		for (TaskWorker other : taskTeam) {
			if (other.getTaskWorker().equals(user.getCollaboratorUserData())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This PRIVATE method checks if a user is active on the task team.
	 * 
	 * @param user
	 *            User to check
	 * @return True if task team user is active, FALSE if the task team does not
	 *         have the user active
	 */
	public boolean taskTeamUserIsActive(ProjectCollaborator user) {
		for (TaskWorker other : taskTeam) {
			if (other.getTaskWorker().equals(user.getCollaboratorUserData())) {
				if (other.isTaskWorkerActiveInTask()) {
					return true;
				}

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
	public List<TaskWorker> copyListOfUsersInTask(List<TaskWorker> emptyListOfUsersInTask) {

		emptyListOfUsersInTask = new ArrayList<TaskWorker>();

		for (int iUser = 0; iUser < this.getTaskTeam().size(); iUser++) {
			emptyListOfUsersInTask.add(this.getTaskTeam().get(iUser));
		}

		return emptyListOfUsersInTask;
	}

}