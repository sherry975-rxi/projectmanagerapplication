package project.model;

import org.springframework.hateoas.Link;
import project.restcontroller.RestProjectController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TaskAction {


    private static int projectId;
    private static  String taskId;

    private static final Map<String , Link> CORRESPONDING_LINKS = new HashMap<>();

    public static Map<String, Link> getLinks(int projectId, String taskId){

        //CREATE Task State
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Add estimated dates"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("team").withRel("Add Project Collaborator"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Task details"));

        //PLANNED task state
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Add Budget and Estimated Cost");
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash("team").withRel("Add Project Collaborator");
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("requests/assignmentRequest").withRel("Create Assignment Request");
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("requests/removalRequest").withRel("Create Removal Request");
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Task details"));

        //READY Task State
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Add Start Date"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("team").withRel("Add Project Collaborator"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("team").withRel("Remove all collaborators"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("team").withRel("Remove collaborator from Task"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("requests/assignmentRequest").withRel("Create Assignment Request"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("requests/removalRequest").withRel("Create Removal Request"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Task details"));

        //ONGOING task state
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("team").withRel("Add Project Collaborator"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("team").withRel("Remove collaborator from Task"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("requests/assignmentRequest").withRel("Create Assignment Request"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("requests/removalRequest").withRel("Create Removal Request"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("standBy").withRel("Set StandBy"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("cancel").withRel("Set Cancelled"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("finish").withRel("Set Finished"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Task details"));

        //STANDBY task state
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("team").withRel("Add Project Collaborator"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("cancel").withRel("Set Cancelled"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("finish").withRel("Set Finished"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Task details"));

        //CANCELLED task state
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Task details"));

        //FINISHED task state
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).slash("ongoing").withRel("Set Ongoing"));
        CORRESPONDING_LINKS.put("1", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(taskId).withRel("Task details"));


        return CORRESPONDING_LINKS;
    }
}



