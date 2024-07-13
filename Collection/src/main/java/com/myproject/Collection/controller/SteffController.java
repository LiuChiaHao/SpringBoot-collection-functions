package com.myproject.Collection.controller;

import com.myproject.Collection.entity.StaffEntity;
import com.myproject.Collection.repository.StaffRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Staff API", description = "API for managing staff, ues JWT to login")
public class SteffController {
    private StaffRepository staffRepository;
    @Autowired
    public SteffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);



    @GetMapping("/staffs")
    @Operation(summary = "Get all staff data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all of the staff"),
            @ApiResponse(responseCode = "404", description = "Staff not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "login")
    public List<StaffEntity> findAll(){
        logger.info("Find all staffs");
        return staffRepository.findAll();
    }



    @GetMapping("/staffFindById/{staffId}")
    @Operation(summary = "Get specific staff data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the specific staff"),
            @ApiResponse(responseCode = "404", description = "Staff not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "login")
    public StaffEntity findById(
            @Parameter(description = "ID of the staff to be fetched", required = true)
            @PathVariable int staffId){

        logger.info("Find staff by ID: {}", staffId);
        StaffEntity theStaffEntity = staffRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + staffId));
        return theStaffEntity;
    }

    @Transactional
    @PostMapping("/addStaff")
    @Operation(summary = "Add specific staff data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully add the specific staff"),
            @ApiResponse(responseCode = "404", description = "Staff not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "login")
    public StaffEntity addStaff(
            @Parameter(description = "ID of the staff to be add", required = true)
            @RequestBody StaffEntity staffEntity){

        logger.info("Adding new staff: {}", staffEntity);
        staffEntity.setId(0);
        return staffRepository.save(staffEntity);
    }


    @Transactional
    @PutMapping("/updateStaff")
    @Operation(summary = "Update specific staff data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the specific staff"),
            @ApiResponse(responseCode = "404", description = "Staff not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "login")
    public StaffEntity updateStaff(
            @Parameter(description = "ID of the staff to be update", required = true)
            @RequestBody StaffEntity theStaffEntity) {
        logger.info("Updating staff: {}", theStaffEntity);
        return staffRepository.save(theStaffEntity);
    }




    @DeleteMapping("/deleteStaff/{staffId}")
    @Operation(summary = "Delete specific staff data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the specific staff"),
            @ApiResponse(responseCode = "404", description = "Staff not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "login")
    public String deleteStaff(
            @Parameter(description = "ID of the staff to be delete", required = true)
            @PathVariable int staffId) {
        logger.info("Deleting staff with ID: {}", staffId);

        staffRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + staffId));
        staffRepository.deleteById(staffId);

        return "Deleted employee id - " + staffId;
    }


    @Tag(name = "Product API", description = "API for managing products")
    public class ProductController {
        // Add your endpoints here
    }

}
