package com.serenity.serenity.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class SerenityLocation {

 @SerializedName("location_id")
    private String locationId;
    
    @SerializedName("location_name")
    private String locationName;
}
