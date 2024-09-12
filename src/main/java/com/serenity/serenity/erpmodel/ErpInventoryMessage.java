package com.serenity.serenity.erpmodel;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErpInventoryMessage {
    public String event_type;
    public String target;
    public String headers;
    public ErpNextPayload payload;

}
