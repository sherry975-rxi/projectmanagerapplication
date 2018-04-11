package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US203GetUserStartedNotFinishedTaskListInIncreasingOrderController;
import project.model.Task;
import project.model.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class US203GetUnfinishedTaskUI {
	@Autowired
	private US203GetUserStartedNotFinishedTaskListInIncreasingOrderController unfinishedTaskByUser;

	@Autowired
	private TaskDetailsUI taskSelected;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

	public void displayOptions(User user1) {
		boolean loop = true;
		while (loop) {
			loop = false;

		User user = user1;
		int t;
		t = 0;
		Scanner scannerInput = new Scanner(System.in);

		String myName = user.getName();
		String function = user.getFunction().toUpperCase();
			List<Task> taskList = unfinishedTaskByUser.getUserStartedNotFinishedTaskListInIncreasingOrder(user);

		System.out.println("\n" + myName + " \n" + function);
		System.out.println("___________________________________________________");

			if (taskList.isEmpty()) {
				System.out.println("You have no unfinished tasks.");
			}
			for (int i = 0; i < taskList.size(); i++) {
			t = t + 1;
				System.out.println("\n");
				System.out.println("TASK DESCRIPTION: " + taskList.get(i).getDescription() + ".........................[TASK ID] " + taskList.get(i).getTaskID());
				System.out.println("        --> [Estimated Start Date ]: " + this.dateFormat.format(taskList.get(i).getEstimatedTaskStartDate().getTime()));
				System.out.println("        --> [Estimated Finish Date]: " + this.dateFormat.format(taskList.get(i).getStartDate().getTime()));
				System.out.println("        --> [Start Date           ]: " + this.dateFormat.format(taskList.get(i).getEstimatedTaskStartDate().getTime()));
				System.out.println("        --> [Current State        ]: " + taskList.get(i).getCurrentState());
				System.out.println("\n");
			}
		System.out.println("___________________________________________________");
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {

		case "B":
			break;

		default:


			try {


               taskSelected.setTaskID(option);

				taskSelected.setUser(user);
				taskSelected.taskDataDisplay();
			}

			catch (NullPointerException npe) {
				System.out.println("Please choose a valid option: ");
				System.out.println("");
				loop = true;
			}

			break;
		}
	}
}
}
