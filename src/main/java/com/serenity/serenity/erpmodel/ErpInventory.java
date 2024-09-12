package com.serenity.serenity.erpmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErpInventory {

    private String item_name;

    private String item_code;
  
    private String item_group;
    private String qty;
    private String uom;
    @JsonProperty("conversion_factor") 
    private String conversionFactor;
}
