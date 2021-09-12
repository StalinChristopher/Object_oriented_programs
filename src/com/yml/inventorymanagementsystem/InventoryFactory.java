package com.yml.inventorymanagementsystem;

import java.io.FileReader;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class InventoryFactory {
	
	//method is called inside the constructor to initialise the inventories list
	InventoryFactory(){
		readInventoryFile();
	}
    private List<JSONArray> inventories = new ArrayList<>();
    
    public List<JSONArray> getInventories() {
        return inventories;
    }

    //Method to read the all the different inventories present in the JSON file and create inventory object
    public void readInventoryFile(){
    	try {
            FileReader reader = new FileReader("data/inventorylist.json");
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);
            JSONArray inventoryList = (JSONArray) object.get("inventories");

            Iterator<JSONArray> itr = inventoryList.iterator();
            while (itr.hasNext()) {
                JSONArray inventory = itr.next();
                inventories.add(inventory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
