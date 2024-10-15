package com.serenity.serenity.data.his;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "patient_information")
public class PatientData {
    @Id
    private long id;
    private String uuid;
    @Column(nullable = true)

    private String externalId;
    
    private String externalSystem;
    @Column(nullable = true)

    private LocalDateTime createdAt;
    
    private String mobile;
    @Column(nullable = true)

    private String nationalMobileNumber;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")

    private String lastName;
    @Column(name = "fullname")
    private String fullName;
    
    @Column(nullable = true)
    private String title;
    @Column(nullable = true)

    private String occupation;
    @Column(nullable = true)
    private String employer;
    
    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private String birthDate;
    @Column(nullable = true,name="maritalstatus")
    private String maritalStatus;
    
    private String gender;
    @Column(nullable = true)

    private String nationality;
    
    
    @Column(nullable = true)

    private String otherNames;
    @Column(name = "mrnumber")
    private String mrNumber;
    
    @Column(nullable = true)
    private String bloodType;
    @Column(nullable = true)

    private String passportNumber;

    @Column(nullable = true)
    private String birthTime;

    private String religiousAffiliation;

}
