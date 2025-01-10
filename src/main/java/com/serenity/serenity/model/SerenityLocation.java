package com.serenity.serenity.model;


import com.google.gson.annotations.SerializedName;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
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


    public SerenityLocation (){}

    public SerenityLocation (HttpResponse<JsonNode>  response){
        System.err.println(response.getBody().getArray().getJSONObject(0).getString("uuid"));
        this.locationId = response.getBody().getArray().getJSONObject(0).getString("uuid");
        locationName=(response.getBody().getArray().getJSONObject(0).getString("name"));


    }
}
