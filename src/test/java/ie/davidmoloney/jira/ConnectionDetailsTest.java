package ie.davidmoloney.jira;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConnectionDetailsTest {
    private ConnectionDetails connectionDetails;

    @Before
    public void setup() {
        connectionDetails = new ConnectionDetails();
    }

    @Test
    public void test_that_class_can_read_properties() throws Exception {
        assertThat(connectionDetails .getBaseUri(), is(equalTo("foo")));
        assertThat(connectionDetails.getUser(), is(equalTo("bar")));
        assertThat(connectionDetails.getPassword(), is(equalTo("baz")));
    }



}
