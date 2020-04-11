package ca.etsmtl.log240.financej;

public class Account {
    private String name;
    private String description;

    public Account(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static boolean isValidName(String name) {
        return name.length() >= 3 && name.length() <= 50;
    }

    public static boolean isValidDescription(String description) {
        return description.length() >= 3 && description.length() <= 50;
    }

    public String getName () { return name; }

    public String getDescription () { return description; }
}
