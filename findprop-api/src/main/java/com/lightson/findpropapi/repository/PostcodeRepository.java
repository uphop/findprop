package com.lightson.findpropapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.Postcode;

@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, Long> {
    Postcode findByCode(String code);

    @Query(value = "call get_nearest_postcodes(:longitude, :latitude, :max_range);", nativeQuery = true)
    Postcode findByDistance(
            @Param("longitude") double longitude,
            @Param("latitude") double latitude,
            @Param("max_range") double maxRange);
}
