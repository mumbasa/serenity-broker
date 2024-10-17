package com.serenity.serenity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.serenity.serenity.data.his.Encounter;



public interface EncounterRepository extends JpaRepository<Encounter,Long>,JpaSpecificationExecutor<Encounter>{

}
