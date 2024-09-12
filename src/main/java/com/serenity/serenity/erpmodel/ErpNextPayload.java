package com.serenity.serenity.erpmodel;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("to") 
    private String to;
    private ArrayList<ErpInventory> items;

}
