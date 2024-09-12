package com.serenity.serenity.erpmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePayload {
   @JsonProperty("name")
        private String name;

        @JsonProperty("purpose")
        private String purpose;

        @JsonProperty("items")
        private List<UpdateItem> items;

        @JsonProperty("posting_date")
        private String postingDate;

        @JsonProperty("posting_time")
        private String postingTime;

}
