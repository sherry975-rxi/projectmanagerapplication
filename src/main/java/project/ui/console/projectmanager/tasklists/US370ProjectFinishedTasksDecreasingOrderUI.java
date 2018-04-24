package project.ui.console.projectmanager.tasklists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.PrintProjectInfoController;
import project.controllers.US367MarkFinishedTaskAsUnfinishedController;
import project.controllers.US370GetProjectFinishedTaskListController;
import project.model.Project;
import project.model.Task;

import java.util.List;
import java.util.Scanner;

@Component
public class US370ProjectFinishedTasksDecreasingOrderUI {

	@Autowired
	private PrintProjectInfoController projectInfo;

	@Autowired
	private US370GetProjectFinishedTaskListController projectFinishedTaskList;

	@Autowired
	private US367MarkFinishedTaskAsUnfinishedController taskToMarkAsUnfinished;

	private Project proj;

	public US370ProjectFinishedTasksDecreasingOrderUI() {
		//Empty constructor for JPA purposes
	}
	/**
	 * This method executes all options to execute through this UI Presents the
	 * project details and the task's list of project Uses a switch case to treat
	 * the user's input
	 */
	public void projectDataDisplay() {
		

		projectInfo.setProjID(proj.getIdCode());
		projectInfo.setProject();

		String line = "___________________________________________________";
		Scanner scannerInput = new Scanner(System.in);

        while (true) {

		System.out.println(":PROJECT: " + projectInfo.printProjectNameInfo().toUpperCase());
		System.out.println(line);
		System.out.println(":ID: " + projectInfo.printProjectIDCodeInfo());
		System.out.println(":STATUS: " + projectInfo.printProjectStatusInfo());
		System.out.println(":DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
		System.out.println(":START DATE: " + projectInfo.printProjectStartDateInfo());
		System.out.println(":FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
		System.out.println(":PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
		System.out.println(":PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
		System.out.println(":PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
		System.out.println(line);
		System.out.println("              :FINISHED TASKS:                 ");


		List<Task> finishedTasksDecreasingOrder = projectFinishedTaskList.getFinishedTasksInDescreasingOrder(this.proj);

		for (int i = 0; i < finishedTasksDecreasingOrder.size(); i++) {
			System.out.println("[" + finishedTasksDecreasingOrder.get(i).getTaskID() + "] "
					+ finishedTasksDecreasingOrder.get(i).getDescription());
		}

		System.out.println("To roll back a task from Finish back to Ongoing, choose the task ID number.");
            System.out.println();
		System.out.println("[B] Back \n");

		String choice = scannerInput.nextLine().toUpperCase();
		if (choice == "B") {
			break;
		}

		else{
			try {
				taskToMarkAsUnfinished.markFinishedTaskAsUnfinished(choice);
			}

			catch (NullPointerException npe) {
                Logger log = LoggerFactory.getLogger(US367MarkFinishedTaskAsUnfinishedController.class);
                log.error("There is no task with the number provided.", npe);
				System.out.println("Please choose a valid option: ");
                System.out.println();
			}
		}
	}
	}

	public void setProj(Project proj) {
		this.proj = proj;
	}

}



// Integer projectID
// Display list
// 1.Choose task to mark as not Finished US 367
