package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.Task;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class US203GetUserStartedNotFinishedTaskListInIncreasingOrderController {

	@Autowired
	public TaskService taskService;

	public US203GetUserStartedNotFinishedTaskListInIncreasingOrderController() {

	}

	/**
	 * This method returns all the started not finished Tasks the User has. -
	 * US203v2
	 * 
	 * @param myUser
	 *            The User to search for it's unfinished tasks
	 * @return Task List
	 */
	public List<Task> getUserStartedNotFinishedTaskListInIncreasingOrder(User myUser) {

		List<Task> myUnfinishedAllTasks = taskService.getStartedNotFinishedUserTaskList(myUser);
		List<Task> myUnfinishedTasksWDeadline = new ArrayList<>();
		List<Task> myUnfinishedAllTasksWODeadline = new ArrayList<>();

		for (Task other : myUnfinishedAllTasks) {
			if (other.getTaskDeadline() != null) {
				myUnfinishedTasksWDeadline.add(other);
			} else {
				myUnfinishedAllTasksWODeadline.add(other);
			}
		}
		myUnfinishedAllTasks = taskService.sortTaskListByDeadline(myUnfinishedTasksWDeadline);
		myUnfinishedAllTasks.addAll(myUnfinishedAllTasksWODeadline);
		return myUnfinishedAllTasks;
	}

}
