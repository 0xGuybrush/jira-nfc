package ie.davidmoloney.jira;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AuthenticatedJiraClientTest {

    public static final String BROWSE = "/browse/";
    public static final String REST = "/rest/api/latest/issue/";
    public static final String PROJECT_KEY = "VAA";
    public static final String SEPARATOR = "-";
    private static final String TEST_KEY = "TEST";
    protected String vaa11144 = BROWSE + PROJECT_KEY + SEPARATOR + "11144";
    protected String test_rest_issue = REST + TEST_KEY + SEPARATOR + "36";
    protected HttpClient client;
    protected ConnectionDetails connectionDetails;


    @Before
    public void setUp() throws Exception {
        client = new DefaultHttpClient();
        connectionDetails = new ConnectionDetails();
    }

    @Test
    public void test_that_an_unauthenticated_get_request_returns_the_correct_response() throws Exception {
        HttpGet get = new NoFollowGet(connectionDetails.getBaseUri() + vaa11144);
        HttpResponse response = client.execute(get);
        Header location = response.getLastHeader("Location");

        assertThat(response.getStatusLine().getStatusCode(), is(302));
        assertThat(location.getValue(),
                is(connectionDetails.getBaseUri() + "/login.jsp?permissionViolation=true&os_destination=%2Fbrowse%2F" + PROJECT_KEY + SEPARATOR + "11144"));
    }

    @Test
    public void test_that_an_authenticated_get_request_with_invalid_credentials_will_return_the_correct_response() throws Exception {
        connectionDetails.setUSER("fred");
        connectionDetails.setPASSWORD("fred");

        HttpGet get = new AuthorizedNoFollowGet(connectionDetails, vaa11144);
        HttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode(), is(401));
    }

    @Test
    public void test_that_an_authenticated_get_request_with_valid_credentials_will_return_the_correct_response() throws Exception {
        HttpGet get = new AuthorizedNoFollowGet(connectionDetails, vaa11144);
        HttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode(), is(200));

    }

    @Test
    public void test_that_an_authenticated_get_request_to_the_rest_api_will_return_details_as_json() throws Exception {
        HttpGet get = new AuthorizedNoFollowGet(connectionDetails, test_rest_issue);
        HttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode(), is(200));
        assertThat(response.getHeaders("Content-Type")[0].getValue(), is(equalTo("application/json;charset=UTF-8")));
    }
}
