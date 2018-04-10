package project.model.costCalculationInterface;

import project.model.ProjectCollaborator;
import project.model.Report;

import java.util.List;

public class AverageCollaboratorCost implements CostCalculationInterface {
    
    @Override
    public void updateCalculationMethod(Report report, List<ProjectCollaborator> collabsList) {
        double average=
                collabsList.stream().mapToDouble(ProjectCollaborator::getCostPerEffort).average().orElse(0);

        report.setCost(average);
    }
}
