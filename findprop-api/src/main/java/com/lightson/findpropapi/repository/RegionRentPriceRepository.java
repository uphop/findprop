package com.lightson.findpropapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.Region;
import com.lightson.findpropapi.entity.RegionRentPrice;

@Repository
public interface RegionRentPriceRepository extends JpaRepository<RegionRentPrice, Long> {
    RegionRentPrice findByRegionAndPropertyTypeAndBedrooms(Region region,
            String propertyType, Integer bedrooms);
}