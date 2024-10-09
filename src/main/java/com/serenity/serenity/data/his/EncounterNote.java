package com.serenity.serenity.data.his;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class EncounterNote {
  @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;

    private String uuid;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    private String note;
    @JsonProperty("encounter_id")
    private String encounterId;
    @JsonProperty("encounter_date")
    private String encounterDate;
    @JsonProperty("patient_mr_number")
    private String patientMrNumber;
    @JsonProperty("encounter_type")
    private String encounterType;
    @JsonProperty("note_type")
    private String noteType;
    @JsonProperty("is_edited")
    private boolean isEdited;
    @JsonProperty("is_recalled")
    private boolean isRecalled;
    @JsonProperty("is_formatted")
    private boolean isFormatted;
    @JsonProperty("practitioner_name")
    private String practitionerName;
    @JsonProperty("pratitioner_role_type")
    private String practitionerRoleType;
    @JsonProperty("practitioner_id")
    private String practitionerId;
    
    private String dataSource;
}
