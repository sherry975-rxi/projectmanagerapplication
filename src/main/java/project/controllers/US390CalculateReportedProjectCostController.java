package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class US390CalculateReportedProjectCostController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProjectService projectService;

	public US390CalculateReportedProjectCostController() {
		//Empty constructor created for JPA integration tests
	}

	/*
	 * 
	 * This controllers allows a Project Manager to get the reported cost to the
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

		return taskService.getTotalCostReportedToProjectUntilNow(project);

	}

	/**
	 * @param project
	 *            The project that the Project Manager wants to know the reported
	 *            cost of
	 * @return The cost reported to each task.
	 */

	public List<String> calculeReportedCostOfEachTaskController(Project project) {

		return new ArrayList<>(taskService.getReportedCostOfEachTask(project));
	}

	public List<String> getTaskId(Project project) {
		List<String> taskID = new ArrayList<>();

		for (Task other : taskService.getProjectTasks(project)) {
			taskID.add(other.getTaskID());
		}

		return taskID;
	}

    /**
     * @param project
     *            The project that the Project Manager wants to know the reported
     *            cost edited
     * @param chosenMethod
     *            The Project Class number assigned to each calculation method
     */
	public void selectReportCostCalculation(Project project, int chosenMethod) {
        project.setCalculationMethod(chosenMethod);

        projectService.saveProject(project);
        taskService.calculateReportEffortCost(project);
	}


}
