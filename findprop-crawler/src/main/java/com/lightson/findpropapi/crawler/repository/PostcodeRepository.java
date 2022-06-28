package com.lightson.findpropapi.crawler.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lightson.findpropapi.crawler.entity.Postcode;

@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, Long> {
    @Query(value = "call get_nearest_postcodes(:longitude, :latitude, :max_range);", nativeQuery = true)
    ArrayList<Postcode> findByDistance(
            @Param("longitude") double longitude,
            @Param("latitude") double latitude,
            @Param("max_range") double maxRange);
}
