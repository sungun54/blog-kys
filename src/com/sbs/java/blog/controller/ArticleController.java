package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.util.Util;

public class ArticleController extends Controller {
	public ArticleController(Connection dbConn,	String actionMethodName, HttpServletRequest req, HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	public void beforeAction() {
		super.beforeAction();
	}
	
	public String doAction() {
		
		switch (actionMethodName) {
		case "list":
			return actionList();
		case "doWrite":
			return actionDoWrite();
		case "detail":
			return actionDetail();
		case "write":
			return actionWrite();
		case "modify":
			return actionModify();
		case "doModify":
			return actionDoModify();
		case "replyModify":
			return actionReplyModify();
		case "replyDoModify":
			return actionDoReplyModify();
		case "doDelete":
			return actionDoDelete();
		case "doReplyWrite":
			return actionDoReplyWrite();
		case "doDeleteReply":
			return actionDoDeleteReply();
		}

		return "";
	}
	
	@Override
	public String getControllerName() {
		return "article";
	}
	
	private String actionDoReplyModify() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}
		int id = Util.getInt(req, "id");
		String body = req.getParameter("body");
		String redirectUrl = req.getParameter("redirectUrl");
//		String cateItemId = req.getParameter("cateItemId");
//		String page = req.getParameter("page");
//		redirectUrl+= "&cateItemId=" + cateItemId + "&page=" + page; 
		body = body.replaceAll("<!--", "");
		body = body.replaceAll("--!>", "");
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		
		Map<String, Object> getReplyCheckRsModifyAvailableRs = articleService.getReplyCheckRsModifyAvailable(id,
				loginedMemberId);

		if (Util.isSuccess(getReplyCheckRsModifyAvailableRs) == false) {
			return "html:<script> alert('" + getReplyCheckRsModifyAvailableRs.get("msg")
					+ "'); history.back(); </script>";
		}		
		
		articleService.modifyReply(id, body);
		
		return "html:<script> alert('댓글이 수정되었습니다.'); location.replace('"+ redirectUrl+"'); </script>";
	}

	private String actionReplyModify() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");
		
		ArticleReply articleReply = articleService.getArticleReply(id);
		req.setAttribute("articleReply", articleReply);
		
		return "article/replyModify.jsp";
	}

	private String actionDoDeleteReply() {
		String redirectUrl = req.getParameter("redirectUrl");

		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}
		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}
		int id = Util.getInt(req, "id");
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Map<String, Object> getReplyCheckRsDeleteAvailableRs = articleService.getReplyCheckRsDeleteAvailable(id,
				loginedMemberId);
		if (Util.isSuccess(getReplyCheckRsDeleteAvailableRs) == false) {
			return "html:<script> alert('" + getReplyCheckRsDeleteAvailableRs.get("msg")
					+ "'); history.back(); </script>";
		}
		
		articleService.replyDelete(id);
		
		return "html:<script> alert('댓글이 삭제되었습니다.'); location.replace('"+ redirectUrl+"'); </script>";
	}

	private String actionDoReplyWrite() {
//		if(session.getAttribute("loginedMemberId")== null) {
//			return "html:<script> alert('로그인 후 이용해주세요.'); location.replace('../home/main'); </script>";
//		}
		int articleId = 0;
		if(req.getParameter("id") != null) {
			articleId = Integer.parseInt(req.getParameter("id"));
		}		
		String body = req.getParameter("body");
		int memberId = Integer.parseInt(req.getParameter("memberId"));
		String redirectUrl = req.getParameter("redirectUrl");
		
		body = body.replaceAll("<!--", "");
		body = body.replaceAll("--!>", "");
		
		articleService.replyWrite(articleId, body, memberId);
		
		if(redirectUrl == "") {
			return "html:<script> alert('댓글이 작성되었습니다.'); location.replace('../home/main'); </script>";
		}
		
		return "html:<script> alert('댓글이 작성되었습니다. '); location.replace('"+ redirectUrl+"'); </script>";
		
	}

	private String actionDoModify() {
		int id = Integer.parseInt(req.getParameter("id"));
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		int cateItemId = Util.getInt(req, "cateItemId");
		String redirectUrl = req.getParameter("redirectUrl");
//		String cateItemId1 = req.getParameter("cateItemId1");
//		String page = req.getParameter("page");
//		
//		redirectUrl+= "&cateItemId=" + cateItemId1 + "&page=" + page; 
		
		articleService.modify(id, cateItemId, title, body);
		
		if(redirectUrl == "") {
			return "html:<script> alert('댓글이 작성되었습니다.'); location.replace('../home/main'); </script>";
		}
		
		return "html:<script> alert('" + id + "번 게시물이 수정되었습니다.'); location.replace('"+ redirectUrl+"'); </script>";
	}

	private String actionModify() {
		int id = Integer.parseInt(req.getParameter("id"));
		
		Article article = articleService.getArticle(id);
		req.setAttribute("article", article);
		return "article/modify.jsp";
	}

	private String actionDoDelete() {
		int id = Integer.parseInt(req.getParameter("id"));
		
		articleService.delete(id);
		
		return "html:<script> alert('게시물이 삭제되었습니다.'); location.replace('../home/board'); </script>";
	}

	private String actionWrite() {		
		return "article/write.jsp";
	}
	
	private String actionDoWrite() {
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		int memberId = Integer.parseInt(req.getParameter("memberId"));
		int cateItemId = Util.getInt(req, "cateItemId");
		
		
		/*
		 * title = title.replaceAll("(?i)<script", "lt;script"); body =
		 * body.replaceAll("(?i)<script", "lt;script");
		 */
		
		int id = articleService.write(cateItemId, title, body, memberId);
		
		String Url = "list?id="+ id +"&cateItemId="+ cateItemId +"&page=1";
		
		return "html:<script> alert('" + id + "번 게시물이 생성되었습니다.'); location.replace('"+Url+"'); </script>";
	}

	private String actionDetail() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}
		
		int id = 1;
		if(req.getParameter("id") != null) {
			id = Integer.parseInt(req.getParameter("id"));
		}
		int cateItemId = 0;
		if(req.getParameter("cateItemId")!=null){
			cateItemId = Integer.parseInt(req.getParameter("cateItemId"));
		}
		int page = 1;
		if(req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}	
		
		List<Article> articles = articleService.getForPrintListArticles(page, cateItemId);
		req.setAttribute("articles", articles);
		List<Article> allArticles = articleService.getAllArticles(cateItemId);		
		req.setAttribute("allArticles", allArticles);	
		String cateName = articleService.getCategoryName(cateItemId);
		req.setAttribute("cateName", cateName);		
		Article article = articleService.getArticle(id);		
		req.setAttribute("article", article);
		req.setAttribute("page", page);

		return "article/detail.jsp";
	}

	private String actionList() {
//		long startTime = System.nanoTime();		
		
		int id = 0;
		if(req.getParameter("id") != null) {
			id = Integer.parseInt(req.getParameter("id"));
		}		

		int cateItemId = 0;
		if (req.getParameter("cateItemId") != null) {
			cateItemId = Integer.parseInt(req.getParameter("cateItemId"));
		}
		int page = 1;		
		
		if(req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}

		int itemsInAPage = 5;
		
		if(id != 0) {
			Article article = articleService.getArticle(id);		
			req.setAttribute("article", article);
			page = articleService.getPageWhereArticleInclude(itemsInAPage, cateItemId, id);
		}		
		
		int totalCount = articleService.getForPrintListArticlesCount(cateItemId);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);

		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		articleService.increaseHit(id);
		List<Article> allArticles = articleService.getAllArticles(cateItemId);		
		req.setAttribute("allArticles", allArticles);			
		List<Article> articles = articleService.getForPrintListArticles(page, cateItemId);
		req.setAttribute("articles", articles);
		List<ArticleReply> articleReplies = articleService.getArticleReplies(id);
		req.setAttribute("articleReplies", articleReplies);		
		String cateName = articleService.getCategoryName(cateItemId);
		req.setAttribute("cateName", cateName);
		req.setAttribute("cPage", page);
		req.setAttribute("cateItemId", cateItemId);
		
//		long endTime = System.nanoTime();
//		long estimatedTime = endTime - startTime;
//		double seconds = estimatedTime / 1000000000.0;
//		System.out.println("seconds : " + seconds);
		
		return "article/list.jsp";
	}
}