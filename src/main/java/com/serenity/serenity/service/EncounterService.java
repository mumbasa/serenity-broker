package com.serenity.serenity.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serenity.serenity.configuration.EncounterSpecification;
import com.serenity.serenity.data.his.Encounter;
import com.serenity.serenity.repository.EncounterRepository;

@Service
public class EncounterService {

    @Autowired
    EncounterRepository encounterRepository;


public List<Encounter> findAll( List<String> mrNumbers,String practitionerId,String encounterType) {
        // Only create specifications for fields with values:
        Specification<Encounter> filters = Specification.where(mrNumbers.isEmpty() ? null : EncounterSpecification.filterByMrNumber(mrNumbers))
                                                       .and(StringUtils.isBlank(practitionerId) ? null : EncounterSpecification.filterByPractionerId(practitionerId))
                                                       .and(StringUtils.isBlank(encounterType) ? null : EncounterSpecification.filterByEncounterClass(encounterType));
                                           
                                                    
                                                       
        // User our created Specification to query our repository
        return encounterRepository.findAll(filters)
                                 .stream()
                                 .toList();
    }



}
