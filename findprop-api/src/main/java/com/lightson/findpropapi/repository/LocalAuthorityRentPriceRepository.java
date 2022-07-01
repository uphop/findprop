package com.lightson.findpropapi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;

@Repository
public interface LocalAuthorityRentPriceRepository extends JpaRepository<LocalAuthorityRentPrice, Long> {
        @Cacheable("local_authority_rent_prices")
        @Query(value = "select * from local_authority_rent_price where local_authority_id = :localAuthorityId and property_type = :propertyType and bedrooms = :bedrooms and source = 'ons' order by published desc;", nativeQuery = true)
        public List<LocalAuthorityRentPrice> findLocalAuthorityRentPrices(Long localAuthorityId,
                        String propertyType, Integer bedrooms);

        @Cacheable("local_authority_similar_rent_prices")
        @Query(value = "call get_similar_local_authority_rent_prices(:localAuthorityId, :propertyType, :bedrooms);", nativeQuery = true)
        public List<LocalAuthorityRentPrice> findSimilarLocalAuthorityRentPrices(Long localAuthorityId,
                        String propertyType,
                        Integer bedrooms);
}
