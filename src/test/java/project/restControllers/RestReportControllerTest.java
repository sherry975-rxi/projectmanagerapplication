package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import java.util.Calendar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RestReportControllerTest {

    @Mock
    private TaskService taskServiceMock;
    @Mock
    private ProjectService projectServiceMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private Report reportMock;
@Mock
    private Task taskDto;
    private User userDto;
    private Project projectDto;
    private ProjectCollaborator projectCollaboratorDto;
    private TaskCollaborator taskCollaboratorDto;

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
        this.projectCollaboratorDto = new ProjectCollaborator(userDto, 3);
        projectCollaboratorDto.setProject(projectDto);
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
    public void createReportTest () throws Exception {

        //GIVEN
        //A set of parameters to create a report and a taskID and a ProjectID and a UserID
        taskCollaboratorDto = new TaskCollaborator(projectCollaboratorDto);
        double reportedTime = 3.0;
        Calendar firstDateOfReport;
        firstDateOfReport = Calendar.getInstance();
        String email = "rp@gmail.com";
        String taskID = "1";
        int projectID = 1;

        //WHEN one performs a post request to url /projects/{id}/tasks/{id}⁄reports/
        when(projectServiceMock.getProjectById(any(Integer.class))).thenReturn(projectDto);
        when(taskServiceMock.getTaskByTaskID(any(String.class))).thenReturn(taskDto);
        //when(taskDto.getTaskCollaboratorByEmail(any(String.class))).thenReturn(taskCollaboratorDto);
        taskDto.createReport(taskCollaboratorDto, firstDateOfReport, reportedTime);
        //Mockito.verify(taskDto,Mockito.times(1)).createReport(taskCollaboratorDto, firstDateOfReport, reportedTime);

        MockHttpServletResponse response = mvc.perform(post("/projects/" + projectID + "/tasks/" + taskID + "/reports/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonReport.write(reportMock).getJson()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
