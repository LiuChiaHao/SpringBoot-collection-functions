package com.myproject.Collection.controller;

import com.myproject.Collection.entity.StaffEntity;
import com.myproject.Collection.repository.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SteffController {
    private StaffRepository staffRepository;
    @Autowired
    public SteffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);



    @GetMapping("/staffs")
    public List<StaffEntity> findAll(/*@RequestHeader(value = "Authorization", required = false) String token*/){
/*
        logger.info("sssssssssssssssssssssss " + token);

        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            logger.info("JWT Token: " + jwtToken);
        } else {
            logger.info("No JWT Token found in the request header.");
        }
*/
        logger.info("Find all staff");
        return staffRepository.findAll();
    }


/*
    GetMapping("/staffs")
    public List<StaffEntity> findAll(){
        logger.info("Find all staff");
        return staffRepository.findAll();
    }*/

    @GetMapping("/staffFindById/{staffId}")
    public StaffEntity findById(@PathVariable int staffId){
        logger.info("Find staff by ID: {}", staffId);
        StaffEntity theStaffEntity = staffRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + staffId));
        return theStaffEntity;
    }

    //haven't finish 401 errer
    @Transactional
    @PostMapping("/addStaff")
    public StaffEntity addStaff(@RequestBody StaffEntity staffEntity){
        logger.info("Adding new staff: {}", staffEntity);
        staffEntity.setId(0);
        return staffRepository.save(staffEntity);
    }


    @Transactional
    @PutMapping("/updateStaff")
    public StaffEntity updateStaff(@RequestBody StaffEntity theStaffEntity) {
        logger.info("Updating staff: {}", theStaffEntity);
        return staffRepository.save(theStaffEntity);
    }




    @DeleteMapping("/deleteStaff/{staffId}")
    public String deleteStaff(@PathVariable int staffId) {
        logger.info("Deleting staff with ID: {}", staffId);

        staffRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + staffId));
        staffRepository.deleteById(staffId);

        return "Deleted employee id - " + staffId;
    }

}
