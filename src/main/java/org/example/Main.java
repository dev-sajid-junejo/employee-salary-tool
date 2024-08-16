package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Step 1: Get the public IP address of the user
            String ipServiceUrl = "http://checkip.amazonaws.com";
            URL url = new URL(ipServiceUrl);
            Scanner scanner = new Scanner(url.openStream());
            String ipAddress = scanner.useDelimiter("\\A").next().trim();
            scanner.close();

            System.out.println("User's IP Address: " + ipAddress);

            // Step 2: Fetch the API key from environment variables
            String apiKey = System.getenv("IPAPI_KEY");

            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("API key is missing. Please set the IPAPI_KEY environment variable.");
            }

            // Step 3: Use the IP address to get the geolocation information
            String geoUrl = "http://api.ipapi.com/" + ipAddress + "?access_key=" + apiKey;

            HttpURLConnection connection = (HttpURLConnection) new URL(geoUrl).openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Scanner geoScanner = new Scanner(reader);
            String response = geoScanner.useDelimiter("\\A").next();
            geoScanner.close();

            // Parse the JSON response using Gson
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            String countryCode = jsonObject.get("country_code").getAsString();
            String countryName = jsonObject.get("country_name").getAsString();

            // Print the country code and name
            System.out.println("User's country code: " + countryCode);
            System.out.println("User's country name: " + countryName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
