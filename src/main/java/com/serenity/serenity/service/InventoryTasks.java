package com.serenity.serenity.service;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.serenity.serenity.data.his.SerenityInventoryStore;
import com.serenity.serenity.model.ErpNextIventory;
import com.serenity.serenity.model.SerenityBroker;
import com.serenity.serenity.model.SerenityInventoryItem;
import com.serenity.serenity.model.SerenityInventoryResponse;
import com.serenity.serenity.model.SerenityStock;
import com.serenity.serenity.repository.InventoryRepository;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;

@Component
public class InventoryTasks {
    Logger LOGGER = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    @Value("${serenity.token}")
    private String serenityToken;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    RestTemplate restTemplate;

    public void serentityInventoryUpdate(List<SerenityInventoryItem> stocks, boolean k)
            throws RestClientException, UnsupportedEncodingException {
        System.err.println("------------------------ start");
        List<SerenityInventoryItem> newEntries = new ArrayList<>();
        List<SerenityInventoryItem> oldEtries = new ArrayList<>();
        // looping to separate the new from the old stock
        for (SerenityInventoryItem stock : stocks) {
            stock.setExternal_system("erpnext");
            SerenityInventoryResponse res = stockCounter2(stock);
            if (res.getTotal() > 0) {
                  // stock.setLocation_id(res.getData().get(0).getLocationId());
                //stock.setLocation_name(res.getData().get(0).getLocationName());
                oldEtries.add(stock);

            } else {
                LOGGER.info("found item to create");
                newEntries.add(stock);
            }

        }
        System.err.println("------------------------ starting");
        // processing the lists
        if (!newEntries.isEmpty()) {
            try {
                serenityCeate(newEntries);
            } catch (Exception e) {
                LOGGER.error("error occured");
                e.printStackTrace();
            }
        }
        if (!oldEtries.isEmpty()) {
            for (SerenityInventoryItem s : oldEtries) {
                serenityUpdate(s);
            }

        }
    }

    public void serentityInventoryAdjust2(List<SerenityInventoryItem> stocks, boolean k)
            throws UnsupportedEncodingException {

        Map<String, SerenityInventoryItem> itemMap = new HashMap<>();

        LOGGER.info("Creating map");

        // adding same items irrespective of batch numbers
        stocks.stream().forEach(e -> {
            e.setExternal_system("erpnext");

            if (itemMap.containsKey(e.getName())) {
                SerenityInventoryItem item = itemMap.get(e.getCode());
                item.setIn_hand_quantity(item.getIn_hand_quantity() + e.getIn_hand_quantity());
                itemMap.replace(e.getName(), item);
            } else {
                itemMap.put(e.getName(), e);

            }
        });

        List<SerenityInventoryItem> newEntries = new ArrayList<>();
        List<SerenityInventoryItem> oldEtries = new ArrayList<>();
        // looping to separate the new from the old stock

        for (SerenityInventoryItem stock : itemMap.values()) {
            SerenityInventoryResponse res = stockCounter2(stock);
            if (res.getTotal() > 0) {
                oldEtries.add(stock);
                LOGGER.info("found item to update");

            } else {
                LOGGER.info("found item to create");
                newEntries.add(stock);
            }

        }
        System.err.println("------------------------ Adjusting");
        // processing the lists
        if (!newEntries.isEmpty()) {
            try {
                serenityCeate(newEntries);
            } catch (Exception error) {
                LOGGER.error("error occured");
                List<SerenityInventoryStore> store = new ArrayList<>();
                newEntries.stream().forEach(e -> {
                    store.add(new SerenityInventoryStore(e, "create", error));
                });
                inventoryRepository.saveAll(store);
                error.printStackTrace();
            }
        }
        if (!oldEtries.isEmpty()) {
            for (SerenityInventoryItem s : oldEtries) {
                try {
                    serenityUpdate(s);
                } catch (Exception error) {
                    error.printStackTrace();
                    inventoryRepository.save(new SerenityInventoryStore(s, "update", error));

                }
            }
        }
    }

    public void serentityInventoryAdjust(List<SerenityInventoryItem> stocks, boolean k)
            throws UnsupportedEncodingException {
        System.err.println("------------------------ start");
        List<SerenityInventoryItem> newEntries = new ArrayList<>();
        List<SerenityInventoryItem> oldEtries = new ArrayList<>();
        // looping to separate the new from the old stock
        for (SerenityInventoryItem stock : stocks) {
            stock.setExternal_system("erpnext");

            SerenityInventoryResponse res = stockAdjust(stock);
            if (res.getTotal() > 0) {
                oldEtries.add(stock);
                LOGGER.info("found item to update");

            } else {
                LOGGER.info("found item to create");
                newEntries.add(stock);
            }

        }
        System.err.println("------------------------ Adjusting");
        // processing the lists
        if (!newEntries.isEmpty()) {

            serenityCeate(stocks);
        }

        if (!oldEtries.isEmpty()) {
            for (SerenityInventoryItem s : oldEtries) {
                serenityUpdate(s);
            }
        }
    }

    @SuppressWarnings("null")
    public SerenityInventoryResponse serenitySearch(SerenityInventoryItem stock) throws UnsupportedEncodingException {
        LOGGER.info("Searching for " + stock.getCode());

        String params = URLEncoder.encode(stock.getCode() + "&location_name=" + stock.getLocation_name(), "UTF-8");
        String url = "https://stag.api.cloud.serenity.health/v2/inventory?code=" + params;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-api-key", "efomrddi");
        // headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<SerenityInventoryResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                SerenityInventoryResponse.class);
        // setting the stock with the data in serenity
        if (response.getBody().getTotal() > 0) {
            System.out.println("found ---------------------------");
            SerenityStock s = response.getBody().getData().get(0);
            int qty = (int) (s.getInHandQuantity() + stock.getIn_hand_quantity());
            stock.setUuid(response.getBody().getData().get(0).getUuid());
            stock.setLocation_id(response.getBody().getData().get(0).getLocationId());
            stock.setIn_hand_quantity(qty);
            System.err.println("Serenity Stock is " + s.getInHandQuantity() + " Addition  is "
                    + stock.getIn_hand_quantity() + " is quantity");
        }

        return response.getBody();
    }

    public void serentityTransfer(List<SerenityInventoryItem> stocks) {
        for (SerenityInventoryItem item : stocks) {

            stockTransferCheck(item);

        }
    }

    @SuppressWarnings("null")
    public void serenitySearchF(SerenityInventoryItem stock) throws UnsupportedEncodingException {
        String[] locations = { stock.getLocation_name(), stock.getSourceName() };
        List<SerenityInventoryItem> items = new ArrayList<>();
        LOGGER.info("Searching for " + stock.getCode());
        int count = 0; // this is to create a counter to enable the substraction from the source stock
        for (String location : locations) {
            System.err.println(location + "------------------");

            String params = URLEncoder.encode(stock.getCode() + "&location_name=" + stock.getLocation_name(), "UTF-8");
            String url = "https://stag.api.cloud.serenity.health/v2/inventory?code=" + params;
            // "&batch_number="+stock.getBatchNumber();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.add("x-api-key", "efomrddi");
            // headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<SerenityInventoryResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                    SerenityInventoryResponse.class);
            LOGGER.info(response.getBody().toString());

            // setting the stock with the data in serenity
            if (response.getBody().getTotal() > 0) {
                SerenityInventoryItem item = new SerenityInventoryItem();
                SerenityStock s = response.getBody().getData().get(0);
                int qty = (int) s.getInHandQuantity() + stock.getIn_hand_quantity();
                item.setUuid(response.getBody().getData().get(0).getUuid());
                item.setReason(stock.getReason());

                if (count > 0) {

                    item.setLocation_id(stock.getSourceId());
                    item.setLocation_name(stock.getSourceName());
                    item.setIn_hand_quantity((int) (s.getInHandQuantity() - stock.getIn_hand_quantity()));
                    item.setName(stock.getCode());
                    item.setCode(stock.getCode());
                    LOGGER.info(stock.getIn_hand_quantity() + "\tSubstacting stock " + s.getInHandQuantity());
                } else {
                    item.setLocation_id(stock.getLocation_id());
                    item.setLocation_name(stock.getLocation_name());
                    item.setIn_hand_quantity((int) (s.getInHandQuantity() + stock.getIn_hand_quantity()));
                    item.setName(stock.getCode());
                    item.setCode(stock.getCode());
                    LOGGER.info(stock.getIn_hand_quantity() + "\tTransfering stock" + s.getInHandQuantity());

                }
                LOGGER.info(location + "------------------\t" + stock.getLocation_name() + " ---count "
                        + stock.getIn_hand_quantity());
                items.add(item);

            } else {

                // if it is target
                if (count == 0) {
                    serenityCeate(stock);
                }
            }
            count++;
        }
        System.err.println(items.size());
        for (SerenityInventoryItem it : items) {
            System.err.println("Syncing for \t" + it.getLocation_name() + "\t" + it.getName());
            serenityUpdate(it);

        }

    }

    public String serenityCeate(List<SerenityInventoryItem> stock) {
        Gson j = new Gson();
        LOGGER.info("Adding new entries to inventory for " + j.toJson(stock));
        String url = "https://stag.api.cloud.serenity.health/v2/inventory";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.add("x-api-key", "efomrddi");
        // headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
        HttpEntity<List<SerenityInventoryItem>> httpEntity = new HttpEntity<>(stock, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public String serenityCeate(SerenityInventoryItem stock) {
        LOGGER.info("Adding new entries to inventory for " + stock.toString());

        String url = "https://stag.api.cloud.serenity.health/v2/inventory";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.add("x-api-key", "efomrddi"); // Add token if needed
        HttpEntity<SerenityInventoryItem> httpEntity = new HttpEntity<>(stock, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return response.getBody();
    }

    public String serenityUpdate(SerenityInventoryItem stock) {
        LOGGER.info("Updating inventory for " + stock.getCode() + "\t " + stock.getIn_hand_quantity());

        HttpResponse<String> response = Unirest
                .patch("https://stag.api.cloud.serenity.health/v2/inventory/" + stock.getUuid())
                .header("Content-Type", "application/json")
                .header("x-api-key", "efomrddi")
                // .header("Authorization", "Bearer "+serenityToken) // Add token if needed
                .body(stock)
                .asString();
        System.err.println(response.getBody());
        return response.getBody();

    }

    public String serenityUpdate(SerenityInventoryItem stock, boolean tea)
            throws RestClientException, UnsupportedEncodingException {
        LOGGER.info("Updating inventory for " + stock.getCode() + "\t " + stock.getIn_hand_quantity());

        String url = "https://stag.api.cloud.serenity.health/v2/inventory";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.add("x-api-key", "efomrddi");
        // headers.set("Authorization", "Bearer "+serenityToken); // Add token if needed
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

        String endpoint = "https://staging-erp.nyaho.tech/api/resource/Delivery Note";
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "token b85940219b20a4d:0cc7ecdba6659cd"); // Add token if needed

        HttpEntity<ErpNextIventory> httpEntity = new HttpEntity<>(inventory, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity,
                    String.class);
            System.err.println(response.getBody());
            return response.getBody();

        } catch (Exception e) {

            e.printStackTrace();
            return "failed";
        }
 
    }

    public String stockCounter2(String code,String location) {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("x-api-key", "efomrddi");

        HttpResponse<JsonNode> jsonResponse = Unirest.get("https://stag.api.cloud.serenity.health/v2/inventory")
                .headers(headers)
                .queryString("code", code)
                .queryString("location_name", location)
                .asJson();
        System.err.println(jsonResponse.getBody().toPrettyString());

        return jsonResponse.getBody().getObject().getInt("total") + "";
    }

    public SerenityInventoryResponse stockAdjust(SerenityInventoryItem item) {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("x-api-key", "efomrddi");

        HttpResponse<JsonNode> jsonResponse = Unirest.get("https://stag.api.cloud.serenity.health/v2/inventory")
                .headers(headers)
                .queryString("code", item.getCode())
                .queryString("location_name", item.getLocation_name())
                .asJson();
        System.err.println(jsonResponse.getBody().toPrettyString());
        SerenityInventoryResponse response = new SerenityInventoryResponse();

        if (jsonResponse.getBody().getObject().getInt("total") > 0) {
          
           // int qty = (int) (jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getInt("in_hand_quantity"));
            //item.setIn_hand_quantity(qty);
            item.setUuid(jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("uuid"));
            item.setLocation_id(jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("location_id"));
            item.setLocation_name(jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("location_name"));

            response.setTotal(jsonResponse.getBody().getObject().getInt("total"));
            response.setSize(jsonResponse.getBody().getObject().getInt("size"));
        }

        return response;
    }


    public SerenityInventoryResponse stockCounter2(SerenityInventoryItem item) {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("x-api-key", "efomrddi");

        HttpResponse<JsonNode> jsonResponse = Unirest.get("https://stag.api.cloud.serenity.health/v2/inventory")
                .headers(headers)
                .queryString("code", item.getCode())
                .queryString("location_name", item.getLocation_name())
                .asJson();
        System.err.println(jsonResponse.getBody().toPrettyString()+" ----data");
        SerenityInventoryResponse response = new SerenityInventoryResponse();

        if (jsonResponse.getBody().getObject().getInt("total") > 0) {
          
            int qty = (int) (jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getInt("in_hand_quantity") + item.getIn_hand_quantity());
            item.setIn_hand_quantity(qty);
            item.setUuid(jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("uuid"));
            item.setLocation_id(jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("location_id"));
            item.setLocation_name(jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("location_name"));

            response.setTotal(jsonResponse.getBody().getObject().getInt("total"));
            response.setSize(jsonResponse.getBody().getObject().getInt("size"));
        }

        return response;
    }

    public void stockTransferCheck(SerenityInventoryItem stock) {
        String[] locations = { stock.getLocation_name(), stock.getSourceName() };
        List<SerenityInventoryItem> items = new ArrayList<>();
        LOGGER.info("Searching for " + stock.getCode());
        int count = 0; // this is to create a counter to enable the substraction from the source stock
        for (String location : locations) {
            System.err.println(location + "------------------");

            Map<String, String> headers = new HashMap<>();
            headers.put("accept", "application/json");
            headers.put("x-api-key", "efomrddi");

            HttpResponse<JsonNode> jsonResponse = Unirest.get("https://stag.api.cloud.serenity.health/v2/inventory")
                    .headers(headers)
                    .queryString("code", stock.getCode())
                    .queryString("location_name", location)
                    .asJson();
            System.err.println(jsonResponse.getBody().toPrettyString());
            if (jsonResponse.getBody().getObject().getInt("total") > 0) {
                SerenityInventoryItem item = new SerenityInventoryItem();
                int qty = jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0)
                        .getInt("in_hand_quantity");// + stock.getIn_hand_quantity();
                item.setUuid(
                        jsonResponse.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("uuid"));
                item.setReason(stock.getReason());

                if (count > 0) {

                    item.setLocation_id(stock.getSourceId());
                    item.setLocation_name(stock.getSourceName());
                    item.setIn_hand_quantity((int) (qty - stock.getIn_hand_quantity()));
                    item.setName(stock.getCode());
                    item.setCode(stock.getCode());
                    LOGGER.info(stock.getIn_hand_quantity() + "\tSubstacting stock from " + qty);
                } else {
                    item.setLocation_id(stock.getLocation_id());
                    item.setLocation_name(stock.getLocation_name());
                    item.setIn_hand_quantity((int) (qty + stock.getIn_hand_quantity()));
                    item.setName(stock.getCode());
                    item.setCode(stock.getCode());
                    LOGGER.info(stock.getIn_hand_quantity() + "\tTransfering stock" + qty);

                }
                LOGGER.info(location + "------------------\t" + stock.getLocation_name() + " ---count "
                        + stock.getIn_hand_quantity());
                items.add(item);

            } else {

                // if it is target
                if (count == 0) {
                    serenityCeate(stock);
                }
            }
            count++;

        }

        System.err.println(items.size());
        for (SerenityInventoryItem it : items) {
            System.err.println("Syncing for \t" + it.getLocation_name() + "\t" + it.getName());
            serenityUpdate(it);

        }

    }

}
