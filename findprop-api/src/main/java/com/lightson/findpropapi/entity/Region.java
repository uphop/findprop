package com.lightson.findpropapi.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "region")
public class Region implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "code")
    @JsonIgnore
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LocalAuthority> localAuthorities;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RegionRentPrice> regionRentPrices;

    public Region(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Region() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LocalAuthority> getLocalAuthorities() {
        return localAuthorities;
    }

    public void setLocalAuthorities(Set<LocalAuthority> localAuthorities) {
        this.localAuthorities = localAuthorities;
    }

    public Set<RegionRentPrice> getRegionRentPrices() {
        return regionRentPrices;
    }

    public void setRegionRentPrices(Set<RegionRentPrice> regionRentPrices) {
        this.regionRentPrices = regionRentPrices;
    }

    @Override
    public String toString() {
        return "Region [code=" + code + ", id=" + id + ", localAuthorities=" + localAuthorities + ", name=" + name
                + ", regionRentPrices=" + regionRentPrices + "]";
    }

}
