package com.lightson.findpropapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;

@Repository
public interface PostcodeAreaRentPriceRepository extends JpaRepository<PostcodeAreaRentPrice, Long> {
    PostcodeAreaRentPrice findByPostcodeAreaAndPropertyTypeAndBedrooms(String postcodeArea,
            String propertyType, Integer bedrooms);
}
