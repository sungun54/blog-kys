package com.sbs.java.blog.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.service.MailService;

public class MailController extends Controller {
	private String gmailId;
	private String gmailPw;
	public MailController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp, String gmailId, String gmailPw) {
		super(dbConn, actionMethodName, req, resp);
		this.gmailId = gmailId;
		this.gmailPw = gmailPw;
	}

	@Override
	public String getControllerName() {
		return "mail";
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "send":
			return actionSend();
		case "doSend":
			return actionDoSend();
		}

		return "";
	}

	private String actionSend() {
		return "mail/send.jsp";
	}

	public String actionDoSend() {
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		String email = req.getParameter("email");
		
		MailService mailService = new MailService(gmailId, gmailPw, gmailId, "관리자", dbConn, attrService);
		mailService.send(email, title, body);
		
		return "html:<script> alert('이메일이 발송되었습니다.'); location.replace('../home/main'); </script>";
	}
}
