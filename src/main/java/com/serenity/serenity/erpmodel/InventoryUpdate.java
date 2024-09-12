package com.serenity.serenity.erpmodel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class InventoryUpdate {
 @JsonProperty("payload")
    private UpdatePayload payload;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("target")
    private String target;

    @JsonProperty("headers")
    private String headers;

}
