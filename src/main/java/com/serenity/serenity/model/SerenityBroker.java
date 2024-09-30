package com.serenity.serenity.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SerenityBroker implements Serializable{
   
private String eventType;
private String target;
//private List<Map<String,String>> headers;
public SerenityPayload payload;


}
