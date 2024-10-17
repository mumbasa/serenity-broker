package com.serenity.serenity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serenity.serenity.data.his.Encounter;
import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.service.EncounterService;
import com.serenity.serenity.service.NoteService;

@RestController
public class EncounterController {
   @Autowired
    EncounterService encounterService;

    @GetMapping("/patient/encounter/mrnumbers")
    public ResponseEntity<List<Encounter>>  getEncounterMrs(@RequestParam(required = false) List<String> mrNumbers,@RequestParam(required = false) String practitionerId,@RequestParam(required = false) String encounterType) {
        {
      
        return ResponseEntity.ok(encounterService.findAll(mrNumbers,practitionerId,encounterType));
    }


    }



}
