package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controller.US203GetUserStartedNotFinishedTaskListInIncreasingOrderController;
import project.model.User;

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

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");

		for (int i = 0; i < unfinishedTaskByUser.getUserStartedNotFinishedTaskListInIncreasingOrder(user).size(); i++) {
			t = t + 1;
			System.out.println("["
					+ unfinishedTaskByUser.getUserStartedNotFinishedTaskListInIncreasingOrder(user).get(i).getTaskID() + "]" + " "
					+ unfinishedTaskByUser.getUserStartedNotFinishedTaskListInIncreasingOrder(user).get(i).getDescription());
		}
		System.out.println("___________________________________________________");
		System.out.println("[B] Back \n");

		String option = scannerInput.nextLine().toUpperCase();

		switch (option) {

		case "B":
			break;

		default:


			try {
				split = option.split("\\.");
				projID = Integer.valueOf(split[0]);

				taskSelected.setProjectID(projID);
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
