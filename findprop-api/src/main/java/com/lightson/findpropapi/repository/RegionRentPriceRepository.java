package com.lightson.findpropapi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lightson.findpropapi.entity.RegionRentPrice;

@Repository
public interface RegionRentPriceRepository extends JpaRepository<RegionRentPrice, Long> {
        @Cacheable("region_rent_prices")
        @Query(value = "select * from region_rent_price where region_id = :regionId and property_type = :propertyType and bedrooms = :bedrooms and source = 'ons' order by published desc;", nativeQuery = true)
        public List<RegionRentPrice> findRegionRentPrices(Long regionId,
                        String propertyType, Integer bedrooms);
}