package com.backend.agrosensor.agrosensorbackend.entity.base.device;

import java.util.List;

import com.backend.agrosensor.agrosensorbackend.entity.base.Measurement;
import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Esp32.class, name = "esp32")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "device")
@Data
public abstract class Device {
    @Id
    private String id;
    private String type;
    private Boolean active;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device")
    @JsonIgnore
    private List<Measurement> measurements;

}
