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
        assertThat(jira.getJIRA_URL(), is(equalTo("foo")));
        assertThat(jira.getJIRA_USER(), is(equalTo("bar")));
        assertThat(jira.getJIRA_PASSWORD(), is(equalTo("baz")));
    }
}
