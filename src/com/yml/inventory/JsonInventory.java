package com.yml.inventory;
import java.util.*;
import java.util.Map.Entry;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public class JsonInventory {

	public static void main(String[] args) {
		JSONParser jsonParser  = new JSONParser();
		Map<String, Double> map = new HashMap<String,Double>();
		try {
			Reader reader = new FileReader("data/inventory.json");
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			System.out.println(jsonObject);
			JSONArray array = (JSONArray) jsonObject.get("inventory");
			System.out.println(array);
			Iterator<JSONObject> iterator = array.iterator();
			while(iterator.hasNext()) {
				JSONObject jsonObj2 = iterator.next();
				System.out.println(); 
				System.out.println(jsonObj2);
				String name = (String) jsonObj2.get("name");
				System.out.println("Name : "+name);
				String type = (String) jsonObj2.get("type");
				System.out.println("Type : "+type);
				double weight = (double) jsonObj2.get("weight");
				System.out.println("Weight : "+weight);
				double pricePerKg = (double) jsonObj2.get("pricePerKg");
				System.out.println("PricePerKg : "+pricePerKg);
				double totalPrice = weight * pricePerKg;
				map.put(name, totalPrice);
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		
		writeJsonOutput(map);

	}

	private static void writeJsonOutput(final Map<String, Double> map) {
		try {
			Writer writer = new FileWriter("data/results.json");
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
			System.out.println(mainObject);
			writer.write(mainObject.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
