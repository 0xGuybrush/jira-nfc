package ie.davidmoloney.jira;

public class ConnectionDetails {

    private String BASE_URI;
    private String USER;
    private String PASSWORD;
    private String PROJECT_KEY;

    public ConnectionDetails() {
        BASE_URI = System.getProperty("url");
        USER = System.getProperty("user");
        PASSWORD = System.getProperty("password");
        PROJECT_KEY = System.getProperty("projectKey");
    }

    public void setBaseUri(String BASE_URI) {
        this.BASE_URI = BASE_URI;
    }

    public void setUser(String USER) {
        this.USER = USER;
    }

    public void setPassword(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getProjectKey() {
        return PROJECT_KEY;
    }

    public String getUser() {
        return USER;
    }

    public String getBaseUri() {
        return BASE_URI;
    }

    public String getPassword() {
        return PASSWORD;
    }
}
