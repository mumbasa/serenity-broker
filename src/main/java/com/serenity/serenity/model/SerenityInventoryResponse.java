package com.serenity.serenity.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SerenityInventoryResponse {
    private List<SerenityStock> data;
    private int total;
    private int page;
    private int  size;

}
