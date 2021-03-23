package com.example.TaskScheduler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportTaskStatus() {
        String result;
        String url = "https://6057fa1ec3f49200173ad23.mockapi.io";
        String endResult [] = new String[3];
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            // Set connection timeout
            con.setConnectTimeout(3000);
            con.connect();

            int code = con.getResponseCode();
            if (code <500) {
                result = "UP";
                endResult[0]=url;
                endResult[1]=result;
                endResult[2]=timestamp.toString();
            }

        } catch (Exception e) {
            result = "DOWN";
            endResult[0]=url;
            endResult[1]=result;
            endResult[2]=timestamp.toString();
        }

        log.info("\n\nAPI URL - "+endResult[0]+"\nAPI Status - "+endResult[1]+"\nTime - {}",dateFormat.format(new Date()));
    }
}
