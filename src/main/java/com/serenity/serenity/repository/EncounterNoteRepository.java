package com.serenity.serenity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.serenity.serenity.data.his.EncounterNote;

@Repository
public interface EncounterNoteRepository extends  JpaRepository<EncounterNote, Long>{

    Page<EncounterNote> findByPatientMrNumberIn(List<String> ids,Pageable pageable);
    Page<EncounterNote> findByPatientMrNumberContains(String id,Pageable pageable);
    Page<EncounterNote> findByPractitionerIdContains(String id,Pageable pageable);
    Page<EncounterNote> findByNoteType(String id,Pageable pageable);
    @Query(value = "SELECT * FROM encounternote where encounterdate BETWEEN ?1 AND ?2",nativeQuery = true)
    List<EncounterNote> findByDateCreated(String start,String date);
}
