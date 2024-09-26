package com.serenity.serenity;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

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
	 void load() throws FileNotFoundException {
        
            JsonReader jsonReader = new JsonReader(new FileSystemResource(ResourceUtils.getFile("classpath:baba.json")),
                    "name", "gender", "patient_id", "dob", "mobile");

            List<Document> documents = jsonReader.get();
            
            this.vectorStore.add(documents);
  }
}
