package ie.davidmoloney.jira;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class AuthenticatedJiraClient extends DefaultHttpClient {

    private final ConnectionDetails connectionDetails = new ConnectionDetails();

    public HttpResponse execute(String relativeUri) {
        HttpGet get = new AuthorizedNoFollowGet(connectionDetails, relativeUri);
        try {
            return super.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}