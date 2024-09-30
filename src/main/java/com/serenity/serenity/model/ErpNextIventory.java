package com.serenity.serenity.model;

import java.util.ArrayList;
import java.util.Calendar;

import com.serenity.serenity.utilities.Utility;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErpNextIventory {
   public String customer;
    public String company;
    public String currency;
    public int conversion_rate;
    public String selling_price_list;
    public String price_list_currency;
    public int plc_conversion_rate;
    public String delivery_date;
    public int docstatus;
    public String set_warehouse;
    public ArrayList<ErpNextItem> items;

    public ErpNextIventory(SerenityPayload payload) {
        items = new ArrayList<>();
        this.setCustomer("Serenity EMR");
        this.setCompany("Nyaho Medical Centre");
        this.setCurrency("GHS");
        this.setConversion_rate(1);
        this.setSelling_price_list("Standard Selling");
        this.setPrice_list_currency("GHS");
        this.setPlc_conversion_rate(1);
        this.setDelivery_date(Calendar.getInstance().toString());
        this.setDocstatus(1);
        this.setSet_warehouse(Utility.getErpnextLocation(payload.getLocation_name()));
        payload.items.forEach(e ->{ 
            items.add(new ErpNextItem(e));

        });
    
   
    }

    public ErpNextIventory() {}
}
