package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class ArticleDao extends Dao {
	private Connection dbConn;

	public ArticleDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public List<Article> getForPrintListArticles(int page, int cateItemId) {
		SecSql secSql = new SecSql();

		int itemsInAPage = 5;
		int limitFrom = (page - 1) * itemsInAPage;

		secSql.append("SELECT * ");
		secSql.append("FROM Article ");
		secSql.append("WHERE displayStatus = 1 ");
		if (cateItemId != 0) {
			secSql.append("AND cateItemId = ? ", cateItemId);
		}
		secSql.append("ORDER BY id DESC ");
		secSql.append("LIMIT ?, ? ", limitFrom, itemsInAPage);

		/*
		 * String sql = "";
		 * 
		 * int itemsInAPage = 5; int limitFrom = (page - 1) * itemsInAPage;
		 * 
		 * sql += String.format("SELECT * "); sql += String.format("FROM Article "); //
		 * sql += String.format("WHERE displayStatus = 1 "); if (cateItemId != 0) { sql
		 * += String.format("WHERE cateItemId = %d ", cateItemId); } sql +=
		 * String.format("ORDER BY id DESC "); sql += String.format("LIMIT %d, %d ",
		 * limitFrom, itemsInAPage);
		 * 
		 */
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public List<Article> getAllArticles(int cateItemId) {

		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM Article ");
		secSql.append("WHERE cateItemId = ? ", cateItemId);

		/*
		 * String sql = ""; sql += String.format("SELECT * "); sql +=
		 * String.format("FROM Article "); sql +=
		 * String.format("WHERE cateItemId = %d ", cateItemId);
		 */
		List<Article> articles = new ArrayList<>();

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public String getCategoryName(int cateItemId) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM cateItem ");
		secSql.append("WHERE id = ? ", cateItemId);
		/*
		 * String sql = ""; sql += String.format("SELECT * "); sql +=
		 * String.format("FROM cateItem "); sql += String.format("WHERE id = %d ",
		 * cateItemId);
		 */
		Category cateItem = null;

		Map<String, Object> row = DBUtil.selectRow(dbConn, secSql);
		cateItem = new Category(row);

		return cateItem.getName();
	}

	public Article getArticle(int id) {

		SecSql secSql = new SecSql();

		secSql.append("SELECT A.*, M.nickname AS extra__writerName");
		secSql.append("FROM Article AS A");
		secSql.append("LEFT JOIN `Member` AS M ");
		secSql.append("ON A.memberId = M.id ");
		secSql.append("WHERE A.id = ?", id);

		/*
		 * String sql = ""; sql += String.format("SELECT * "); sql +=
		 * String.format("FROM Article "); sql += String.format("WHERE id = %d", id);
		 */
		Article article = null;

		Map<String, Object> row = DBUtil.selectRow(dbConn, secSql);

		article = new Article(row);

		return article;
	}

	public List<Category> getCateItems() {
		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM cateItem ");
		secSql.append("ORDER BY id ASC ");

		/*
		 * String sql = ""; sql += String.format("SELECT * "); sql +=
		 * String.format("FROM cateItem "); sql += String.format("ORDER BY id ASC ");
		 */
		List<Category> cateItems = new ArrayList<>();

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		for (Map<String, Object> row : rows) {
			cateItems.add(new Category(row));
		}

		return cateItems;
	}

	public List<Article> getAllArticles() {
		SecSql secSql = new SecSql();

		secSql.append("SELECT A.*, M.nickname AS extra__writerName");
		secSql.append("FROM Article AS A");
		secSql.append("LEFT JOIN `Member` AS M ");
		secSql.append("ON A.memberId = M.id ");
		secSql.append("ORDER BY id DESC ");

		/*
		 * String sql = ""; sql += String.format("SELECT * "); sql +=
		 * String.format("FROM Article "); sql += String.format("ORDER BY id DESC ");
		 */
		List<Article> articles = new ArrayList<>();

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public List<Article> getArticles(int page, String searchKeyword) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM Article ");
		secSql.append("WHERE title LIKE CONCAT('%', ?, '%')", searchKeyword);
		secSql.append("ORDER BY id DESC LIMIT ?, 5", (page - 1) * 5);

		/*
		 * String sql = ""; sql += String.format("SELECT * "); sql +=
		 * String.format("FROM Article "); sql += "WHERE title LIKE '%" + searchKeyword
		 * + "%' "; sql += String.format("ORDER BY id DESC LIMIT %d, 5", (page - 1) *
		 * 5);
		 */
		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public List<Article> getArticles(int page) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM Article ");
		secSql.append("ORDER BY id DESC LIMIT ?, 5", (page - 1) * 5);
		/*
		 * String sql = ""; sql += String.format("SELECT * "); sql +=
		 * String.format("FROM Article "); sql +=
		 * String.format("ORDER BY id DESC LIMIT %d, 5", (page - 1) * 5);
		 */
		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public List<Article> getAllArticles(String searchKeyword) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT A.*, M.nickname AS extra__writerName");
		secSql.append("FROM Article AS A");
		secSql.append("LEFT JOIN `Member` AS M ");
		secSql.append("ON A.memberId = M.id ");
		secSql.append("WHERE title LIKE CONCAT('%', ?, '%')", searchKeyword);

		List<Article> articles = new ArrayList<>();
		/*
		 * String sql = ""; sql += String.format("SELECT * "); sql +=
		 * String.format("FROM Article "); sql += "WHERE title LIKE '%" + searchKeyword
		 * + "%' "; List<Article> articles = new ArrayList<>();
		 */

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public int getOverCountOnList(int cateItemId, int id) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT COUNT(*) AS cnt ");
		secSql.append("FROM Article ");
		secSql.append("WHERE displayStatus = 1 ");
		if (cateItemId != 0) {
			secSql.append("AND cateItemId = ? ", cateItemId);
		}
		secSql.append("AND id > ? ", id);

		return DBUtil.selectRowIntValue(dbConn, secSql);
		/*
		 * String sql = ""; sql += String.format("SELECT COUNT(*) AS cnt "); sql +=
		 * String.format("FROM Article "); sql +=
		 * String.format("WHERE displayStatus = 1 "); if (cateItemId != 0) { sql +=
		 * String.format("AND cateItemId = %d ", cateItemId); } sql +=
		 * String.format("AND id > %d ", id);
		 * 
		 * return DBUtil.selectRowIntValue(dbConn, sql);
		 */
	}

	public int getOverCountOnList(int id) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT COUNT(*) AS cnt ");
		secSql.append("FROM Article ");
		secSql.append("WHERE displayStatus = 1 ");
		secSql.append("AND id > ? ", id);

		return DBUtil.selectRowIntValue(dbConn, secSql);
		/*
		 * String sql = ""; sql += String.format("SELECT COUNT(*) AS cnt "); sql +=
		 * String.format("FROM Article "); sql +=
		 * String.format("WHERE displayStatus = 1 "); sql +=
		 * String.format("AND id > %d ", id);
		 * 
		 * return DBUtil.selectRowIntValue(dbConn, sql);
		 */
	}

	public int write(int cateItemId, String title, String body, int memberId) {
		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO Article ");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", title = ? ", title);
		secSql.append(", body = ? ", body);
		secSql.append(", displayStatus = '1'");
		secSql.append(", hit = '0'");
		secSql.append(", memberId = ? ", memberId);
		secSql.append(", cateItemId = ?", cateItemId);

		return DBUtil.insert(dbConn, secSql);

		/*
		 * String sql = "";
		 * 
		 * sql += String.format("INSERT INTO Article "); sql +=
		 * String.format("SET regDate = NOW() "); sql +=
		 * String.format(", updateDate = NOW() "); sql +=
		 * String.format(", title = '%s' ", title); sql +=
		 * String.format(", body = '%s' ", body); sql +=
		 * String.format(", displayStatus = '1' "); sql +=
		 * String.format(", cateItemId = '%d' ", cateItemId);
		 * 
		 * return DBUtil.insert(dbConn, sql);
		 */
	}

	public void delete(int id) {
		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM Article ");
		secSql.append("WHERE id = ?", id);

		DBUtil.delete(dbConn, secSql);
	}

	public void modify(int id, int cateItemId, String title, String body) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE Article ");
		secSql.append("SET title = ? ", title);
		secSql.append(", body = ? ", body);
		secSql.append(", cateItemId = ? ", cateItemId);
		secSql.append("WHERE id = ?", id);

		DBUtil.update(dbConn, secSql);
	}

	public int increaseHit(int id) {
		SecSql sql = SecSql.from("UPDATE Article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, sql);
	}

	public void replyWrite(int articleId, String body, int memberId) {
		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO ArticleReply ");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", `body` = ? ", body);
		secSql.append(", articleId = ?", articleId);
		secSql.append(", memberId = ?", memberId);

		DBUtil.insert(dbConn, secSql);
	}

	public List<ArticleReply> getArticleReplies(int id) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT A.*, M.nickname AS extra__writerName");
		secSql.append("FROM ArticleReply AS A");
		secSql.append("LEFT JOIN `Member` AS M ");
		secSql.append("ON A.memberId = M.id ");
		secSql.append("WHERE articleId = ? ", id);
		secSql.append("ORDER BY id DESC ");

		List<ArticleReply> articleReplies = new ArrayList<>();
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		for (Map<String, Object> row : rows) {
			articleReplies.add(new ArticleReply(row));
		}

		return articleReplies;
	}

	public void replyDelete(int id) {
		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM ArticleReply ");
		secSql.append("WHERE id = ?", id);

		DBUtil.delete(dbConn, secSql);		
	}

	public ArticleReply getArticleReply(int id) {
		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM ArticleReply ");
		secSql.append("WHERE id = ?", id);
		
		ArticleReply articleReply = null;	
		
		Map<String, Object> row = DBUtil.selectRow(dbConn, secSql);
		
		articleReply = new ArticleReply(row);
		
		return articleReply;
	}

	public void modify(int id, String body) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE ArticleReply ");
		secSql.append("SET body = ? ", body);
		secSql.append("WHERE id = ?", id);

		DBUtil.update(dbConn, secSql);
	}

	public int getForPrintListArticlesCount(int cateItemId) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) AS cnt ");
		sql.append("FROM Article ");
		sql.append("WHERE displayStatus = 1 ");

		if (cateItemId != 0) {
			sql.append("AND cateItemId = ? ", cateItemId);
		}

		int count = DBUtil.selectRowIntValue(dbConn, sql);
		return count;
	}

	public int getForPrintListArticlesCount() {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) AS cnt ");
		sql.append("FROM Article ");
		sql.append("WHERE displayStatus = 1 ");

		int count = DBUtil.selectRowIntValue(dbConn, sql);
		return count;
	}

	public int getLastArticleId() {
		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM Article ");
		secSql.append("ORDER BY id DESC LIMIT 1");
				
		Map<String, Object> row = DBUtil.selectRow(dbConn, secSql);
		
		Article article = new Article(row);
		
		int id = article.getId();
		
		return id;
	}
}
