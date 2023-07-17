package de.backend.smarthome_backend.service;

import de.backend.smarthome_backend.entity.Schedule;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class provides a service for sending schedules to an MQTT broker in the context of a grid.
 */
@Service
public class MqttServiceForGrid {

    private final MqttService service;

    /**
     * Constructs a new instance of {@code MqttServiceForGrid} with the provided {@code MqttService}.
     *
     * @param service the MQTT service to be used for communication with the broker
     */
    @Autowired
    public MqttServiceForGrid(MqttService service) {
        this.service = service;
    }

    /**
     * Sends the specified schedule to the grid via MQTT.
     *
     * @param schedule the schedule to be sent to the grid
     * @throws MqttException if an error occurs while sending the schedule
     */
    public void sendScheduleToGrid(Schedule schedule) throws MqttException {
        final MqttClient scheduleClient = service.getClient("scheduleService");
        scheduleClient.publish("fhdo/schedule", new MqttMessage(schedule.toString().getBytes()));
    }
}
