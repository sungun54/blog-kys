package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;

public class HomeController extends Controller {

	public HomeController(Connection dbConn, String actionMethodName, HttpServletRequest req, HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}
	
	@Override
	public String getControllerName() {
		return "home";
	}
	
	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "main":
			return actionMain();
		case "board":
			return actionBoard();
		case "aboutMe":
			return actionAboutMe();
		}

		return "";
	}

	private String actionBoard() {
		int page = 1;

		if (req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		
		int id = 0;
		if(req.getParameter("id") != null) {                                                                                                                                                                                                                                                                                   
			 
			id = Integer.parseInt(req.getParameter("id"));
		}		
		int itemsInAPage = 5;
		
		if(id != 0) {
			Article article = articleService.getArticle(id);		
			req.setAttribute("article", article);
			page = articleService.getPageWhereArticleInclude(itemsInAPage, id);
		}
		int totalCount = articleService.getForPrintListArticlesCount();
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		
		List<Article> articles = articleService.getArticles(page);
		List<Article> allArticles = articleService.getAllArticles();
		req.setAttribute("allArticles", allArticles);
		req.setAttribute("articles", articles);
		req.setAttribute("page", page);

		return "home/board.jsp";
	}

	private String actionAboutMe() {
		return "home/aboutMe.jsp";
	}

	private String actionMain() {
		int page = 1;

		if (req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
		}

		String searchKeyword = "";
		if (req.getParameter("searchKeyword") != null) {
			searchKeyword = req.getParameter("searchKeyword");
		}
		int id = 0;
		if(req.getParameter("id") != null) {
			id = Integer.parseInt(req.getParameter("id"));
		}		
		
		int itemsInAPage = 5;
		
		if(id != 0) {
			Article article = articleService.getArticle(id);		
			req.setAttribute("article", article);
			page = articleService.getPageWhereArticleInclude(itemsInAPage, id);
		}
		List<Article> articles = null;
		List<Article> allArticles = null;

		if (searchKeyword.equals("")) {
			articles = articleService.getArticles(page);
			allArticles = articleService.getAllArticles();
		} else {
			articles = articleService.getArticles(page, searchKeyword);
			allArticles = articleService.getAllArticles(searchKeyword);
		}
		
		int totalCount = articleService.getForPrintListArticlesCount();
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		
		if(id == 0) {
			id = articleService.getLastArticleId();
		}
		
		List<ArticleReply> articleReplies = articleService.getArticleReplies(id);
		req.setAttribute("articleReplies", articleReplies);	
		req.setAttribute("searchKeyword", searchKeyword);
		req.setAttribute("allArticles", allArticles);
		req.setAttribute("articles", articles);
		req.setAttribute("cPage", page);
		return "home/main.jsp";
	}
}