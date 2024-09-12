package com.serenity.serenity.erpmodel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayloadHeader {
    public String event_type;
    public String target;
}
