package com.lightson.findpropapi.repository;

import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.LocalAuthority;
import com.lightson.findpropapi.entity.LocalAuthorityUtilityPrice;

@Repository
public interface LocalAuthorityUtilityPriceRepository extends JpaRepository<LocalAuthorityUtilityPrice, Long> {
    @Cacheable("local_authority_utility__prices")
    Set<LocalAuthorityUtilityPrice> findByLocalAuthorityAndPropertyTypeAndBedrooms(LocalAuthority localAuthority,
            String propertyType, Integer bedrooms);
}
