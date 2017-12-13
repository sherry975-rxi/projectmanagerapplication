package main.project.model;

public class Report {

	int reportedTime;
	TaskWorker taskWorker;

	public Report(int time, TaskWorker taskWorker) {

		this.reportedTime = time;
		this.taskWorker = taskWorker;
	}

}
