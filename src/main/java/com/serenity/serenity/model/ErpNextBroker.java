package com.serenity.serenity.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErpNextBroker implements Serializable{
   
private String eventType;
private String target;
public List<SerenityInventoryItem> payload;


}
