package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.StaticSmartHome;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Service class for performing a handshake with a remote server.
 */
@Service
public class HandschakeService {
    /**
     * Sends a handshake request to the specified URL with the given payload.
     *
     * @param urlString The URL to send the handshake request to.
     * @param payload   The payload to include in the request body.
     */
    public static void sendHandshake(String urlString, String payload) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");


            connection.setDoOutput(true);

            if (payload != null && !payload.isEmpty()) {
                connection.getOutputStream().write(payload.getBytes(StandardCharsets.UTF_8));
                connection.getOutputStream().close();
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                StaticSmartHome.smartHomeId = jsonObject.getInt("id");

                in.close();

            } else {
                System.out.println("Fehler beim REST-Aufruf. Statuscode: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the service by sending an initial handshake request.
     * This method is annotated with `@PostConstruct` to indicate that it should be executed after the class is constructed.
     */
    @PostConstruct
    public void handyshakiyayy() {
        sendHandshake("https://icecreamparty.de/api/smartgrid/smart-home ", "{\"url\": \"100.92.31.21:8082\"}");
    }

    public int getSmartHomeId(String jsonResponse){
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getInt("id");
    }
}
