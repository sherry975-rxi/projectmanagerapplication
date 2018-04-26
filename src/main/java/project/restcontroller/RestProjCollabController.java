package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.services.ProjectService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/projects/{projectId}/team")
public class RestProjCollabController {

    private final ProjectService projectService;

    private final UserService userService;

    @Autowired
    public RestProjCollabController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    /**
     * This method gets all active project collaborators from a project
     *
     * @param projectId Project id to get project collaborators from
     *
     * @return List of project collaborators with the new user added with a link to open each collaborator
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProjectCollaborator>> getProjectTeam(@PathVariable int projectId) {

        List<ProjectCollaborator> collaborators = new ArrayList<>();

        Project project = projectService.getProjectById(projectId);
        collaborators.addAll(projectService.getActiveProjectTeam(project));

        for(ProjectCollaborator collaborator: collaborators) {
            Link selfRel = linkTo(RestProjectController.class).slash(projectId).slash("team").slash(collaborator.getProjectCollaboratorId()).withSelfRel();
            collaborator.add(selfRel);

        }

        return ResponseEntity.ok().body(collaborators);
    }

    /**
     * This method gets all active project collaborators from a project
     *
     * @param collaboratorId Project id to get project collaborators from
     *
     * @return List of project collaborators with the new user added with a link to open each collaborator
     */
    @RequestMapping(value = "/{collaboratorId}", method = RequestMethod.GET)
    public ResponseEntity<ProjectCollaborator> getProjectCollaborator(@PathVariable int projectId, @PathVariable long collaboratorId) {

        ProjectCollaborator collaborator = projectService.getProjectCollaboratorById(collaboratorId);

        Link selfRel = linkTo(RestProjectController.class).slash(projectId).slash("team").slash(collaboratorId).withSelfRel();
        collaborator.add(selfRel);

        return ResponseEntity.ok().body(collaborator);
    }

    /**
     * This method creates and adds a collaborator to a specific project
     *
     * @param collaborator Collaborator to create
     * @param projectId Id of the project
     *
     * @return List of project collaborators with the new user added with a link to open each collaborator
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProjectCollaborator> addCollaborator(@RequestBody ProjectCollaborator collaborator, @PathVariable int projectId) {

        ResponseEntity<ProjectCollaborator> response;
        String userEmail = collaborator.getCollaborator().getEmail();

        if(projectId != collaborator.getProject().getProjectId() || userService.getUserByEmail(userEmail) == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        else {

        ProjectCollaborator projectCollaborator = projectService.createProjectCollaboratorWithEmail(userEmail, projectId, collaborator.getCostPerEffort());
        response = new ResponseEntity<>(projectCollaborator, HttpStatus.CREATED);

        Link selfRel = linkTo(RestProjectController.class).slash(projectId).slash("team").slash(projectCollaborator.getProjectCollaboratorId()).withSelfRel();
        projectCollaborator.add(selfRel);

        Link reference1 = linkTo(RestProjectController.class).slash(projectId).slash("team").withRel("Project Collaborator List");
        projectCollaborator.add(reference1);

        }
         return response;
    }

    /**
     * This method deactivates a collaborator of a specific project
     *
     * @param collaboratorId Collaborator to deactivate
     * @param projectId Id of the project
     *
     * @return List of project collaborators with the new user added with a link to open each collaborator
     */
    @RequestMapping(value = "/{collaboratorId}", method = RequestMethod.DELETE)
    public ResponseEntity<ProjectCollaborator> removeCollaborator(@PathVariable long collaboratorId, @PathVariable int projectId) {

        ResponseEntity<ProjectCollaborator> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        ProjectCollaborator projectCollaborator = projectService.getProjectCollaboratorById(collaboratorId);

        if(projectCollaborator != null){
            projectCollaborator.setStatus(false);
            projectService.updateProjectCollaborator(projectCollaborator);

            response = new ResponseEntity<>(projectCollaborator, HttpStatus.OK);

            Link selfRel = linkTo(RestProjectController.class).slash(projectId).slash("team").slash(projectCollaborator.getProjectCollaboratorId()).withSelfRel();
            projectCollaborator.add(selfRel);

            Link reference1 = linkTo(RestProjectController.class).slash(projectId).slash("team").withRel("Project Collaborator List");
            projectCollaborator.add(reference1);
        }

        return response;
    }
}

