package com.lightson.findpropapi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;

@Repository
public interface PostcodeAreaRentPriceRepository extends JpaRepository<PostcodeAreaRentPrice, Long> {
    @Cacheable("postcode_area_rent_prices")
    public List<PostcodeAreaRentPrice> findByPostcodeAreaAndPropertyTypeAndBedrooms(String postcodeArea,
            String propertyType, Integer bedrooms);
}
