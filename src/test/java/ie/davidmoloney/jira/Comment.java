package ie.davidmoloney.jira;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class Comment {
    StringEntity entity;
    Comment(String comment) throws UnsupportedEncodingException {
        entity = new StringEntity(String.format("{\"update\":{\"comment\":[{\"add\":{\"body\":\"%s\"}}]}}", comment), AuthenticatedJiraClientTest.UTF_8);
    }

    public StringEntity toStringEntity() {
        return entity;
    }
}
