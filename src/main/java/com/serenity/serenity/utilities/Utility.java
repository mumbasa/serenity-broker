package com.serenity.serenity.utilities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.serenity.serenity.erpmodel.ErpInventory;
import com.serenity.serenity.erpmodel.ErpInventoryMessage;
import com.serenity.serenity.erpmodel.ErpNextPayload;
import com.serenity.serenity.erpmodel.MessageObjects;
import com.serenity.serenity.model.SerenityInventoryItem;
import com.serenity.serenity.model.SerenityLocation;

public class Utility {

    public static List<SerenityInventoryItem> getSerenityInventoryFromErp(ErpNextPayload payload) {
        List<SerenityInventoryItem> items = new ArrayList<>();

        for (ErpInventory inv : payload.getItems()) {
            SerenityInventoryItem item = new SerenityInventoryItem();
            item.setLocation_name(payload.getTo());
            item.setName(inv.getItem_name());
            item.setCode(inv.getItem_code());
            item.setIn_hand_quantity((int) Double.parseDouble(inv.getQty()));
            item.setReason(payload.getPurpose());
            items.add(item);

        }
        return items;

    }

    public static List<SerenityInventoryItem> getSerenityInventoryFromErp(MessageObjects message) {
        List<SerenityInventoryItem> items = new ArrayList<>();
        for (ErpInventoryMessage mess : message.getMessages()) {
            for (ErpInventory inv : mess.getPayload().getItems()) {
                SerenityInventoryItem item = new SerenityInventoryItem();
                item.setLocation_name(mess.getPayload().getTo());
                item.setName(inv.getItem_name());
                item.setCode(inv.getItem_code());
                item.setIn_hand_quantity(Integer.parseInt(inv.getQty()));
                item.setReason(mess.getPayload().getPurpose());
                items.add(item);
                item.setLocation_id("29e22113-9d7b-46a6-a857-810ca3567ca7");

            }
        }
        return items;

    }

    public static SerenityLocation getLocationDetails(String locaton) {
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
        Map<String, SerenityLocation> locations = new HashMap<>();

        for (int i = 0; i < erpNextLocation.length; i++) {
            SerenityLocation location = new SerenityLocation(serenitylocations.get(i).getLocationId(), serenitylocations.get(i).getLocationName());
            System.err.println(location.getLocationName());
            locations.put(erpNextLocation[i], location);
        }

        return locations.get(locaton);

    }


    public static void main(String[] args) {
        System.err.println(getLocationDetails("Pharmacy - Octagon - NMC"));
    }
}
