package com.serenity.serenity.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class SerenityPayload {
   
    public String location_id;
    public String location_name;
    public List<SerenityInventoryItem>items;

}
