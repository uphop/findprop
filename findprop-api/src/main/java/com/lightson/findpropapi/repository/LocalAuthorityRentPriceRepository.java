package com.lightson.findpropapi.repository;

import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.LocalAuthority;
import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;

@Repository
public interface LocalAuthorityRentPriceRepository extends JpaRepository<LocalAuthorityRentPrice, Long> {
    @Cacheable("local_authority_rent_prices")
    LocalAuthorityRentPrice findByLocalAuthorityAndPropertyTypeAndBedrooms(LocalAuthority localAuthority,
            String propertyType, Integer bedrooms);

    @Cacheable("local_authority_similar_rent_prices")
    @Query(value = "call get_similar_local_authority_rent_prices(:localAuthorityId, :propertyType, :bedrooms);", nativeQuery = true)
    Set<LocalAuthorityRentPrice> findSimilarLocalAuthorityRentPrices(Long localAuthorityId, String propertyType, Integer bedrooms);
}
