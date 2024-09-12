package com.serenity.serenity.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ErpNextPayload {
   

    public String name;
    public String posting_date;
    public String purpose;
    public String posting_time;
    public String from;
    @JsonProperty("to") 
    public String myto;

    public List<SerenityStock>items;

}
