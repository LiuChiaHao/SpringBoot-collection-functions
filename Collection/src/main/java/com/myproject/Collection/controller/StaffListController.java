package com.myproject.Collection.controller;

import com.myproject.Collection.dto.LoginRequestDTO;
import com.myproject.Collection.entity.StaffEntity;
import com.myproject.Collection.repository.StaffRepository;
import com.myproject.Collection.service.FileStorageService;
import com.myproject.Collection.service.FileStorageServiceImplement;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/staffList")
public class StaffListController {
    private StaffRepository staffRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public StaffListController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String showStaffList(Model theModel){
        logger.info("Showing staff list");
/*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        LoginRequestDTO.UserDTO userDTO = new LoginRequestDTO.UserDTO();
        userDTO.setUsername(username);*/

        List<StaffEntity> theStaff = staffRepository.findAll();
        theModel.addAttribute("staff", theStaff);
        return "staffs/staff-list-page";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        logger.info("Showing add new staff page");
        StaffEntity theStaffEntity = new StaffEntity();
        theModel.addAttribute("staff", theStaffEntity);
        return "staffs/add-staff-page";
    }


    @GetMapping("/showFormForPaypal")
    public String showPaypal(Model theModel) {
        logger.info("Showing paypal page");
        return "paypal-page";
    }

    @GetMapping("/showFormForJWT")
    public String showFormForJWT(Model theModel) {
        logger.info("Showing JWT page");
        return "JWT-page";
    }

    @PostMapping("/uploadFile")
        public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes theRedirectAttributes) {
        logger.info("Uploading file: {}", file.getOriginalFilename());
        ResponseEntity<String> response = fileStorageService.saveFile(file);
        theRedirectAttributes.addFlashAttribute("fileStatus", response.getBody());
        return "redirect:/staffList";
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        logger.info("Downloading file: {}", fileName);
        return fileStorageService.downloadFile(fileName);
    }

    @GetMapping("/showFormForUpdate")
    public String updateStaff(@RequestParam("staffId") int theId, Model theModel){
        logger.info("Showing form to update staff with ID: {}", theId);
        StaffEntity theStaffEntity = staffRepository.findById(theId).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + theId));
        theModel.addAttribute("staff", theStaffEntity);
        return "staffs/add-staff-page";
    }

    @GetMapping("/delete")
    public String deleteStaff(@RequestParam("staffId") int theId){
        logger.info("Deleting staff with ID: {}", theId);
        StaffEntity theStaffEntity = staffRepository.findById(theId).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + theId));
        staffRepository.delete(theStaffEntity);
        return "redirect:/staffList";
    }

    @PostMapping("/save")
    public String saveStaff(@Valid @ModelAttribute("staff") StaffEntity theStaffEntity, BindingResult bindingResult){
        logger.info("Saving staff: {}", theStaffEntity);

        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors encountered while saving staff: {}", bindingResult.getAllErrors());
            return "staffs/add-staff-page";
        }
        staffRepository.save(theStaffEntity);
        return "redirect:/staffList";
    }
}
