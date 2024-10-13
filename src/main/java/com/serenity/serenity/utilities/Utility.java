package com.serenity.serenity.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.serenity.serenity.data.his.Patient;
import com.serenity.serenity.erpmodel.ErpInventory;
import com.serenity.serenity.erpmodel.ErpNextPayload;
import com.serenity.serenity.erpmodel.UpdateItem;
import com.serenity.serenity.erpmodel.UpdatePayload;
import com.serenity.serenity.model.SerenityInventoryItem;
import com.serenity.serenity.model.SerenityLocation;

public class Utility {

    public static List<SerenityInventoryItem> getSerenityInventoryFromErp(ErpNextPayload payload) {
        List<SerenityInventoryItem> items = new ArrayList<>();

        for (ErpInventory inv : payload.getItems()) {
            SerenityInventoryItem item = new SerenityInventoryItem();
            item.setLocation_name(Utility.getLocationDetails(payload.getToWarehouse()).getLocationName());
            item.setLocation_id(Utility.getLocationDetails(payload.getToWarehouse()).getLocationId());
            item.setName(inv.getItemName());
            item.setCode(inv.getItemCode());
            item.setIn_hand_quantity((int) Double.parseDouble(inv.getQty()));
            item.setReason(payload.getPurpose());
            item.setSellingPrice(inv.getSellingRate());
            item.setBatchNumber(inv.getBatchNumber());
            try{
            item.setSourceName(Utility.getLocationDetails(payload.getFromWarehouse()).getLocationName());
            item.setSourceId(Utility.getLocationDetails(payload.getFromWarehouse()).getLocationId());
            }catch(NullPointerException e){
                System.err.println("This is stores");

            }
            items.add(item);

                System.err.println(item.getName() + " loading =>"+ item.getIn_hand_quantity() );
        }
        return items;

    }

  
    public static List<SerenityInventoryItem> getSerenityInventoryFromErp(UpdatePayload update) {
        List<SerenityInventoryItem> items = new ArrayList<>();

        for (UpdateItem inv : update.getItems()) {
            SerenityInventoryItem item = new SerenityInventoryItem();
            System.err.println(update.getWarehouse() +" warehouse");
            item.setLocation_name(Utility.getLocationDetails(update.getWarehouse()).getLocationName());
            item.setLocation_id(Utility.getLocationDetails(update.getWarehouse()).getLocationId());
            item.setName(inv.getItemName());
            item.setCode(inv.getItemCode());
            item.setIn_hand_quantity((int)(inv.getQuantity()));
            item.setReason(update.getPurpose());
            item.setSellingPrice(inv.getSellingRate());
            items.add(item);
         

        }
        return items;

    }



    public static List<Patient> getSerenityInventoryFromErp(List<Document> documents) {
        List<Patient> items = new ArrayList<>();

        for (Document inv : documents) {
            Patient item = new Patient();
            String[] lines = inv.getContent().split("\n");
            item.setConfidence((1-(float)inv.getMetadata().get("distance"))*100);
            for(String s : lines){
                
                try{
                Field field = Patient.class.getDeclaredField(s.split(":")[0].strip());
                field.setAccessible(true);
                field.set(item, s.split(":")[1].strip());
                }catch(Exception e ){
                    System.err.println("Not Found\t"+s.split(":")[0].strip());

                }
            
            }

            items.add(item);

        }
        return items;

    }



    public static SerenityLocation getLocationDetails(String locaton) {
        String serenityLocation = "[{\"locationId\": \"6b46da79-5613-4827-91ae-f46aaf65d4da\",\"locationName\": \"Accra Central (Octagon)\"},"
                + "{\"locationId\": \"23f59485-8518-4f4e-9146-d061dfe58175\",\"locationName\": \"Airport Primary Care\"},"
                + "{\"locationId\": \"b60c55f5-63dd-4ba2-9fe9-8192f57aaed2\",\"locationName\": \"Tema Primary Care\"},"
                + "{\"locationId\": \"a79ae42b-03b7-4f5e-ac1a-cd42729c0b04\",\"locationName\": \"Takoradi Primary Care\"},"
                + "{\"locationId\": \"29e22113-9d7b-46a6-a857-810ca3567ca7\",\"locationName\": \"Airport Main\"},"
                + "{\"locationId\": \"25d3f170-2c76-413f-b4e0-7c09b4847b68\",\"locationName\": \"Kumasi\"}"
                + "{\"locationId\": \"2550dc16-3f64-4cee-b808-6c13b255d159\",\"locationName\": \"Ward - Airport Main\"}"+
                "]";
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<SerenityLocation>>() {
        }.getType();
        ArrayList<SerenityLocation> serenitylocations = gson.fromJson(serenityLocation, listType);

        String[] erpNextLocation = {"Pharmacy - Octagon - NMC", "Pharmacy - Airport Primary Care - NMC", "Pharmacy - Tema - NMC", "Pharmacy - Takoradi - NMC", "Main Pharmacy - Airport Main - NMC","Ward Pharmacy - Airport Main - NMC"};
        Map<String, SerenityLocation> locations = new HashMap<>();

        for (int i = 0; i < erpNextLocation.length; i++) {
            SerenityLocation location = new SerenityLocation(serenitylocations.get(i).getLocationId(), serenitylocations.get(i).getLocationName());
            locations.put(erpNextLocation[i], location);
        }

        return locations.get(locaton);

    }


    public static String getErpnextLocation(String locaton) {
        String serenityLocation = "[{\"locationId\": \"6b46da79-5613-4827-91ae-f46aaf65d4da\",\"locationName\": \"Accra Central (Octagon)\"},"
                + "{\"locationId\": \"23f59485-8518-4f4e-9146-d061dfe58175\",\"locationName\": \"Airport Primary Care\"},"
                + "{\"locationId\": \"b60c55f5-63dd-4ba2-9fe9-8192f57aaed2\",\"locationName\": \"Tema Primary Care\"},"
                + "{\"locationId\": \"a79ae42b-03b7-4f5e-ac1a-cd42729c0b04\",\"locationName\": \"Takoradi Primary Care\"},"
                + "{\"locationId\": \"29e22113-9d7b-46a6-a857-810ca3567ca7\",\"locationName\": \"Airport Main\"},"
                + "{\"locationId\": \"25d3f170-2c76-413f-b4e0-7c09b4847b68\",\"locationName\": \"Kumasi\"}]";
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<SerenityLocation>>() {
        }.getType();
        ArrayList<SerenityLocation> serenitylocations = gson.fromJson(serenityLocation, listType);

        String[] erpNextLocation = {"Pharmacy - Octagon - NMC", "Pharmacy - Airport Primary Care - NMC", "Pharmacy - Tema - NMC", "Pharmacy - Takoradi - NMC", "Main Pharmacy - Airport Main - NMC"};
        Map<String, String> locations = new HashMap<>();

        for (int i = 0; i < erpNextLocation.length; i++) {
            SerenityLocation location = new SerenityLocation(serenitylocations.get(i).getLocationId(), serenitylocations.get(i).getLocationName());
            locations.put(serenitylocations.get(i).getLocationName(),erpNextLocation[i]);
        }

        return locations.get(locaton);

    }

    public static void main(String[] args) {
        Patient p = new Patient();
        try{
        Field name =Patient.class.getDeclaredField("name");
        name.setAccessible(true);
        name.set(p, "fafa");
    }catch(Exception e){

    }
    System.err.println(p.getName());
    }
}
