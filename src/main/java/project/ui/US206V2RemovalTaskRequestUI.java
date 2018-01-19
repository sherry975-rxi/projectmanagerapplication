package project.ui;

import java.util.List;
import java.util.Scanner;

import project.controller.US206V2RemovalTaskRequestController;
import project.model.User;

public class US206V2RemovalTaskRequestUI {

	User user;
	List<String> userTasks;

	/**
	 * Constructor to instantiate a new CollaboratorRemovalrequestUI
	 * 
	 * @param user
	 *            User that asks for the removal
	 * @param projectID
	 *            Project ID of project where the task is
	 * @param taskID
	 *            Task ID of the task that the user wants to be removed from
	 */
	public US206V2RemovalTaskRequestUI(User user) {
		this.user = user;
	}

	/**
	 * This method displays the user unfinished tasks in order to the user choose a
	 * task.
	 * 
	 */
	public void displayTasksFromUser() {

		String myname = user.getName();
		String function = user.getFunction().toUpperCase();

		System.out.println("\n" + myname + " \n" + function);
		System.out.println("___________________________________________________");
		System.out.println("\n");

		US206V2RemovalTaskRequestController controller = new US206V2RemovalTaskRequestController(this.user);
		userTasks = controller.getUnfinishedTaskListFromUser();
		for (int taskIndex = 0; taskIndex < userTasks.size(); taskIndex++) {
			System.out.println(userTasks.get(taskIndex));
		}

		System.out.println("\n");
		System.out.println("Please choose a task: \n");
		System.out.println("___________________________________________________");

		this.chooseATask();

	}

	/**
	 * This method allows a user to choose a task and confirms if user is certain of
	 * its action. Also, if the deleted did not work, it asks for a new ID.
	 * 
	 * 
	 */
	public void chooseATask() {

		Scanner input = new Scanner(System.in);
		String taskID = input.nextLine();

		System.out.println("Are you sure you want to delete this task? \n");
		System.out.println("[Y] to continue \n");
		System.out.println("[N] to cancel \n");

		String yerOrNo = input.nextLine();

		if (yerOrNo.equalsIgnoreCase("y")) {
			if (createRequest(taskID) == true) {
				System.out.println("Your task removal is pending Project Manager approval");
				backToMenu();
			} else {
				System.out.println("[ERROR: Task not found!]" + "\n" + "Please choose a valid Task");
				this.displayTasksFromUser();
				this.backToMenu();

			}
		}

		else {
			this.displayTasksFromUser();
			this.backToMenu();
		}
	}

	/**
	 * This method instantiates a new US206V2RemovaltaskRequestController and
	 * creates the actual request. This method is called by the chooseTask method.
	 * 
	 * @param taskID
	 *            Input of the user
	 * 
	 * @return TRUE if the request was created FALSE if not
	 */
	public boolean createRequest(String taskID) {
		US206V2RemovalTaskRequestController controller = new US206V2RemovalTaskRequestController(this.user);
		controller.setTaskID(taskID);
		controller.setProjectIDFromTaskID(taskID);
		return controller.createRequest();
	}

	/**
	 * This method allows the user to go Back, go to the Main Menu or Exit, after a
	 * successfully deleted task.
	 */
	public void backToMenu() {

		Scanner scannerInput = new Scanner(System.in);
		String option = scannerInput.nextLine().toUpperCase();

		System.out.println("___________________________________________________");
		System.out.println("[B] Back");
		System.out.println("[M] MainMenu");
		System.out.println("[E] Exit \n");

		switch (option) {
		case "B":
			// Menu3 menuThree = new Menu3();
			// // TODO when this menu is done is necessary to include a method.
			break;
		case "M":
			MainMenuUI.mainMenu();

			break;
		case "E":
			System.exit(0);
		}
	}
}
