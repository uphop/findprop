package com.lightson.findpropapi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;

@Repository
public interface PostcodeAreaRentPriceRepository extends JpaRepository<PostcodeAreaRentPrice, Long> {
    @Cacheable("postcode_area_rent_prices")
    @Query(value = """
                        select * from postcode_area_rent_price
                        where postcode_area = :postcodeArea and
                        property_type = :propertyType and
                        bedrooms = :bedrooms and 
                        source = 'ons'
                        order by published desc;
                         """, nativeQuery = true)
    public List<PostcodeAreaRentPrice> findPostcodeAreaRentPrices(String postcodeArea,
            String propertyType, Integer bedrooms);
}
