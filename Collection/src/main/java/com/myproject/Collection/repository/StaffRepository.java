package com.myproject.Collection.repository;

import com.myproject.Collection.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
//Spring Data JPA repository for managing StaffEntity objects.
public interface StaffRepository extends JpaRepository<StaffEntity, Integer>{
}
