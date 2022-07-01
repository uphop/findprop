package com.lightson.findpropapi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lightson.findpropapi.entity.PostcodeRentPrice;

@Repository
public interface PostcodeRentPriceRepository extends JpaRepository<PostcodeRentPrice, Long> {
    @Cacheable("postcode_rent_prices")
    @Query(value = """
                        select * from postcode_rent_price
                        where postcode_id = :postcodeId and
                        property_type = :propertyType and
                        bedrooms = :bedrooms and 
                        source = 'property_data'
                        order by published desc;
                         """, nativeQuery = true)
    public List<PostcodeRentPrice> findPostcodeRentPrices(Long postcodeId,
            String propertyType, Integer bedrooms);
}
