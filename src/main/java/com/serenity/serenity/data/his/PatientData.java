package com.serenity.serenity.data.his;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
public class PatientData {
    @Id
    private long id;
    @JsonProperty("uuid")
    private String uuid;
    
    @JsonProperty("external_id")
    private String externalId;
    
    @JsonProperty("external_system")
    private String externalSystem;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @SerializedName("mobile")
    private String mobile;
    
    @JsonProperty("national_mobile_number")
    private String nationalMobileNumber;
    
    @SerializedName("first_name")
    private String firstName;
    
    @SerializedName("last_name")
    private String lastName;
    
    @SerializedName("email")
    private String email;
    
    @SerializedName("birth_date")
    private String birthDate;
    
    @SerializedName("gender")
    private String gender;
    
    @JsonProperty("nationality")
    private String nationality;
    
    @JsonProperty("national_id")
    private String nationalId;
    
    @JsonProperty("national_id_type")
    private String nationalIdType;
    
    @JsonProperty("other_names")
    private String otherNames;
    
    @SerializedName("mr_number")
    private String mrNumber;
    
    @SerializedName("managing_organization_id")
    private String managingOrganizationId;
    
    @JsonProperty("author_practitioner_id")
    private String authorPractitionerId;
      
    @JsonProperty("contribution_value")
    private double contributionValue;
    
    @JsonProperty("contribution_currency")
    private String contributionCurrency;
    
    @JsonProperty("contribution_type")
    private String contributionType;


}
