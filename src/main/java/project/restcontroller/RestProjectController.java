package project.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
@RestController
@RequestMapping("/projects")
public class RestProjectController  {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;


    @Autowired
    public RestProjectController(ProjectService projectService, UserService userService,  TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectDetails(@PathVariable int projectId) {
        Project project = this.projectService.getProjectById(projectId);
        Link selfRef = linkTo(RestProjectController.class).slash(project.getProjectId()).withSelfRel();
        project.add(selfRef);
        //Link toPMUpdate = linkTo(RestProjectController.class).slash(project.getProjectId()).slash("pm").withRel("toPm");
        // project.add(toPMUpdate);
        //Link toCalculationMethodUpdate = linkTo(RestProjectController.class).slash(project.getProjectId()).slash("calculationMethod")
               // .withRel("changeCalculationMethod");
        //project.add(toCalculationMethodUpdate);
        this.projectService.getProjectTeam(project);
        return ResponseEntity.ok(project);
    }

    /**
     * Creates a Project with the parameters Name, Description and Project Manager if the response body only has this
     * info. In case the Response Body has an EffortUnit and/or a Budget, the project will be created with the all the
     * Response body information.
     */
    @RequestMapping(value = "" , method = RequestMethod.POST)
    public ResponseEntity<Project> createProject(@RequestBody Project projectDTO) {
        User projectManager = userService.getUserByEmail(projectDTO.getProjectManager().getEmail());

        Project proj = projectService.createProject(projectDTO.getName(), projectDTO.getDescription(), projectManager);

        if (projectDTO.getEffortUnit() != null){
                proj.setEffortUnit(projectDTO.getEffortUnit());
            }

        if (projectDTO.getBudget() != 0) {
            proj.setProjectBudget(projectDTO.getBudget());
        }

        this.projectService.addProjectToProjectContainer(proj);

        Link selfRef = linkTo(RestProjectController.class).slash(proj.getProjectId()).withSelfRel();
        proj.add(selfRef);

        return ResponseEntity.ok().body(proj);
    }

    /**
     * This method change the project manager of a given project
     *
     * @param projectInfoToUpdate
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/{projectId}" , method = RequestMethod.PATCH)
    @PatchMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project projectInfoToUpdate, @PathVariable int projectId){

        if(projectService.isProjectInProjectContainer(projectId)) {
            projectInfoToUpdate.setProjectId(projectId);
            projectService.updateProject(projectInfoToUpdate);
            return ResponseEntity.ok().body(this.projectService.getProjectById(projectId));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * This controller's method uses GET to get the cost of the project through the calculation method defined previously.
     */
    @RequestMapping(value = "/cost", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Double>> getProjectCost(@PathVariable int projectId) {
        Project project = this.projectService.getProjectById(projectId);
        Map<String, Double> projectCost = new HashMap<>();
        projectCost.put("projectCost", taskService.getTotalCostReportedToProjectUntilNow(project));
        return  ResponseEntity.ok().body(projectCost);
    }
}
