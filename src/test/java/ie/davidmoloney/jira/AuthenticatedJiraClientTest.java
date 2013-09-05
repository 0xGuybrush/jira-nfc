package ie.davidmoloney.jira;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    protected String test_issue = BROWSE + TEST_KEY + SEPARATOR + "36";
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
    public void test_that_an_unauthenticated_put_request_returns_the_correct_response() throws Exception {
        HttpPut put = new HttpPut(connectionDetails.getBaseUri() + test_issue);
        HttpResponse response = client.execute(put);
        assertThat(response.getStatusLine().getStatusCode(), is(302));
        Header location = response.getLastHeader("Location");

        assertThat(location.getValue(),
                is(connectionDetails.getBaseUri() + "/login.jsp?permissionViolation=true&os_destination=%2Fbrowse%2F" + TEST_KEY + SEPARATOR + "36"));
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
    public void test_that_an_authenticated_put_request_with_invalid_credentials_will_return_the_correct_response() throws Exception {
        connectionDetails.setUSER("fred");
        connectionDetails.setPASSWORD("fred");

        HttpPut put = new AuthorizedNoFollowPut(connectionDetails, test_issue);
        HttpResponse response = client.execute(put);

        assertThat(response.getStatusLine().getStatusCode(), is(401));

    }

    @Test
    public void test_that_an_authenticated_get_request_with_valid_credentials_will_return_the_correct_response() throws Exception {
        HttpGet get = new AuthorizedNoFollowGet(connectionDetails, vaa11144);
        HttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode(), is(200));

    }

    @Test
    @Ignore
    public void test_that_an_authenticated_put_request_with_valid_credentials_can_add_a_comment_to_an_issue() throws Exception {
        HttpPut put = new AuthorizedNoFollowPut(connectionDetails, test_issue);
        put.addHeader("Content-Type", "application/json");
        //CURL DATA format: --data "{\"update\":{\"comment\":[{\"add\":{\"body\":\"Hello\"}}]}}"

        StringEntity entity = new StringEntity("{\"update\":{\"comment\":[{\"add\":{\"body\":\"Hello\"}}]}}","UTF-8");
        put.setEntity(entity);
        HttpResponse response = client.execute(put);

        assertThat(response.getStatusLine().getStatusCode(), is(204));

    }

    @Test
    public void test_that_an_authenticated_get_request_to_the_rest_api_will_return_details_as_json() throws Exception {
        HttpResponse response = getHttpResponseForTestIssue();
        assertThat(response.getStatusLine().getStatusCode(), is(200));
        assertThat(response.getHeaders("Content-Type")[0].getValue(), is(equalTo("application/json;charset=UTF-8")));
    }

    private HttpResponse getHttpResponseForTestIssue() throws IOException {
        HttpGet get = new AuthorizedNoFollowGet(connectionDetails, test_rest_issue);
        return client.execute(get);
    }

    @Test
    public void test_that_we_can_parse_the_status_of_a_ticket_from_the_response() throws Exception {
        JiraResponseParser jiraResponseParser = new JiraResponseParser();
        JiraTicket jiraTicket = jiraResponseParser.parse(getHttpResponseForTestIssue());
        assertThat(jiraTicket.getStatusId(), is(equalTo(1)));
    }

    @Test
    public void test_that_we_can_update_an_issue_with_a_comment() throws Exception {


    }

    private class JiraResponseParser {
        private JsonParser jsonParser = new JsonParser();
        private JsonElement jsonElement;

        public JiraTicket parse(HttpResponse responseFromJira) {
            try {
                StringBuilder jsonResponse = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseFromJira.getEntity().getContent(), "UTF-8"));
                for (String line = null; (line = reader.readLine()) != null;) {
                    jsonResponse.append(line).append("\n");
                }
                jsonElement = jsonParser.parse(jsonResponse.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }


            return new JiraTicket(jsonElement);
        }
    }

    private class JiraTicket {
        public static final String ID_KEY = "id";
        private JsonObject ticketFields;
        private JsonObject statusField;
        private int statusId;

        public JiraTicket(JsonElement jsonElement) {
            this.ticketFields = ((JsonObject) jsonElement).getAsJsonObject("fields");
            this.statusField = ticketFields.getAsJsonObject("status");
            this.statusId = statusField.get(ID_KEY).getAsInt();
        }

        public int getStatusId() {
            return statusId;
        }
    }
}
