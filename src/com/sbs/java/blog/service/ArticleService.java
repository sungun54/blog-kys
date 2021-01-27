package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dao.ArticleDao;
import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.util.Util;

public class ArticleService extends Service {

	private ArticleDao articleDao;

	public ArticleService(Connection dbConn) {
		articleDao = new ArticleDao(dbConn);
	}

	public List<Article> getForPrintListArticles(int page, int cateItemId) {
		return articleDao.getForPrintListArticles(page, cateItemId);
	}

	public List<Article> getAllArticles(int cateItemId) {
		return articleDao.getAllArticles(cateItemId);
	}

	public String getCategoryName(int cateItemId) {
		return articleDao.getCategoryName(cateItemId);
	}

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public List<Category> getCateItems() {
		return articleDao.getCateItems();
	}

	public List<Article> getAllArticles() {
		return articleDao.getAllArticles();
	}

	public List<Article> getArticles(int page, String searchKeyword) {
		return articleDao.getArticles(page, searchKeyword);
	}

	public List<Article> getArticles(int page) {
		return articleDao.getArticles(page);
	}

	public List<Article> getAllArticles(String searchKeyword) {
		return articleDao.getAllArticles(searchKeyword);
	}

	public int getPageWhereArticleInclude(int itemsInAPage, int cateItemId, int id) {
		int overCount = articleDao.getOverCountOnList(cateItemId, id);
		int rank = overCount + 1;

		return (int) Math.ceil(rank / (double) itemsInAPage);
	}

	public int getPageWhereArticleInclude(int itemsInAPage, int id) {
		int overCount = articleDao.getOverCountOnList(id);
		int rank = overCount + 1;

		return (int) Math.ceil(rank / (double) itemsInAPage);
	}

	public int write(int cateItemId, String title, String body, int memberId) {
		return articleDao.write(cateItemId, title, body, memberId);
	}

	public void delete(int id) {
		articleDao.delete(id);
	}

	public void modify(int id, int cateItemId, String title, String body) {
		articleDao.modify(id, cateItemId, title, body);
	}

	public void increaseHit(int id) {
		articleDao.increaseHit(id);
	}

	public void replyWrite(int articleId, String body, int memberId) {
		articleDao.replyWrite(articleId, body, memberId);
	}

	public List<ArticleReply> getArticleReplies(int id) {
		return articleDao.getArticleReplies(id);
	}

	public void replyDelete(int id) {
		articleDao.replyDelete(id);
	}

	public ArticleReply getArticleReply(int id) {
		return articleDao.getArticleReply(id);
	}

	public void modifyReply(int id, String body) {
		articleDao.modify(id, body);
	}

	public int getForPrintListArticlesCount(int cateItemId) {
		return articleDao.getForPrintListArticlesCount(cateItemId);
	}

	public int getForPrintListArticlesCount() {
		return articleDao.getForPrintListArticlesCount();
	}

	public int getLastArticleId() {
		return articleDao.getLastArticleId();
	}

	private Map<String, Object> getReplyCheckRsDeleteAvailable(ArticleReply articleReply, int actorId) {
		Map<String, Object> rs = new HashMap<>();
		if (articleReply == null) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "존재하지 않는 댓글 입니다.");
			return rs;
		}
		if (articleReply.getMemberId() != actorId) {
			rs.put("resultCode", "F-2");
			rs.put("msg", "권한이 없습니다.");
			return rs;
		}
		rs.put("resultCode", "S-1");
		rs.put("msg", "작업이 가능합니다.");
		return rs;
	}
	
	private void updateArticleExtraDataForPrint(Article article, int actorId) {
		boolean deleteAvailable = Util.isSuccess(getCheckRsDeleteAvailable(article, actorId));
		article.getExtra().put("deleteAvailable", deleteAvailable);
		boolean modifyAvailable = Util.isSuccess(getCheckRsModifyAvailable(article, actorId));
		article.getExtra().put("modifyAvailable", modifyAvailable);
	}
	
	private Map<String, Object> getCheckRsModifyAvailable(Article article, int actorId) {
		return getCheckRsDeleteAvailable(article, actorId);
	}
	private Map<String, Object> getCheckRsDeleteAvailable(Article article, int actorId) {
		Map<String, Object> rs = new HashMap<>();
		if (article == null) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "존재하지 않는 게시물 입니다.");
			return rs;
		}
		if (article.getMemberId() != actorId) {
			rs.put("resultCode", "F-2");
			rs.put("msg", "권한이 없습니다.");
			return rs;
		}
		rs.put("resultCode", "S-1");
		rs.put("msg", "작업이 가능합니다.");
		return rs;
	}
	
	private void updateArticleReplyExtraDataForPrint(ArticleReply articleReply, int actorId) {
		boolean deleteAvailable = Util.isSuccess(getReplyCheckRsDeleteAvailable(articleReply, actorId));
		articleReply.getExtra().put("deleteAvailable", deleteAvailable);
		boolean modifyAvailable = Util.isSuccess(getReplyCheckRsModifyAvailable(articleReply, actorId));
		articleReply.getExtra().put("modifyAvailable", modifyAvailable);
	}
	
	private Map<String, Object> getReplyCheckRsModifyAvailable(ArticleReply articleReply, int actorId) {
		return getReplyCheckRsDeleteAvailable(articleReply, actorId);
	}
	
	public Map<String, Object> getReplyCheckRsModifyAvailable(int id, int loginedMemberId) {
		ArticleReply articleReply = getArticleReply(id);
		return getReplyCheckRsModifyAvailable(articleReply, loginedMemberId);
	}

	public Map<String, Object> getReplyCheckRsDeleteAvailable(int id, int actorId) {
		ArticleReply articleReply = this.getArticleReply(id);

		return getReplyCheckRsDeleteAvailable(articleReply, actorId);
	}
}