package cysapi.models.animals;

public enum Genus {
    COW("İnek", "CW"),
    SHEEP("Koyun", "SP"),
    GOAT("Keçi", "GT");

    private final String displayName , code;

    Genus(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return code;
    }
}
