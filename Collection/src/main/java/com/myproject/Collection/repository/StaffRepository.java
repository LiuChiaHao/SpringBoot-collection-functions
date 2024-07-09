package com.myproject.Collection.repository;

import com.myproject.Collection.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffEntity, Integer>{
}
