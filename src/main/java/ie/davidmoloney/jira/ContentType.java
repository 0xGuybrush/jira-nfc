package ie.davidmoloney.jira;

public enum ContentType {
    JSON("application/json");
    private String value;

    public String getValue() {
        return value;
    }

    ContentType(String value) {
        this.value = value;
    }
}