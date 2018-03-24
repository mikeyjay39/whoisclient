package whoisclient;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Whois {

    public static List<String> getServerList() {
        List<String> list = new ArrayList<>();

        try (
                BufferedReader br = new BufferedReader(new FileReader("/home/michael/WhoisServers.txt"));
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

    public static void whois() {
        String domain = "mikeyjay.com";
        String hostName = "whois.verisign-grs.com";
        int port = 43;

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
                System.out.println(s);
            }
        }
        catch (Exception e) {}
    }
}
