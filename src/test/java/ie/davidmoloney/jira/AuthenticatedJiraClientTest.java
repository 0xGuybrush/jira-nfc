package ie.davidmoloney.jira;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AuthenticatedJiraClientTest {

    public static final String BROWSE = "/browse/";
    public static final String PROJECT_KEY = "VAA";
    public static final String SEPARATOR = "-";

    @Test
    public void test_that_an_unauthenticated_get_request_returns_the_correct_response() throws Exception {
        ConnectionDetails connectionDetails = new ConnectionDetails();
        HttpGet get = new NoFollowGet(connectionDetails.getBaseUri() + BROWSE + PROJECT_KEY + SEPARATOR + "11144");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        Header location = response.getLastHeader("Location");

        assertThat(response.getStatusLine().getStatusCode(), is(302));
        assertThat(location.getValue(),
                is(connectionDetails.getBaseUri() + "/login.jsp?permissionViolation=true&os_destination=%2Fbrowse%2F" + PROJECT_KEY + SEPARATOR + "11144"));
    }

    @Test
    public void test_that_an_authenticated_get_request_with_invalid_credentials_will_return_the_correct_response() throws Exception {
        HttpClient client = new DefaultHttpClient();
        ConnectionDetails connectionDetails = new ConnectionDetails();
        connectionDetails.setUSER("fred");
        connectionDetails.setPASSWORD("fred");

        HttpGet get = new AuthorizedNoFollowGet(connectionDetails, BROWSE + PROJECT_KEY + SEPARATOR + "11144");
        HttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode(), is(401));
    }

    @Test
    public void test_that_an_authenticated_get_request_with_valid_credentials_will_return_the_correct_response() throws Exception {
        HttpClient client = new DefaultHttpClient();
        ConnectionDetails connectionDetails = new ConnectionDetails();
        HttpGet get = new AuthorizedNoFollowGet(connectionDetails, BROWSE + PROJECT_KEY + SEPARATOR + "11144");
        HttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode(), is(200));
    }

}
