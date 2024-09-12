package com.serenity.serenity.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SerenityStock {
   
    @JsonProperty("uuid")
     String uuid;

    @JsonProperty(namespace="created_at",required=false)
     String createdAt;

    @JsonProperty("updated_at")
     String updatedAt;

    @JsonProperty("provider_id")
     String providerId;

    @JsonProperty("code")
     String code;

    @JsonProperty("sub_group")
     String subGroup;

    @JsonProperty("name")
     String name;

    @JsonProperty("location_id")
     String locationId;

    @JsonProperty("location_name")
     String locationName;

    @JsonProperty("dosage_amount")
     double dosageAmount;

    @JsonProperty("dosage_unit")
     String dosageUnit;

    @JsonProperty("dosage_form")
     String dosageForm;

    @JsonProperty("batch_number")
     String batchNumber;

    @JsonProperty("rate")
     double rate;

    @JsonProperty("unit_price")
     double unitPrice;

    @JsonProperty("currency")
     String currency;

    @JsonProperty("selling_price")
     double sellingPrice;

    @JsonProperty("initial_quantity")
     double initialQuantity;

    @JsonProperty("net_release_quantity")
     double netReleaseQuantity;

    @JsonProperty("in_hand_quantity")
     double inHandQuantity;

    @JsonProperty("expiry_date")
     String expiryDate;

    @JsonProperty("category")
     String category;

    @JsonProperty("medication")
     String medication;

    @JsonProperty("external_id")
     String externalId;

    @JsonProperty("stock_date")
     String stockDate;

    @JsonProperty("reason")
     String reason;

    @JsonProperty("revenue_tag_id")
     String revenueTagId;

    @JsonProperty("revenue_tag_display")
     String revenueTagDisplay;

}
