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


    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}