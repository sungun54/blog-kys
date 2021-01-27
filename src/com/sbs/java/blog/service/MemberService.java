package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dto.Member;

public class MemberService extends Service {
	
	private MemberDao memberDao;
	private AttrService attrService;
	
	public MemberService(Connection dbConn, AttrService attrService) {
		memberDao = new MemberDao(dbConn);
		this.attrService = attrService;
	}
	
	public int join(String loginId, String name, String nickname, String loginPw, String email) {
		return memberDao.join(loginId, name, nickname, loginPw, email);
	}

	public Map<String, Object> login(String loginId, String loginPw) {
		return memberDao.login(loginId, loginPw);
	}


	public Member getMemberById(int loginedMemberId) {
		return memberDao.getMemberById(loginedMemberId);
	}

	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}
	
	public boolean isJoinableNickname(String nickname) {
		return memberDao.isJoinableNickname(nickname);
	}

	public boolean isJoinableEmail(String email) {
		return memberDao.isJoinableEmail(email);
	}

	public Map<String, Object> findLoginId(String name, String email) {
		return memberDao.findLoginId(name, email);
	}

	public void temporaryPw(int id, String uuid) {
		memberDao.temporaryPw(id, uuid);
	}

	public Map<String, Object> findLoginPw(String name, String email, String loginId) {
		return memberDao.findLoginPw(name, email, loginId);
	}

	public void Modify(String nickname, String email, int id) {
		memberDao.Modify(nickname, email, id);		
	}

	public boolean nicknameCheck(String nickname) {
		return memberDao.nicknameCheck(nickname);	
	}

	public void pwModify(int id, String loginPw) {
		memberDao.pwModify(id, loginPw);	
		
	}

	public boolean pwConfirm(int id, String loginPw) {
		return memberDao.pwConfirm(id, loginPw);		
	}

	public void mailAuth(int id) {
		memberDao.mailAuth(id);
	}

	public boolean keyConfirm(int id, String key) {
		return memberDao.keyConfirm(id, key);
	}
	

	public void deleteMember(String loginId) {
		memberDao.deleteMember(loginId);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public boolean getRegDateMember(String loginId) {
		return memberDao.getRegDateMember(loginId);
	}
	
	public String genModifyPrivateAuthCode(int actorId) {
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + actorId + "__extra__modifyPrivateAuthCode", authCode);

		return authCode;
	}

	public boolean isValidModifyPrivateAuthCode(int actorId, String authCode) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__modifyPrivateAuthCode");

		return authCodeOnDB.equals(authCode);
	}

	public void Modify(String nickname, int id) {
		memberDao.Modify(nickname, id);				
	}

	public void update(int id) {
		memberDao.update(id);	
	}

	public boolean getRegDateMemberYear(String loginId) {
		return memberDao.getRegDateMemberYear(loginId);
	}

}
