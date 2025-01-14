package com.serenity.serenity.service;


import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rabbitmq.client.AMQP.Basic.Ack;
import com.serenity.serenity.erpmodel.ErpInventoryMessage;
import com.serenity.serenity.erpmodel.InventoryUpdate;
import com.serenity.serenity.erpmodel.PayloadHeader;
import com.serenity.serenity.model.SerenityBroker;
import com.serenity.serenity.utilities.Utility;


@Component
public class InventoryListener {

    @Autowired
    InventoryTasks inventoryTasks;
    Logger LOGGER = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    @RabbitListener(queues = "erpnext", concurrency = "4", containerFactory = "createRabbitListenerFactory")
    public void receiveMessage(String message) {

        try{
     SerenityBroker brokerMessage = new Gson().fromJson(message, SerenityBroker.class);
        LOGGER.info(message);
       
        switch (brokerMessage.getEventType()) {
            
            case "inventory/dispense" -> {
                brokerMessage.payload.items.stream().forEach(e-> {e.setExternal_system("erpnext");
            
            });
                System.err.println(inventoryTasks.dispense(brokerMessage));
            }
            default ->
                System.err.println("default");
        }
    }catch(Exception e){
        e.printStackTrace();
        System.err.println("wrong format =>"+message);
    }

    }

    @RabbitListener(queues = "serenity", concurrency = "5", containerFactory = "createRabbitListenerFactory")
    public void getBillInfo(String message) throws  UnsupportedEncodingException {
        LOGGER.info(message);
       try{
        PayloadHeader headerMessage = new Gson().fromJson(message, PayloadHeader.class);

        switch (headerMessage.getEventType()) {
            case "inventory/update" -> {
                ErpInventoryMessage brokerMessage = new Gson().fromJson(message, ErpInventoryMessage.class);
                      inventoryTasks.serentityInventoryUpdate(Utility.getSerenityInventoryFromErp(brokerMessage.payload),true);
                      System.out.println("update");

            }
            case "inventory/create" -> {
                LOGGER.info(message);

                ErpInventoryMessage brokerMessage = new Gson().fromJson(message, ErpInventoryMessage.class);
                inventoryTasks.serentityInventoryUpdate(Utility.getSerenityInventoryFromErp(brokerMessage.payload),true);
                LOGGER.info("create");
            }

            case "inventory/adjust" -> {
                InventoryUpdate brokerMessage = new Gson().fromJson(message, InventoryUpdate.class);
                System.err.println(brokerMessage.getPayload().getItems());
                inventoryTasks.serentityInventoryAdjust(Utility.getSerenityInventoryFromErp(brokerMessage.getPayload()),true);
                LOGGER.info("adjust");
            }

            case "inventory/transfer" -> {
                LOGGER.info(message);

                ErpInventoryMessage brokerMessage = new Gson().fromJson(message, ErpInventoryMessage.class);

               inventoryTasks.serentityTransfer(Utility.getSerenityInventoryFromErp(brokerMessage.getPayload()));
                LOGGER.info(brokerMessage.toString());
            }
            default ->
            LOGGER.info("default");
        }
    }catch(Exception e){
e.printStackTrace();
      LOGGER.info("wrong format =>"+message);
    }

    }
    
    public void editInvenotry() {
        System.out.println("ssafdsafasdfsdafsda");

    
    }

     
}
