package ca.etsmtl.log240.financej;

public class Account {
    public static final int MINIMUM_NAME_LENGTH = 3;
    public static final int MINIMUM_DESCRIPTION_LENGTH = 3;
    public static final int MAXIMUM_NAME_LENGTH = 50;
    public static final int MAXIMUM_DESCRIPTION_LENGTH = 50;

    private String name;
    private String description;

    public Account(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static boolean isValidName(String name) { return name.length() >= MINIMUM_NAME_LENGTH && name.length() <= MAXIMUM_NAME_LENGTH; }

    public static boolean isValidDescription(String description) { return description.length() >= MINIMUM_DESCRIPTION_LENGTH && description.length() <= MAXIMUM_DESCRIPTION_LENGTH; }

    public String getName () { return name; }

    public String getDescription () { return description; }
}
