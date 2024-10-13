package com.serenity.serenity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<EncounterNote> getNotes(String ids) {

        return encounterNoteRepository.findByPatientMrNumber(ids);

    }


    public List<EncounterNote> getByType(String tye) {

        return encounterNoteRepository.findByNoteType(tye);

    }

    public List<EncounterNote> getByPractitioner(String practitioner) {

        return encounterNoteRepository.findByPractitionerName(practitioner);

    }


    public List<EncounterNote> getByDates(String start,String end) {

        return encounterNoteRepository.findByDateCreated(start, end);

    }
    public List<EncounterNote> getNotes(List<String> ids) {
        System.err.println(ids);

        return encounterNoteRepository.findByPatientMrNumberIn(ids);

    }

}
