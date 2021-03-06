package utils;

import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


public class ProcessUtils {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(ProcessUtils.class);

    public static void main(String args[]) {
        ProcessUtils proc = new ProcessUtils();
    }


    public String exec(String cmd,File workDir){
        String res = "";

        try {
            String line;
            Process p = Runtime.getRuntime().exec(cmd,null,workDir);
            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = bri.readLine()) != null) {
                res += line + "\n";
            }
            bri.close();
            while ((line = bre.readLine()) != null) {
                res += line  + "\n";
            }
            bre.close();
            p.waitFor();
            log.info("Done.");
        }
        catch (Exception e) {
            log.error("Error executing " + cmd,e);
        }

        return res;
    }
}
