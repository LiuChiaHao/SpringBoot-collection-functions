package com.myproject.Collection.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "employee")
@Schema(description = "Staff entity representing a staff member.")
public class StaffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier for the staff member.", example = "1")
    private int id;
    @NotNull(message = "First name is required")
    @Size(min=1, message="First name can not be empty")
    @Column(name = "firstName")
    @Schema(description = "First name of the staff member.", example = "John")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min=1, message="Last name can not be empty")
    @Column(name = "lastName")
    @Schema(description = "Last name of the staff member.", example = "Doe")
    private String lastName;
    @NotNull(message = "Email is required")
    @Size(min=1, message="Email can not be empty")
    @Column(name = "email")
    @Schema(description = "Email address of the staff member.", example = "john.doe@example.com")
    private String email;


    public StaffEntity() {
    }

    public StaffEntity(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "StaffEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
