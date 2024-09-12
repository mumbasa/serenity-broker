package com.serenity.serenity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serenity.serenity.model.SerenityInventoryItem;
import com.serenity.serenity.model.SerenityInventoryResponse;
import com.serenity.serenity.service.InventoryTasks;

@RestController
public class RabbitController {

    @Autowired
    InventoryTasks tasks;

    @GetMapping("dispense")
    public ResponseEntity<SerenityInventoryResponse> mains(@RequestParam String name,@RequestParam String location) {
        SerenityInventoryItem stock = new SerenityInventoryItem();
        stock.setName(name);
        stock.setLocation_name(location);
        return new ResponseEntity<SerenityInventoryResponse>(tasks.serenitySearch(stock),HttpStatus.OK);
    }
}
