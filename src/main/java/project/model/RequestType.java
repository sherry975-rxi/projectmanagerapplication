package project.model;

public enum RequestType {


    //Allows control of the value set for each enumeration
    ASSIGNMENT(0, "Assignment"),
    REMOVAL(1, "Removal"),
    NOT_AVAILABLE(2, "Not Available");

    private int type;
    private String description;

    /**
     * Constructor for Enum Class. By convention, a constructor of a Class of the type enum is private,
     * so its omission prevents redundancy.
     * @param type EffortUnit type
     * @param description Description for enum type
     */
    RequestType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * This method returns the enumerated Effort Unit corresponding to a type
     *
     * @param type Integer corresponding to an enum Effort Unit
     * @return The EffortUnit if it exists, null, if type provided is null or throws an exception if the type provided
     * doesn't corresponds to an existent EffortUnit
     */

    public static RequestType toEnum(Integer type) {

        if (type == null) {
            return null;
        }
        for (RequestType x : RequestType.values()) {

            if (type.equals(x.getType())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Código inválido " + type);
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}