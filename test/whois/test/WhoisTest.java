package whois.test;

import org.junit.*;
import whoisclient.*;
import static whoisclient.Whois.*;

public class WhoisTest {
    String result = "";


    @Before
    public void setUp() {
        result = "";
    }

    @After
    public void tearDown() {
        result = "";
    }

    @Test
    public void comLookup() {
        result = whois("mikeyjay.com");
        Assert.assertNotEquals("", result);
    }
}
