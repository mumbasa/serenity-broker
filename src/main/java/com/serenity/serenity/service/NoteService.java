package com.serenity.serenity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serenity.serenity.data.his.EncounterNote;
import com.serenity.serenity.repository.EncounterNoteRepository;



@Service
public class NoteService {


    @Autowired
    EncounterNoteRepository encounterNoteRepository;    


    public static void main(String[] args) {
    
//getHisNote(numbr);



}

/* public void getNotes(List<PatientMapping> mapping){
List<String> sources = mapping.stream().map(PatientMapping::getSource).distinct().collect(Collectors.toList());
Set<String> his = new HashSet<>();
mapping.stream().forEach(e ->{
his.add(e.getSourceId());

});

List<EncounterNote> notes= new ArrayList<>();
notes.addAll(getHisNote(his.stream().collect(Collectors.toList())));
notes.stream().sorted(Comparator.comparing(EncounterNote::getCreatedAt)).toList();


}
 */



    public  List<EncounterNote>  getHisNote(List<String> numbers){
        List<EncounterNote> notes = new ArrayList<>();
    String queryDetails =  String.join("' OR source.patient_mr_number='",numbers.toArray(String[]::new )) ;  
    String sql = "SELECT `source`.`created_at` AS `created_at`, " +
    "`source`.`updated_at` AS `updated_at`, " +
    "`source`.`note` AS `note`, " +
    "`source`.`note_type` AS `note_type`, " +
    "`source`.`encounter_date` AS `encounter_date`, " +
    "`source`.`patient_mr_number` AS `patient_mr_number`, " +
    "`source`.`encounter_type` AS `encounter_type`, " +
    "`source`.`is_recalled` AS `is_recalled`, " +
    "`source`.`practitioner_role_type` AS `practitioner_role_type`, " +
    "`source`.`practitioner_name` AS `practitioner_name`, " +
    "`source`.`practitioner_id` AS `practitioner_id`, " +
    "`source`.`is_edited` AS `is_edited`, " +
    "`pm`.`PName` AS `patient_name`, " +
    "`pm`.`Mobile` AS `patient_mobile`, " +
    "`pm`.`DOB` AS `patient_dob`, " +
    "`pm`.`Age` AS `patient_age`, " +
    "`pm`.`Gender` AS `patient_gender` " +
    "FROM ( " +
    "    SELECT CONCAT( " +
    "        progress_notes.TransactionId, " +
    "        \"_nyaho_his_nursing_doctorprogressnote_\", " +
    "        progress_notes.ID " +
    "    ) AS `uuid`, " +
    "    DATE_FORMAT(progress_notes.EntryDate, '%d-%b-%Y %l:%i %p') AS `created_at`, " +
    "    progress_notes.UpdateDate AS `updated_at`, " +
    "    progress_notes.ProgressNote AS `note`, " +
    "    FALSE AS `is_formatted`, " +
    "    'progress note' AS `note_type`, " +
    "    progress_notes.TransactionId AS `encounter_id`, " +
    "    DATE_FORMAT(progress_notes.NoteDate, '%d-%b-%Y %l:%i %p') AS `encounter_date`, " +
    "    patients.Patient_ID AS `patient_mr_number`, " +
    "    'progress note' AS `encounter_type`, " +
    "    FALSE AS `is_edited`, " +
    "    FALSE AS `is_recalled`, " +
    "    'unknown' AS `practitioner_role_type`, " +
    "    CONCAT(practitioners.title, ' ', practitioners.Name) AS `practitioner_name`, " +
    "    progress_notes.UserID AS `practitioner_id` " +
    "FROM nursing_doctorprogressnote AS progress_notes " +
    "INNER JOIN patient_ipd_profile AS admissions " +
    "    ON admissions.Transaction_ID = progress_notes.TransactionId " +
    "INNER JOIN patient_master AS patients " +
    "    ON admissions.PatientID = patients.Patient_ID " +
    "LEFT JOIN employee_master AS practitioners " +
    "    ON progress_notes.UserID = practitioners.Employee_ID " +
    "ORDER BY progress_notes.EntryDate DESC " +
    ") AS `source` " +
    "LEFT JOIN `patient_master` AS `pm` " +
    "    ON `source`.`patient_mr_number` = `pm`.`Patient_ID` " +
    "WHERE source.patient_mr_number ='"+queryDetails +"'";
    /* SqlRowSet set =hisJdbcTemplate.queryForRowSet(sql);
    while(set.next()){
        EncounterNote note = new EncounterNote();
        note.setCreatedAt(set.getString(1));
        note.setUpdatedAt(set.getString(2));
        note.setNote(set.getString(3));
        note.setNoteType(set.getString(4));
        note.setEncounterDate(set.getString(5));
        note.setPatientMrNumber(set.getString(6));
        note.setRecalled(set.getBoolean(7));
        note.setPractitionerRoleType(set.getString(8));
        note.setPractitionerName(set.getString(9));
        note.setPractitionerId(set.getString(10));
        note.setEdited(set.getBoolean(11));
        note.setDataSource("his");
        notes.add(note);
    } */

    return notes;

    }

public List<EncounterNote> getNotes(String ids){

return encounterNoteRepository.findByPatientMrNumber(ids);

}


public List<EncounterNote> getNotes(List<String> ids){
    System.err.println(ids);

    return encounterNoteRepository.findByPatientMrNumberIn(ids);
    
    }
    


  
}


