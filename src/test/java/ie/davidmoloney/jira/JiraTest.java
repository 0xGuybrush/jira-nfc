package ie.davidmoloney.jira;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JiraTest {
    private Jira jira;

    @Before
    public void setup() {
        jira = new Jira();
    }

    @Test
    public void test_that_class_can_read_properties() throws Exception {
        assertThat(jira.getJiraUrl(), is(equalTo("foo")));
        assertThat(jira.getJiraUser(), is(equalTo("bar")));
        assertThat(jira.getJiraPassword(), is(equalTo("baz")));
    }



}
