package com.serenity.serenity;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class SerenityApplication {

   @Autowired
    private VectorStore vectorStore;

	@Value("${patients.data}")
	String patientsData;

	public static void main(String[] args) {
		SpringApplication.run(SerenityApplication.class, args);
	}

	@PostConstruct
	 void load() {
        
            JsonReader jsonReader = new JsonReader(new FileSystemResource(patientsData),
                    "name", "gender", "patient_id", "dob", "mobile");

            List<Document> documents = jsonReader.get();
            
            this.vectorStore.add(documents);
  }
}
