package com.serenity.serenity.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serenity.serenity.data.Patient;
import com.serenity.serenity.data.PatientMapping;
import com.serenity.serenity.dto.PatientMappingDTO;
import com.serenity.serenity.model.SerenityInventoryItem;
import com.serenity.serenity.model.SerenityInventoryResponse;
import com.serenity.serenity.service.InventoryTasks;
import com.serenity.serenity.service.PatientService;


@RestController
@RequestMapping("/patient")
public class RabbitController {

    @Autowired
    InventoryTasks tasks;

   @Autowired
    private PatientService patientService;

    @GetMapping("/search")
    public ResponseEntity<List<Patient>>  list(@RequestParam("query") String query) {
      
        return ResponseEntity.ok(patientService.list(query));
    }


    @GetMapping("/mapping")
    public ResponseEntity<List<PatientMapping>>  patientMapping(@RequestParam("query") String query) {
      
        return ResponseEntity.ok(patientService.guestPatientMapping(query));
    }

    @PostMapping("/patient/mapping")
    public ResponseEntity<List<PatientMapping>>  patientMapping(@RequestBody List<PatientMapping> mapping) {
      
        return ResponseEntity.ok(patientService.savePatientMapping(mapping));
    }
    @GetMapping("dispense")
    public ResponseEntity<SerenityInventoryResponse> mains(@RequestParam String name,@RequestParam String location) {
        SerenityInventoryItem stock = new SerenityInventoryItem();
        stock.setName(name);
        stock.setLocation_name(location);
        return new ResponseEntity<SerenityInventoryResponse>(tasks.serenitySearch(stock),HttpStatus.OK);
    }
   
}
