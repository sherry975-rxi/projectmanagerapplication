package project.controller;

import project.model.Project;

public class CalculateReportedProjectCostController {

	/**
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

}
