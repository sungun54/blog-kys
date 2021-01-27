package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.util.Util;

public class MailService {
	private String gmailId;
	private String gmailPw;
	private String from;
	private String fromName;
	private MemberDao memberDao;
	private AttrService attrService;
	
	public MailService(String gmailId, String gmailPw, String from, String fromName, Connection dbConn, AttrService attrService) {
		this.gmailId = gmailId;
		this.gmailPw = gmailPw;
		this.from = from;
		this.fromName = fromName;
		memberDao = new MemberDao(dbConn);
		this.attrService = attrService;
	}
	
	public void send(String to, String title, String body) {
		Util.sendMail(gmailId, gmailPw, from, fromName, to, title, body);
	}

	private String init() {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		int num = 0;

		do {
			num = ran.nextInt(75) + 48;
			if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				sb.append((char) num);
			} else {
				continue;
			}

		} while (sb.length() < size);
		if (lowerCheck) {
			return sb.toString().toLowerCase();
		}
		return sb.toString();
	}

	// 난수를 이용한 키 생성
	private boolean lowerCheck;
	private int size;

	public String getKey(boolean lowerCheck, int size) {
		this.lowerCheck = lowerCheck;
		this.size = size;
		return init();
	}

	// 회원가입 발송 이메일(인증키 발송)
	public void mailSendWithUserKey(String email, int id, HttpServletRequest req) {
		String key = getKey(false, 20);
		attrService.setValue("member__"+ id +"__extra__emailAuthCode", key);
		Util.cofirmMail(email, id, req, key, gmailId, gmailPw);
			
	}
}


