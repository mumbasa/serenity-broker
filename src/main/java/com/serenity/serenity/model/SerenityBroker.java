package com.serenity.serenity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.messaging.handler.annotation.Payload;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SerenityBroker implements Serializable{
   
private String eventType;
private String target;
private List<Map<String,String>> headers;
public SerenityPayload payload;


}
