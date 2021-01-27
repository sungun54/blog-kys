package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.Map;
import java.util.TimerTask;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.Attr;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class MemberDao {
	
	private Connection dbConn;

	public MemberDao(Connection dbConn) {
		this.dbConn = dbConn;
	}
	
	public int join(String loginId, String name, String nickname, String loginPw, String email) {
		SecSql secSql = new SecSql();
		/*
		sql += String.format("INSERT INTO Member ");
		sql += String.format("SET regDate = NOW()");
		sql += String.format(", loginId = '%s'", loginId);
		sql += String.format(", loginPw = '%s'", loginPw);
		sql += String.format(", nickname = '%s'", nickname);
		sql += String.format(", name = '%s'", name);
		*/
		secSql.append("INSERT INTO `Member` ");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", loginId = ? ", loginId);
		secSql.append(", loginPw = ? ", loginPw);
		secSql.append(", nickname = ? ", nickname);
		secSql.append(", name = ? ", name);
		secSql.append(", mailAuthCode = 1");
		secSql.append(", mailAuthStatus = 0");
		secSql.append(", email = ? ", email);	
		
		
		return DBUtil.insert(dbConn, secSql);
	}

	public Map<String, Object> login(String loginId, String loginPw) {
		SecSql secSql = new SecSql();
		
		secSql.append("SELECT * ");
		secSql.append("FROM `Member` ");
		secSql.append("WHERE loginId = ? ", loginId);
		secSql.append("AND loginPw = ?", loginPw);
		
		return DBUtil.selectRow(dbConn, secSql);
		/*
		String sql = "";
		
		sql += String.format("SELECT * ");
		sql += String.format("FROM `Member` ");
		sql += String.format("WHERE loginId = '%s' ", loginId);
		sql += String.format("AND loginPw = '%s'", loginPw);
		
		return DBUtil.selectRow(dbConn, sql);
		*/
	}

	public boolean isJoinableLoginId(String loginId) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `Member`");
		sql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowIntValue(dbConn, sql) == 0;
	}

	public boolean isJoinableNickname(String nickname) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `Member`");
		sql.append("WHERE nickname = ?", nickname);

		return DBUtil.selectRowIntValue(dbConn, sql) == 0;
	}

	public boolean isJoinableEmail(String email) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `Member`");
		sql.append("WHERE email = ?", email);

		return DBUtil.selectRowIntValue(dbConn, sql) == 0;
	}
	
	public Member getMemberById(int id) {
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM `Member`");
		sql.append("WHERE id = ?", id);

		return new Member(DBUtil.selectRow(dbConn, sql));
	}

	public Map<String, Object> findLoginId(String name, String email) {
		SecSql sql = SecSql.from("SELECT * ");
		sql.append("FROM `Member` ");
		sql.append("WHERE name = ? ", name);
		sql.append("AND email = ? ", email);

		return DBUtil.selectRow(dbConn, sql);
	}

	public void temporaryPw(int id, String uuid) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE `Member` ");
		secSql.append("SET loginPw = ? ", uuid);
		secSql.append("WHERE id = ?", id);

		DBUtil.update(dbConn, secSql);		
	}

	public Map<String, Object> findLoginPw(String name, String email, String loginId) {
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM `Member`");
		sql.append("WHERE name = ?", name);
		sql.append("AND email = ? ", email);
		sql.append("AND loginId = ?", loginId);
		
		return DBUtil.selectRow(dbConn, sql);
	}

	public void Modify(String nickName, String email, int id) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE `Member` ");
		secSql.append("SET nickName = ? ", nickName);
		secSql.append(", email = ? ", email);
		secSql.append("WHERE id = ?", id);

		DBUtil.update(dbConn, secSql);	
		
	}

	public boolean nicknameCheck(String nickname) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `Member`");
		sql.append("WHERE nickname = ?", nickname);

		return DBUtil.selectRowIntValue(dbConn, sql) == 0;
	}

	public void pwModify(int id, String loginPw) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE `Member` ");
		secSql.append("SET loginPw = ? ", loginPw);
		secSql.append("WHERE id = ?", id);

		DBUtil.update(dbConn, secSql);			
	}

	public boolean pwConfirm(int id, String loginPw) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `Member`");
		sql.append("WHERE id = ?", id);
		sql.append("AND loginPw = ?", loginPw);		

		return DBUtil.selectRowIntValue(dbConn, sql) == 1;
	}

	public void GetKey(String loginId, String key) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE `Member` ");
		secSql.append("SET mailAuthCode = ? ", key);
		secSql.append("WHERE loginId = ?", loginId);

		DBUtil.update(dbConn, secSql);				
	}

	public boolean keyConfirm(int id, String key) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `Member`");
		sql.append("WHERE id = ?", id);
		sql.append("AND mailAuthCode = ?", key);

		return DBUtil.selectRowIntValue(dbConn, sql) == 1;
	}

	public void mailAuth(int id) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE `Member` ");
		secSql.append("SET mailAuthStatus = 1 ");
		secSql.append("WHERE id = ?", id);

		DBUtil.update(dbConn, secSql);		
	}

	public void deleteMember(String loginId) {
		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM `Member` ");
		secSql.append("WHERE loginId = ?", loginId);
		
		DBUtil.delete(dbConn, secSql);
	}

	public Member getMemberByLoginId(String loginId) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT *");
		secSql.append("FROM `Member`");
		secSql.append("WHERE loginId = ?", loginId);

		Member member = null;

		Map<String, Object> row = DBUtil.selectRow(dbConn, secSql);

		if ( row.isEmpty() == false ) {
			member = new Member(row);			
		}

		return member;
	}
	
	public boolean getRegDateMember(String loginId) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(M.regDate) > 60 * 10 AS regDatePastSeconds");
		secSql.append("FROM `Member` AS M");
		secSql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowIntValue(dbConn, secSql) == 1;
	}

	public void Modify(String nickname, int id) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE `Member` ");
		secSql.append("SET nickName = ? ", nickname);

		secSql.append("WHERE id = ?", id);
		DBUtil.update(dbConn, secSql);		
	}

	public void update(int id) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE `Member` ");
		secSql.append("SET updateDate = NOW() ");

		secSql.append("WHERE id = ?", id);
		DBUtil.update(dbConn, secSql);			
	}

	public boolean getRegDateMemberYear(String loginId) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(M.updateDate) > 60 * 60 * 24 * 365 AS regDatePastSeconds");
		secSql.append("FROM `Member` AS M");
		secSql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowIntValue(dbConn, secSql) == 1;
	}

}

