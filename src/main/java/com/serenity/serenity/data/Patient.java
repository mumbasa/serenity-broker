package com.serenity.serenity.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Patient {
@JsonProperty("first_name")
private String firstName;
@JsonProperty("last_name")
private String lastname;
@JsonProperty("full_name")
private String name;
private String mobile;
@JsonProperty("mr_number")
private String mrNumber;
private String id;
@JsonProperty("birth_date")
private String dob;
private String gender;
private double confidence;
@JsonProperty("external_system")
private String source;


}
