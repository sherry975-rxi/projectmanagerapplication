package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Report;
import project.model.StateEnum;
import project.model.Task;
import project.model.User;
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = { "project.Services", "project.controller", "project.model" })
public class US215TotalTimeSpentOnTaskLastMonthControllerTest {

	@Autowired
	ProjectService projectContainer;
	@Autowired
	UserService userContainer;
	@Autowired
	TaskService taskRepo;

	Project proj;
	Task taskA;
	Task taskB;
	Task taskC;
	User user;
	User userA;
	User userB;
	ProjectCollaborator projCollabA;

	@Autowired
	US215TotalTimeSpentOnTaskLastMonthController controller;

	@Before
	public void setUp() {

		// Add user to User Repository
		userA = userContainer.createUser("Fek Quin", "ugandan@nackls.com", "cluck1337", "Follower of da wae",
				"919898997", "Debil Strit", "SP1T-0N-H1M", "NacklsCiti", "QuinLend", "UGANDA");

		userB = userContainer.createUser("Fek Quin2", "ugandan2@nackls.com", "cluck13372", "Follower of da wae2",
				"9198989972", "Debil Strit2", "SP1T-0N-H1M2", "NacklsCiti2", "QuinLend2", "UGANDA2");

		// Add a project to the project repository
		proj = projectContainer.createProject("Best project", "Fainding da quin an spitting on de non-beleevahs!",
				userA);

		// Add user to proj
		projCollabA = projectContainer.createProjectCollaborator(userA, proj, 5);

		// Create and add tasks to Task Repository
		taskA = taskRepo.createTask("Faind fek quin!", proj);
		taskB = taskRepo.createTask("Spit on non-beleevahs!", proj);
		taskC = taskRepo.createTask("Follou da wae!", proj);

		// Add User to tasks, and forces states as Ongoing
		taskA.addProjectCollaboratorToTask(projCollabA);
		taskB.addProjectCollaboratorToTask(projCollabA);
		taskC.addProjectCollaboratorToTask(projCollabA);

		taskA.setTaskState(new OnGoing());
		taskA.setCurrentState(StateEnum.ONGOING);

		taskB.setTaskState(new OnGoing());
		taskB.setCurrentState(StateEnum.ONGOING);

		taskC.setTaskState(new OnGoing());
		taskC.setCurrentState(StateEnum.ONGOING);

		// Create reports in the tasks
		taskA.createReport(taskA.getTaskCollaboratorByEmail("ugandan@nackls.com"), Calendar.getInstance(), 0);
		taskB.createReport(taskB.getTaskCollaboratorByEmail("ugandan@nackls.com"), Calendar.getInstance(), 0);
		taskC.createReport(taskC.getTaskCollaboratorByEmail("ugandan@nackls.com"), Calendar.getInstance(), 0);

		// Set task state as finished last month, forces state as finished
		Calendar finishDate = Calendar.getInstance();
		finishDate.add(Calendar.MONTH, -1);

		taskA.setFinishDate(finishDate);

		taskB.setFinishDate(finishDate);

		taskC.setFinishDate(finishDate);

		taskA.setTaskState(new Finished());
		taskA.setCurrentState(StateEnum.FINISHED);

		taskB.setTaskState(new Finished());
		taskB.setCurrentState(StateEnum.FINISHED);

		taskC.setTaskState(new Finished());
		taskC.setCurrentState(StateEnum.FINISHED);

		taskRepo.saveTask(taskA);
		taskRepo.saveTask(taskB);
		taskRepo.saveTask(taskC);
	}

	@Test
	public final void testGetTotalTimeOfFinishedTasksFromUserLastMonth() {

		assertEquals(0, controller.getTotalTimeOfFinishedTasksFromUserLastMonth(userA), 0.000001);
		assertEquals(0, controller.getTotalTimeOfFinishedTasksFromUserLastMonth(userB), 0.000001);

		Report reportA = taskA.getReports().get(0);
		reportA.setReportedTime(10);
		Report reportB = taskB.getReports().get(0);
		reportB.setReportedTime(15);
		Report reportC = taskC.getReports().get(0);
		reportC.setReportedTime(25);

		taskRepo.saveTask(taskA);
		taskRepo.saveTask(taskB);
		taskRepo.saveTask(taskC);

		assertEquals(50, controller.getTotalTimeOfFinishedTasksFromUserLastMonth(userA), 0.000001);
	}

}
