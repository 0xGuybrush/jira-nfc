package ie.davidmoloney.jira;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JiraTest {
    private Jira jira;

    @Test
    public void test_that_class_can_reach_jira() throws Exception {
        assertThat(jira.connectToJira(), is(equalTo(200)));
    }
}
