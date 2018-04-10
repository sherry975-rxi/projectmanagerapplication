package project.model.costCalculationInterface;

import project.model.ProjectCollaborator;
import project.model.Report;

import java.util.List;

/**
 * This interface will be used to calculate project cost based on the first instance of a user, as a project
 * collaborator
 */

public class FirstCollaboratorCost implements CostCalculationInterface {
    @Override
    public void updateCalculationMethod(Report report, List<ProjectCollaborator> collabsList) {
        ProjectCollaborator firstInstance = collabsList.get(0);

        for(ProjectCollaborator collab : collabsList) {

            if(collab.getStartDate().before(firstInstance.getStartDate())) {
                firstInstance=collab;
            }

        }

        report.setCost(firstInstance.getCostPerEffort());
    }
}
