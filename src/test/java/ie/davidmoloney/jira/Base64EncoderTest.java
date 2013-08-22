package ie.davidmoloney.jira;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class Base64EncoderTest {
    public static final String FRED_FRED_AS_BASE_64 = "ZnJlZDpmcmVk";
    Base64Encoder encoder = new Base64Encoder();

    @Test
    public void test() throws Exception {
        String encoded = encoder.encode("fred", "fred");
        assertThat(encoded, (equalTo(FRED_FRED_AS_BASE_64)));
    }
}
