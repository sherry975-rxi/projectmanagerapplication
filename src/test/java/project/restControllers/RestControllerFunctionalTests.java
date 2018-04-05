package project.restControllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;
import project.model.*;
import project.model.taskstateinterface.*;
import project.services.*;

import java.nio.charset.Charset;
import java.util.Calendar;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureDataJpa
@ComponentScan({ "project.services", "project.model", "project.controllers", "project.restControllers"  })
public class RestControllerFunctionalTests {

    /**
     * This is a functional test to be used for ALL rest controllers
     * TODO add missing tests!
     *
     * Currently implemented:
     *
     * US203 - Find Pending Tasks of given user
     */

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

// Removed HTTP Message converter until it can be fixed, to not break the build
    //    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;


    User owner;
    User mike;

    Project findPendingTasks;

    ProjectCollaborator collabMike;

    Calendar expectedStartDate;
    Calendar expectedDeadline;

    Task ongoingTask;
    Task secondOngoingTask;
    Task unstartedTask;


//    @Autowired
//    public void setConverters(HttpMessageConverter<?>[] converters) {
//
//        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
//                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
//                .findAny()
//                .orElse(null);
//
//        assertNotNull("the JSON message converter must not be null",
//                this.mappingJackson2HttpMessageConverter);
//    }


    @Before
    public void setUp(){

        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        // creates two users
        owner = userService.createUser("Owner boi", "hue@hue.com", "001", "Owns projects", "0000000", "here", "there", "where", "dunno", "mars");
        mike = userService.createUser("Mike", "mike@mike.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");


        // creates a project with user owner as manager
        findPendingTasks = projectService.createProject("Find tasks!", "Please help me find tasks", owner);


        // creates a collaborator
        collabMike = projectService.createProjectCollaborator(mike, findPendingTasks, 10);

        // creates 3 tasks
        ongoingTask = taskService.createTask("Mike has this task", findPendingTasks);

        secondOngoingTask = taskService.createTask("Mike's got it", findPendingTasks);

        unstartedTask = taskService.createTask("Mike does NOT have this task", findPendingTasks);

        // creates an expected start and finish date
        expectedStartDate= Calendar.getInstance();
        expectedStartDate.add(Calendar.MONTH, -1);
        expectedDeadline= Calendar.getInstance();
        expectedDeadline.add(Calendar.MONTH, 1);

        //adds necessary information for ongoing task and second ongoing task to enter the Ongoing state
        ongoingTask.setTaskBudget(10);
        ongoingTask.setEstimatedTaskEffort(10);
        ongoingTask.setStartDate(expectedStartDate);
        ongoingTask.setTaskDeadline(expectedDeadline);
        ongoingTask.setTaskState(new Planned());
        ongoingTask.addProjectCollaboratorToTask(collabMike);
        ongoingTask.setStartDate(Calendar.getInstance());
        ongoingTask.setTaskState(new OnGoing());
        ongoingTask.setCurrentState(StateEnum.ONGOING);

        secondOngoingTask.setTaskBudget(10);
        secondOngoingTask.setEstimatedTaskEffort(10);
        secondOngoingTask.setStartDate(expectedStartDate);
        secondOngoingTask.setTaskDeadline(expectedDeadline);
        secondOngoingTask.addProjectCollaboratorToTask(collabMike);
        secondOngoingTask.setStartDate(Calendar.getInstance());
        secondOngoingTask.setTaskState(new OnGoing());
        secondOngoingTask.setCurrentState(StateEnum.ONGOING);

        // adds info for unstartedtask to enter Planning state
        unstartedTask.setTaskBudget(10);
        unstartedTask.setEstimatedTaskEffort(10);
        unstartedTask.setStartDate(expectedStartDate);
        unstartedTask.setTaskDeadline(expectedDeadline);
        unstartedTask.setTaskState(new Planned());
        unstartedTask.setCurrentState(StateEnum.PLANNED);

        // saves all tasks with updated info
        taskService.saveTask(ongoingTask);
        taskService.saveTask(secondOngoingTask);
        taskService.saveTask(unstartedTask);
    }

    @After
    public void tearDown(){
        owner = null;
        mike = null;
        findPendingTasks=null;
        collabMike=null;
        unstartedTask=null;
        secondOngoingTask=null;
        ongoingTask=null;
    }


    /**
     * This test asserts all the set up data is in the database, to isolate variables when solving errors in HTTP tests below.
     * REMOVE THE COMMENT IF INTEGRATION TEST BREAKS!!

    @Test
    public void databaseContainsDataTest() {
        // asserts that owner and mike's email returns his object, while a non existing email returns null
        assertEquals(owner, userService.getUserByEmail("hue@hue.com"));
        assertEquals(mike, userService.getUserByEmail("mike@mike.com"));
        assertEquals(null, userService.getUserByEmail("huehue@hue.com"));

        // asserts all the tasks have the correct state

        assertEquals(StateEnum.ONGOING, ongoingTask.getCurrentState());
        assertEquals(StateEnum.ONGOING, secondOngoingTask.getCurrentState());
        assertEquals(StateEnum.PLANNED, unstartedTask.getCurrentState());

        //asserts Mike is in the correct tasks
        assertTrue(ongoingTask.isProjectCollaboratorActiveInTaskTeam(collabMike));
        assertTrue(secondOngoingTask.isProjectCollaboratorActiveInTaskTeam(collabMike));
        assertFalse(unstartedTask.isProjectCollaboratorActiveInTaskTeam(collabMike));
    }
     */

    /**
     * This test confirms the rest Controller for US203 works correctly (currently not yet implemented)
     */
    @Test
    public void US203testBrowserOutput() throws Exception {

        String onGoingTaskString = ongoingTask.getTaskID() + " - " + ongoingTask.getDescription();

        // this confirms mike's ID returns a "501 not yet implemented"
        mockMvc.perform(get("/users/" + String.valueOf(mike.getId()) + "/viewPendingTasks")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0]", is(onGoingTaskString)));

        // this confirms an Invalid (non INT) ID returns a "401 unauthorized"
        mockMvc.perform(get("/users/" + "INVALID" + "/viewPendingTasks")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0]", is("401 Unauthorized")));

        // this confirms an Invalid (negative) ID returns a "401 unauthorized"
        mockMvc.perform(get("/users/" + String.valueOf(-2) + "/viewPendingTasks")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0]", is("401 Unauthorized")));
    }


//    protected String json(Object o) throws IOException {
//        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
//        this.mappingJackson2HttpMessageConverter.write(
//                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
//        return mockHttpOutputMessage.getBodyAsString();
//    }

}
