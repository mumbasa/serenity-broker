package com.serenity.serenity.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serenity.serenity.configuration.NoteSpecification;
import com.serenity.serenity.configuration.PatientSpecification;
import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.data.his.PatientData;
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

     public List<EncounterNote> findAll( String mrNumber,String practitionerId,String noteType,String startDate,String endDate,String keyword,String encounterType) {
        // Only create specifications for fields with values:
        Specification<EncounterNote> filters = Specification.where(StringUtils.isBlank(mrNumber) ? null : NoteSpecification.filterByMrNumber(mrNumber))
                                                       .and(StringUtils.isBlank(practitionerId) ? null : NoteSpecification.filterByPractionerId(practitionerId))
                                                       .and(StringUtils.isBlank(noteType) ? null : NoteSpecification.filterByType(noteType))
                                                       .and(StringUtils.isBlank(keyword) ? null : NoteSpecification.filterByKeyword(keyword))
                                                       .and(StringUtils.isBlank(encounterType) ? null : NoteSpecification.filterByEncounterType(encounterType))
                                                       .and(StringUtils.isBlank(startDate)||StringUtils.isBlank(endDate) ? null : NoteSpecification.filterByDates(startDate,endDate));

                                                    
                                                       
        // User our created Specification to query our repository
        return encounterNoteRepository.findAll(filters)
                                 .stream()
                                 .toList();
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
