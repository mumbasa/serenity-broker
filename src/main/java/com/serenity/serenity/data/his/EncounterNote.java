package com.serenity.serenity.data.his;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name="encounternote")
public class EncounterNote {
  @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;

    private String uuid;
    @JsonProperty("created_at")
   @Column(name="createdat")
    private String createdAt;
    @Column(name="updatedat")
    private String updatedAt;

    @Column(columnDefinition="TEXT")
    private String note;
   
   
    @Column(name="encounterid")
    @JsonProperty("encounter_id")
    private String encounterId;

    @JsonProperty("encounter_date")
   @Column(name="encounterdate")
    private String encounterDate;



   @JsonProperty("patient_mr_number")
   @Column(name="patientmrnumber")
    private String patientMrNumber;

    @Column(name="encountertype")
    @JsonProperty("encounter_type")
    private String encounterType;
  
    @Column(name="notetype")
    @JsonProperty("note_type")
    private String noteType;
  
    @Column(name="isedited")
    @JsonProperty("is_edited")
    private boolean isEdited;
  
    @Column(name="isrecalled")
    @JsonProperty("is_recalled")
    private boolean isRecalled;
  
    @Column(name="isformatted")
    @JsonProperty("is_formatted")
    private boolean isFormatted;
 
    @Column(name="practitionername")
    @JsonProperty("practitioner_name")
    private String practitionerName;
   
    @Column(name="pratitionerrole_ype")
    @JsonProperty("pratitioner_role_type")
   private String practitionerRoleType;
    
   @Column(name="practitionerid")
   @JsonProperty("practitioner_id")
    private String practitionerId;
    private String dataSource;
}
