package com.serenity.serenity.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serenity.serenity.model.SerenityInventoryItem;
import com.serenity.serenity.model.SerenityInventoryResponse;
import com.serenity.serenity.service.InventoryTasks;

@RestController
@RequestMapping("/patient")
public class RabbitController {

    @Autowired
    InventoryTasks tasks;

   @Autowired
    private VectorStore vectorStore;

	@Value("${patients.data}")
	String patientsData;
   
    public void create() {
        System.err.println(" startring");
        Document a = new Document( "Prometheus collects metrics from targets by scraping metrics HTTP endpoints. Since Prometheus exposes data in the same manner about itself, it can also scrape and monitor its own health.");
       Document b = new Document("Prometheus local time series database stores data in a custom, highly efficient format on local storage.");
       List<Document> c = new ArrayList<>();
       c.add(a);
       c.add(b);
       vectorStore.add(c);
    }

    @GetMapping
    public String list(@RequestParam("query") String query) {
        List<Document> results = vectorStore.similaritySearch(SearchRequest.query(query).withTopK(5));
        return results.toString();
    }
    @GetMapping("dispense")
    public ResponseEntity<SerenityInventoryResponse> mains(@RequestParam String name,@RequestParam String location) {
        SerenityInventoryItem stock = new SerenityInventoryItem();
        stock.setName(name);
        stock.setLocation_name(location);
        return new ResponseEntity<SerenityInventoryResponse>(tasks.serenitySearch(stock),HttpStatus.OK);
    }
    @GetMapping("/baba")
     void load() {
        
            JsonReader jsonReader = new JsonReader(new FileSystemResource(patientsData),
                    "name", "gender", "patient_id", "dob", "mobile");

            List<Document> documents = jsonReader.get();
            
            this.vectorStore.add(documents);
  }
}
