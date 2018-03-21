package project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import project.Services.TaskService;
import project.model.Project;
import project.model.Task;

/**
 * This controller is used to respond to US380 (Como Gestor de projeto, quero
 * obter uma lista de tarefas não concluídas e com data de conclusão vencida.
 * Consideram-se também as tarefas não iniciadas e cuja data prevista de)
 * 
 * @author Group3
 *
 */

@Controller
public class US380GetProjectExpiredTaskListController {

	@Autowired
	private TaskService taskService;

	/**
	 * This methods gets all the unfinished tasks with expired deadline and returns
	 * the Tasks with these conditions in the form of a List of Strings, with the
	 * taskId and Description of each task.
	 * 
	 * @param proj
	 *            The project to search for its expired Tasks
	 * 
	 * @return Task List
	 */
	public List<String> getUnfinishedTaskListWithExpiredDeadline(Project proj) {

		List<Task> taskListExpDeadline = taskService.getProjectExpiredTasks(proj);
		List<String> taskListToPrint = new ArrayList<>();

		for (int i = 0; i < taskListExpDeadline.size(); i++) {

			String taskDescription = taskListExpDeadline.get(i).getDescription();
			String taskID = "[" + taskListExpDeadline.get(i).getTaskID() + "]";
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
