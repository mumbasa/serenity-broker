package com.serenity.serenity.erpmodel;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DispenseHeader {
    
    @SerializedName("eventType")
    private String eventType;
    
}
