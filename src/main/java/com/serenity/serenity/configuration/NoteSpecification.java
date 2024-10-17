package com.serenity.serenity.configuration;

import org.springframework.data.jpa.domain.Specification;

import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.data.his.PatientData;


public class NoteSpecification {

    public NoteSpecification(){

    }

    public static Specification<EncounterNote> filterByMrNumber(String mr) {
        return (root, query, builder) -> builder.equal(root.get("patientMrNumber"), mr );
    }

    public static Specification<EncounterNote> filterByType(String type) {
        return (root, query, builder)-> builder.like(root.get("noteType"), "%" + type + "%");
    }


    public static Specification<EncounterNote> filterByPractionerId(String practitioner) {
        return (root, query, builder)-> builder.equal(root.get("practitionerId"), practitioner);
    }

    public static Specification<EncounterNote> filterByDates(String startDate,String endDate) {
        return (root, query, builder)-> builder.between(root.get("encounterDate"), startDate, endDate);
    }

    public static Specification<EncounterNote> filterByKeyword(String keyword) {
        return (root, query, builder)-> builder.like(root.get("note"), "%" + keyword + "%");
    }

    public static Specification<EncounterNote> filterByEncounterType(String keyword) {
        return (root, query, builder)-> builder.equal(root.get("encounterType"), keyword);
    }
}

