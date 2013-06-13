package ie.davidmoloney.jira;

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

    public int connectToJira() {
        return 0;
    }

    public String getJIRA_USER() {
        return JIRA_USER;
    }

    public String getJIRA_URL() {
        return JIRA_URL;
    }

    public String getJIRA_PASSWORD() {
        return JIRA_PASSWORD;
    }
}
