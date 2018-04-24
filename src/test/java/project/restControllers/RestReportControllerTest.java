package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.model.*;
import project.restcontroller.RestReportController;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class RestReportControllerTest {

    @Mock
    private TaskService taskServiceMock;
    @Mock
    private ProjectService projectServiceMock;
    @Mock
    private UserService userServiceMock;

    private Project projectDto;
    private User userDto;
    private Task taskDto;
    private ProjectCollaborator projectCollaboratorDto;
    private ProjectCollaborator projectCollaboratorDto1;

    @InjectMocks
    private RestReportController victim;

    private MockMvc mvc;
    private JacksonTester<Report> jacksonReport;


    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();
        userDto = new User("Rui", "rp@gmail.com", "02", "Simples colaborador", "638192945");
        projectDto = new Project("Projeto para pintura", "Pintura de interiores", userDto);
        taskDto = new Task("Criar tarefa", projectDto);
        projectCollaboratorDto = new ProjectCollaborator(userDto, 3);
        projectCollaboratorDto1 = new ProjectCollaborator(userDto, 5);
        projectCollaboratorDto.setProject(projectDto);
        projectCollaboratorDto1.setProject(projectDto);
    }

    @After
    public void tearDown() {
        projectDto = null;
       userDto = null;
       taskDto = null;
       projectCollaboratorDto = null;
        projectCollaboratorDto1 = null;
       victim = null;
       mvc = null;
       jacksonReport = null;
    }



    /**
     * GIVEN a set of parameters to create a report and a taskID and a ProjectID and a UserID
     *
     * WHEN one creates a Report associated with the Task that matches a taskID and only if the taskCollaborator that creates the report exists in TaskTeam
     *
     * THEN it is expected to be successfully created
     *
     * @throws Exception
     */
    @Test
    public void createReportTestHappyPath () throws Exception {

        //GIVEN
        //A set of parameters to create a report and a taskID and a ProjectID and a UserID
        int projid = 1;
        String taskid = "1";
        int userId = 1;
        when(projectServiceMock.getProjectById(projid)).thenReturn(projectDto);
        when(taskServiceMock.getTaskByTaskID(any(String.class))).thenReturn(taskDto);
        when(userServiceMock.getUserByEmail(any(String.class))).thenReturn(userDto);

        //WHEN the Report is created
        //one performs a post request to url /projects/{id}/tasks/{id}⁄reports/
        TaskCollaborator taskCollaboratorDto = new TaskCollaborator(projectCollaboratorDto);
        taskDto.addProjectCollaboratorToTask(projectCollaboratorDto);
        double reportedTime = 3.0;
        Calendar firstDateOfReport;
        firstDateOfReport = Calendar.getInstance();

        Report reportDto = new Report(taskCollaboratorDto, firstDateOfReport);
        reportDto.setReportedTime(reportedTime);
        taskDto.createReport(taskCollaboratorDto, firstDateOfReport, reportedTime);
        when(taskServiceMock.saveTask(any(Task.class))).thenReturn(taskDto);

        MockHttpServletResponse response = mvc.perform(post("/projects/" + projid + "/tasks/" + taskid + "/reports/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonReport.write(reportDto).getJson()))
                .andReturn().getResponse();


        //THEN the report is created
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    /**
     * GIVEN a set of parameters to create a report and a taskID and a ProjectID and a UserID
     *
     * WHEN one creates a Report associated with the Task that matches a taskID
     * without the taskCollaborator that creates the report existing in TaskTeam
     *
     * THEN the creation of the Report is Not Allowed
     *
     * @throws Exception
     */
    @Test
    public void createReportTestUNHappyPath () throws Exception {

        //GIVEN
        //A set of parameters to create a report and a taskID and a ProjectID and a UserID
        int projid = 1;
        String taskid = "1";
        int userId = 1;
        when(projectServiceMock.getProjectById(projid)).thenReturn(projectDto);
        when(taskServiceMock.getTaskByTaskID(any(String.class))).thenReturn(taskDto);
        when(userServiceMock.getUserByEmail(any(String.class))).thenReturn(userDto);

        //WHEN the Report is created
        //one performs a post request to url /projects/{id}/tasks/{id}⁄reports/
        TaskCollaborator taskCollaboratorDto = new TaskCollaborator(projectCollaboratorDto);
        double reportedTime = 3.0;
        Calendar firstDateOfReport;
        firstDateOfReport = Calendar.getInstance();

        Report reportDto = new Report(taskCollaboratorDto, firstDateOfReport);
        reportDto.setReportedTime(reportedTime);
        taskDto.createReport(taskCollaboratorDto, firstDateOfReport, reportedTime);
        when(taskServiceMock.saveTask(any(Task.class))).thenReturn(taskDto);

        MockHttpServletResponse response = mvc.perform(post("/projects/" + projid + "/tasks/" + taskid + "/reports/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonReport.write(reportDto).getJson()))
                .andReturn().getResponse();


        //THEN the report is created
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());
    }



    /**
     * GIVEN a taskID and a ProjectID
     *
     * WHEN  a task has reports
     *
     * THEN all reports from a Task are retrieved
     *
     * @throws Exception
     */
    @Test
    public void getTaskReports () throws Exception {

        //GIVEN
        int projid = 1;
        String taskid = "1";
        when(projectServiceMock.getProjectById(projid)).thenReturn(projectDto);
        when(taskServiceMock.getTaskByTaskID(any(String.class))).thenReturn(taskDto);

        //WHEN
        List<Report> reports = taskDto.getReports();

        MockHttpServletResponse response = mvc.perform(get("/projects/" + projid + "/tasks/" + taskid + "/reports/")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    }
