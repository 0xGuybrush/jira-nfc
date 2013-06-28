package ie.davidmoloney.jira;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class RequestManagerTest {

    private RequestManager requestManager;

    @Before
    public void setUp() throws Exception {
        requestManager = new RequestManager();
    }

    @Test
    public void test_that_we_can_base64_encode_the_username_and_password() throws Exception {
        String sampleUsername = "fred";
        String samplePassword = "fred";
        String expectedHeaderValue = "Basic ZnJlZDpmcmVk";

        Header returnedAuthorizationHeader = requestManager.createAuthorizationHeader(sampleUsername, samplePassword);

        assertThat(returnedAuthorizationHeader, is(BasicHeader.class));
        assertThat(returnedAuthorizationHeader.getName(), is(equalTo("Authorization")));
        assertThat(returnedAuthorizationHeader.getValue(), is(equalTo(expectedHeaderValue)));
    }

    @Test
    public void test_that_we_can_request_the_correct_content_type() throws Exception {
        Header returnedContentTypeHeader = requestManager.createContentTypeHeader(ContentType.JSON);
        assertThat(returnedContentTypeHeader.getName(), is(equalTo("Content-Type")));
        assertThat(returnedContentTypeHeader.getValue(), is(equalTo("application/json")));
    }

    @Test
    public void test_that_an_unauthenticated_get_request_returns_the_correct_response() throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(requestManager.getJiraUrl() + "/browse/VAA-11144");
        get.getParams().setParameter("http.protocol.handle-redirects", false);
        HttpResponse response = client.execute(get);
        assertThat(response.getStatusLine().getStatusCode(), is(302));
        Header location = response.getLastHeader("Location");
        assertThat(location.getValue(),
                is(requestManager.getJiraUrl() +"/login.jsp?permissionViolation=true&os_destination=%2Fbrowse%2FVAA-11144"));
    }

    @Test
    public void test_that_an_authenticated_get_request_with_invalid_credentials_will_return_the_correct_response() throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(requestManager.getJiraUrl() + "/browse/VAA-11144");
        get.getParams().setParameter("http.protocol.handle-redirects", false);
        get.setHeader(requestManager.createAuthorizationHeader("fred", "fred"));
        HttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode(), is(401));
    }

    @Test
    public void test_that_an_authenticated_get_request_with_valid_credentials_will_return_the_correct_response() throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(requestManager.getJiraUrl() + "/browse/VAA-11144");
        get.getParams().setParameter("http.protocol.handle-redirects", false);
        get.setHeader(requestManager.createAuthorizationHeader(requestManager.getJiraUser(), requestManager.getJiraPassword()));
        HttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode(), is(200));
    }

}
