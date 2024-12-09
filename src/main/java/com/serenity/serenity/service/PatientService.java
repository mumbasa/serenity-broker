package com.serenity.serenity.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.serenity.serenity.configuration.PatientSpecification;
import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.data.his.Patient;
import com.serenity.serenity.data.his.PatientData;
import com.serenity.serenity.data.his.PatientMapping;
import com.serenity.serenity.repository.EncounterNoteRepository;
import com.serenity.serenity.repository.PatientMappingRepository;
import com.serenity.serenity.repository.PatientRepository;
import com.serenity.serenity.utilities.Utility;

@Service
public class PatientService {

   // @Autowired
    //private VectorStore vectorStore;
    @Autowired
    private PatientMappingRepository patientMappingRepository;
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    EncounterNoteRepository encounterNoteRepository;

   /*  public List<Patient> list(String query) {

        List<Document> results = vectorStore.similaritySearch(SearchRequest.query(query).withTopK(5));
        System.err.println(results.get(0).getContent().split("\n")[0]);
        System.err.println(results.get(0).getMetadata().get("distance"));
        return Utility.getSerenityInventoryFromErp(results);
    }
 */
    public List<PatientMapping> guestPatientMapping(String query) {

        return patientMappingRepository.findBySerenityId(query);

    }

    public List<PatientMapping> savePatientMapping(List<PatientMapping> mapping) {

        return patientMappingRepository.saveAll(mapping);

    }




    public Optional<PatientData> getByDOBMobile(String mobile,String dob){
return patientRepository.findByMobileAndBirthDate(mobile, dob);
        
    }

   public List<PatientData> findAll(String dob, String mobile, String mrNumber,String gender) {
        // Only create specifications for fields with values:
        Specification<PatientData> filters = Specification.where(StringUtils.isBlank(dob) ? null : PatientSpecification.filterDateOfBirth(dob))
                                                       .and(StringUtils.isBlank(mobile) ? null : PatientSpecification.filterByMobile(mobile))
                                                       .and(StringUtils.isBlank(mrNumber) ? null : PatientSpecification.filterByMrNumber(mrNumber))
                                                       .and(StringUtils.isBlank(gender) ? null : PatientSpecification.filterByGender(gender));

                                                    
                                                       
        // User our created Specification to query our repository
        return patientRepository.findAll(filters)
                                 .stream()
                                 .toList();
    }
}
