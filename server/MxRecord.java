package greeleysmtpserver.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MxRecord {

    private String domain;

    public MxRecord(String domain) {
        this.domain = domain;
    }

    public List<String> executeLookup() {

        String command = "dig " + domain + "mx +short | sort -n";
        List<String> mailServers = new ArrayList<>();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                    mailServers.add(line);
            }

            if (mailServers.isEmpty()) return null;
            else return mailServers;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

