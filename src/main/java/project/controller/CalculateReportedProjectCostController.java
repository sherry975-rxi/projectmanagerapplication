package project.controller;

import org.springframework.stereotype.Service;
import project.model.Project;
import project.model.Task;

import java.util.ArrayList;
import java.util.List;


public class CalculateReportedProjectCostController {

	/*
	 * 
	 * This controller allows a Project Manager to get the reported cost to the
	 * Project he is the manager of
	 *
	 */

	/**
	 * @param project
	 *            The project that the Project Manager wants to know the reported
	 *            cost of
	 * @return This method returns a double, which is the value that was reported on
	 *         that project
	 */
	public double calculateReportedProjectCostController(Project project) {

		return project.getTotalCostReportedToProjectUntilNow();

	}

	/**
	 * @param project
	 *            The project that the Project Manager wants to know the reported
	 *            cost of
	 * @return The cost reported to each task.
	 */

	public List<String> calculeReportedCostOfEachTaskController(Project project) {

		return new ArrayList<>(project.getTaskRepository().getReportedCostOfEachTask());
	}

	public List<String> getTaskId(Project project) {
		List<String> taskID = new ArrayList<>();

		for (Task other : project.getTaskRepository().getAllTasksfromProject()) {
			taskID.add(other.getTaskID());
		}

		return taskID;
	}

}
