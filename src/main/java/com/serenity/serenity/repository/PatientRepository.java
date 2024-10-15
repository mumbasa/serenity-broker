package com.serenity.serenity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.serenity.serenity.data.his.PatientData;

public interface PatientRepository extends JpaRepository<PatientData,Long> ,JpaSpecificationExecutor<PatientData>{
    Optional<PatientData> findByMrNumber(String mr);
    Optional<PatientData> findByMobile(String mr);
    Optional<PatientData> findByMobileAndBirthDate(String mobile,String date);
}
