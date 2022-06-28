package com.lightson.findpropapi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lightson.findpropapi.entity.Postcode;
import com.lightson.findpropapi.entity.PostcodeRentPrice;

@Repository
public interface PostcodeRentPriceRepository extends JpaRepository<PostcodeRentPrice, Long> {
    @Cacheable("postcode_rent_prices")
    public List<PostcodeRentPrice> findByPostcodeAndPropertyTypeAndBedrooms(Postcode postcode,
            String propertyType, Integer bedrooms);
}
