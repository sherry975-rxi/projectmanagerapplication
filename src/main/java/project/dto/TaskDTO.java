package project.dto;

import org.springframework.hateoas.ResourceSupport;
import project.model.*;
import project.model.taskstateinterface.Created;
import project.model.taskstateinterface.TaskStateInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class TaskDTO extends ResourceSupport {


    private String taskID;
    private String description;
    private List<TaskCollaborator> taskTeam;
    private List<Report> reports;
    private StateEnum currentState;
    private List<TaskTeamRequest> pendingTaskTeamRequests;
    private Calendar creationDate;
    private Calendar startDate;
    private Calendar finishDate;
    private TaskStateInterface taskState;
    private double estimatedTaskEffort;
    private Calendar estimatedTaskStartDate;
    private Calendar taskDeadline;
    private double taskBudget;
    private List<Task> taskDependency;
    private Integer startDateInterval;
    private Integer deadlineInterval;
    private Calendar cancelDate;
    private Project project;
    private List<String> actions;


    /**
     * Empty Constructor
     */
    private TaskDTO() {

    }

    /**
     * Instantiates a taskDTO from another task
     *
     * @param task
     */
    public TaskDTO(Task task) {

        this.description = task.getDescription();
        this.creationDate = task.getCreationDate();
        this.startDate = task.getStartDate();
        this.finishDate = task.getFinishDate();
        this.taskState = task.getTaskState();
        this.taskTeam = task.getTaskTeam();
        this.reports = task.getReports();
        this.estimatedTaskEffort = task.getEstimatedTaskEffort();
        this.estimatedTaskStartDate = task.getEstimatedTaskStartDate();
        this.taskDeadline = task.getTaskDeadline();
        this.taskBudget = task.getTaskBudget();
        this.taskDependency = task.getTaskDependency();
        this.taskState = task.getTaskState();
        this.startDateInterval = task.getStartDateInterval();
        this.deadlineInterval = task.getDeadlineInterval();
        this.currentState = StateEnum.CREATED;
        this.project = task.getProject();
        this.pendingTaskTeamRequests = task.getPendingTaskTeamRequests();
        this.actions = new ArrayList<>();
    }



    /**
     * Creates a taskDTO with the same parameters used to create a Task
     *
     * @param description
     *            Description of the task set by the user
     * @param selectedProject
     *            Id of the project to which the task belongs to
     *
     */
    public TaskDTO(String description, Project selectedProject) {
        this.description = description;
        this.creationDate = Calendar.getInstance();
        this.startDate = null;
        this.finishDate = null;
        this.taskTeam = new ArrayList<>();
        this.reports = new ArrayList<>();
        this.estimatedTaskEffort = 0;
        this.estimatedTaskStartDate = null;
        this.taskDeadline = null;
        this.taskBudget = 0;
        this.startDateInterval = null;
        this.deadlineInterval = null;
        this.taskDependency = new ArrayList<>();
        this.taskState = new Created();
        this.cancelDate = null;
        this.currentState = StateEnum.CREATED;
        this.project = selectedProject;
        this.pendingTaskTeamRequests = new ArrayList<>();
        this.actions = new ArrayList<>();
    }


    /**
     * Creates a taskDTO with the same parameters used to create a Task
     *
     */
    public TaskDTO(String description, int estimatedTaskEffort, Calendar estimatedTaskStartDate, Calendar taskDeadline,
                int estimatedBudgetCostTask) {

        this.description = description;
        this.creationDate = Calendar.getInstance();
        this.startDate = null;
        this.finishDate = null;
        this.taskTeam = new ArrayList<>();
        this.reports = new ArrayList<>();
        this.estimatedTaskEffort = estimatedTaskEffort;
        this.estimatedTaskStartDate = estimatedTaskStartDate;
        this.taskDeadline = taskDeadline;
        this.taskBudget = estimatedBudgetCostTask;
        this.startDateInterval = null;
        this.deadlineInterval = null;
        this.taskDependency = new ArrayList<>();
        this.taskState = new Created();
        this.currentState = StateEnum.CREATED;
        this.pendingTaskTeamRequests = new ArrayList<>();
        this.actions = new ArrayList<>();
    }


    public String getTaskID() {
        return taskID;
    }

    public String getDescription() {
        return description;
    }

    public List<TaskCollaborator> getTaskTeam() {
        return taskTeam;
    }

    public List<Report> getReports() {
        return reports;
    }

    public StateEnum getCurrentState() {
        return currentState;
    }

    public List<TaskTeamRequest> getPendingTaskTeamRequests() {
        return pendingTaskTeamRequests;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public TaskStateInterface getTaskState() {
        return taskState;
    }

    public double getEstimatedTaskEffort() {
        return estimatedTaskEffort;
    }

    public Calendar getEstimatedTaskStartDate() {
        return estimatedTaskStartDate;
    }

    public Calendar getTaskDeadline() {
        return taskDeadline;
    }

    public double getTaskBudget() {
        return taskBudget;
    }

    public List<Task> getTaskDependency() {
        return taskDependency;
    }

    public Integer getStartDateInterval() {
        return startDateInterval;
    }

    public Integer getDeadlineInterval() {
        return deadlineInterval;
    }

    public Calendar getCancelDate() {
        return cancelDate;
    }

    public Project getProject() {
        return project;
    }

    public List<String> getActions() {
        return actions;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TaskDTO taskDTO = (TaskDTO) o;
        return Objects.equals(taskID, taskDTO.taskID);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), taskID);
    }
}
