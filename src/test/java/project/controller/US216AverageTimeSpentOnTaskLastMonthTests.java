package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US216AverageTimeSpentOnTaskLastMonthTests {

	/**
	 * Tests US216
	 * 
	 * US216: Como colaborador, eu pretendo obter a média de tempo gasto por tarefa,
	 * nas tarefas que concluí no último mês.
	 * 
	 * uses methods getAverageTimeLastMonthFinishedTasksUser() and
	 * getTimeSpentOnTask();
	 */

	/**
	 * This method returns the average time spent by task during the last month.
	 * This method gets the total time spent on every task finished on last month.
	 * Then it will divide that time by the number of tasks.
	 * 
	 * @return Returns the average time spent by finished task in the last month.
	 * 
	 */
	@Autowired
	TaskService taskService;
	@Autowired
	ProjectService projectService;
	@Autowired
	UserService userService;
	@Autowired
	US216AverageTimeSpentOnTaskLastMonthController controller;

	User user1;
	User user2;
	ProjectCollaborator projectCollaborator1;
	Project myProject;
	Task task1;
	Task task2;
	Task task3;
	Task task4;

	@Before
	public void setUp() {

		user1 = userService.createUser("Daniel", "daniel@gmail.com", "001", "Programador", "910000000", "Rua Azul",
				"5679-987", "braga", "braga", "portugal");
		user2 = userService.createUser("Rita", "rita@gmail.com", "002", "Gestora de Projeto", "920000000", "rua verde",
				"6789", "porto", "porto", "portugal");

		// create myProject
		myProject = projectService.createProject("Projecto I", "Projecto de Gestão", user1);

		// Generate a Start Calendar
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.YEAR, 2017);

		// Generates a Finish Calendar
		Calendar finishDate = Calendar.getInstance();
		finishDate.add(Calendar.MONTH, -2);
		Calendar otherFinishDate = Calendar.getInstance();
		otherFinishDate.add(Calendar.MONTH, -1);

		// Four new tasks were created and added to project1
		task1 = taskService.createTask("Task 1", myProject);
		task2 = taskService.createTask("Task 2", myProject);
		task3 = taskService.createTask("Task 3", myProject);
		task4 = taskService.createTask("Task 4", myProject);

		// Project 1 added to the project repository.
		projectService.addProjectToProjectContainer(myProject);

		// create project collaborators
		projectCollaborator1 = myProject.createProjectCollaborator(user1, 10);

		// user2 added user 1 to the ProjectTeam
		projectService.addProjectCollaborator(projectCollaborator1);

		// defines finish date to task, and mark it as Finished
		task1.setEstimatedTaskStartDate(startDate);
		task1.setTaskDeadline(finishDate);
		task1.addProjectCollaboratorToTask(projectCollaborator1);
		Calendar startDateTask1 = startDate;
		startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
		task1.setStartDate(startDateTask1);
		task1.setFinishDate(finishDate);
		taskService.saveTask(task1);

		task2.setEstimatedTaskStartDate(startDate);
		task2.setTaskDeadline(finishDate);
		task2.addProjectCollaboratorToTask(projectCollaborator1);
		Calendar startDateTask2 = startDate;
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		task2.setStartDate(startDateTask1);
		task2.setFinishDate(otherFinishDate);
		task2.setCurrentState(StateEnum.ONGOING);
		task2.setTaskState(new OnGoing());
		task2.createReport(task2.getTaskCollaboratorByEmail("daniel@gmail.com"), Calendar.getInstance(), 0);
		task2.updateReportedTime(5, task2.getTaskCollaboratorByEmail("daniel@gmail.com"), 0);
		task2.setCurrentState(StateEnum.FINISHED);
		task2.setTaskState(new Finished());
		taskService.saveTask(task2);

		task3.setEstimatedTaskStartDate(startDate);
		task3.setTaskDeadline(finishDate);
		task3.addProjectCollaboratorToTask(projectCollaborator1);
		Calendar startDateTask3 = startDate;
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		task3.setStartDate(startDateTask1);
		task3.setFinishDate(otherFinishDate);
		task3.setCurrentState(StateEnum.ONGOING);
		task3.setTaskState(new OnGoing());
		task3.createReport(task2.getTaskCollaboratorByEmail("daniel@gmail.com"), Calendar.getInstance(), 0);
		task3.updateReportedTime(10, task2.getTaskCollaboratorByEmail("daniel@gmail.com"), 0);
		task3.setCurrentState(StateEnum.FINISHED);
		task3.setTaskState(new Finished());
		taskService.saveTask(task3);

		// List to compare to the getLastMonthFinishedUserTaskList.
		List<Task> expResult = new ArrayList<Task>();
		// expResult.add(t1);
		expResult.add(task2);
		expResult.add(task3);

	}

	@Test
	public void getAverageTimeLastMonthFinishedTasksUser() {

		double expectAverageTime = 7.5;

		assertEquals(expectAverageTime, controller.getAverageTimeOfFinishedTasksFromUserLastMonth(user1), 0.000000001);

	}
}
