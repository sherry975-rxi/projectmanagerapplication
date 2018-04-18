package project.restcontroller;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("projects/{projid}/tasks/{taskid}/reports/")
public class RestReportController extends ResourceSupport {
}
