package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US207CreateTaskReportController;
import project.model.TaskCollaborator;

import java.util.Calendar;
import java.util.Scanner;

@Component
public class US207And208CreateOrUpdateTaskReportUI {

    @Autowired
    private US207CreateTaskReportController createUpdateReportController;

    private TaskCollaborator taskCollaborator;


    public US207And208CreateOrUpdateTaskReportUI() {
        //Creation of an empty constructor for the interface
    }


    public void createReport() {


        Scanner input = new Scanner(System.in);

        boolean loop = true;
        while (loop) {
            loop = false;
            System.out.println("______________________________________________");
            System.out.println("");

            for (int i = 0; i < createUpdateReportController.getReportsCreationDateByGivenUser(taskCollaborator).size(); i++) {
                System.out.println("Report Index:    " + createUpdateReportController.getReportsIndexByGivenUser().get(i));
                System.out.println("");
                System.out.println("Report Date:     " + createUpdateReportController.getReportsCreationDateByGivenUser(taskCollaborator).get(i).toString());
                System.out.println("Reported time:   " + createUpdateReportController.getReportTimeByGivenUser(taskCollaborator).get(i));
                System.out.println("");
                System.out.println("______________________________________________");
                System.out.println("");
            }

            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("[1] Update Task Report");
            System.out.println("[2] Create New Report");


            String choice = input.nextLine().toUpperCase();
            System.out.println();
            switch (choice) {
                case "1":
                    input = caseOne(input);
                    break;
                case "2":
                    input = caseTwo(input);
                    break;
                default:
                    System.out.println("Choose a valid option:");
                    loop = true;


            }
        }

    }

    /**
     * Case one allows the user to select a task Report and update teh reported time
     *
     * @param input
     * @return
     */
    public Scanner caseOne(Scanner input) {
        System.out.println("");
        System.out.println("Select a Task index to update it's report:");
        System.out.println("");

        if (input.hasNextInt()) {
            int taskReportIndex = input.nextInt();
            if (this.createUpdateReportController.getReportsIndexByGivenUser().contains(taskReportIndex)) {
                System.out.println("Type a new report time");
                if (input.hasNextDouble()) {
                    double updatedReportTime = input.nextDouble();
                    this.createUpdateReportController.updateTaskReport(updatedReportTime, taskCollaborator, taskReportIndex);
                } else {
                    System.out.println("Not a valid number");
                }

            } else {
                System.out.println("The Report index typed doesn't belong to the user");
            }

        } else {
            System.out.println("Not a valid number!");
        }

        return input;
    }

    /**
     * Case two allows a user to create a new Task report
     *
     * @param input
     * @return
     */
    public Scanner caseTwo(Scanner input) {
        System.out.println("Create a new Task Report");
        System.out.println();
        System.out.println("Type a report time:");

        if (input.hasNextDouble()) {
            Double taskReportTime = input.nextDouble();

            if (this.createUpdateReportController.createReportController(taskReportTime, Calendar.getInstance())) {
                System.out.println("The Report was successfully created");
            } else {
                System.out.println("The Report couldn't be created");
            }

        } else {
            System.out.println("Not a valid number!");
        }

        return input;
    }

    public void setTaskCollaboratorThroughEmail(String email) {
        this.taskCollaborator = createUpdateReportController.getTaskCollaboratorByEmail(email);
    }
}
