package project.model.costcalculationinterface;

import project.model.ProjectCollaborator;
import project.model.Report;

import java.util.List;

/**
 * This interface will be used to calculate project cost based on the last instance of a user, as a project
 * collaborator
 */

public class LastCollaboratorCost implements CostCalculationInterface {

    @Override
    public void updateCalculationMethod(Report report, List<ProjectCollaborator> collabsList){

        ProjectCollaborator lastInstance = collabsList.get(0);

        for(ProjectCollaborator collab : collabsList) {
            if(collab.getStartDate().after(lastInstance.getStartDate())) {
                lastInstance=collab;
            }
        }

        report.setCost(lastInstance.getCostPerEffort());

    }

}
