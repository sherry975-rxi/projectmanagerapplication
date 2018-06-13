package project.model.costcalculationinterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CostCalculationFactory {

    private Map<String,CostCalculationInterface> costCalculationMethod = new HashMap<>();

    /**
     * This method instantiates a hashmap containing a cost calculation method for each String Key
     */
    private void initCostCalculationMethod() {

        CostCalculationInterface firstCost = new FirstCollaboratorCost();
        CostCalculationInterface lastCost = new LastCollaboratorCost();
        CostCalculationInterface averageCost = new AverageCollaboratorCost();
        CostCalculationInterface firstAndLastCost = new FirstAndLastCollaboratorCost();


        costCalculationMethod.put("CI", firstCost);
        costCalculationMethod.put("CF", lastCost);
        costCalculationMethod.put("CM", averageCost);
        costCalculationMethod.put("CIFM", firstAndLastCost);


    }

    /**
     * This method fetches the report cost calculator according to the String Key contained in the project
     *
     * @param costCalculationType
     * @return
     */
    public Optional<CostCalculationInterface> getCostCalculationMethod(String costCalculationType) {
        Optional<CostCalculationInterface> method = Optional.empty();

        this.initCostCalculationMethod();

        if(costCalculationMethod.containsKey(costCalculationType)) {
            method = Optional.of(costCalculationMethod.get(costCalculationType));
        }

        return method;

    }


}
