package whoisclient;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;


public class Whois {

    public static void main(String[] args) {

        switch (args.length) {
            case 1:
                System.out.println(whois(args[0]));
                break;
            case 2:
                System.out.println(whois(args[0], args[1]));
                break;
            default:
                System.out.println("Usage: <domainname> [hostname]");
                break;
        }
    }

    /**
     * Imports Whois servers from a txt file
     * @return
     */
    public static List<String> getServerList() {
        List<String> list = new ArrayList<>();

        try (
                BufferedReader br = new BufferedReader(new FileReader("WhoisServers.txt"));
        )
        {
            String s = null;
            while ((s = br.readLine()) != null) {

                list.add(s);
            }
        }
        catch (Exception e){}
        return list;

    }

    /**
     * Builds a HashMap of Whois servers
     * key is the TLD
     * value is a list of Whois servers
     * @param list
     * @return
     */
    public static Map<String, ArrayList<String>> buildMap(List<String> list) {
        Map<String, ArrayList<String>> map = list.stream()
                .collect(Collectors.toMap(
                        ((String s) -> s.split(" ")[0]),
                        ((String s) -> {
                            ArrayList<String> aL1 = new ArrayList<>();
                            aL1.add(s.split(" ")[1]);
                            return aL1;
                        }),
                        ((s1, s2) -> {
                            ArrayList<String> aL2 = new ArrayList<>(s1);
                            aL2.add(s2.get(0));
                            return aL2;
                        }),
                        HashMap::new));

        return map;
    }

    /**
     * Method that queries Whois via sockets connection
     * @param domain
     * @param hostName
     * @return
     */
    public static String whoisQuery(String domain, String hostName) {
        int port = 43;
        StringBuilder result = new StringBuilder();
            try (
                    Socket echoSocket = new Socket(hostName, port);
                    PrintWriter out =
                            new PrintWriter(echoSocket.getOutputStream(), true);
                    BufferedReader in =
                            new BufferedReader(
                                    new InputStreamReader(echoSocket.getInputStream()));
                    BufferedReader stdIn =
                            new BufferedReader(
                                    new InputStreamReader(System.in))


            ){
                out.println(domain);
                String s = null;
                while ((s = in.readLine()) != null) {
                    result.append(s);
                }
            }
            catch (Exception e) {}
        return result.toString();
        }


    /**
     * Main Whois call method.
     * @param domain
     * @return
     */
    public static String whois(String domain) {
        List<String> servers = getWhoisServers(getTLD(domain));
        String hostName;
        int noServers = servers.size();
        String result = "";
        for(int i = 0; i < noServers && result == ""; i++) {
            hostName = servers.get(i);
            result = whois(domain, hostName);
        }
        return result;
    }

    /**
     * Whois that allows you to specify target Whois server
     * @param domain
     * @param hostname is the target Whois server
     * @return
     */
    public static String whois(String domain, String hostname) {
        return whoisQuery(domain, hostname);
    }

    /**
     * get domain's TLD
     * @param domain
     * @return
     */
    public static String getTLD(String domain) {
        return domain.replaceFirst("\\w*\\.", "");
    }

    /**
     * Returns a list of Whois servers for a specific TLD
     * @param tld
     * @return
     */
    public static List<String> getWhoisServers(String tld) {
        List list = getServerList();
        Map<String, List<String>> map = buildMap(list);
        return map.get(tld);
    }

}
