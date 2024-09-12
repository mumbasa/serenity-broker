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

 @SerializedName("locationId")
    private String locationId;
    
    @SerializedName("locationName")
    private String locationName;
}
