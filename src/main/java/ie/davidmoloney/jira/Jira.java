package ie.davidmoloney.jira;

import org.apache.http.Header;

import java.io.IOException;
import java.util.Properties;

public class Jira {

    private String JIRA_URL;
    private String JIRA_USER;
    private String JIRA_PASSWORD;

    public Jira() {
        Properties properties = getPropertiesFromFile();
        JIRA_URL = properties.getProperty("url");
        JIRA_USER = properties.getProperty("user");
        JIRA_PASSWORD = properties.getProperty("password");
    }

    private Properties getPropertiesFromFile() {
        Properties prop = new Properties();
        try {
            prop.load(Jira.class.getClassLoader().getResourceAsStream("config.properties"));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public String getJiraUser() {
        return JIRA_USER;
    }

    public String getJiraUrl() {
        return JIRA_URL;
    }

    public String getJiraPassword() {
        return JIRA_PASSWORD;
    }

    public Header encodeDetails(String username, String password) {
        RequestManager requestManager = new RequestManager();
        return requestManager.createAuthorizationHeader(username, password);
    }
}
