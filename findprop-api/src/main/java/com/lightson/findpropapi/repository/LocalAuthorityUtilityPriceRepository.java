package com.lightson.findpropapi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lightson.findpropapi.entity.LocalAuthorityUtilityPrice;

@Repository
public interface LocalAuthorityUtilityPriceRepository extends JpaRepository<LocalAuthorityUtilityPrice, Long> {
    @Cacheable("local_authority_utility_prices")
    @Query(value = """
            select * from local_authority_utility_price
            where local_authority_id = :localAuthorityId and
            property_type = :propertyType and
            bedrooms = :bedrooms
            order by published desc;
             """, nativeQuery = true)
    public List<LocalAuthorityUtilityPrice> findLocalAuthorityUtilityPrices(Long localAuthorityId,
            String propertyType, Integer bedrooms);
}
