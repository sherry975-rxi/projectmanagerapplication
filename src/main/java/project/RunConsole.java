package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;

import java.util.Optional;

@Component
public class RunConsole implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {

    }
}
