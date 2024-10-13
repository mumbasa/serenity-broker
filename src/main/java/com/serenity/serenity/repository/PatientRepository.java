package com.serenity.serenity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serenity.serenity.data.his.PatientData;

public interface PatientRepository extends JpaRepository<PatientData,Long> {
    
}
