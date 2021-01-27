package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.List;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dao.MessageDao;
import com.sbs.java.blog.dto.Message;

public class MessageService extends Service {
	private MessageDao messageDao;
	
	public MessageService(Connection dbConn) {
		messageDao = new MessageDao(dbConn);
	}

	public List<Message> getMessages(int toId) {
		return messageDao.getMessages(toId);
	}	
	
}
