package com.lightson.findpropapi.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "local_authority")
public class LocalAuthority implements Serializable {
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    @JsonIgnore
    private Region region;

    @OneToMany(mappedBy = "localAuthority", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Postcode> postcodes;

    @OneToMany(mappedBy = "localAuthority", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LocalAuthorityRentPrice> localAuthorityRentPrices;

    @ManyToMany
    @JoinTable(name = "related_local_authority", joinColumns = @JoinColumn(name = "anchor_local_authority_id"), inverseJoinColumns = @JoinColumn(name = "related_local_authority_id"))
    @JsonIgnore
    private Set<LocalAuthority> relatedAuthorities;

    public LocalAuthority(Long id, String code, String name, Region region) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.region = region;
    }

    public LocalAuthority() {
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Set<Postcode> getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(Set<Postcode> postcodes) {
        this.postcodes = postcodes;
    }

    public Set<LocalAuthorityRentPrice> getLocalAuthorityRentPrices() {
        return localAuthorityRentPrices;
    }

    public void setLocalAuthorityRentPrices(Set<LocalAuthorityRentPrice> localAuthorityRentPrices) {
        this.localAuthorityRentPrices = localAuthorityRentPrices;
    }

    @Override
    public String toString() {
        return "LocalAuthority [code=" + code + ", id=" + id + ", localAuthorityRentPrices=" + localAuthorityRentPrices
                + ", name=" + name + ", postcodes=" + postcodes + ", region=" + region + "]";
    }

    public Set<LocalAuthority> getRelatedAuthorities() {
        return relatedAuthorities;
    }

    public void setRelatedAuthorities(Set<LocalAuthority> relatedAuthorities) {
        this.relatedAuthorities = relatedAuthorities;
    }
}
