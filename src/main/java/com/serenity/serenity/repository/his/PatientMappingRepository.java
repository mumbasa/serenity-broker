package com.serenity.serenity.repository.his;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serenity.serenity.data.his.PatientMapping;

@Repository
public interface PatientMappingRepository extends  JpaRepository<PatientMapping, Long> {
    
    public List<PatientMapping> findBySerenityId(String serenityId);
}
