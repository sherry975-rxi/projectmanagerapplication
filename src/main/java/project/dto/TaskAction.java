package project.dto;

import org.springframework.hateoas.Link;
import project.restcontroller.RestProjectController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public final class TaskAction {
    
    private static final String TASKS = "tasks"; 
    private static final String TASK_DETAILS = "Task Details"; 
    private static final String ADD_COLLABORATOR = "Add Project Collaborator";
    private static final String REQUESTS_ASSIGNMENT = "requests/assignmentRequest";
    private static final String REQUESTS_REMOVAL = "requests/removalRequest";
    private static final String CREATE_ASSIGNMENT_REQ = "Create Assignment Request";
    private static final String CREATE_REMOVAL_REQ = "Create Removal Request";

    private static final Map<String , Link> CORRESPONDING_LINKS = new HashMap<>();
    
    private TaskAction() {
    }

    public static Map<String, Link> getLinks(int projectId, String taskId){

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
        

        return CORRESPONDING_LINKS;
    }
}



