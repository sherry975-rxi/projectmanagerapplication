package project.model.costcalculationinterface;

import project.model.ProjectCollaborator;
import project.model.Report;

import java.util.List;

public interface CostCalculationInterface {

    void updateCalculationMethod(Report report, List<ProjectCollaborator> collabsList);



}
