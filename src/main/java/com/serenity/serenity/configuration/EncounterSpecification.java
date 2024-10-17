package com.serenity.serenity.configuration;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.serenity.serenity.data.his.Encounter;
import com.serenity.serenity.data.his.PatientData;


public class EncounterSpecification {

    public EncounterSpecification(){

    }

    public static Specification<Encounter> filterByEncounterClass(String encounterClass) {
        return (root, query, builder) -> builder.like(root.get("encounterClass"), "%" + encounterClass + "%");
    }


    public static Specification<Encounter> filterByPractionerId(String pid) {
        return (root, query, builder)-> builder.like(root.get("practitionerId"), "%" + pid + "%");
    }
    public static Specification<Encounter> filterByMrNumber(List<String> mr) {
        return (root, query, builder)->root.get("patientMrNumber").in(mr);
    }

}

