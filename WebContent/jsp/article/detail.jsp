<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article article = (Article) request.getAttribute("article");
%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
%>
<%
	String cateName = (String) request.getAttribute("cateName");
%>
<%
	List<Article> allArticles = (List<Article>) request.getAttribute("allArticles");
%>
<%
	int page1 = (int) request.getAttribute("page");
%>
<nav class="showDetail visible-on-md-up">
	<h1>게시물 상세페이지</h1>
	<div class="detail">
		<div class="detail-title">
			제목 :
			<%=article.getTitle()%></div>
		<div class="detail-regDateAndName">
			<span>작성자 : 김용순</span><span>작성 날짜 : <%=article.getRegDate()%></span>
		</div>
		<div class="detail" id="origin1" style="display: none"><%=article.getBody()%></div>
		<div id="viewer1">
			<script>
				console.clear();
				var editor1__initialValue = $('#origin1').html();
				var editor1 = new toastui.Editor({
					el : document.querySelector('#viewer1'),
					height : '600px',
					initialValue : editor1__initialValue,
					viewer : true,
					plugins : [ toastui.Editor.plugin.codeSyntaxHighlight ]
				});
			</script>
		</div>
		<div class="detail-tag">
			<span>#JDBC</span> <span>#JDBC Driver</span>
		</div>
		<!-- div class="detail-nextAndPrevious"><a href="#" class="previous"><span>이전글</span></a> <a href="#" class="next"><span>다음글</span></a></div>-->

	</div>
</nav>
<nav class="all-article-list visible-on-md-up">
	<h2><%=cateName%> (<%=allArticles.size()%>)</h2>
	<div class="list">
		<ul>
			<%
				for (Article article1 : articles) {
			%>
			<a href="${pageContext.request.contextPath}/s/article/list?id=<%=article1.getId()%>&cateItemId=<%=article.getCateItemId()%>&page=1">
				<li>
					<span class="title"><%=article1.getTitle()%></span> <span class="regDate"><%=article1.getRegDate()%></span>
				</li>
			</a>
			<%
				}
			%>
		</ul>
		<div class="page">
			<%
				for (int i = 1; i <= Math.ceil((double) allArticles.size() / 5); i++) {
			%>
			<%
				if (i == page1) {
			%>
			<a href="${pageContext.request.contextPath}/s/article/list?id=<%=article.getId()%>&page=<%=i%>&cateItemId=<%=article.getCateItemId()%>"><span
				style="font-weight: bold">[ <%=i%> ]
			</span></a>
			<%
				} else {
			%>
			<a href="${pageContext.request.contextPath}/s/article/list?id=<%=article.getId()%>&page=<%=i%>&cateItemId=<%=article.getCateItemId()%>"><span>[
					<%=i%> ]
			</span></a>
			<%
				}
			%>
			<%
				}
			%>
		</div>
	</div>
</nav>

<nav class="showDetail-m visible-on-sm-down">
	<h1>게시물 상세페이지</h1>
	<div class="detail">
		<div class="detail-title">
			제목 :
			<%=article.getTitle()%></div>
		<div class="detail-regDateAndName">
			<span>작성자 : 김용순</span><span>작성 날짜 : <%=article.getRegDate()%></span>
		</div>
		<div class="detail" id="origin1" style="display: none"><%=article.getBody()%></div>
		<div id="viewer1">
			<script>
				console.clear();
				var editor1__initialValue = $('#origin1').html();
				var editor1 = new toastui.Editor({
					el : document.querySelector('#viewer1'),
					height : '600px',
					initialValue : editor1__initialValue,
					viewer : true,
					plugins : [ toastui.Editor.plugin.codeSyntaxHighlight ]
				});
			</script>
		</div>
		<div class="detail-tag">
			<span>#JDBC</span> <span>#JDBC Driver</span>
		</div>
		<!-- div class="detail-nextAndPrevious"><a href="#" class="previous"><span>이전글</span></a> <a href="#" class="next"><span>다음글</span></a></div>-->

	</div>
</nav>
<nav class="all-article-list-m visible-on-sm-down">
	<h2><%=cateName%> (<%=allArticles.size()%>)</h2>
	<div class="list">
		<ul>
			<%
				for (Article article1 : articles) {
			%>
			<a href="${pageContext.request.contextPath}/s/article/list?id=<%=article1.getId()%>&cateItemId=<%=article.getCateItemId()%>&page=1">
				<li>
					<span class="title"><%=article1.getTitle()%></span> <span class="regDate"><%=article1.getRegDate()%></span>
				</li>
			</a>
			<%
				}
			%>
		</ul>
		<div class="page">
			<%
				for (int i = 1; i <= Math.ceil((double) allArticles.size() / 5); i++) {
			%>
			<%
				if (i == page1) {
			%>
			<a href="${pageContext.request.contextPath}/s/article/list?id=<%=article.getId()%>&page=<%=i%>&cateItemId=<%=article.getCateItemId()%>"><span
				style="font-weight: bold">[ <%=i%> ]
			</span></a>
			<%
				} else {
			%>
			<a href="${pageContext.request.contextPath}/s/article/list?id=<%=article.getId()%>&page=<%=i%>&cateItemId=<%=article.getCateItemId()%>"><span>[
					<%=i%> ]
			</span></a>
			<%
				}
			%>
			<%
				}
			%>
		</div>
	</div>
</nav>

<%@ include file="/jsp/part/foot.jspf"%>
