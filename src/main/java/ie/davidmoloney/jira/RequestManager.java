package ie.davidmoloney.jira;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.Properties;

public class RequestManager {

    private String JIRA_URL;
    private String JIRA_USER;
    private String JIRA_PASSWORD;

    public RequestManager() {
        Properties properties = getPropertiesFromFile();
        JIRA_URL = properties.getProperty("url");
        JIRA_USER = properties.getProperty("user");
        JIRA_PASSWORD = properties.getProperty("password");
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


    public Header createAuthorizationHeader(String username, String password) {
        StringBuilder authorizationValue = new StringBuilder();
        authorizationValue.append("Basic ");
        String userPassCombo = username + ":" + password;
        String encodedUserPassCombo = new String(Base64.encodeBase64(userPassCombo.getBytes()));
        authorizationValue.append(encodedUserPassCombo);
        return new BasicHeader(HttpHeaders.AUTHORIZATION, authorizationValue.toString());
    }

    public Header createContentTypeHeader(ContentType contentType) {
        return new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.JSON.getValue());
    }
}