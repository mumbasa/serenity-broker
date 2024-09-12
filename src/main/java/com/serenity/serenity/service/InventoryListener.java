package com.serenity.serenity.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.serenity.serenity.erpmodel.ErpInventoryMessage;
import com.serenity.serenity.model.SerenityBroker;
import com.serenity.serenity.utilities.Utility;


@Component
public class InventoryListener {

    @Autowired
    InventoryTasks inventoryTasks;
    Logger LOGGER = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    @RabbitListener(queues = "erpnext", concurrency = "4", containerFactory = "createRabbitListenerFactory")
    public void receiveMessage(String message) {
      SerenityBroker brokerMessage = new Gson().fromJson(message, SerenityBroker.class);
        LOGGER.info(brokerMessage.toString());
        
        switch (brokerMessage.getEventType()) {
            
            case "inventory/dispense" -> {
                System.err.println(inventoryTasks.dispense(brokerMessage));
            }
            default ->
                System.err.println("default");
        }

    }

    @RabbitListener(queues = "serenity", concurrency = "4", containerFactory = "createRabbitListenerFactory")
    public void getBillInfo(String message) {
        ErpInventoryMessage brokerMessage = new Gson().fromJson(message, ErpInventoryMessage.class);
        LOGGER.info(brokerMessage.getPayload() +" ------------payload");
        switch (brokerMessage.getEvent_type()) {
            case "inventory/update" -> {
                      inventoryTasks.serentityInventoryUpdate(Utility.getSerenityInventoryFromErp(brokerMessage.payload),true);
                      System.out.println("update");

            }
            case "inventory/create" -> {
         
                inventoryTasks.serentityInventoryUpdate(Utility.getSerenityInventoryFromErp(brokerMessage.payload),true);
                LOGGER.info("create");
            }

            case "inventory/adjust" -> {
                inventoryTasks.serentityInventoryAdjust(Utility.getSerenityInventoryFromErp(brokerMessage.payload),true);
                LOGGER.info("create");
            }
            default ->
            LOGGER.info("default");
        }

    }
    
    public void editInvenotry() {
        System.out.println("ssafdsafasdfsdafsda");

    
    }

     
}
