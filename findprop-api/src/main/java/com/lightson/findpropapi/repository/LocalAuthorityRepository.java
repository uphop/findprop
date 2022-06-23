package com.lightson.findpropapi.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.LocalAuthority;

@Repository
public interface LocalAuthorityRepository extends JpaRepository<LocalAuthority, Long> {

    @Cacheable("local_authorities")
    public LocalAuthority getById(Long id);
   
}
