package com.sbs.java.blog.dto;

import java.util.Map;

public class Message extends Dto {
	private String updateDate;
	private String toId;
	private String fromId;
	private String title;
	private String body;
	
	public Message(Map<String, Object> row) {	
		super(row);
		this.updateDate = (String)row.get("updateDate");
		this.toId = (String)row.get("toId");
		this.fromId = (String)row.get("fromId");
		this.title = (String)row.get("title");
		this.body = (String)row.get("body");
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Message [updateDate=" + updateDate + ", toId=" + toId + ", fromId=" + fromId + ", title=" + title
				+ ", body=" + body + ", getId()=" + getId() + ", getRegDate()=" + getRegDate() + ", getExtra()="
				+ getExtra() + ", toString()=" + super.toString() + "]";
	}	
	
}
