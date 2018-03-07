package project.controller;

import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class US207CreateTaskReportController {

    private User username;
    private UserRepository userRepository;
    private Company company;
    private ProjectRepository projectRepository;
    private String email;
    private Task task;

    /**
     * Constructor of US207CreateTaskReportController
     *
     * @param email The email of the user that will create a task report
     */
    public US207CreateTaskReportController(String email, String taskID) {
        this.company = Company.getTheInstance();
        this.projectRepository = company.getProjectsRepository();
        this.userRepository = company.getUsersRepository();
        this.username = userRepository.getUserByEmail(email);
        this.email = email;
        for (Task other : projectRepository.getUserTasks(username)) {
            if (other.getTaskID().equals(taskID)) {
                task = other;
            }
        }

    }


    /**
     * This method returns a TaskCollaborator by its email
     *
     * @return
     */
    public TaskCollaborator getTaskCollaboratorByEmail(String userEmail) {

        TaskCollaborator taskCollaboratorByEmail = null;

        for (TaskCollaborator other : this.task.getTaskTeam()) {
            if (other.getProjCollaborator().getUserFromProjectCollaborator().getEmail().equals(userEmail) && other.getFinishDate() == null) {
                taskCollaboratorByEmail = other;
            }
        }

        return taskCollaboratorByEmail;
    }


    /**
     * This method creates or updates a Report with a given time
     *
     * @param timeToReport The time associated to the report
     * @return TRUE if the report is created, FALSE if not
     */
    public boolean createReportController(double timeToReport, Calendar dateOfReport) {

        boolean wasReportCreated = false;

        wasReportCreated = task.createReport(task.getTaskCollaboratorByEmail(email), dateOfReport, timeToReport);

        return wasReportCreated;

    }

    /**
     * This method gets the report Index of a given user
     *
     * @return A List with the index of the reports of a given taskCollaborator
     */
    public List<Integer> getReportsIndexByGivenUser() {

        List<Integer> reportsOfGivenUser;

        reportsOfGivenUser = task.getReportsIndexOfTaskCollaborator(username.getEmail());

        return reportsOfGivenUser;

    }


    /**
     * @param newReportTime    The update reported time
     * @param taskCollaborator The TaskCollaborator associated to the Report
     * @param reportToChange   The report index to change
     * @return TRUE if the report was updated, FALSE if not
     */
    public boolean updateTaskReport(double newReportTime, TaskCollaborator taskCollaborator, Integer reportToChange) {
        Boolean wasReportUpdated = false;
        wasReportUpdated = task.updateReportedTime(newReportTime, taskCollaborator, reportToChange);

        return wasReportUpdated;

    }

    /**
     * This method returns the dates of when the reports were created
     *
     * @param taskCollaborator The TaskCollaborator to search its reports for
     * @return A List with the dates of the report creation dates
     */
    public List<Calendar> getReportsCreationDateByGivenUser(TaskCollaborator taskCollaborator) {

        List<Calendar> reportsDate = new ArrayList<>();
        for (Report other : this.task.getReports()) {
            if (other.getTaskCollaborator().equals(taskCollaborator)) {
                reportsDate.add(other.getDateOfReport());
            }
        }

        return reportsDate;
    }


    /**
     * This method returns the dates of when the reports were updated
     *
     * @param taskCollaborator The TaskCollaborator to search its reports for
     * @return A List with the dates of the report update dates
     */
    public List<Calendar> getReportsUpdateDateByGivenUser(TaskCollaborator taskCollaborator) {

        List<Calendar> reportsDate = new ArrayList<>();
        for (Report other : this.task.getReports()) {
            if (other.getTaskCollaborator().equals(taskCollaborator)) {
                reportsDate.add(other.getDateOfUpdate());
            }
        }

        return reportsDate;
    }

    /**
     * This method returns the reported times of a given TaskCollaborator
     *
     * @param taskCollaborator The TaskCollaborator to search its reports for
     * @return A List with the reported times in the reports by a given user
     */
    public List<Double> getReportTimeByGivenUser(TaskCollaborator taskCollaborator) {

        List<Double> reportsTime = new ArrayList<>();
        for (Report other : this.task.getReports()) {
            if (other.getTaskCollaborator().equals(taskCollaborator)) {
                reportsTime.add(other.getReportedTime());
            }
        }

        return reportsTime;
    }
}
