package de.backend.smarthome_backend.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

/**
 * This class provides a service for interacting with an MQTT broker.
 */
@Service
public class MqttService {

    private String brokerUrl = "tcp://159.89.104.105:1883";
    private String username = "fhdo";
    private char[] password = "fhdo".toCharArray();

    /**
     * Returns an MQTT client with the specified client ID.
     *
     * @param clientID the client ID for the MQTT client
     * @return an MQTT client
     * @throws MqttException if an error occurs while creating or connecting the MQTT client
     */
    public MqttClient getClient(String clientID) throws MqttException {
        final var client = new MqttClient(brokerUrl, clientID, new MemoryPersistence());
        var options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password);
        options.setCleanSession(true);
        client.connect(options);
        return client;
    }

}
