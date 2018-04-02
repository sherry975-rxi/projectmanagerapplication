package project.model;

public enum EffortUnit {

    //Allows control of the value set for each enumeration
    HOURS(0, "Hours"),
    PM(1, "Person Month");

    private int code;
    private String description;

    /**
     * Constructor for Enum Class. By convention, a constructor of a Class of the type enum is private,
     * so its omission prevents redundancy.
     * @param code EffortUnit code
     * @param description Description for enum type
     */
    EffortUnit(int code, String description) {
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

    public static EffortUnit toEnum(Integer code) {

        if (code == null) {
            return null;
        }
        for (EffortUnit x : EffortUnit.values()) {

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

