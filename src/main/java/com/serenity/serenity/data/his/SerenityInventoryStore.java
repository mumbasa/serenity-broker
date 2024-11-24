package com.serenity.serenity.data.his;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.serenity.serenity.model.SerenityInventoryItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
public class SerenityInventoryStore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String uuid;
    private String name;
    private String code;
    private String locationId;
    private String locationName;
    private String quantityDispensed;
    private int inHandQuantity;
    @JsonProperty("selling_price")
    @SerializedName("selling_price")
    private double sellingPrice;
    private String reason;
    private String sourceName;
    @JsonProperty("batch_number")
    @SerializedName("batch_number")
    private String batchNumber;
    private String sourceId;
    private String externalSystem;
    private String event;
    @Column(columnDefinition = "text")
    private String error;

    public SerenityInventoryStore(SerenityInventoryItem item,String event,Exception error){
        this.batchNumber=item.getBatchNumber();
        this.uuid=item.getUuid();
        this.name=item.getName();
        this.code=item.getCode();
        this.locationId=item.getLocation_id();
        this.locationName=item.getLocation_name();
        this.quantityDispensed=item.getQuantity_dispensed();
        this.inHandQuantity=item.getIn_hand_quantity();
        this.sellingPrice=item.getSellingPrice();
        this.reason=item.getReason();
        this.sourceId=item.getSourceId();
        this.sourceName=item.getSourceName();
        this.externalSystem=item.getExternal_system();
        this.event=event;
        this.error = error.getMessage();
    }
}
