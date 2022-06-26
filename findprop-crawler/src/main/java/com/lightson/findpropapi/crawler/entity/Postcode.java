package com.lightson.findpropapi.crawler.entity;

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

    @Column(name = "local_authority_id")
    private Integer local_authority_id;

    public Postcode(Long id, String code, Double longitude, Double latitude,
            Integer local_authority_id) {
        this.id = id;
        this.code = code;
        this.longitude = longitude;
        this.latitude = latitude;
        this.local_authority_id = local_authority_id;
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

    public Integer getLocalAuthority() {
        return local_authority_id;
    }

    public void setLocalAuthority(Integer local_authority_id) {
        this.local_authority_id = local_authority_id;
    }

    @Override
    public String toString() {
        return "PostcodeEntity [code=" + code + ", id=" + id + ", latitude=" + latitude + ", local_authority_id="
                + local_authority_id + ", longitude=" + longitude + "]";
    }

}
