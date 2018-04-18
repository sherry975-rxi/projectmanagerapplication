package project.restcontroller;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.services.ProjectService;

@RestController
@RequestMapping("projects/")
public class RestProjectController extends ResourceSupport {
    private final ProjectService projectService;

    public RestProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value= "{projectId}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectDetails(@PathVariable int projectId) {
        Project project = this.projectService.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }
}
