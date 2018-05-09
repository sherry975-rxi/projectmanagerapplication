package project.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet/")

public class ReactIntegrationExample {

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public String greet() {
        return "Hello world 2!";
    }
}