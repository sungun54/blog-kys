package com.sbs.java.blog.dto;

import java.util.Map;

public class Category extends Dto {
	private String name;
	
	public Category(Map<String, Object> row) {
		super(row);
		this.name = (String)row.get("name");
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
