package com.lightson.findpropapi.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "postcode")
public class Postcode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "local_authority_id", nullable = false)
    @JsonIgnore
    private LocalAuthority localAuthority;

    public Postcode(Long id, String code, Double longitude, Double latitude,
            LocalAuthority localAuthority) {
        this.id = id;
        this.code = code;
        this.longitude = longitude;
        this.latitude = latitude;
        this.localAuthority = localAuthority;
    }

    public Postcode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LocalAuthority getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(LocalAuthority localAuthority) {
        this.localAuthority = localAuthority;
    }

    @Override
    public String toString() {
        return "PostcodeEntity [code=" + code + ", id=" + id + ", latitude=" + latitude + ", localAuthority="
                + localAuthority + ", longitude=" + longitude + "]";
    }
    
}
