package de.backend.smarthome_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.backend.smarthome_backend.StaticTimeClass;
import de.backend.smarthome_backend.entity.Tarif;
import de.backend.smarthome_backend.entity.TarifEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling MQTT tariff data.
 */
@Service
public class MqttTarifService {

    private final MqttService service;
    private final TarifService tarifService;

    /**
     * Constructs a new instance of the MqttTarifService class.
     *
     * @param service      the MQTT service instance
     * @param tarifService the tariff service instance
     */
    @Autowired
    public MqttTarifService(MqttService service, TarifService tarifService) {
        this.service = service;
        this.tarifService = tarifService;
    }

    /**
     * Initializes the MQTT tariff service by subscribing to the designated topic.
     *
     * @throws Exception if an error occurs during initialization
     */
    @PostConstruct
    public void init() throws Exception {
        var clientID = "HomeTarifService";
        var topic = "fhdo/tarif";

        final var client = service.getClient(clientID);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        client.subscribe(topic, (topic1, message) -> {
            try {
                var tarif = mapper.readValue(message.getPayload(), Tarif.class);

                    TarifEntity tarifEntity = new TarifEntity(StaticTimeClass.timeAndDate.time.toString(),StaticTimeClass.timeAndDate.date.toString(),tarif.getPrice());
                    tarifService.saveTarifEntity(tarifEntity);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
