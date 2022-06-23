package com.lightson.findpropapi.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    @Cacheable("regions")
    Region findByName(String name);

    @Cacheable("regions")
    public Region getById(Long id);
}
