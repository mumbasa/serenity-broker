package com.serenity.serenity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SerenityInventoryItem {
    private String uuid;
    private String name;
    private String code;
    private String location_id;
    private String location_name;
    private String quantity_dispensed;
    private int in_hand_quantity;
    @JsonProperty("batch_number")
    @SerializedName("selling_price")
    private double sellingPrice;
    private String reason;
    private String sourceName;
    @SerializedName("batch_number")
       private String batchNumber;
    private String sourceId;
}
