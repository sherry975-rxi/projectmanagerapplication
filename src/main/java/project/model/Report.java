package project.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * This Class Stores Reported Information associated with a Task Collaborator
 *
 * @author Group3
 *
 */
@Entity
@Table(name = "Report")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "reportDbId", scope = Report.class)
public class Report extends ResourceSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIdentityReference(alwaysAsId = true)
    private int reportDbId;
	private double reportedTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TaskCollaborator_id")
	private TaskCollaborator taskCollaborator;

	private double cost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Task_id")
	@JsonIgnore
	private Task task;
	private Calendar firstDateOfReport;
	private Calendar dateOfUpdate;

	public Report(){

	}

	/**
	 * This method creates a report
     *
	 * @param taskCollaborator
	 *            Task Collaborator
	 */


	public Report(TaskCollaborator taskCollaborator, Calendar reportDate) {

		this.reportedTime = 0;
		this.taskCollaborator = taskCollaborator;
		this.cost = taskCollaborator.getCost();
		this.firstDateOfReport = reportDate;
		this.dateOfUpdate = reportDate;
	}

	/**
     * This method returns the reportDbId of the Report
     *
     * @return reportDbId
	 */
	public int getDbId() {
        return reportDbId;
	}

	/**
     * This method returns the cost of a taskCollaborator during a certain period
     *
     * @return cost
	 */
    public double getCost() {
        return cost;
	}

	/**
	 * This method returns the Task associated to the Report
	 *
	 * @return Task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * This method sets a Task to the Task attribute of the Report
	 *
	 * @return Void
	 */
	public void setTask(Task task) {
		this.task = task;
	}

    /**
     * Returns the Task Collaborator associated to this Report
     *
     * @return Task Collaborator
     */
    public TaskCollaborator getTaskCollaborator() {
        return this.taskCollaborator;
    }

	/**
     * This method returns the time that a Task Collaborator spent on a task
     *
     * @return Time spent on task by a Task Collaborator
	 */
    public double getReportedTime() {
        return this.reportedTime;
    }

	/**
	 * Sets the time that a a Task Collaborator spent on a task
	 *
	 * @param time Time spent on task
	 */
	public void updateReportedTime(double time) {
		this.reportedTime = time;
		this.dateOfUpdate = Calendar.getInstance();

	}

	/**
     * Sets the time that a a Task Collaborator spent on a task
     *
     * @param time
     *            Time spent on task
	 */
    public void setReportedTime(double time) {
        this.reportedTime = time;

    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), reportDbId);
    }

	/**
	 * Sets the Task Collaborator to the report
	 *
	 * @return void
	 */
	public void setTaskCollaborator(TaskCollaborator taskCollab) {
		this.taskCollaborator = taskCollab;

	}

	/**
	 * Sets a date to the report
	 *
	 * @return void
	 */
	public void setFirstDateOfReport(Calendar reportDate) {
		this.firstDateOfReport = reportDate;
	}


	/**
	 * Gets the date of the report
	 *
	 * @return void
	 */
	public Calendar getFirstDateOfReport() {
		return this.firstDateOfReport;
	}

	/**
	 * Sets a cost to the Report
	 *
	 * @return void
	 */

	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Gets the date of when the Report was last updated
	 *
	 * @return LocalDate
	 */
	public Calendar getDateOfUpdate() {
		return dateOfUpdate;
	}

	/**
	 * Sets a date to the report
	 *
	 * @return LocalDate
	 */
	public void setDateOfUpdate(Calendar dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
    }

    public int getReportDbId() {
        return reportDbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Report report = (Report) o;
        return Objects.equals(taskCollaborator, report.taskCollaborator) &&
                Objects.equals(task, report.task);
    }

    /**
     * This method sets an ID to the Report
     *
     * @return void
     */

    @JsonProperty("reportDbId")
    public void setReportDbId(int reportDbId) {
        this.reportDbId = reportDbId;
    }
}
