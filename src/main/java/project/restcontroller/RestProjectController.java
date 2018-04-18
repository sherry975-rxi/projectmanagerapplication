package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Project;
import project.services.ProjectService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
@RequestMapping("projects/")
public class RestProjectController {

    ProjectService projectService;

    @Autowired
    public RestProjectController(ProjectService projServ) {
        this.projectService = projServ;
    }

    /**
     * This method returns a ResponseEntity that contains all the active projects from the project service with a link to open each project
     * @return ResponseEntity with all the active projects
     */
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getActiveProjects() {

        List<Project> activeProjects = this.projectService.getActiveProjects();
        for (Project project : activeProjects) {
            Link selfLink = linkTo(RestProjectController.class).slash(project.getProjectId()).withSelfRel();
            project.add(selfLink);


        }

        return ResponseEntity.ok().body(activeProjects);

    }
}