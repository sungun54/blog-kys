package com.sbs.java.blog.dto;

import java.util.Map;

public class Article extends Dto{
	private String updateDate;
	private String title;
	private String body;	
	private int cateItemId;
	private int displayStatus;
	private int hit;
	private int memberId;
	
	public Article(Map<String, Object> row) {	
		super(row);
		this.updateDate = (String)row.get("updateDate");
		this.title = (String)row.get("title");
		this.body = (String)row.get("body");
		this.cateItemId = (int)row.get("cateItemId");
		this.displayStatus = (int)row.get("displayStatus");
		this.hit = (int) row.get("hit");
		this.memberId = (int) row.get("memberId");
	}
	
	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	@Override
	public String toString() {
		return "Article [id=" + getId() + ", regDate=" + getRegDate() + ", updateDate=" + updateDate + ", title=" + title
				+ ", body=" + body + "]";
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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

	public int getCateItemId() {
		return cateItemId;
	}

	public void setCateItemId(int cateItemId) {
		this.cateItemId = cateItemId;
	}

	public int getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(int displayStatus) {
		this.displayStatus = displayStatus;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getBodyForXTemplate() {
		return body.replaceAll("(?i)script", "<!--REPLACE:script-->");
	}
	
}
