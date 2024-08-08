package com.myproject.Collection.controller;

import com.myproject.Collection.dto.LoginRequestDTO;
import com.myproject.Collection.entity.StaffEntity;
import com.myproject.Collection.exception.StorageFileNotFoundException;
import com.myproject.Collection.repository.StaffRepository;
import com.myproject.Collection.service.FileStorageService;
import com.myproject.Collection.service.FileStorageServiceImplement;

import com.myproject.Collection.service.StorageServiceImplement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/staffList")
public class StaffListController {
    private StaffRepository staffRepository;
    private final StorageServiceImplement storageServiceImplement;

    //injection a FileStorageService
    @Autowired
    private FileStorageService fileStorageService;


    @Autowired
    public StaffListController(StaffRepository staffRepository, StorageServiceImplement storageServiceImplement) {
        this.staffRepository = staffRepository;
        this.storageServiceImplement = storageServiceImplement;
    }


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    //var staffRepository get all staff and send it to the staff-list-page
    //Model.addAttribute is a method that send data to html
    @GetMapping
    public String showStaffList(Model theModel){
        logger.info("Showing staff list");
        List<StaffEntity> theStaff = staffRepository.findAll();
        theModel.addAttribute("staff", theStaff);
        return "staffs/staff-list-page";
    }

    //showing a page to add staff
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        logger.info("Showing add new staff page");
        StaffEntity theStaffEntity = new StaffEntity();
        theModel.addAttribute("staff", theStaffEntity);
        return "staffs/add-staff-page";
    }

    //showing a page to send and get paypal API
    @GetMapping("/showFormForPaypal")
    public String showPaypal(Model theModel) {
        logger.info("Showing paypal page");
        return "paypal-page";
    }

    //showing a page to generate JWT secret
    @GetMapping("/showFormForJWT")
    public String showFormForJWT(Model theModel) {
        logger.info("Showing JWT page");
        return "JWT-page";
    }

    //showing other functions page
    @GetMapping("/showOtherFunctions")
    public String showOtherFunctions() {
        logger.info("Showing other functions page");
        return "other-functions-page";
    }
/*
    //var fileStorageService to save file
    //@RequestParam will get the file from the upload button
    //RedirectAttributes used to add flash attributes that are available after a redirect.
    @PostMapping("/uploadFile")
        public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes theRedirectAttributes) {
        logger.info("Uploading file: {}", file.getOriginalFilename());
        ResponseEntity<String> response = fileStorageService.saveFile(file);
        theRedirectAttributes.addFlashAttribute("fileStatus", response.getBody());
        return "redirect:/staffList";
    }*/

    // this method display all path that has been uploaded
    @GetMapping("/uploadFile")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("fileStatus", storageServiceImplement.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(StaffListController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "redirect:/staffList";
    }

    //this method download the file that has stored on server
    //staffList/test.pdf use url+download filename to download
    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageServiceImplement.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    //store the client file to server
    @PostMapping("/uploadFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageServiceImplement.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/staffList";
    }

/*
    //download file
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        logger.info("Downloading file: {}", fileName);
        return fileStorageService.downloadFile(fileName);
    }*/

    //show form for update user
    @GetMapping("/showFormForUpdate")
    public String updateStaff(@RequestParam("staffId") int theId, Model theModel){
        logger.info("Showing form to update staff with ID: {}", theId);
        //StaffEntity theStaffEntity = staffRepository.findById(theId).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + theId));

        // make a fake null entity not found
        StaffEntity theStaffEntity = staffRepository.findById(100).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + theId));
        theModel.addAttribute("staff", theStaffEntity);
        return "staffs/add-staff-page";
    }

    //delete user
    @GetMapping("/delete")
    public String deleteStaff(@RequestParam("staffId") int theId){
        logger.info("Deleting staff with ID: {}", theId);
        StaffEntity theStaffEntity = staffRepository.findById(theId).orElseThrow(() -> new EntityNotFoundException("StaffEntity not found with id " + theId));
        staffRepository.delete(theStaffEntity);
        return "redirect:/staffList";
    }

    //after adding a user this method will save the new user
    //and the BindingResult will make sure if the form is empty
    @PostMapping("/save")
    public String saveStaff(@Valid @ModelAttribute("staff") StaffEntity theStaffEntity, BindingResult bindingResult){
        logger.info("Saving staff: {}", theStaffEntity);

        // make sure if bindingResult has error
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors encountered while saving staff: {}", bindingResult.getAllErrors());
            return "staffs/add-staff-page";
        }
        // save entity
        staffRepository.save(theStaffEntity);
        return "redirect:/staffList";
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
