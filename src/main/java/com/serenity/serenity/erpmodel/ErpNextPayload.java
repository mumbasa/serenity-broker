package com.serenity.serenity.erpmodel;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErpNextPayload {
    @JsonProperty("name") 
    private String name;
    @JsonProperty("posting_date") 
    private String postingDate;
    private String purpose;
    @JsonProperty("posting_time") 
    private String postingTime;
    private String from;
    @SerializedName("to") 
    private String to;
    private ArrayList<ErpInventory> items;

}
