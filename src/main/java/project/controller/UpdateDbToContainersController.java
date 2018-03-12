package project.controller;

import project.model.ProjectContainer;
import project.model.UserContainer;

public class UpdateDbToContainersController {

    UserContainer userContainer;
    ProjectContainer projectContainer;

    public void updateDBtoContainer(){
        this.projectContainer = new ProjectContainer();
        this.userContainer = new UserContainer();
        projectContainer.updateProjectContainer();
        userContainer.updateUserContainer();

    }

}
