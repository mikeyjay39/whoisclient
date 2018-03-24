package whoisclient;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;


public class Whois {

    public static void main(String[] args) {
       // whois();
        List list = getServerList();
        Map map = buildMap(list);
        System.out.println(map.get("com"));
    }

    /**
     * Imports Whois servers from a txt file
     * @return
     */
    public static List<String> getServerList() {
        List<String> list = new ArrayList<>();

        try (
                BufferedReader br = new BufferedReader(new FileReader("WhoisServers.txt"));
                // BufferedReader br = new BufferedReader(new FileReader("/home/michael/WhoisServers.txt"));
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
                        TreeMap::new));

        return map;
    }

    /**
     * Whois query
     */
    public static String whois(String domain) {
        domain = "mikeyjay.com";
        //String hostName = "whois.verisign-grs.com";
        String hostName = "whois.verisign-grs.com";
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
               // System.out.println(s);
            }
        }
        catch (Exception e) {}
        return result.toString();
    }
}
