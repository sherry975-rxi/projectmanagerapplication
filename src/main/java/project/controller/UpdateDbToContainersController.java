package project.controller;

import project.model.ProjectContainer;
import project.model.UserContainer;

public class UpdateDbToContainersController {

    UserContainer userContainer;
    ProjectContainer projectContainer;

    public void updateDBtoContainer(){
        this.projectContainer = Company.getTheInstance().getProjectsContainer();
        this.userContainer = Company.getTheInstance().getUsersContainer();
        projectContainer.updateProjectContainer();
        userContainer.updateUserContainer();

    }

}
