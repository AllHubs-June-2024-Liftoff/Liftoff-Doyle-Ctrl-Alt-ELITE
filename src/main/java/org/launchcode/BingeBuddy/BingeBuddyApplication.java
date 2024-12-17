package org.launchcode.BingeBuddy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@SpringBootApplication
public class BingeBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingeBuddyApplication.class, args);

        try {
            // Public API
            String apiUrl = "https://www.omdbapi.com/#usage";

            // Connect to the API
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Check for a successful connection
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            // Read the API response
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            scanner.close();

            // Print the raw JSON response
            System.out.println("Raw JSON Response: " + informationString);

            // Parse JSON response using Gson
            JsonArray jsonArray = JsonParser.parseString(informationString.toString()).getAsJsonArray();

            if (jsonArray.size() > 0) {
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                // Extract and print specific data
                int woeid = jsonObject.get("woeid").getAsInt();
                String location = jsonObject.get("title").getAsString();

                System.out.println("Location: " + location);
                System.out.println("WOEID: " + woeid);
            } else {
                System.out.println("No data found for the provided query.");
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}



