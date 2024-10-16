package com.serenity.serenity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.service.NoteService;

@RestController
public class NoteController {
   @Autowired
    NoteService noteService;

    @GetMapping("/patient/encounter/")
    public ResponseEntity<List<EncounterNote>>  getEncounterNotes(@RequestParam(required = false) String type,@RequestParam(required = false) String mrNumber,@RequestParam(required = false) String practitionerId,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate,@RequestParam(required = false) String keyword,@RequestParam(required = false) String encounterType) {
      
        return ResponseEntity.ok(noteService.findAll(mrNumber,practitionerId,type,startDate,endDate,keyword,encounterType));
    }


    @GetMapping("/patient/encounter/mrnumbers")
    public ResponseEntity<List<EncounterNote>>  getEncounterMrs(@RequestParam List<String> mrNumbers,@RequestParam int page,@RequestParam int size) {
      
        return ResponseEntity.ok(noteService.getNotesByIds(mrNumbers, page, size));
    }






}
