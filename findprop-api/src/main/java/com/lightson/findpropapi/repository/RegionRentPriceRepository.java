package com.lightson.findpropapi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.Region;
import com.lightson.findpropapi.entity.RegionRentPrice;

@Repository
public interface RegionRentPriceRepository extends JpaRepository<RegionRentPrice, Long> {
        @Cacheable("region_rent_prices")
        public List<RegionRentPrice> findByRegionAndPropertyTypeAndBedrooms(Region region,
                        String propertyType, Integer bedrooms);
}