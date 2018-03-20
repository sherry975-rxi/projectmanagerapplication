package project.model;

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
import project.Services.UserService;
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

		private Calendar startDate;
		private Project project;
		private User user;
		private Task notAmock;
		private ProjectCollaborator projectCollaborator;
		private TaskCollaborator taskCollaborator;

		@InjectMocks
		private TaskService victimTask;

		@InjectMocks
		private UserService victimUser;

		@InjectMocks
		private ProjectService victimProject;

		@Before
		public void setUp(){
			initMocks(this);

			this.victimTask = new TaskService(taskRepository);
			this.victimUser = new UserService(userRepository);
			this.victimProject = new ProjectService(projectsRepository, projectCollaboratorRepository);

			this.startDate = Calendar.getInstance();
			this.startDate.set(Calendar.YEAR, 2017);
			this.startDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
			this.startDate.set(Calendar.DAY_OF_MONTH, 25);
			this.startDate.set(Calendar.HOUR_OF_DAY, 14);


			this.victimProject = new ProjectService(projectsRepository, projectCollaboratorRepository);
			this.victimUser = new UserService();
			this.user = new User("name", "email", "idNumber", "function", "phone");
			this.project = victimProject.createProject("name3", "description4", user);
			this.projectCollaborator = new ProjectCollaborator(user, 10);
			this.taskCollaborator = new TaskCollaborator(new ProjectCollaborator(user, 10));


			project = victimProject.createProject("testing", "Test", user);
		}

		/**
		 * Tests the construction of an instance of Task
		 */
		@Ignore //corrigir este teste
		@Test
		public void testCreateTask() {
//			Task expectedTask = new Task("firstTask", project);
//			Task actualTask= victimTask.createTask("firstTask", project);
//			assertEquals(expectedTask, actualTask);
//
//			expectedTask = new Task("firstTask", project);
//			actualTask= victimTask.createTask("firstTask", project);
//			assertEquals(expectedTask, actualTask);
			
			when(taskRepository.save(taskMock)).thenReturn(taskMock);
			
			//verify(taskRepository, times(1)).save(taskMock);
						
			assertEquals(taskMock, victimTask.createTask(taskMock.getDescription(), taskMock.getProject()));
			
		}

		/**
		 * Tests the method getTaskRepository() to verify if the task list is returned.
		 */
		@Test
		public void testGetTaskRepository(){
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			taskListMock.add(task2Mock);
			when(taskRepository.findAll()).thenReturn(taskListMock);
			
			assertEquals(taskListMock, victimTask.getTaskRepository());
		}
		
		/**
		 * Tests the getProjectTasks() method to verify if the task list for the project 
		 * is returned.
		 */
		@Test
		public void testGetProjectTasks(){		
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			taskListMock.add(task2Mock);
			when(taskRepository.findAllByProject(project)).thenReturn(taskListMock);

			assertEquals(taskListMock, victimTask.getProjectTasks(project));
		}

		/**
		 * Tests if the getUserTasks() method to verify is a task list from a certain user is returned.
		 */
		@Test
		public void testGetUserTasks(){
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);

			List<TaskCollaborator> listTaskCollaborator= new ArrayList<>();
			listTaskCollaborator.add(taskCollaborator);
			when(taskMock.getTaskTeam()).thenReturn(listTaskCollaborator);

			List<Task> expectedList = new ArrayList<>();
			expectedList.add(taskMock);
			
			assertEquals(expectedList, victimTask.getUserTasks(user));
		}
		
		/**
		 * Tests the getFinishedTasksFromUser() method to verify if the tasks from a certain user, marked as finished,
		 * are returned.
		 */
		@Test
		public void testGetAllFinishedTasksFromUser() {
			
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
			
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getFinishedTaskListOfUserInProject(projectCollaborator));		
		}
		
		
		/**
		 * Tests the getUnfinishedUserTaskList() method to verify if the tasks from a certain user, marked as unfinished,
		 * are returned.
		 */
		@Test
		public void testGetUnfinishedUserTaskList() {
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
			
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getUnfinishedTasksFromProjectCollaborator(projectCollaborator));
		}		
		
		/**
		 * Tests the getStartedNotFinishedUserTaskList() method to verify if the tasks from a certain user, marked as Started but
		 * the state "unfinished" are returned.
		 */
		@Test
		public void testGetStartedNotFinishedUserTaskList(){
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.viewTaskStateName()).thenReturn("Canceled");
			when(taskMock.getStartDate()).thenReturn(startDate);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
		}
		
		/**
		 * Tests the getLastMonthFinishedUserTaskList() method to verify if the tasks marked as Finished, of a certain user,
		 * are returned, in decreasing order.
		 */
		@Ignore //problema com o calendário
		@Test
		public void testGetLastMonthFinishedUserTaskList() {
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getAllFinishedTasksFromUser(user)).thenReturn(taskListMock);
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, Calendar.FEBRUARY);
			when(taskMock.getFinishDate()).thenReturn(calendar);
			
			assertEquals(taskListMock, victimTask.getLastMonthFinishedUserTaskList(user));	
		}
		
		@Ignore
		@Test
		public void testSortTaskListDecreasingOrder() {
			taskMock.setFinishDate(Calendar.getInstance());
			task2Mock.setFinishDate(Calendar.getInstance());
			List<Task> result = new ArrayList<>();
			result.add(taskMock);
			result.add(task2Mock);
			
		
			
		}
		
		@Ignore
		@Test
		public void testSortTaskListByDeadline() {
			
		}
		
		@Ignore
		@Test
		public void testGetTotalTimeOfFinishedTasksFromUserLastMonth() {
			
		}
		
		@Ignore
		@Test
		public void testGetTimeSpentByProjectCollaboratorInAllTasksLastMonth() {
			
		}
		
		@Ignore
		@Test
		public void testGetAverageTimeOfFinishedTasksFromUserLastMonth() {
			
		}
		
		
		@Ignore
		@Test
		public void testGetFinishedUserTasksFromLastMonthInDecreasingOrder() {
			
		}
		
		/**
		 * Tests the getAllFinishedUserTasksInDecreasingOrder() method, to verify f a list with the finished tasks of a 
		 * certain user by decreasing order of date is returned.
		 */
		@Ignore //terminar teste
		@Test
		public void testGetAllFinishedUserTasksInDecreasingOrder() {
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			taskListMock.add(task2Mock);
			
			when(victimTask.getUserTasks(user)).thenReturn(taskListMock);
			
			assertEquals(taskListMock, victimTask.getAllFinishedUserTasksInDecreasingOrder(user));
		}
		
		@Ignore
		@Test
		public void testGetStartedNotFinishedUserTasksInIncreasingDeadlineOrder() {
			
		}
		
		//rever teste
		@Test
		public void testSaveTask() {
			when(taskRepository.save(taskMock)).thenReturn(taskMock);
			
			assertEquals(taskMock, victimTask.saveTask(taskMock));
		}
		
		/**
		 * Tests the getUnfinishedTasksFromProjectCollaborator() method to verify if a list of all unfinished tasks
		 * of a certain project is returned.
		 */
		@Test
		public void testGetUnFinishedTasksFromProjectCollaborator() {
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getUnfinishedTasksFromProjectCollaborator(projectCollaborator));
		}

		/**
		 * Tests the getStartedNotFinishedTasksFromProjectCollaborator() method to verify if a list of started, but not yes finished tasks,
		 * assigned to a certain project collaborator, is returned.
		 */
		@Test
		public void testGetStartedNotFinishedTasksFromProjectCollaborator(){
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getAllTasksFromProjectCollaborator(projectCollaborator)).thenReturn(taskListMock);
			
			
			//test that when condition is other.isTaskFinished() is false, the task is removed from list
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.viewTaskStateName()).thenReturn("Cancelled");
			when(taskMock.getStartDate()).thenReturn(startDate);
			when(taskMock.isProjectCollaboratorInTaskTeam(projectCollaborator)).thenReturn(true);
			List<Task> expectedTaskList = new ArrayList<>();
			assertEquals(expectedTaskList, victimTask.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
			
			//test that when condition is "Cancelled".equals(other.viewTaskStateName()) is false, the task is removed from list
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.viewTaskStateName()).thenReturn("Finished");
			when(taskMock.getStartDate()).thenReturn(startDate);
			when(taskMock.isProjectCollaboratorInTaskTeam(projectCollaborator)).thenReturn(true);
			assertEquals(expectedTaskList, victimTask.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
			
			//test that when condition is other.getStartDate() == null is false, the task is removed from list
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.viewTaskStateName()).thenReturn("Cancelled");
			when(taskMock.getStartDate()).thenReturn(null);
			when(taskMock.isProjectCollaboratorInTaskTeam(projectCollaborator)).thenReturn(true);
			assertEquals(expectedTaskList, victimTask.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
			
			//test that when all conditions are false, the task is not removed from list
			expectedTaskList.add(taskMock);
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.viewTaskStateName()).thenReturn("Finished");
			when(taskMock.getStartDate()).thenReturn(startDate);
			when(taskMock.isProjectCollaboratorInTaskTeam(projectCollaborator)).thenReturn(true);
			assertEquals(expectedTaskList, victimTask.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
		}
		
		/**
		 * Tests the getFinishedTaskListofUserInProject() method to verify if a list of only the finished tasks of a 
		 * certain user in a project is returned.
		 */
		@Test
		public void testFinishedTaskListOfUserInProject() {
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getFinishedTaskListOfUserInProject(projectCollaborator));
		}
		
	
		/**
		 * Tests the getFinishedTasksFromProjectCollaboratorInGivenMonth() method to verify if a list of all tasks
		 * finished x months ago by a certain project collaborator is returned.
		 */
		@Test
		public void testGetFinishedTasksFromProjectCollaboratorInGivenMonth() {
			Calendar calendar = Calendar.getInstance();
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.getFinishDate()).thenReturn(calendar);
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, 0));
			assertEquals(expectedTaskList, victimTask.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, -1));


			calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
			when(taskMock.getFinishDate()).thenReturn(calendar);
			assertEquals(new ArrayList<>(), victimTask.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, 0));
		}
		
		/**
		 * Tests the isTaskinTaskRepository() method to verify if a certain task exists in the task list.
		 */
		@Test
		public void testIsTaskInTaskContainer(){
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			assertTrue(victimTask.isTaskInTaskRepository(taskMock));
			assertFalse(victimTask.isTaskInTaskRepository(new Task()));
		}
		
		/**
		 * Tests the getAllTasksFromProjectCollaborator() method to verify if a list of all the tasks of a certain 
		 * user, which is a project collaborator, is returned.
		 */
		@Test
		public void testGetAllTasksFromProjectCollaborator(){
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);

			assertEquals(expectedTaskList, victimTask.getAllTasksFromProjectCollaborator(projectCollaborator));
		}
		
		/**
		 * Tests the isCollaboratorActiveOnAnyTask() method to verify if a given user had any task assigned to him.
		 */
		@Test
		public void testIsCollaboratorActiveOnAnyTask(){		
			List<Task> taskListMock = new ArrayList<>();
			taskListMock.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskListMock);
			
			when(taskMock.isProjectCollaboratorActiveInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
			assertTrue(victimTask.isCollaboratorActiveOnAnyTask(projectCollaborator));


			when(taskMock.isProjectCollaboratorActiveInTaskTeam(any(ProjectCollaborator.class))).thenReturn(false);
			assertFalse(victimTask.isCollaboratorActiveOnAnyTask(projectCollaborator));
		}
		
		/**
		 * Tests the getProjectTasksWithoutCollaboratorAssigned() method to verify if a list with all the tasks without
		 * collaborators assigned is returned.
		 */
		@Test
		public void testGetAllTasksWithoutCollaboratorsAssigned(){
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(victimTask.getProjectTasks(project)).thenReturn(taskList);
			
			when(taskMock.isTaskTeamEmpty()).thenReturn(true);
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getProjectTasksWithoutCollaboratorsAssigned(project));

			when(taskMock.isTaskTeamEmpty()).thenReturn(false);
			when(taskMock.doesTaskTeamHaveActiveUsers()).thenReturn(false);
			assertEquals(expectedTaskList, victimTask.getProjectTasksWithoutCollaboratorsAssigned(project));

			when(taskMock.isTaskTeamEmpty()).thenReturn(false);
			when(taskMock.doesTaskTeamHaveActiveUsers()).thenReturn(true);
			expectedTaskList.remove(taskMock);
			assertEquals(expectedTaskList, victimTask.getProjectTasksWithoutCollaboratorsAssigned(project));
		}
		
		
		/**
		 * Tests the getProjectFinishedTasks() method to verify is a list with all finished tasks
		 * from a certain project is returned.
		 */
		@Test
		public void testGetProjectFinishedTasks(){
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(victimTask.getProjectTasks(project)).thenReturn(taskList);
			
			when(taskMock.isTaskFinished()).thenReturn(true);
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);

			assertEquals(expectedTaskList, victimTask.getProjectFinishedTasks(project));
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
			when(victimTask.getProjectTasks(project)).thenReturn(taskList);
			
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

			assertEquals(expectedTaskList, victimTask.getProjectFinishedTasksInDecreasingOrder(project));
		}
		
		/**
		 * Tests the get ProjectUnfinishedTasks() method to verify if a list with all unfinished tasks
		 * in a certain project is returned.
		 */
		@Test
		public void testGetUnFinishedTasks(){
			Calendar calendar2 = Calendar.getInstance();
			calendar2.add(Calendar.MONTH, -1);
			
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(victimTask.getProjectTasks(project)).thenReturn(taskList);
			
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.viewTaskStateName()).thenReturn("OnGoing");
			when(taskMock.getStartDate()).thenReturn(calendar2);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getProjectUnFinishedTasks(project));
		}
		
		/**
		 * Tests the getProjectOnGoingTasks() method to verify if a list with
		 * all the OnGoing tasks is returned.
		 */
		@Test
		public void testGetProjectOnGoingTasks(){
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(victimTask.getProjectTasks(project)).thenReturn(taskList);
			
			TaskStateInterface onGoing = new OnGoing();
			when(taskMock.getTaskState()).thenReturn(onGoing);
			when(taskMock.viewTaskStateName()).thenReturn("OnGoing");
			when(taskMock.isTaskFinished()).thenReturn(false);
			
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getProjectOnGoingTasks(project));	
		}

		/**
		 * Tests the getProjectUnstartedTasks() method to verify if a list with all unstarted tasks 
		 * (with the stated "Created", "Planned" and/or "Ready") of a certain project is returned.
		 */
		@Test
		public void testGetProjectUnstartedTasks(){
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(victimTask.getProjectTasks(project)).thenReturn(taskList);
			
			TaskStateInterface created = new Created();
			when(taskMock.getTaskState()).thenReturn(created);
			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getProjectUnstartedTasks(project));
			
			TaskStateInterface planned = new Planned();
			when(taskMock.getTaskState()).thenReturn(planned);
			assertEquals(expectedTaskList, victimTask.getProjectUnstartedTasks(project));
			
			TaskStateInterface ready = new Ready();
			when(taskMock.getTaskState()).thenReturn(ready);
			assertEquals(expectedTaskList, victimTask.getProjectUnstartedTasks(project));
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
			when(victimTask.getProjectTasks(project)).thenReturn(taskList);
			
			//all conditions on if are true
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.getTaskDeadline()).thenReturn(calendar2);

			List<Task> expectedTaskList = new ArrayList<>();
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getProjectExpiredTasks(project));

			//the condition other.getTaskDeadline().before(today) is false
			when(taskMock.isTaskFinished()).thenReturn(false);
			Calendar calendar = Calendar.getInstance();
			when(taskMock.getTaskDeadline()).thenReturn(calendar);
			expectedTaskList.remove(taskMock);
			taskList.remove(taskMock);
			assertEquals(expectedTaskList, victimTask.getProjectExpiredTasks(project));

			//the condition other.getTaskDeadline() != null is false
			when(taskMock.isTaskFinished()).thenReturn(false);
			when(taskMock.getTaskDeadline()).thenReturn(null);
			assertEquals(expectedTaskList, victimTask.getProjectExpiredTasks(project));

			//the condition !other.isTaskFinished() is false
			when(taskMock.isTaskFinished()).thenReturn(true);
			when(taskMock.getTaskDeadline()).thenReturn(calendar2);
			assertEquals(expectedTaskList, victimTask.getProjectExpiredTasks(project));
		}
		
		@Test
		public void testGetTaskByID(){
			when(taskRepository.findById(12L)).thenReturn(taskMock);
			assertEquals(taskMock, victimTask.getTaskByID(12L));
		}
		
		/**
		 * Tests the deleteTask() method to verify if a task is deleted from the Task repository, if 
		 * it hasn't started. 
		 */
		@Test
		public void testDeleteTask(){
			when(taskMock.viewTaskStateName()).thenReturn("Planned");
			assertTrue(victimTask.deleteTask(taskMock));

			when(taskMock.viewTaskStateName()).thenReturn("Created");
			assertTrue(victimTask.deleteTask(taskMock));

			when(taskMock.viewTaskStateName()).thenReturn("Ready");
			assertTrue(victimTask.deleteTask(taskMock));
			
			when(taskMock.viewTaskStateName()).thenReturn("Assigned");
			assertTrue(victimTask.deleteTask(taskMock));

			when(taskMock.viewTaskStateName()).thenReturn("Finished");
			assertFalse(victimTask.deleteTask(taskMock));
		}

		/**
		 * Tests the getProjectCancelledTasks() method to verify if a list of all cancelled tasks
		 * in a certain project is returned.
		 */
		@Test
		public void testGetProjectCancelledTasks(){
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			when(victimTask.getProjectTasks(project)).thenReturn(taskList);
			
			
			TaskStateInterface finished = new Finished();
			when(taskMock.getTaskState()).thenReturn(finished);
			List<Task> expectedTaskList = new ArrayList<>();
			assertEquals(expectedTaskList, victimTask.getProjectCancelledTasks(project));
			
			TaskStateInterface cancelled = new Cancelled();
			when(taskMock.getTaskState()).thenReturn(cancelled);
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getProjectCancelledTasks(project));
		}
		
		/**
		 * Tests the getReportedCostOfEachTask() method to verify is the cost reported to each task
		 * is returned.
		 */
		@Ignore
		@Test
		public void testGetReportedCostOfEachTask(){
			List<Task> taskList = new ArrayList<>();
			taskList.add(taskMock);
			taskList.add(task2Mock);
			when(taskRepository.findAllByProject(project)).thenReturn(taskList);
			
		
			when(taskMock.getTaskCost()).thenReturn(10.0);
			
			List<String> expectedList = new ArrayList<>();
			expectedList.add("10.0, 0.0".trim());

			assertEquals(expectedList, victimTask.getReportedCostOfEachTask(project));
		}

		@Test
		public void testGetTaskListOfWhichDependenciesCanBeCreated(){
			List<Task> taskList = new ArrayList<>();
			when(victimTask.getTaskRepository()).thenReturn(taskList);

			project = victimProject.createProject("testing", "Test", user);

			taskMock.setProject(project);

			//test case taskState is finished
			TaskStateInterface finished = new Finished();
			when(taskMock.getTaskState()).thenReturn(finished);

			List<Task> expectedTaskList = new ArrayList<>();
			assertEquals(expectedTaskList, victimTask.getTaskListOfWhichDependenciesCanBeCreated(project));

			//test case taskState is cancelled
			TaskStateInterface cancelled = new Cancelled();
			when(taskMock.getTaskState()).thenReturn(cancelled);
			assertEquals(expectedTaskList, victimTask.getTaskListOfWhichDependenciesCanBeCreated(project));

			//test case taskState isn't finished neither cancelled
			taskList.add(taskMock);
			when(victimTask.getTaskRepository()).thenReturn(taskList);
			TaskStateInterface onGoing = new OnGoing();
			when(taskMock.getTaskState()).thenReturn(onGoing);
			expectedTaskList.add(taskMock);
			assertEquals(expectedTaskList, victimTask.getTaskListOfWhichDependenciesCanBeCreated(project));
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

				victimTask.saveTask(taskMock);

				verify(taskRepository, times(1)).save(taskMock);

                when(taskRepository.findAllByProject(project)).thenReturn(expected);

				assertEquals(victimTask.getAllProjectTaskAssignmentRequests(project).size(), 0);
                assertEquals(victimTask.viewAllProjectTaskAssignmentRequests(project).size(), 0);
                assertEquals(victimTask.getAllProjectTaskRemovalRequests(project).size(), 0);
                assertEquals(victimTask.viewAllProjectTaskRemovalRequests(project).size(), 0);


                assertFalse(notAmock.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));


                // when the task assignment requests is created, asserts the lists of assignment requests contain 1 entry
                notAmock.createTaskAssignementRequest(projectCollaborator);

                assertEquals(victimTask.getAllProjectTaskAssignmentRequests(project).size(), 1);
                assertEquals(victimTask.viewAllProjectTaskAssignmentRequests(project).size(), 1);
                assertEquals(victimTask.getAllProjectTaskRemovalRequests(project).size(), 0);
                assertEquals(victimTask.viewAllProjectTaskRemovalRequests(project).size(), 0);


                // then, deletes the request and adds the collaborator to the task, asseting all lists become empty
                // and that the task team contains the project collaborator
                assertTrue(notAmock.deleteTaskAssignementRequest(projectCollaborator));
                notAmock.addProjectCollaboratorToTask(projectCollaborator);

                assertEquals(victimTask.getAllProjectTaskAssignmentRequests(project).size(), 0);
                assertEquals(victimTask.viewAllProjectTaskAssignmentRequests(project).size(), 0);
                assertEquals(victimTask.getAllProjectTaskRemovalRequests(project).size(), 0);
                assertEquals(victimTask.viewAllProjectTaskRemovalRequests(project).size(), 0);

                assertTrue(notAmock.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

                // when a task removal request is created for projectCollaborator
                // asserts the task removal requests contain 1 entry

                notAmock.createTaskRemovalRequest(projectCollaborator);

                assertEquals(victimTask.getAllProjectTaskAssignmentRequests(project), 0);
                assertEquals(victimTask.viewAllProjectTaskAssignmentRequests(project), 0);
                assertEquals(victimTask.getAllProjectTaskRemovalRequests(project), 1);
                assertEquals(victimTask.viewAllProjectTaskRemovalRequests(project), 1);


                // then, approves the request and deletes it, asserting the list of requests all contain 0 entries
                // and that projectCollaborator is no longer active on task
                assertTrue(notAmock.deleteTaskRemovalRequest(projectCollaborator));
                notAmock.removeProjectCollaboratorFromTask(projectCollaborator);

                assertFalse(notAmock.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

                assertEquals(victimTask.getAllProjectTaskAssignmentRequests(project), 0);
                assertEquals(victimTask.viewAllProjectTaskAssignmentRequests(project), 0);
                assertEquals(victimTask.getAllProjectTaskRemovalRequests(project), 0);
                assertEquals(victimTask.viewAllProjectTaskRemovalRequests(project), 0);

        }


}
