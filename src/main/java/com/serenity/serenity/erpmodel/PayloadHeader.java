package com.serenity.serenity.erpmodel;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayloadHeader {
    @SerializedName("event_type")
    private String eventType;
    
}
