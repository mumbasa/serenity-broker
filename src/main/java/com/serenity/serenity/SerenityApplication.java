package com.serenity.serenity;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;



@SpringBootApplication
public class SerenityApplication {

   @Autowired
    private VectorStore vectorStore;

	@Value("${patients.data}")
	String patientsData;
        Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());

	public static void main(String[] args) {
		SpringApplication.run(SerenityApplication.class, args);
	}

      // @PostConstruct 
	 void load() throws FileNotFoundException {
                logger.info("working to add");

         //   JsonReader jsonReader = new JsonReader(new FileSystemResource(ResourceUtils.getFile("classpath:new.json")),
         //           "name", "gender", "patient_id", "dob", "mobile");
         JsonReader jsonReader = new JsonReader(new FileSystemResource(ResourceUtils.getFile("classpath:all.json")),
         "name", "gender", "mrNumber", "dob", "mobile","lastname","firstname","id","source");
         logger.info("found to add");

            List<Document> documents = jsonReader.get();
           
            for (int i=0; i<Math.ceil(documents.size()/100) ; i++){
                logger.info("adding");

               this.vectorStore.add( documents.subList(i*100,(i*100)+100));
            }
            
      
  }
}
