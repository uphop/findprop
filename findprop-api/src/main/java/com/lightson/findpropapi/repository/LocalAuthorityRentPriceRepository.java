package com.lightson.findpropapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.LocalAuthority;
import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;

@Repository
public interface LocalAuthorityRentPriceRepository extends JpaRepository<LocalAuthorityRentPrice, Long> {
    LocalAuthorityRentPrice findByLocalAuthorityAndPropertyTypeAndBedrooms(LocalAuthority localAuthority,
            String propertyType, Integer bedrooms);
}
