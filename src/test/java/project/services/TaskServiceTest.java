package project.services;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import project.Repository.ProjCollabRepository;
import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.model.*;
import project.model.taskstateinterface.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TaskServiceTest {

		@Mock
		private Task taskMock;

		@Mock
		private Task task2Mock;

		@Mock
		private TaskRepository taskRepository;

		@Mock
		private ProjectsRepository projectsRepository;

		@Mock
		private UserRepository userRepository;
		
		@Mock
		private ProjCollabRepository projectCollaboratorRepository;

		@Mock
		private ProjectCollaborator projectCollaboratorMock;

		@Mock
		private TaskCollaborator taskCollaboratorMock;

		private Calendar startDate;
		private Project project;
		private User user;
		private Task notAmock;
		private ProjectCollaborator projectCollaborator;
		private ProjectCollaborator projectCollaborator2;
		private TaskCollaborator taskCollaborator;
		private TaskCollaborator taskCollaborator2;
		private ProjectService projectService;

		@InjectMocks
		private TaskService victim = new TaskService(taskRepository);

		private void mocksGetTaskRepository() {
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(taskRepository.findAll()).thenReturn(taskList);
		}

		private void mocksGetProjectTasks() {
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(taskRepository.findAllByProject(project)).thenReturn(taskList);
		}

		public void mocksGetAllFinishedTasksFromUser() {
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(taskRepository.findAll()).thenReturn(taskListMock);

			List<TaskCollaborator> listTaskCollaborator= new ArrayList<>();
			listTaskCollaborator.add(taskCollaborator);
			when(taskMock.getTaskTeam()).thenReturn(listTaskCollaborator);

			//when the current state is finished, the task is added to list
			when(taskMock.getCurrentState()).thenReturn(StateEnum.FINISHED);
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
		}

		@Before
		public void setUp(){
			initMocks(this);

			this.projectService = new ProjectService(projectsRepository, projectCollaboratorRepository);

			this.startDate = Calendar.getInstance();
			this.startDate.set(Calendar.YEAR, 2017);
			this.startDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
			this.startDate.set(Calendar.DAY_OF_MONTH, 25);
			this.startDate.set(Calendar.HOUR_OF_DAY, 14);

			this.user = new User("name", "email", "idNumber", "function", "phone");
			this.project = projectService.createProject("testing", "Test", user);
			this.projectCollaborator = new ProjectCollaborator(user, 10);
			this.projectCollaborator2 = new ProjectCollaborator(user, 10);
			this.taskCollaborator = new TaskCollaborator(new ProjectCollaborator(user, 10));
			this.taskCollaborator2 = new TaskCollaborator(new ProjectCollaborator(user, 10));

		}

		/**
		 * Tests the construction of an instance of Task
		 */
		@Test
		public void testCreateTask() {
			when(taskRepository.save(taskMock)).thenReturn(taskMock);
			victim.createTask("description", project);
			verify(taskRepository, times(1)).save(any(Task.class));
		}

	/**
	 * Tests the getFinishedTasksFromUser() method to verify if the tasks from a certain user, marked as finished,
	 * are returned.
	 */
	@Test
	public void testGetAllFinishedTasksFromUser() {
		List<Task> taskListMock = new ArrayList<>();
		taskListMock.add(taskMock);
		when(taskRepository.findAll()).thenReturn(taskListMock);

		List<TaskCollaborator> listTaskCollaborator= new ArrayList<>();
		listTaskCollaborator.add(taskCollaborator);
		when(taskMock.getTaskTeam()).thenReturn(listTaskCollaborator);

		//when the current state is finished, the task is added to list
		when(taskMock.getCurrentState()).thenReturn(StateEnum.FINISHED);
		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getAllFinishedTasksFromUser(user));

		//when the current state isn't finished, the task isn't added to list
		when(taskMock.getCurrentState()).thenReturn(StateEnum.CREATED);
		expectedTaskList.remove(taskMock);
		assertEquals(expectedTaskList, victim.getAllFinishedTasksFromUser(user));
	}
		
		/**
		 * Tests the getUnfinishedUserTaskList() method to verify if the tasks from a certain user, marked as unfinished,
		 * are returned.
		 */
		@Test
		public void testGetUnfinishedUserTaskList() {
			this.mocksGetTaskRepository();
			
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
			
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getUnfinishedTasksFromProjectCollaborator(projectCollaborator));
		}		
		
		/**
		 * Tests the getStartedNotFinishedUserTaskList() method to verify if the tasks from a certain user, marked as Started but
		 * the state "unfinished" are returned.
		 */
		@Test
		public void testGetStartedNotFinishedUserTaskList(){
			this.mocksGetTaskRepository();
			
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.viewTaskStateName()).thenReturn("Canceled");
			when(taskMock.getStartDate()).thenReturn(startDate);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
		}

		@Test
		public void testSortTaskListDecreasingOrder() {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.YEAR, 2017);
			when(taskMock.getFinishDate()).thenReturn(calendar1);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(Calendar.YEAR, 2016);
			when(task2Mock.getFinishDate()).thenReturn(calendar2);

			List<Task> result = new ArrayList<>();
			result.add(task2Mock);
			result.add(taskMock);
		
			List<Task> orderedListToCompare = new ArrayList<>();
			orderedListToCompare.add(taskMock);
			orderedListToCompare.add(task2Mock);

			assertEquals(orderedListToCompare, victim.sortTaskListDecreasingOrder(result));
		}

		@Test
		public void testSortTaskListByDeadline() {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.YEAR, 2017);
			when(taskMock.getTaskDeadline()).thenReturn(calendar1);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(Calendar.YEAR, 2016);
			when(task2Mock.getTaskDeadline()).thenReturn(calendar2);

			List<Task> result = new ArrayList<>();
			result.add(task2Mock);
			result.add(taskMock);

			List<Task> orderedListToCompare = new ArrayList<>();
			orderedListToCompare.add(taskMock);
			orderedListToCompare.add(task2Mock);

			assertEquals(orderedListToCompare, victim.sortTaskListByDeadline(result));
		}

	@Test
	public void testGetTotalTimeOfFinishedTasksFromUserLastMonth() {
		this.mocksGetAllFinishedTasksFromUser();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		when(taskMock.getFinishDate()).thenReturn(calendar);

		when(projectCollaboratorMock.getUserFromProjectCollaborator()).thenReturn(user);
		when(taskMock.getTimeSpentByProjectCollaboratorOntask(any(ProjectCollaborator.class))).thenReturn(10.0);

		List<ProjectCollaborator> projectCollaboratorsList = new ArrayList<>();
		when(projectCollaboratorMock.getCollaborator()).thenReturn(user);
		projectCollaboratorsList.add(projectCollaboratorMock);
		when(projectCollaboratorRepository.findAll()).thenReturn(projectCollaboratorsList);

		assertEquals(10.0, victim.getTotalTimeOfFinishedTasksFromUserLastMonth(user), 0.1);
	}
		
		@Ignore
		@Test
		public void testGetTimeSpentByProjectCollaboratorInAllTasksLastMonth() {
			
		}

		@Test
		public void testGetAverageTimeOfFinishedTasksFromUserLastMonth() {
			when(projectCollaboratorMock.getCollaborator()).thenReturn(user);
			List<ProjectCollaborator> projectCollaboratorsList = new ArrayList<>();
			projectCollaboratorsList.add(projectCollaborator);
			when(projectCollaboratorRepository.findAll()).thenReturn(projectCollaboratorsList);

			when(taskMock.getTimeSpentByProjectCollaboratorOntask(any(ProjectCollaborator.class))).thenReturn(10.0);
			when(task2Mock.getTimeSpentByProjectCollaboratorOntask(any(ProjectCollaborator.class))).thenReturn(20.0);

			List<TaskCollaborator> taskCollaboratorsList = new ArrayList<>();
			taskCollaboratorsList.add(taskCollaborator);
			when(taskMock.getTaskTeam()).thenReturn(taskCollaboratorsList);
			when(task2Mock.getTaskTeam()).thenReturn(taskCollaboratorsList);
			when(taskCollaboratorMock.getProjectCollaboratorFromTaskCollaborator()).thenReturn(projectCollaborator);
			when(projectCollaboratorMock.getCollaborator()).thenReturn(user);
			when(task2Mock.getCurrentState()).thenReturn(StateEnum.FINISHED);
			when(taskMock.getCurrentState()).thenReturn(StateEnum.FINISHED);

			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.MONTH, Calendar.FEBRUARY);
			when(taskMock.getFinishDate()).thenReturn(calendar1);
			when(task2Mock.getFinishDate()).thenReturn(calendar1);

			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			taskList.add(task2Mock);
			when(taskRepository.findAll()).thenReturn(taskList);

			assertEquals(15.0, victim.getAverageTimeOfFinishedTasksFromUserLastMonth(user), 0.1);
		}

		@Test
		public void testGetFinishedUserTasksFromLastMonthInDecreasingOrder() {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.DAY_OF_MONTH, 25);
			calendar1.set(Calendar.MONTH, Calendar.FEBRUARY);
			when(taskMock.getFinishDate()).thenReturn(calendar1);

			Calendar calendar2 = Calendar.getInstance();
			calendar1.set(Calendar.DAY_OF_MONTH, 24);
			calendar2.set(Calendar.MONTH, Calendar.FEBRUARY);
			when(task2Mock.getFinishDate()).thenReturn(calendar2);

			when(taskMock.getCurrentState()).thenReturn(StateEnum.FINISHED);
			when(task2Mock.getCurrentState()).thenReturn(StateEnum.FINISHED);

			List<Task> taskList = new ArrayList<>();
			taskList.add(task2Mock);
			taskList.add(taskMock);
			when(taskRepository.findAll()).thenReturn(taskList);

			List<TaskCollaborator> listTaskCollaborator= new ArrayList<>();
			listTaskCollaborator.add(taskCollaborator);
			when(taskMock.getTaskTeam()).thenReturn(listTaskCollaborator);
			when(task2Mock.getTaskTeam()).thenReturn(listTaskCollaborator);

			List<Task> orderedListToCompare = new ArrayList<>();
			orderedListToCompare.add(taskMock);
			orderedListToCompare.add(task2Mock);

			assertEquals(orderedListToCompare, victim.getFinishedUserTasksFromLastMonthInDecreasingOrder(user));
		}
		
		/**
		 * Tests the getAllFinishedUserTasksInDecreasingOrder() method, to verify f a list with the finished tasks of a 
		 * certain user by decreasing order of date is returned.
		 */
		@Test
		public void testGetAllFinishedUserTasksInDecreasingOrder() {
			this.mocksGetTaskRepository();

			List<TaskCollaborator> listTaskCollaborator= new ArrayList<>();
			listTaskCollaborator.add(taskCollaborator);
			when(taskMock.getTaskTeam()).thenReturn(listTaskCollaborator);

			List<Task> expectedList = new ArrayList<>();
			expectedList.add(taskMock);
			
			assertEquals(expectedList, victim.getAllFinishedUserTasksInDecreasingOrder(user));
		}

		@Test
		public void testGetStartedNotFinishedUserTasksInIncreasingDeadlineOrder() {
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			taskList.add(task2Mock);
			when(taskRepository.findAll()).thenReturn(taskList);

			List<TaskCollaborator> taskCollaboratorList = new ArrayList<>();
			taskCollaboratorList.add(taskCollaborator);
			when(taskMock.getTaskTeam()).thenReturn(taskCollaboratorList);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(Calendar.YEAR, 2017);
			when(taskMock.getTaskDeadline()).thenReturn(calendar2);

			when(task2Mock.getTaskTeam()).thenReturn(taskCollaboratorList);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.YEAR, 2016);
			when(task2Mock.getTaskDeadline()).thenReturn(calendar1);

			when(taskMock.getCurrentState()).thenReturn(StateEnum.ONGOING);
			when(task2Mock.getCurrentState()).thenReturn(StateEnum.ONGOING);

			List<Task> expectedList = new ArrayList<>();
			expectedList.add(taskMock);
			expectedList.add(task2Mock);
			assertEquals(expectedList, victim.getStartedNotFinishedUserTasksInIncreasingDeadlineOrder(user));
		}

		@Test
		public void testSaveTask() {
			when(taskRepository.save(taskMock)).thenReturn(taskMock);
			assertEquals(taskMock, victim.saveTask(taskMock));
		}
		
		/**
		 * Tests the getUnfinishedTasksFromProjectCollaborator() method to verify if a list of all unfinished tasks
		 * of a certain project is returned.
		 */
		@Test
		public void testGetUnFinishedTasksFromProjectCollaborator() {
			this.mocksGetTaskRepository();
			
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getUnfinishedTasksFromProjectCollaborator(projectCollaborator));
		}

		/**
		 * Tests the getStartedNotFinishedTasksFromProjectCollaborator() method to verify if a list of started, but not yes finished tasks,
		 * assigned to a certain project collaborator, is returned.
		 */
		@Test
		public void testGetStartedNotFinishedTasksFromProjectCollaborator(){
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victim.getAllTasksFromProjectCollaborator(projectCollaborator)).thenReturn(taskListMock);
			
			//test that when condition is other.isTaskFinished() is false, the task is removed from list
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.viewTaskStateName()).thenReturn("Cancelled");
			when(taskMock.getStartDate()).thenReturn(startDate);
			when(taskMock.isProjectCollaboratorInTaskTeam(projectCollaborator)).thenReturn(true);
			List<Task> expectedTaskList = new ArrayList<>();
			assertEquals(expectedTaskList, victim.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
			
			//test that when condition is "Cancelled".equals(other.viewTaskStateName()) is false, the task is removed from list
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.viewTaskStateName()).thenReturn("Finished");
			when(taskMock.getStartDate()).thenReturn(startDate);
			when(taskMock.isProjectCollaboratorInTaskTeam(projectCollaborator)).thenReturn(true);
			assertEquals(expectedTaskList, victim.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
			
			//test that when condition is other.getStartDate() == null is false, the task is removed from list
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.viewTaskStateName()).thenReturn("Cancelled");
			when(taskMock.getStartDate()).thenReturn(null);
			when(taskMock.isProjectCollaboratorInTaskTeam(projectCollaborator)).thenReturn(true);
			assertEquals(expectedTaskList, victim.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
			
			//test that when all conditions are false, the task is not removed from list
			expectedTaskList.add(taskMock);
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.viewTaskStateName()).thenReturn("Finished");
			when(taskMock.getStartDate()).thenReturn(startDate);
			when(taskMock.isProjectCollaboratorInTaskTeam(projectCollaborator)).thenReturn(true);
			assertEquals(expectedTaskList, victim.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
		}
		
		/**
		 * Tests the getFinishedTaskListofUserInProject() method to verify if a list of only the finished tasks of a 
		 * certain user in a project is returned.
		 */
		@Test
		public void testFinishedTaskListOfUserInProject() {
			this.mocksGetTaskRepository();
			
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getFinishedTaskListOfUserInProject(projectCollaborator));
		}
		
	
		/**
		 * Tests the getFinishedTasksFromProjectCollaboratorInGivenMonth() method to verify if a list of all tasks
		 * finished x months ago by a certain project collaborator is returned.
		 */
		@Test
		public void testGetFinishedTasksFromProjectCollaboratorInGivenMonth() {
			Calendar calendar = Calendar.getInstance();
			this.mocksGetTaskRepository();
			
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.getFinishDate()).thenReturn(calendar);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, 0));
			assertEquals(expectedTaskList, victim.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, -1));


			calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
			when(taskMock.getFinishDate()).thenReturn(calendar);
			assertEquals(new ArrayList<>(), victim.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, 0));
		}
		
		/**
		 * Tests the isTaskinTaskRepository() method to verify if a certain task exists in the task list.
		 */
		@Test
		public void testIsTaskInTaskContainer(){
			this.mocksGetTaskRepository();
			
			assertTrue(victim.isTaskInTaskRepository(taskMock));
			assertFalse(victim.isTaskInTaskRepository(new Task()));
		}
		
		/**
		 * Tests the getAllTasksFromProjectCollaborator() method to verify if a list of all the tasks of a certain 
		 * user, which is a project collaborator, is returned.
		 */
		@Test
		public void testGetAllTasksFromProjectCollaborator(){
			this.mocksGetTaskRepository();
			
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);

			assertEquals(expectedTaskList, victim.getAllTasksFromProjectCollaborator(projectCollaborator));
		}
		
		/**
		 * Tests the isCollaboratorActiveOnAnyTask() method to verify if a given user had any task assigned to him.
		 */
		@Test
		public void testIsCollaboratorActiveOnAnyTask(){		
			this.mocksGetTaskRepository();
			
			when(taskMock.isProjectCollaboratorActiveInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
			assertTrue(victim.isCollaboratorActiveOnAnyTask(projectCollaborator));


			when(taskMock.isProjectCollaboratorActiveInTaskTeam(any(ProjectCollaborator.class))).thenReturn(false);
			assertFalse(victim.isCollaboratorActiveOnAnyTask(projectCollaborator));
		}
		
		/**
		 * Tests the getProjectTasksWithoutCollaboratorAssigned() method to verify if a list with all the tasks without
		 * collaborators assigned is returned.
		 */
		@Test
		public void testGetAllTasksWithoutCollaboratorsAssigned(){
			this.mocksGetProjectTasks();
			
			when(taskMock.isTaskTeamEmpty()).thenReturn(true);
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getProjectTasksWithoutCollaboratorsAssigned(project));

			when(taskMock.isTaskTeamEmpty()).thenReturn(false);
			when(taskMock.doesTaskTeamHaveActiveUsers()).thenReturn(false);
			assertEquals(expectedTaskList, victim.getProjectTasksWithoutCollaboratorsAssigned(project));

			when(taskMock.isTaskTeamEmpty()).thenReturn(false);
			when(taskMock.doesTaskTeamHaveActiveUsers()).thenReturn(true);
			expectedTaskList.remove(taskMock);
			assertEquals(expectedTaskList, victim.getProjectTasksWithoutCollaboratorsAssigned(project));
		}
		
		
		/**
		 * Tests the getProjectFinishedTasks() method to verify is a list with all finished tasks
		 * from a certain project is returned.
		 */
		@Test
		public void testGetProjectFinishedTasks(){
			this.mocksGetProjectTasks();
			
			when(taskMock.isTaskFinished()).thenReturn(true);
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);

			assertEquals(expectedTaskList, victim.getProjectFinishedTasks(project));
		}
		
		/**
		 * Tests the getFinishedTasksInDecreasingOrder() method, to verify if a list of all tasks finished from 
		 * a certain project in decreasing order is returned.
		 */
		@Test
		public void testGetProjectFinishedTasksInDecreasingOrder(){
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			taskList.add(task2Mock);
			when(taskRepository.findAllByProject(project)).thenReturn(taskList);
			
			Calendar calendar = Calendar.getInstance();
			when(taskMock.getFinishDate()).thenReturn(calendar);
			when(taskMock.isTaskFinished()).thenReturn(true);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.add(Calendar.MONTH, -1);
			when(task2Mock.getFinishDate()).thenReturn(calendar2);
			when(task2Mock.isTaskFinished()).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			expectedTaskList.add(task2Mock);

			assertEquals(expectedTaskList, victim.getProjectFinishedTasksInDecreasingOrder(project));
		}
		
		/**
		 * Tests the get ProjectUnfinishedTasks() method to verify if a list with all unfinished tasks
		 * in a certain project is returned.
		 */
		@Test
		public void testGetUnFinishedTasks(){
			Calendar calendar2 = Calendar.getInstance();
			calendar2.add(Calendar.MONTH, -1);
			
			this.mocksGetProjectTasks();
			
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.viewTaskStateName()).thenReturn("OnGoing");
			when(taskMock.getStartDate()).thenReturn(calendar2);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getProjectUnFinishedTasks(project));
		}
		
		/**
		 * Tests the getProjectOnGoingTasks() method to verify if a list with
		 * all the OnGoing tasks is returned.
		 */
		@Test
		public void testGetProjectOnGoingTasks(){
			this.mocksGetProjectTasks();
			
			TaskStateInterface onGoing = new OnGoing();
			when(taskMock.getTaskState()).thenReturn(onGoing);
			when(taskMock.viewTaskStateName()).thenReturn("OnGoing");
			when(taskMock.isTaskFinished()).thenReturn(false);
			
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getProjectOnGoingTasks(project));
		}

		/**
		 * Tests the getProjectUnstartedTasks() method to verify if a list with all unstarted tasks 
		 * (with the stated "Created", "Planned" and/or "Ready") of a certain project is returned.
		 */
		@Test
		public void testGetProjectUnstartedTasks(){
			this.mocksGetProjectTasks();
			
			TaskStateInterface created = new Created();
			when(taskMock.getTaskState()).thenReturn(created);
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getProjectUnstartedTasks(project));
			
			TaskStateInterface planned = new Planned();
			when(taskMock.getTaskState()).thenReturn(planned);
			assertEquals(expectedTaskList, victim.getProjectUnstartedTasks(project));
			
			TaskStateInterface ready = new Ready();
			when(taskMock.getTaskState()).thenReturn(ready);
			assertEquals(expectedTaskList, victim.getProjectUnstartedTasks(project));
		}
		
		
		/**
		 * Tests the getProjectExpiredTasks() method to verify if a list of all the tasks marked as unfinished
		 * but which deadline already passed, is returned.
		 */
		@Test
		public void testGetExpiredTasks(){
			Calendar calendar2 = Calendar.getInstance();
			calendar2.add(Calendar.MONTH, -1);

			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(taskRepository.findAllByProject(project)).thenReturn(taskList);
			
			//all conditions on if are true
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.getTaskDeadline()).thenReturn(calendar2);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getProjectExpiredTasks(project));

			//the condition other.getTaskDeadline().before(today) is false
			when(taskMock.isTaskFinished()).thenReturn(false);
			Calendar calendar = Calendar.getInstance();
			when(taskMock.getTaskDeadline()).thenReturn(calendar);
			expectedTaskList.remove(taskMock);
			taskList.remove(taskMock);
			assertEquals(expectedTaskList, victim.getProjectExpiredTasks(project));

			//the condition other.getTaskDeadline() != null is false
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.getTaskDeadline()).thenReturn(null);
			assertEquals(expectedTaskList, victim.getProjectExpiredTasks(project));

			//the condition !other.isTaskFinished() is false
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.getTaskDeadline()).thenReturn(calendar2);
			assertEquals(expectedTaskList, victim.getProjectExpiredTasks(project));
		}
		
		@Test
		public void testGetTaskByID(){
			when(taskRepository.findById(12L)).thenReturn(taskMock);
			assertEquals(taskMock, victim.getTaskByID(12L));
		}
		
		/**
		 * Tests the deleteTask() method to verify if a task is deleted from the Task repository, if 
		 * it hasn't started. 
		 */
		@Test
		public void testDeleteTask(){
			when(taskMock.viewTaskStateName()).thenReturn("Planned");
			assertTrue(victim.deleteTask(taskMock));

			when(taskMock.viewTaskStateName()).thenReturn("Created");
			assertTrue(victim.deleteTask(taskMock));

			when(taskMock.viewTaskStateName()).thenReturn("Ready");
			assertTrue(victim.deleteTask(taskMock));
			
			when(taskMock.viewTaskStateName()).thenReturn("Assigned");
			assertTrue(victim.deleteTask(taskMock));

			when(taskMock.viewTaskStateName()).thenReturn("Finished");
			assertFalse(victim.deleteTask(taskMock));
		}

		/**
		 * Tests the getProjectCancelledTasks() method to verify if a list of all cancelled tasks
		 * in a certain project is returned.
		 */
		@Test
		public void testGetProjectCancelledTasks(){
			this.mocksGetProjectTasks();
			
			TaskStateInterface finished = new Finished();
			when(taskMock.getTaskState()).thenReturn(finished);
			List<Task> expectedTaskList = new ArrayList<>();
			assertEquals(expectedTaskList, victim.getProjectCancelledTasks(project));
			
			TaskStateInterface cancelled = new Cancelled();
			when(taskMock.getTaskState()).thenReturn(cancelled);
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getProjectCancelledTasks(project));
		}
		
		/**
		 * Tests the getReportedCostOfEachTask() method to verify is the cost reported to each task
		 * is returned.
		 */
		@Test
		public void testGetReportedCostOfEachTask(){
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			taskList.add(task2Mock);
			when(taskRepository.findAllByProject(project)).thenReturn(taskList);

			when(taskMock.getTaskCost()).thenReturn(10.0);
			
			List<String> expectedList = new ArrayList<>();
			expectedList.add("10.0, 0.0");

			assertEquals(expectedList.toString().trim(), victim.getReportedCostOfEachTask(project).toString().trim());
		}

		@Test
		public void testGetTaskListOfWhichDependenciesCanBeCreated(){
			this.mocksGetProjectTasks();
			this.mocksGetTaskRepository();

			//test case taskState is finished
			TaskStateInterface finished = new Finished();
			when(taskMock.getTaskState()).thenReturn(finished);
			List<Task> expectedTaskList = new ArrayList<>();
			assertEquals(expectedTaskList, victim.getTaskListOfWhichDependenciesCanBeCreated(project));

			//test case taskState is cancelled
			TaskStateInterface cancelled = new Cancelled();
			when(taskMock.getTaskState()).thenReturn(cancelled);
			assertEquals(expectedTaskList, victim.getTaskListOfWhichDependenciesCanBeCreated(project));

			//test case taskState isn't finished neither cancelled
			this.mocksGetTaskRepository();
			TaskStateInterface onGoing = new OnGoing();
			when(taskMock.getTaskState()).thenReturn(onGoing);
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victim.getTaskListOfWhichDependenciesCanBeCreated(project));
		}

        /**
         *
         *  This test confirms all the methods to get and view the lists of requests are working correctly
         *
         */

		@Test
		public void testGetAssignment_RemovalRequestsList(){

		        // given a new task in project, asserts it has no requests and no active team

				notAmock = project.createTask("This is not a mock");

				List<Task> expected = new ArrayList<>();
				expected.add(notAmock);

				victim.saveTask(taskMock);

				verify(taskRepository, times(1)).save(taskMock);

                when(taskRepository.findAllByProject(project)).thenReturn(expected);

				assertEquals(victim.getAllProjectTaskAssignmentRequests(project).size(), 0);
                assertEquals(victim.viewAllProjectTaskAssignmentRequests(project).size(), 0);
                assertEquals(victim.getAllProjectTaskRemovalRequests(project).size(), 0);
                assertEquals(victim.viewAllProjectTaskRemovalRequests(project).size(), 0);


                assertFalse(notAmock.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));


                // when the task assignment requests is created, asserts the lists of assignment requests contain 1 entry
                notAmock.createTaskAssignementRequest(projectCollaborator);

                assertEquals(victim.getAllProjectTaskAssignmentRequests(project).size(), 1);
                assertEquals(victim.viewAllProjectTaskAssignmentRequests(project).size(), 1);
                assertEquals(victim.getAllProjectTaskRemovalRequests(project).size(), 0);
                assertEquals(victim.viewAllProjectTaskRemovalRequests(project).size(), 0);


                // then, deletes the request and adds the collaborator to the task, asseting all lists become empty
                // and that the task team contains the project collaborator
                assertTrue(notAmock.deleteTaskAssignementRequest(projectCollaborator));
                notAmock.addProjectCollaboratorToTask(projectCollaborator);

                assertEquals(victim.getAllProjectTaskAssignmentRequests(project).size(), 0);
                assertEquals(victim.viewAllProjectTaskAssignmentRequests(project).size(), 0);
                assertEquals(victim.getAllProjectTaskRemovalRequests(project).size(), 0);
                assertEquals(victim.viewAllProjectTaskRemovalRequests(project).size(), 0);

                assertTrue(notAmock.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

                // when a task removal request is created for projectCollaborator
                // asserts the task removal requests contain 1 entry

                notAmock.createTaskRemovalRequest(projectCollaborator);

                assertEquals(victim.getAllProjectTaskAssignmentRequests(project), 0);
                assertEquals(victim.viewAllProjectTaskAssignmentRequests(project), 0);
                assertEquals(victim.getAllProjectTaskRemovalRequests(project), 1);
                assertEquals(victim.viewAllProjectTaskRemovalRequests(project), 1);


                // then, approves the request and deletes it, asserting the list of requests all contain 0 entries
                // and that projectCollaborator is no longer active on task
                assertTrue(notAmock.deleteTaskRemovalRequest(projectCollaborator));
                notAmock.removeProjectCollaboratorFromTask(projectCollaborator);

                assertFalse(notAmock.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

                assertEquals(victim.getAllProjectTaskAssignmentRequests(project), 0);
                assertEquals(victim.viewAllProjectTaskAssignmentRequests(project), 0);
                assertEquals(victim.getAllProjectTaskRemovalRequests(project), 0);
                assertEquals(victim.viewAllProjectTaskRemovalRequests(project), 0);

        }


}
