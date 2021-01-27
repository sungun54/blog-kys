<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
%>
<%
	List<Article> allArticles = (List<Article>) request.getAttribute("allArticles");
%>
<%
	int page1 = (int) request.getAttribute("page");
%>

<nav class="all-article-list visible-on-md-up">
	<h2>전체 글 (${totalCount})</h2>
	<div class="list">
		<ul>
			<c:forEach items="${articles}" var="article">
				<a
					href="${pageContext.request.contextPath}/s/article/list?id=${article.id}&cateItemId=${article.cateItemId}&page=1"><li>
						<span class="title">${article.title}</span> <span class="regDate">${article.regDate}</span>
				</li></a>
			</c:forEach>
		</ul>
		<div class="page">
			<c:forEach var="i" begin="1" end="${totalPage}" step="1">
				<c:if test="${i == cPage}">
					<a
						href="${pageContext.request.contextPath}/s/home/main?page=${i}<c:if test="${searchKeyword != '' }">&searchKeyword=${param.searchKeyword}</c:if>"><span
						style="font-weight: bold">[ ${i} ] </span></a>
				</c:if>
				<c:if test="${i != cPage}">
					<a
						href="${pageContext.request.contextPath}/s/home/main?page=${i}<c:if test="${searchKeyword != '' }">&searchKeyword=${param.searchKeyword}</c:if>"><span>[
							${i} ] </span></a>
				</c:if>
			</c:forEach>
		</div>
	</div>
</nav>

<nav class="all-article-list-m visible-on-sm-down">
	<h2>전체 글 (${totalCount})</h2>
	<div class="list">
		<ul>
			<c:forEach items="${articles}" var="article">
				<a
					href="${pageContext.request.contextPath}/s/article/list?id=${article.id}&cateItemId=${article.cateItemId}&page=1"><li>
						<span class="title">${article.title}</span> <span class="regDate">${article.regDate}</span>
				</li></a>
			</c:forEach>
		</ul>
		<div class="page">
			<c:forEach var="i" begin="1" end="${totalPage}" step="1">
				<c:if test="${i == cPage}">
					<a
						href="${pageContext.request.contextPath}/s/home/main?page=${i}<c:if test="${searchKeyword != '' }">&searchKeyword=${param.searchKeyword}</c:if>"><span
						style="font-weight: bold">[ ${i} ] </span></a>
				</c:if>
				<c:if test="${i != cPage}">
					<a
						href="${pageContext.request.contextPath}/s/home/main?page=${i}<c:if test="${searchKeyword != '' }">&searchKeyword=${param.searchKeyword}</c:if>"><span>[
							${i} ] </span></a>
				</c:if>
			</c:forEach>
		</div>
	</div>
</nav>
<%@ include file="/jsp/part/foot.jspf"%>