package com.sbs.java.blog.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.service.ArticleService;
import com.sbs.java.blog.service.AttrService;
import com.sbs.java.blog.service.MemberService;
import com.sbs.java.blog.service.MessageService;

public abstract class Controller {
	protected Connection dbConn;
	protected String actionMethodName; 
	protected HttpServletRequest req; 
	protected HttpServletResponse resp;
	protected HttpSession session;
	protected ArticleService articleService;
	protected MemberService memberService;
	protected MessageService messageService;
	protected AttrService attrService;
	
	public Controller(Connection dbConn, String actionMethodName, HttpServletRequest req, HttpServletResponse resp) {
		this.dbConn = dbConn;
		this.actionMethodName = actionMethodName;
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();
		articleService = new ArticleService(dbConn);	
		memberService = new MemberService(dbConn, attrService);
		messageService = new MessageService(dbConn);
		attrService = new AttrService(dbConn);		
	}
	
	public void beforeAction() {
		List<Category> categories = articleService.getCateItems();		
		req.setAttribute("categories", categories);
		int loginedMemberId = 0;
		boolean isLogined = false;
		Member loginedMember = null;
		if(session.getAttribute("loginedMemberId") != null){
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			isLogined = true;
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		req.setAttribute("loginedMemberId", loginedMemberId);
		req.setAttribute("loginedMember", loginedMember);
		req.setAttribute("isLogined", isLogined);
		
		String currentUrl = req.getRequestURI();
		
		if(req.getQueryString()!= null) {
			currentUrl += "?" + req.getQueryString();
		}
		
		req.setAttribute("currentUrl", currentUrl);
		try {
			req.setAttribute("urlEncodedCurrentUrl", URLEncoder.encode(currentUrl, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	public abstract String doAction();
	
	public void afterAction() {
		
	}

	public String executeAction() {
		
		beforeAction();
		
		String doGuardRs = doGuard();

		if (doGuardRs != null) {
			return doGuardRs;
		}

		
		String rs = doAction();
		afterAction();
		
		return rs;
	}

	public abstract String getControllerName();
	
	private String doGuard() {
		boolean isLogined = (boolean) req.getAttribute("isLogined");

		// 로그인에 관련된 가드 시작
		boolean needToLogin = false;

		String controllerName = getControllerName();

		switch (controllerName) {
		case "member":
			switch (actionMethodName) {
			case "doLogout":
				needToLogin = true;
				break;
			}
			break;
		case "article":
			switch (actionMethodName) {
			case "write":
			case "doWrite":
			case "modify":
			case "doModify":
			case "doDelete":
			case "doReplyWrite":	
				needToLogin = true;
				break;
			}
			break;
		}
		
		if (needToLogin && isLogined == false) {
			return "html:<script> alert('로그인 후 이용해주세요.'); location.href = '../member/login?redirectUrl=" + req.getAttribute("urlEncodedCurrentUrl") + "'; </script>";
		}
		// 로그인에 관련된 가드 끝

		// 로그아웃에 관련된 가드 시작
		boolean needToLogout = false;

		switch (controllerName) {
		case "member":
			switch (actionMethodName) {
			case "login":
			case "join":
				needToLogout = true;
				break;
			}
			break;
		}

		if (needToLogout && isLogined ) {
			return "html:<script> alert('로그아웃 후 이용해주세요.'); history.back(); </script>";
		}
		// 로그아웃에 관련된 가드 끝

		return null;
	}
	
}
