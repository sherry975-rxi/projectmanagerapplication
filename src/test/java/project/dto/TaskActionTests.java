package project.dto;

import org.junit.Test;
import org.springframework.hateoas.Link;
import project.restcontroller.RestProjectController;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TaskActionTests {


    private Map<String , Link> CORRESPONDING_LINKS = new HashMap<>();
    private int projectId;
    private String taskId;
    private static final String TASKS = "tasks";
    private static final String TASK_DETAILS = "Task Details";
    private static final String ADD_COLLABORATOR = "Add Project Collaborator";
    private static final String REQUESTS_ASSIGNMENT = "requests/assignmentRequest";
    private static final String REQUESTS_REMOVAL = "requests/removalRequest";
    private static final String CREATE_ASSIGNMENT_REQ = "Create Assignment Request";
    private static final String CREATE_REMOVAL_REQ = "Create Removal Request";

    /**
     * Tests the getters of the userDTO class
     */
    @Test
    public void getLinks() {

        //GIVEN a projectID, a taskID and a HashMap
        projectId = 1;
        taskId = "1";

        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).withRel("Add estimated dates"));
        CORRESPONDING_LINKS.put("2", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash("team").withRel(ADD_COLLABORATOR));
        CORRESPONDING_LINKS.put("3", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).withRel(TASK_DETAILS));
        CORRESPONDING_LINKS.put("4", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).withRel("Add Budget and Estimated Cost"));
        CORRESPONDING_LINKS.put("5", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash(REQUESTS_ASSIGNMENT).withRel(CREATE_ASSIGNMENT_REQ));
        CORRESPONDING_LINKS.put("6", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash(REQUESTS_REMOVAL).withRel(CREATE_REMOVAL_REQ));
        CORRESPONDING_LINKS.put("7", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).withRel("Add Start Date"));
        CORRESPONDING_LINKS.put("8", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash("team").withRel("Remove all collaborators"));
        CORRESPONDING_LINKS.put("9", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash("team").withRel("Remove collaborator from Task"));
        CORRESPONDING_LINKS.put("10", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash("standBy").withRel("Set StandBy"));
        CORRESPONDING_LINKS.put("11", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash("cancel").withRel("Set Cancelled"));
        CORRESPONDING_LINKS.put("12", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash("finish").withRel("Set Finished"));
        CORRESPONDING_LINKS.put("13", linkTo(RestProjectController.class).slash(projectId).slash(TASKS).slash(taskId).slash("ongoing").withRel("Set Ongoing"));


        //WHEN the method getLinks is invoked
       Map<String , Link> matching_links = TaskAction.getLinks(projectId, taskId);


       //THEN the links are matched according with the keys
       assertEquals(matching_links,CORRESPONDING_LINKS ) ;
    }

}
