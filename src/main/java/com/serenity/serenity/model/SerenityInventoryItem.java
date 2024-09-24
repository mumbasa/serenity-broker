package com.serenity.serenity.model;

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
    private String reason;
}
