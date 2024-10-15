package com.serenity.serenity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.serenity.serenity.model.ErpNextIventory;
import com.serenity.serenity.model.SerenityBroker;
import com.serenity.serenity.model.SerenityInventoryItem;
import com.serenity.serenity.model.SerenityInventoryResponse;
import com.serenity.serenity.model.SerenityStock;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;

@Component
public class InventoryTasks {
    Logger LOGGER = LoggerFactory.getLogger(this.getClass().getCanonicalName());
   
    @Value("${serenity.token}")
    private String serenityToken;

    public String serenityInventoryUpdate(ErpNextIventory inventory) {

        String url = "https://stag.api.cloud.serenity.health/v2/inventory/{item_id}";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "token b565e3d6c1f460d:59426b2f5a3047f"); // Add token if needed

        return "uo";
    }

    public void serentityInventoryUpdate(List<SerenityInventoryItem> stocks, boolean k) {
        System.err.println("------------------------ start");
        List<SerenityInventoryItem> newEntries = new ArrayList<>();
        List<SerenityInventoryItem> oldEtries = new ArrayList<>();
        //looping to separate the new from the old stock
        for (SerenityInventoryItem stock : stocks) {
            SerenityInventoryResponse res = serenitySearch(stock);
            if (res.getTotal() > 0) {
                LOGGER.info("found item to update quantity "+stock.getIn_hand_quantity()+"=>"+(int)res.getData().get(0).getInHandQuantity());

                stock.setLocation_id(res.getData().get(0).getLocationId());
                stock.setLocation_name(res.getData().get(0).getLocationName());
              //  stock.setIn_hand_quantity((int)res.getData().get(0).getInHandQuantity());
                oldEtries.add(stock);

            } else {
                LOGGER.info("found item to create");
                newEntries.add(stock);
            }

        }
        System.err.println("------------------------ starting");
     //processing the lists
        if (!newEntries.isEmpty()) {
            try{
            serenityCeate(newEntries);
            }catch(Exception e){
                LOGGER.error("error occured");
                e.printStackTrace();
            }
        }
        if (!oldEtries.isEmpty()) {
            for(SerenityInventoryItem s : oldEtries){
                serenityUpdate(s);
        }
    
    }
    }

    public void serentityInventoryAdjust2(List<SerenityInventoryItem> stocks, boolean k) {
        System.err.println("------------------------ start");
        Map<String,SerenityInventoryItem> itemMap = new HashMap<>();
        LOGGER.info("Creating map");
    //adding same items irrespective of batch numbers
        stocks.stream().forEach(e ->{
        
            if(itemMap.containsKey(e.getName())){
                SerenityInventoryItem item =  itemMap.get(e.getCode());
                item.setIn_hand_quantity(item.getIn_hand_quantity()+e.getIn_hand_quantity());
                itemMap.replace(e.getName(), item);
            }else{
                itemMap.put(e.getName(), e);
                
            }
       });

        List<SerenityInventoryItem> newEntries = new ArrayList<>();
        List<SerenityInventoryItem> oldEtries = new ArrayList<>();
        //looping to separate the new from the old stock

        for (SerenityInventoryItem stock : itemMap.values()) {
            SerenityInventoryResponse res = stockCount(stock);
            if (res.getTotal() > 0) {
                oldEtries.add(stock);
                LOGGER.info("found item to update");

            } else {
                LOGGER.info("found item to create");
                newEntries.add(stock);
            }

        }
        System.err.println("------------------------ Adjusting");
     //processing the lists
        if (!newEntries.isEmpty()) {
            try{
            serenityCeate(newEntries);
            }catch(Exception e){
                LOGGER.error("error occured");
                e.printStackTrace();
            }
        }
        if (!oldEtries.isEmpty()) {
            for(SerenityInventoryItem s : oldEtries){
            serenityUpdate(s);
        }
    }}




    public void serentityInventoryAdjust(List<SerenityInventoryItem> stocks, boolean k) {
        System.err.println("------------------------ start");
        List<SerenityInventoryItem> newEntries = new ArrayList<>();
        List<SerenityInventoryItem> oldEtries = new ArrayList<>();
        //looping to separate the new from the old stock
        for (SerenityInventoryItem stock : stocks) {
            SerenityInventoryResponse res = stockCount(stock);
            if (res.getTotal() > 0) {
                oldEtries.add(stock);
                LOGGER.info("found item to update");

            } else {
                LOGGER.info("found item to create");
                newEntries.add(stock);
            }

        }
        System.err.println("------------------------ Adjusting");
     //processing the lists
        if (!newEntries.isEmpty()) {
            try{
            serenityCeate(newEntries);
            }catch(Exception e){
                LOGGER.error("error occured");
                e.printStackTrace();
            }
        }
        if (!oldEtries.isEmpty()) {
            for(SerenityInventoryItem s : oldEtries){
            serenityUpdate(s);
        }
    }}


    @SuppressWarnings("null")
    public SerenityInventoryResponse serenitySearch(SerenityInventoryItem stock) {
        LOGGER.info("Searching for "+stock.getName());
        String url = "https://stag.api.cloud.serenity.health/v2/inventory?name=" + stock.getName() + "&location_name=" + stock.getLocation_name()+"&batch_number="+stock.getBatchNumber();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SerenityInventoryResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, SerenityInventoryResponse.class);
//setting the stock with the data in serenity
            if (response.getBody().getTotal() > 0) {
                System.out.println("found ---------------------------");
                SerenityStock s = response.getBody().getData().get(0);
                int qty=(int) (s.getInHandQuantity()+stock.getIn_hand_quantity());
                stock.setUuid(response.getBody().getData().get(0).getUuid());
                stock.setLocation_id(response.getBody().getData().get(0).getLocationId());
                stock.setIn_hand_quantity(qty);
                System.err.println("Serenity Stock is "+s.getInHandQuantity()+" Addition  is "+stock.getIn_hand_quantity() +" is quantity");
            }
       
        return response.getBody();
    }



    public void serentityTransfer(List<SerenityInventoryItem> stocks) {
        for(SerenityInventoryItem item :stocks){
 
         serenitySearchF(item);
 
        }
     }
 
 
 
 
     @SuppressWarnings("null")
     public void serenitySearchF(SerenityInventoryItem stock) {
         String[] locations = {stock.getLocation_name(),stock.getSourceName()};
         List<SerenityInventoryItem> items = new ArrayList<>();
         LOGGER.info("Searching for "+stock.getName());
         int count=0; //this is to create a counter to enable the substraction from the source stock
         for(String location : locations){
            System.err.println(location +"------------------");
         String url = "https://stag.api.cloud.serenity.health/v2/inventory?name=" + stock.getName() + "&location_name=" + location+"&batch_number="+stock.getBatchNumber();
         HttpHeaders headers = new HttpHeaders();
         headers.set("Content-Type", "application/json");
         headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
         HttpEntity<String> httpEntity = new HttpEntity<>(headers);
         RestTemplate restTemplate = new RestTemplate();
         ResponseEntity<SerenityInventoryResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, SerenityInventoryResponse.class);
        LOGGER.info(response.getBody().toString());
 
 
         //setting the stock with the data in serenity
             if (response.getBody().getTotal() > 0) {
                SerenityInventoryItem item = new SerenityInventoryItem();
                 SerenityStock s = response.getBody().getData().get(0);
                 int qty = (int) s.getInHandQuantity()+stock.getIn_hand_quantity();
                 item.setUuid(response.getBody().getData().get(0).getUuid());
                 item.setReason(stock.getReason());

                 if(count>0){
                    
                     item.setLocation_id(stock.getSourceId());
                     item.setLocation_name(stock.getSourceName());
                     item.setIn_hand_quantity((int)(s.getInHandQuantity()-stock.getIn_hand_quantity()));
                     item.setName(stock.getName());
                     item.setCode(stock.getCode());
                     LOGGER.info(stock.getIn_hand_quantity()+"\tSubstacting stock "+s.getInHandQuantity());
                 }else{
                    item.setLocation_id(stock.getLocation_id());
                    item.setLocation_name(stock.getLocation_name());
                    item.setIn_hand_quantity((int)(s.getInHandQuantity()+stock.getIn_hand_quantity()));
                    item.setName(stock.getName());
                    item.setCode(stock.getCode());
                    LOGGER.info(stock.getIn_hand_quantity()+"\tTransfering stock"+s.getInHandQuantity());


                 }
                 LOGGER.info(location +"------------------\t"+stock.getLocation_name()+" ---count "+stock.getIn_hand_quantity());
                 items.add(item);
                
             }else{

                //if it is target 
                if(count==0){
                 serenityCeate(stock);
                }
             }
             count++;
         }
         System.err.println(items.size());
         for (SerenityInventoryItem it : items){
 System.err.println("Syncing for \t"+it.getLocation_name()+"\t"+it.getName());
             serenityUpdate(it);
 
 
 
         }
 
     }



    @SuppressWarnings("null")
    public SerenityInventoryResponse stockCount(SerenityInventoryItem stock) {
        LOGGER.info("Searching for "+stock.getName());
        String url = "https://stag.api.cloud.serenity.health/v2/inventory?name=" + stock.getName() + "&location_name=" + stock.getLocation_name()+"&batch_number="+stock.getBatchNumber();

        //String url = "https://stag.api.cloud.serenity.health/v2/inventory?name=" + stock.getName() + "&location_name=" + stock.getLocation_name();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SerenityInventoryResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, SerenityInventoryResponse.class);
//setting the stock with the data in serenity
            if (response.getBody().getTotal() > 0) {
               
                stock.setUuid(response.getBody().getData().get(0).getUuid());
                stock.setLocation_id(response.getBody().getData().get(0).getLocationId());
          
            }
       
        return response.getBody();
    }




    public String serenityCeate(List<SerenityInventoryItem> stock) {
    Gson j = new Gson();
   ;
    
        LOGGER.info("Adding new entries to inventory for "+ j.toJson(stock));

        String url = "https://stag.api.cloud.serenity.health/v2/inventory";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
        HttpEntity<List<SerenityInventoryItem>> httpEntity = new HttpEntity<>(stock, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return response.getBody();
    }

    public String serenityCeate(SerenityInventoryItem stock) {
        LOGGER.info("Adding new entries to inventory for "+stock.toString());

        String url = "https://stag.api.cloud.serenity.health/v2/inventory";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
        HttpEntity<SerenityInventoryItem> httpEntity = new HttpEntity<>(stock, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return response.getBody();
    }

    public String serenityUpdate(SerenityInventoryItem stock) {
        LOGGER.info("Updating inventory for "+stock.getName() +"\t "+stock.getIn_hand_quantity());

        HttpResponse<String> response = Unirest.patch("https://stag.api.cloud.serenity.health/v2/inventory/" + stock.getUuid())
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+serenityToken) // Add token if needed
                .body(stock)
                .asString();
        System.err.println(response.getBody());
        return response.getBody();

    }

    public String serenityUpdate(SerenityInventoryItem stock, boolean tea) {
        LOGGER.info("Updating inventory for "+stock.getName() +"\t "+stock.getIn_hand_quantity());

        String url = "https://stag.api.cloud.serenity.health/v2/inventory";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
        HttpEntity<SerenityInventoryItem> httpEntity = new HttpEntity<>(stock, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, String.class);
        return response.getBody();
    }

    public String dispense(SerenityBroker message) {
        System.err.println("Starting .....................................");

        ErpNextIventory inventory = new ErpNextIventory(message.getPayload());

        Gson gson = new Gson();
        String json = gson.toJson(inventory);
        System.err.println(json);

        String endpoint = "http://52.212.103.219:8001/api/resource/Delivery Note";
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "token b565e3d6c1f460d:59426b2f5a3047f"); // Add token if needed

        HttpEntity<ErpNextIventory> httpEntity = new HttpEntity<>(inventory, headers);

        RestTemplate restTemplate = new RestTemplate();
        try{
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, String.class);

        return response.getBody();

        }catch(Exception e){

            e.printStackTrace();
            return "failed";
        }

    }

}
