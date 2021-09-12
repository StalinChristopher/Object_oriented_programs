package com.yml.inventorymanagementsystem;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class InventoryManager {
    public static void inventoryManager() {
        InventoryFactory inventoryFactory = new InventoryFactory();
        
        //Fetching the list of inventory objects from InventoryFactory class
        List<JSONArray> inventoryList = inventoryFactory.getInventories();  
        
        //List to store the each inventory with all the items and their respective price
        List<JSONObject> allInventories = new ArrayList<JSONObject>();
        
        //Iterate through the inventory list and calculate total price for each item in an inventory
        for (JSONArray inventory : inventoryList) {
            //priceMap to hold all the items and their total price in a inventory
            Map<String, Double> priceMap = new HashMap<String, Double>();
            Iterator<JSONObject> iterator = inventory.iterator();
            while (iterator.hasNext()) {
                JSONObject item = (JSONObject) iterator.next();
                String name = (String) item.get("name");
                double weight = (double) item.get("weight");
                double pricePerKG = (double) item.get("pricePerKG");
                double totalPrice = weight * pricePerKG;
                priceMap.put(name, totalPrice);
            }

            //converting Map to JSON object and adding it to inventories list
            JSONObject inventoryJSON = new JSONObject();
            inventoryJSON.putAll(priceMap);
            allInventories.add(inventoryJSON);
        }
        
        //Traverse through all the inventories and calculate total cost of an inventory and display JSON string of items in it.
        int inventoryNumber = 1;
        for (JSONObject inventory : allInventories) {
            System.out.println("\nInventory "+inventoryNumber);
            System.out.println("--------------");
            double sum = 0;
            for (Object item : inventory.keySet()) {
                sum += (double) inventory.get(item);
            }
            System.out.println("Inventory Items:\n");
            System.out.println(inventory.toJSONString());
            System.out.println("\nTotal value of the inventory: " + sum);
            System.out.println();
            inventoryNumber++;
        }
    }
}
