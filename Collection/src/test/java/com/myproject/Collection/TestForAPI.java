package com.myproject.Collection;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.myproject.Collection.aspect.GlobalExceptionHandler;
import com.myproject.Collection.controller.SteffController;
import com.myproject.Collection.entity.StaffEntity;
import com.myproject.Collection.repository.StaffRepository;
import com.myproject.Collection.service.JWTServiceImplement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TestForAPI {

    @Autowired
    private JWTServiceImplement theJWTServiceImplement;

    private MockMvc mockMvc;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private SteffController steffController;

    private final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjA3NzAxMTYsInVzZXJuYW1lIjoic3VzYW4ifQ.cNLttU2xg2wvaz7bKfQu6lHArjdxqR5GUyVrNmqhevI";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.steffController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // test API multiple user
    @Test
        public void testGetAllUser() throws Exception {


        ResultActions positiveCase = mockMvc.perform(get("/api/staffs")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON));

        positiveCase.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Emma"))
                .andExpect(jsonPath("$[0].lastName").value("Baumgarten"))
                .andExpect(jsonPath("$[0].email").value("emma@luv2code.com"))
                .andExpect(jsonPath("$[1].firstName").value("Avani"))
                .andExpect(jsonPath("$[1].lastName").value("Gupta"))
                .andExpect(jsonPath("$[1].email").value("avani@luv2code.com"));
    }



    // test API get one user
    @Test
    public void testGetOneUser() throws Exception {

        ResultActions positiveCase = mockMvc.perform(get("/api/staffFindById/2")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON));

        positiveCase.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Emma"))
                .andExpect(jsonPath("$.lastName").value("Baumgarten"))
                .andExpect(jsonPath("$.email").value("emma@luv2code.com"));
    }


    @Test
    public void testAddStaff() throws Exception {
       /* StaffEntity newStaff = new StaffEntity("Deva", "KIm", "deva.kIm@example.com");
        newStaff.setId(0); // Ensure ID is set to 0 before saving
*/
        ResultActions positiveCase = mockMvc.perform(post("/api/addStaff")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Leo\",\"lastName\":\"mouth\",\"email\":\"leo.mouth@example.com\"}"));

        positiveCase.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(28))
                .andExpect(jsonPath("$.firstName").value("Leo"))
                .andExpect(jsonPath("$.lastName").value("mouth"))
                .andExpect(jsonPath("$.email").value("leo.mouth@example.com"));
    }



    // test API update user
    @Test
    public void testUpdateStaff() throws Exception {
/*
        StaffEntity updatedStaff = new StaffEntity("Leslie", "Smith", "Leslie.smith@example.com");
        updatedStaff.setId(13);*/

        ResultActions positiveCase = mockMvc.perform(put("/api/updateStaff")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":13,\"firstName\":\"Leslie\",\"lastName\":\"Smith\",\"email\":\"leslie.smith@example.com\"}"));

        positiveCase.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(13))
                .andExpect(jsonPath("$.firstName").value("Leslie"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("leslie.smith@example.com"));
    }


    @Test
    public void testDeleteStaff() throws Exception {
        int staffId = 13;

        // Mock the staffRepository behavior
        /*when(staffRepository.findById(staffId)).thenReturn(java.util.Optional.of(new StaffEntity("Susan", "Smith", "susan.smith@example.com")));
        doNothing().when(staffRepository).deleteById(staffId);*/

        ResultActions resultActions = mockMvc.perform(delete("/api/deleteStaff/{staffId}", staffId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Deleted employee id - " + staffId));

        // Verify that findById and deleteById were called with the correct arguments
        //verify(staffRepository).findById(staffId);
        //verify(staffRepository).deleteById(staffId);
    }


}


/*
@Test
public void testInvalidTokenErrorJwt() {
    System.out.println(theJWTServiceImplement.generateToken("test"));

    Assertions.assertThrows(SignatureVerificationException.class, () -> {
        theJWTServiceImplement.resolveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjA3MDE1MjUsInVzZXJuYW1lIjoic3VzYW4ifQ.pO7X6tVWoCFtLz3EMiqzSxhOrUQqjU_ThuqxsJgGaGI");
    });
}*/
