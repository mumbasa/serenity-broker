package com.serenity.serenity.erpmodel;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateItem {
        @SerializedName("item_group")
        private String itemGroup;
      
        private String warehouse;

        @SerializedName("item_code")
        private String itemCode;

        @SerializedName("item_name")
        private String itemName;

   
        @SerializedName("qty")
        private double quantity;
        @SerializedName("selling_rate")
        private double sellingRate;

        @SerializedName("old_qty")
        private double transferQuantity;

     
}
