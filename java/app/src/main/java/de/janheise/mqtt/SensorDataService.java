package de.janheise.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.janheise.mqtt.message.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

@Service
@Transactional
public class SensorDataService {
    private Logger log = LoggerFactory.getLogger(SensorDataService.class);
    private SensorDataRepository sensorDataRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public void createFromPayload(final String message) {
        log.debug(message);
        try {
            final MqttMessage mqtt = mapper.readValue(message, MqttMessage.class);
            final Timestamp now = new Timestamp(new Date().getTime());
            mqtt.getSensors().getPayloads().stream().forEach(
                    payload -> {
                        SensorData sd = new SensorData();
                        sd.setDateAndTimeOfImport(now);
                        sd.setAppId(mqtt.getAppId());
                        sd.setDevId(mqtt.getDevId());
                        sd.setSensorId(payload.getSensorId());
                        sd.setValueAsString(payload.getValue());
                        sensorDataRepository.save(sd);
                    }
            );

        } catch (Exception ex) {
            log.error("Could not decode JSON.", ex);
        }
    }
}
