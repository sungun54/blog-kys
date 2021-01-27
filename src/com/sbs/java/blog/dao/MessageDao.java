package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.List;

import com.sbs.java.blog.dto.Message;

public class MessageDao extends Dao{

	private Connection dbConn;

	public MessageDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public List<Message> getMessages(int toId) {
		// TODO Auto-generated method stub
		return null;
	}

}
