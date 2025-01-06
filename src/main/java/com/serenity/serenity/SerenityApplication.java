package com.serenity.serenity;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;

import com.serenity.serenity.service.InventoryTasks;
import com.serenity.serenity.utilities.Utility;

import jakarta.annotation.PostConstruct;



@SpringBootApplication
@EnableJpaRepositories(basePackages="com.serenity.serenity.repository")
public class SerenityApplication {


    @Autowired
    InventoryTasks tasks;

   // @Autowired
    //PatientService patientService;

	@Value("${patients.data}")
	String patientsData;
        Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());

	public static void main(String[] args) {
		SpringApplication.run(SerenityApplication.class, args);
	}

      //@PostConstruct 
	/*  void load() throws FileNotFoundException {
                logger.info("working to add");

         //   JsonReader jsonReader = new JsonReader(new FileSystemResource(ResourceUtils.getFile("classpath:new.json")),
         //           "name", "gender", "patient_id", "dob", "mobile");
         JsonReader jsonReader = new JsonReader(new FileSystemResource(ResourceUtils.getFile("classpath:all.json")),
         "name", "gender", "mrNumber", "dob", "mobile","id","source");
         logger.info("found to add");

            List<Document> documents = jsonReader.get();
           
            for (int i=0; i<Math.ceil(documents.size()/100) ; i++){
                logger.info("adding");
try{
               this.vectorStore.add( documents.subList(i*100,(i*100)+100));
}catch(Exception e){
   e.printStackTrace();
   System.err.println("failed");
}
            }
            
        } */
@PostConstruct
  public void loaders(){
//System.err.println(Utility.getErpnextLocation("Airport Main"));
   //System.err.println(tasks.stockCounter2("PRESCRIPTION-PADS-NEW","Tema Primary Care"));
      System.err.println(tasks.stockCounter2("ORS-NEW","Accra Central (Octagon)"));
      
   
  // System.err.println(tasks.stockCounter2("PROMAN-NEW","Airport Main"));
   //   System.err.println(tasks.stockCounter2("PROMAN-NEW","Tema Primary Care"));

  }
}
