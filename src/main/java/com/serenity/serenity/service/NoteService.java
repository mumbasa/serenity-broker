package com.serenity.serenity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.repository.EncounterNoteRepository;

@Service
public class NoteService {

    @Autowired
    EncounterNoteRepository encounterNoteRepository;

    public static void main(String[] args) {

        // getHisNote(numbr);

    }

        public List<EncounterNote> getNotesByType(String mrNumber,int page,int size) {

        return encounterNoteRepository.findByNoteType(mrNumber,PageRequest.of(page, size)).toList();

    }

    public List<EncounterNote> getNotes(String ids,int page,int size) {

        return encounterNoteRepository.findByPatientMrNumberContains(ids,PageRequest.of(page, size)).getContent();

    }


   
    public List<EncounterNote> getByPractitioner(String practitioner,int page,int size) {

        return encounterNoteRepository.findByPractitionerIdContains(practitioner,PageRequest.of(page, size)).getContent();

    }


    public List<EncounterNote> getByDates(String start,String end) {

        return encounterNoteRepository.findByDateCreated(start, end);

    }
    public List<EncounterNote> getNotesByIds(List<String> ids,int page,int size) {
        System.err.println(ids);

        return encounterNoteRepository.findByPatientMrNumberIn(ids,PageRequest.of(page, size)).getContent();

    }

}
