package com.serenity.serenity.repository.his;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serenity.serenity.data.his.EncounterNote;

@Repository
public interface EncounterNoteRepository extends  JpaRepository<EncounterNote, String>{

}
