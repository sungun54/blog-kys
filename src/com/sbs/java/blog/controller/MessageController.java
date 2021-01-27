package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.Message;
import com.sbs.java.blog.service.MessageService;

public class MessageController extends Controller {
	public MessageController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String getControllerName() {
		return "message";
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "list":
			return actionList();
		}

		return "";
	}

	private String actionList() {
		
		int toId = 0;
		
		List<Message> messages = messageService.getMessages(toId);

		return "message/list.jsp";
	}
}
