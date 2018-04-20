package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.User;
import project.services.ProjectService;
import project.services.UserService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
@RequestMapping("/projects/")
public class RestProjectController  {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public RestProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }


    @RequestMapping(value= "{projectId}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectDetails(@PathVariable int projectId) {

        Project project = this.projectService.getProjectById(projectId);

        return ResponseEntity.ok(project);
    }

    /**
     * This method change the project manager of a given project
     *
     * @param projectInfoToUpdate
     * @param projectId
     * @return
     */
    @RequestMapping(value = "{projectId}" , method = RequestMethod.PATCH)
    public ResponseEntity<Project> updateProject(@RequestBody Project projectInfoToUpdate, @PathVariable int projectId){

        Project projectToBeUpdated = projectService.getProjectById(projectId);

        projectService.updateProject(projectInfoToUpdate, projectToBeUpdated);

        Link reference = linkTo(RestProjectController.class).slash(projectToBeUpdated.getProjectId()).withRel("Project details");
        projectToBeUpdated.add(reference);

        return ResponseEntity.ok(projectToBeUpdated);
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

        Link reference = linkTo(RestProjectController.class).slash(proj.getProjectId()).withRel("Project details");
        proj.add(reference);

        return ResponseEntity.ok().body(proj);
    }
    }


