package com.serenity.serenity.configuration;

import org.springframework.data.jpa.domain.Specification;

import com.serenity.serenity.data.his.PatientData;


public class PatientSpecification {

    public PatientSpecification(){

    }

    public static Specification<PatientData> filterByMobile(String mobile) {
        return (root, query, builder) -> builder.like(root.get("mobile"), "%" + mobile + "%");
    }

    public static Specification<PatientData> filterByMrNumber(String mrNumber) {
        return (root, query, builder)-> builder.like(root.get("mrNumber"), "%" + mrNumber + "%");
    }


    public static Specification<PatientData> filterByGender(String gender) {
        return (root, query, builder)-> builder.equal(root.get("gender"), gender);
    }

    public static Specification<PatientData> filterDateOfBirth(String dob) {
        return (root, query, builder)-> builder.equal(root.get("birthDate"), dob);
    }
}

