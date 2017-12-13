package main.project.model;

import java.util.Calendar;

public class Report {

	private int reportedTime;
	private TaskWorker taskWorker;

	public Report(TaskWorker taskWorker) {

		this.reportedTime = 0;
		this.taskWorker = taskWorker;
	}
	
	public void setReportedTime( int time) {
		this.reportedTime = time;
		
	}
	
	public int getReportedTime() {
		return this.reportedTime;
	}

}
