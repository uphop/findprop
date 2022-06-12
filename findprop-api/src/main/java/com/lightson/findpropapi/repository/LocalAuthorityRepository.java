package com.lightson.findpropapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightson.findpropapi.entity.LocalAuthority;

@Repository
public interface LocalAuthorityRepository extends JpaRepository<LocalAuthority, Long> {

}
