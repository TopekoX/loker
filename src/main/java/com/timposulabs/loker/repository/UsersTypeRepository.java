package com.timposulabs.loker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timposulabs.loker.entity.UsersType;

@Repository
public interface UsersTypeRepository extends JpaRepository<UsersType, Long> {

}
