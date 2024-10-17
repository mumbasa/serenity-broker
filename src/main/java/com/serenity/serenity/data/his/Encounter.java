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
@Table(name = "encounter")
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("uuid")
    @Column(name = "uuid")
    private String uuid;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private String updatedAt;

    @JsonProperty("encounter_class")
    @Column(name = "encounter_class")
    private String encounterClass;

    @JsonProperty("status")
    @Column(name = "status")
    private String status;

    @JsonProperty("display")
    @Column(name = "display")
    private String display;

    @JsonProperty("priority")
    @Column(name = "priority")
    private String priority;

    @JsonProperty("planned_start")
    @Column(name = "planned_start")
    private String plannedStart;

    @JsonProperty("planned_end")
    @Column(name = "planned_end")
    private String plannedEnd;

    @JsonProperty("started_at")
    @Column(name = "started_at")
    private String startedAt;

    @JsonProperty("ended_at")
    @Column(name = "ended_at")
    private String endedAt;

    @JsonProperty("external_id")
    @Column(name = "external_id")
    private String externalId;

    @JsonProperty("external_system")
    @Column(name = "external_system")
    private String externalSystem;

    @JsonProperty("appointment_id")
    @Column(name = "appointment_id")
    private String appointmentId;

    @JsonProperty("location_id")
    @Column(name = "location_id")
    private String locationId;

    @JsonProperty("location_name")
    @Column(name = "location_name")
    private String locationName;

    @JsonProperty("service_type_id")
    @Column(name = "service_type_id")
    private String serviceTypeId;

    @JsonProperty("service_type_name")
    @Column(name = "service_type_name")
    private String serviceTypeName;

    @JsonProperty("service_provider_id")
    @Column(name = "service_provider_id")
    private String serviceProviderId;

    @JsonProperty("patient_mr_number")
    @Column(name = "patient_mr_number")
    private String patientMrNumber;

    @JsonProperty("patient_id")
    @Column(name = "patient_id")
    private String patientId;

    @JsonProperty("patient_full_name")
    @Column(name = "patient_full_name")
    private String patientFullName;

    @JsonProperty("patient_mobile")
    @Column(name = "patient_mobile")
    private String patientMobile;

    @JsonProperty("patient_birth_date")
    @Column(name = "patient_birth_date")
    private String patientBirthDate;

    @JsonProperty("patient_gender")
    @Column(name = "patient_gender")
    private String patientGender;

    @JsonProperty("patient_status")
    @Column(name = "patient_status")
    private String patientStatus;

    @JsonProperty("created_by_id")
    @Column(name = "created_by_id")
    private String createdById;

    @JsonProperty("created_by_name")
    @Column(name = "created_by_name")
    private String createdByName;

    @JsonProperty("user_friendly_id")
    @Column(name = "user_friendly_id")
    private String userFriendlyId;

    @JsonProperty("assigned_to_name")
    @Column(name = "assigned_to_name")
    private String assignedToName;

    @JsonProperty("assigned_to_id")
    @Column(name = "assigned_to_id")
    private String assignedToId;

    @JsonProperty("service_provider_name")
    @Column(name = "service_provider_name")
    private String serviceProviderName;
}
