package project.controller;

import project.model.Project;
import project.model.ProjectContainer;
import project.model.Task;

import java.util.ArrayList;
import java.util.List;

public class US360GetProjectTasksWithoutCollaboratorsAssignedController {

	ProjectContainer myProjRepo;

	public US360GetProjectTasksWithoutCollaboratorsAssignedController() {
		this.myProjRepo = Company.getTheInstance().getProjectsContainer();
	}

	/**
	 * this method return the list of not assigned tasks
	 * 
	 * @param proj
	 * @return
	 */
	public List<Task> getProjectNotAssigned(Project proj) {
		return proj.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned();
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

		List<Task> taskListNotAssigned = proj.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned();
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
