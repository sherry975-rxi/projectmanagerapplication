package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.services.TaskService;
import project.model.Project;
import project.model.Task;

import java.util.ArrayList;
import java.util.List;

@Controller
public class US360GetProjectTasksWithoutCollaboratorsAssignedController {

	@Autowired
	private TaskService taskService;

	/*
	 * Default constructor
	 */
	public US360GetProjectTasksWithoutCollaboratorsAssignedController() {

	}

	/**
	 * this method return the list of not assigned tasks
	 * 
	 * @param proj
	 * @return
	 */
	public List<Task> getProjectNotAssigned(Project proj) {
		return taskService.getProjectTasksWithoutCollaboratorsAssigned(proj);
	}

	/**
	 * This methods gets all the not Assigned task and returns the Tasks with these
	 * conditions in the form of a List of Strings, with the taskId and Description
	 * of each task.
	 * 
	 * @param proj
	 *            The project to search for not started Tasks
	 * 
	 * @return Task List
	 */
	public List<String> getProjectNotAssignedTaskList(Project proj) {

		List<Task> taskListNotAssigned = taskService.getProjectTasksWithoutCollaboratorsAssigned(proj);
		List<String> taskListToPrint = new ArrayList<>();

		for (int i = 0; i < taskListNotAssigned.size(); i++) {

			String taskDescription = taskListNotAssigned.get(i).getDescription();
			String taskID = "[" + taskListNotAssigned.get(i).getTaskID() + "]";
			String taskIDandDescription = taskID + " " + taskDescription;
			taskListToPrint.add(taskIDandDescription);
		}

		return taskListToPrint;
	}

	/**
	 * This method splits a Sting by the space and only return the left part of the
	 * string until the first space
	 * 
	 * @param string
	 *            String to split
	 */
	public String splitStringByFirstSpace(String string) {

		String[] partsTask = string.split(" ");

		return partsTask[0];
	}

}
