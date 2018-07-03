package project.model.costcalculationinterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CostCalculationFactory {

    public enum Method {

        //Allows control of the value set for each enumeration
        CI(1, "First Collaborator Cost"),
        CF(2, "Last Collaborator Cost"),
        CM(3, "Average Collaborator Cost"),
        CIFM(4, "First/Last Average Collaborator Cost");

        private int code;
        private String description;

        /**
         * Constructor for Enum Class. By convention, a constructor of a Class of the type enum is private,
         * so its omission prevents redundancy.
         * @param code EffortUnit code
         * @param description Description for enum type
         */
        Method(int code, String description) {
            this.code = code;
            this.description = description;
        }

        /**
         * This method returns the enumerated Effort Unit corresponding to a code
         *
         * @param code Integer corresponding to an enum Effort Unit
         * @return The EffortUnit if it exists, null, if code provided is null or throws an exception if the code provided
         * doesn't corresponds to an existent EffortUnit
         */

        public static Method toEnum(Integer code) {

            if (code == null) {
                return null;
            }
            for (Method x : Method.values()) {

                if (code.equals(x.getCode())) {
                    return x;
                }
            }

            throw new IllegalArgumentException("Código inválido " + code);
        }



        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

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
