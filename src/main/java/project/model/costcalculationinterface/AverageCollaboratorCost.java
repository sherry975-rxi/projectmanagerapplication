package project.model.costcalculationinterface;

import project.model.ProjectCollaborator;
import project.model.Report;

import java.util.List;

/**
 * This interface will be used to calculate project cost based on the average of the first and  instance of a user,
 * as a project collaborator
 */

public class AverageCollaboratorCost implements CostCalculationInterface {
    
    @Override
    public void updateCalculationMethod(Report report, List<ProjectCollaborator> collabsList) {
        double average=
                collabsList.stream().mapToDouble(ProjectCollaborator::getCostPerEffort).average().orElse(0);

        report.setCost(average);
    }
}
