package com.yml.inventorymanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.simple.*;
import org.json.simple.parser.*;

public class InventoryManager {
	public static void inventoryManager() {
		Map<String, Double> map = new HashMap<String,Double>();
		List<JSONObject> objectList = inventoryFactory();
		for(JSONObject object : objectList) {
			String name = (String) object.get("name");
			String type = (String) object.get("type");
			double weight = (double) object.get("weight");
			double pricePerKg = (double) object.get("pricePerKg");
			double totalPrice = weight * pricePerKg; 
			System.out.println("\nName : "+name);
			System.out.println("Type : "+type);
			System.out.println("Weight : "+weight);
			System.out.println("PricePerKg : "+pricePerKg);
			System.out.println("Total price : "+totalPrice+"\n");
			map.put(name, totalPrice);
		}
		JSONArray array = new JSONArray();
		Set<Entry<String,Double>> set = map.entrySet();
		
		for(Entry<String,Double> entry : set) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",entry.getKey());
			jsonObject.put("totalPrice", entry.getValue());
			array.add(jsonObject);
		}
		JSONObject mainObject = new JSONObject();
		mainObject.put("results", array);
		System.out.println(mainObject.toJSONString());
	}

	private static List<JSONObject> inventoryFactory() {
		List<JSONObject> objList = new ArrayList<JSONObject>();
		try {
			Reader reader = new FileReader("data/inventory.json");
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			JSONArray array = (JSONArray) jsonObject.get("inventory");
//			System.out.println(array);
			Iterator<JSONObject> iterator = array.iterator();
			while(iterator.hasNext()) {
				JSONObject jsonObj2 = iterator.next();
//				System.out.println(); 
//				System.out.println(jsonObj2);
				objList.add(jsonObj2);
			}
		}
			catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return objList;
		
		
	}
}
