package project.model;

import org.springframework.hateoas.Link;
import project.restcontroller.RestProjectController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TaskAction {


    private static int projectId;
    private static  Task task;
    private static final Map<String , Link> correspondingLink = new HashMap<>();

    public void initLinks (){

        correspondingLink.put("US340", linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).withRel("Add estimated dates"));
        correspondingLink.put("US302" ,linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("team").withRel("Add Project Collaborator"));

    }

}


