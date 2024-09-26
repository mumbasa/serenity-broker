package com.serenity.serenity.erpmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErpInventory {
    @SerializedName("item_name")

    private String itemName;
    @SerializedName("item_code")
    private String itemCode;
    @SerializedName("item_group")
    private String itemGroup;
    private String qty;
    private String uom;
    @SerializedName("selling_rate")
    private int sellingRate;
    @SerializedName("valuation_rate")
    private double valuationRate;

    @JsonProperty("conversion_factor") 
    private String conversionFactor;
}
