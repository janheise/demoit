package de.janheise.mqtt.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MqttMessage {
    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("dev_id")
    private String devId;
    @JsonProperty("payload_fields")
    private Sensors sensors;
}
