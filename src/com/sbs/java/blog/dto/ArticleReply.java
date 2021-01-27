package com.sbs.java.blog.dto;

import java.util.Map;

public class ArticleReply extends Dto{
	private String updateDate;
	private String body;	
	private int articleId;
	private int memberId;
	
	public ArticleReply(Map<String, Object> row) {	
		super(row);
		this.updateDate = (String)row.get("updateDate");
		this.body = (String)row.get("body");
		this.articleId = (int)row.get("articleId");
		this.memberId = (int)row.get("memberId");
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "ArticleReply [updateDate=" + updateDate + ", body=" + body + ", articleId=" + articleId + ", memberId="
				+ memberId + "]";
	}
	
	
			
}
