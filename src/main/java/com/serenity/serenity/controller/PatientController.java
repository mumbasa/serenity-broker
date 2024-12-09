package com.serenity.serenity.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.data.his.Patient;
import com.serenity.serenity.data.his.PatientData;
import com.serenity.serenity.data.his.PatientMapping;
import com.serenity.serenity.dto.PatientMappingDTO;
import com.serenity.serenity.model.SerenityInventoryItem;
import com.serenity.serenity.model.SerenityInventoryResponse;
import com.serenity.serenity.service.InventoryTasks;
import com.serenity.serenity.service.NoteService;
import com.serenity.serenity.service.PatientService;


@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    InventoryTasks tasks;

   @Autowired
    private PatientService patientService;

    @Autowired
    NoteService noteService;
/* 
    @GetMapping("/search")
    public ResponseEntity<List<Patient>>  list(@RequestParam("query") String query) {
      
        return ResponseEntity.ok(patientService.list(query));
    }
 */
    @GetMapping("/exact/search")
    public ResponseEntity<Optional<PatientData>>  searchDobANDMobile(@RequestParam("dob") String dob,@RequestParam("mobile") String mobile) {
      
        return ResponseEntity.ok(patientService.getByDOBMobile(mobile, dob));
    }

    @GetMapping("/exact/search/fields")
    public ResponseEntity<List<PatientData>>  searchDobANDMobile(@RequestParam(required = false) String dob,@RequestParam(required = false) String mobile,@RequestParam(required = false) String gender,@RequestParam(required = false) String mrNumber) {
      
        return ResponseEntity.ok(patientService.findAll(dob, mobile, mrNumber, gender));
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
    public ResponseEntity<SerenityInventoryResponse> mains(@RequestParam String name,@RequestParam String location) throws RestClientException, UnsupportedEncodingException {
        SerenityInventoryItem stock = new SerenityInventoryItem();
        stock.setName(name);
        stock.setLocation_name(location);
        return new ResponseEntity<SerenityInventoryResponse>(tasks.serenitySearch(stock),HttpStatus.OK);
    }
   
}
