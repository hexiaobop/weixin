package com.test;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class gsonIm {
	public static void main(String[] args) {
		Gson gson = new Gson();
		JsonObject jsono = new JsonObject();
		jsono.addProperty("s", 1);
		ArrayList<Integer> list = new ArrayList<>();
		JsonArray json = new JsonArray();
		jsono.add("list", json);
		JsonParser p = new JsonParser();
		jsono.addProperty("list1",gson.toJson(list).toString());
		System.out.println(jsono);
		JsonElement jse = p.parse(jsono.toString());
		System.out.println(jse.getAsJsonObject().get("list1"));
		System.out.println();
	}

}
