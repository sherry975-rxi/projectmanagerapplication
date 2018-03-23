package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.US203GetUserStartedNotFinishedTaskListInIncreasingOrderController;
import project.model.Task;
import project.model.User;

import java.util.List;
import java.util.Scanner;

@Component
public class US203GetUnfinishedTaskUI {
	@Autowired
	private US203GetUserStartedNotFinishedTaskListInIncreasingOrderController unfinishedTaskByUser;

	@Autowired
	private TaskDetailsUI taskSelected;

	public void displayOptions(User user1) {
		boolean loop = true;
		while (loop) {
			loop = false;
		int projID;
		String[] split;
		User user = user1;
		int t;
		t = 0;
		Scanner scannerInput = new Scanner(System.in);

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();
			List<Task> taskList = unfinishedTaskByUser.getUserStartedNotFinishedTaskListInIncreasingOrder(user);

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

			if (taskList.size() == 0) {
				System.out.println("You have no unfinished tasks.");
			}
			for (int i = 0; i < taskList.size(); i++) {
			t = t + 1;
			System.out.println("["
					+ taskList.get(i).getTaskID() + "]" + " "
					+ taskList.get(i).getDescription());
		}
		System.out.println("___________________________________________________");
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {

		case "B":
			break;

		default:


			try {
				//split = option.split("\\.");
				//projID = Integer.valueOf(split[0]);


                taskSelected.setTaskID(option);
				//taskSelected.setProjectID(projID);
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
