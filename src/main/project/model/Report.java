package main.project.model;

public class Report {

	private int reportedTime;
	private TaskWorker taskWorker;

	public Report(int time, TaskWorker taskWorker) {

		this.reportedTime = time;
		this.taskWorker = taskWorker;
	}

}
