package de.janheise.mqtt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SensorData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Basic
    private String appId;

    @Basic
    private String devId;

    @Basic
    private String sensorId;

    @Basic
    private String valueAsString;

    @Basic
    private Timestamp dateAndTimeOfImport = new Timestamp(new Date().getTime());

    @Basic
    private Timestamp processed;
}
