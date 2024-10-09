package com.serenity.serenity.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.data.his.Patient;
import com.serenity.serenity.data.his.PatientMapping;
import com.serenity.serenity.repository.EncounterNoteRepository;
import com.serenity.serenity.repository.PatientMappingRepository;
import com.serenity.serenity.utilities.Utility;

@Service
public class PatientService {

    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private PatientMappingRepository patientMappingRepository;

    @Autowired
    EncounterNoteRepository encounterNoteRepository;

    public List<Patient> list(String query) {

        List<Document> results = vectorStore.similaritySearch(SearchRequest.query(query).withTopK(5));
        System.err.println(results.get(0).getContent().split("\n")[0]);
        System.err.println(results.get(0).getMetadata().get("distance"));
        return Utility.getSerenityInventoryFromErp(results);
    }

    public List<PatientMapping> guestPatientMapping(String query) {

        return patientMappingRepository.findBySerenityId(query);

    }

    public List<PatientMapping> savePatientMapping(List<PatientMapping> mapping) {

        return patientMappingRepository.saveAll(mapping);

    }


    public void saveEncounter(){
   EncounterNote n = new EncounterNote();
   n.setDataSource("his");
   n.setNote("Fsfsdfsdaf");
   encounterNoteRepository.save(n);

    }
}
