package ie.davidmoloney.jira;

public class ConnectionDetails {

    private String BASE_URI;
    private String USER;
    private String PASSWORD;

    public ConnectionDetails() {
        BASE_URI = System.getProperty("url");
        USER = System.getProperty("user");
        PASSWORD = System.getProperty("password");
    }

    public void setBASE_URI(String BASE_URI) {
        this.BASE_URI = BASE_URI;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
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
