package com.serenity.serenity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.serenity.serenity.data.his.EncounterNote;

@Repository
public interface EncounterNoteRepository extends  JpaRepository<EncounterNote, Long>{

    List<EncounterNote> findByPatientMrNumberIn(List<String> ids);
    List<EncounterNote> findByPatientMrNumber(String id);
    List<EncounterNote> findByPractitionerName(String id);
    List<EncounterNote> findByNoteType(String id);
    @Query(value = "SELECT * FROM encounternote where encounterdate BETWEEN ?1 AND ?2",nativeQuery = true)
    List<EncounterNote> findByDateCreated(String start,String date);
}
