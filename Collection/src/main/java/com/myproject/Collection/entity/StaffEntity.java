package com.myproject.Collection.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

//@Schema is part of the OpenAPI use to helps to describe the API model properties.
//@Entity Indicates that this class is a JPA entity.
@Entity
// Specifies the name of the database table that this entity maps to.
@Table(name = "employee")
//used to provide metadata about the class or field in the generated API documentation
@Schema(description = "Staff entity representing a staff member.")
public class StaffEntity {
    //The id field will be the unique identifier for each record in the database
    @Id
    //indicates that the database will automatically generate the primary key for each new record.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //the name of the column in the database table that this field will map to
    @Column(name = "id")
    @Schema(description = "Unique identifier for the staff member.", example = "1")
    private int id;
    //@NotNull the blank can not be empty
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
