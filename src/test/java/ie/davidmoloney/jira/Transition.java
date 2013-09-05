package ie.davidmoloney.jira;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class Transition {
    public static final String ID_IN_PROGRESS = "11";
    private StringEntity entity;
    public Transition(String id) throws UnsupportedEncodingException {
        entity = new StringEntity(String.format("{\"transition\": {\"id\": \"%s\"}}", id), AuthenticatedJiraClientTest.UTF_8);
    }

    public StringEntity toStringEntity() {
        return entity;
    }
}
