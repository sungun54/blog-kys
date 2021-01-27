package com.sbs.java.blog.util;

import java.sql.Connection;
import java.util.TimerTask;

import com.sbs.java.blog.dao.MemberDao;

public class TimeTask extends TimerTask{
	private String loginId;
	private MemberDao memberDao;

	public TimeTask(String loginId, Connection dbConn) {
		this.loginId = loginId;
		memberDao = new MemberDao(dbConn);
	}

	@Override
	public void run() {
		memberDao.deleteMember(loginId);	
	}
	
}
