package com.serenity.serenity.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErpNextItem {
    public String item_code;
    public String item_name;
    public String qty;
    public String uom;
    public String conversion_factor;

    public ErpNextItem(){


    }


    public ErpNextItem(SerenityInventoryItem item){
        this.item_code=item.getCode();
        this.item_name=item.getName();
       // this.qty=item.quantity_dispensed;
        this.uom="Nos";
        this.conversion_factor="1";

        
    }
}
