package whoisclient;

import org.junit.*;
import java.util.ArrayList;
import java.util.List;

import static whoisclient.Whois.*;

public class WhoisTest {
    String result = "";
    String com = "hostica.com";


    @Before
    public void setUp() {
        result = "";
    }

    @After
    public void tearDown() {
        result = "";
    }

    @Test
    public void testComLookup() {
        result = whois(com);
        Assert.assertNotEquals("", result);
    }


    //Tests backup whois server for .com TLDs

    @Test
    public void testComLookup2() {
        result = whois(com, "whois.iana.org");
        Assert.assertNotEquals("", result);
    }

    @Test
    public void testOrgLookup() {
        result = whois("hostica.org");
        Assert.assertNotEquals("", result);
    }

    @Test
    public void testNetLookup() {
        result = whois("hostica.net");
        Assert.assertNotEquals("", result);
    }

    @Test
    public void testTLD() {
        result = getTLD("mikeyjay.com");
        Assert.assertEquals("com", result);
    }

    @Test
    public void testGetWhoisServers() {
        List<String> servers = getWhoisServers("com");
        List<String> expected = new ArrayList<>();
        expected.add("whois.verisign-grs.com");
        expected.add("whois.iana.org");
        Assert.assertEquals(expected, servers);
    }
}
