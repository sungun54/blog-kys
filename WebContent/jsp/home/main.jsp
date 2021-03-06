<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="com.sbs.java.blog.dto.ArticleReply"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<!-- 하이라이트 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/highlight.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/styles/default.min.css">

<!-- 하이라이트 라이브러리, 언어 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/css.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/javascript.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/xml.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php-template.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/sql.min.js"></script>

<!-- 코드 미러 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.min.css" />

<!-- 토스트 UI 에디터, 자바스크립트 코어 -->
<script
	src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script
	src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>
<!-- 토스트 UI 에디터, 신택스 하이라이트 플러그인 추가 -->
<script
	src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>

<!-- 토스트 UI 에디터, CSS 코어 -->
<link rel="stylesheet"
	href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	List<Article> allArticles = (List<Article>) request.getAttribute("allArticles");
	int page1 = (int) request.getAttribute("cPage");
	String searchKeyword = (String) request.getAttribute("searchKeyword");
	List<ArticleReply> articleReplies = (List<ArticleReply>) request.getAttribute("articleReplies");
%>

<c:if test="${allArticles.get(0).title == null}">
	<script>alert('검색결과가 없습니다.'); history.back();</script>
</c:if>

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
<script>
	function Delete(idx) {
		var a = confirm("삭제하시겠습니까?");
		
		if (a == true) {
			location.href="${pageContext.request.contextPath}/s/article/doDelete?id=" + idx;
		} else if(a == false){
			return;
		}
	}

</script>



<c:if test="${totalCount != 0}">
	<nav class="showDetail visible-on-md-up">
		<h1>게시물 상세페이지</h1>
		<div class="detail">
			<div class="detail-title">제목 : ${allArticles.get(0).title}</div>
			<div class="detail-hit">
				<h5>조회 : ${allArticles.get(0).hit}</h5>
			</div>
			<div class="detail-regDateAndName">
				<span>작성자 : ${allArticles.get(0).extra.get("writerName")}</span><span>작성
					날짜 : ${allArticles.get(0).regDate}</span>
			</div>
			<script type="text/x-template" id="origin1" style="display: none;">${allArticles.get(0).bodyForXTemplate}</script>
			<div id="viewer1">
				<script>
				console.clear();
				var editor1__initialValue = getBodyFromXTemplate('#origin1');
				var editor1 = new toastui.Editor({
					el : document.querySelector('#viewer1'),
					height : '600px',
					initialValue : editor1__initialValue,
					viewer : true,
					plugins : [ toastui.Editor.plugin.codeSyntaxHighlight,
							youtubePlugin, replPlugin, codepenPlugin ]
				});
			</script>
			</div>
			<div class="detail-tag">
				<span>#JDBC</span> <span>#JDBC Driver</span>
			</div>
			<!-- div class="detail-nextAndPrevious"><a
						href="#" class="previous"><span>이전글</span></a> <a href="#"
						class="next"><span>다음글</span></a>
		</div>
		-->
			<c:if test="${loginedMemberId == allArticles.get(0).memberId}">
				<div class="delete-modfiy-box">
					<span><a
						href="${pageContext.request.contextPath}/s/article/modify?id=${allArticles.get(0).id}">수정</a></span>
					<button class="delete-button"
						onclick="javascript:Delete(${allArticles.get(0).id})">삭제</button>
				</div>
			</c:if>
			<c:if test="${isLogined}">
				<div class="replyWrite-form-box con">
					<form action="../article/doReplyWrite" method="POST"
						class="replyWrite-form form1"
						onsubmit="submitReplyWriteForm(this); return false;">
						<div class="form-row">
							<div class="label">댓글 쓰기</div>
							<div class="input">
								<textarea name="body"></textarea>
							</div>
						</div>
						<input type="hidden" name="redirectUrl"
							value="${param.redirectUrl}"> <input type="hidden"
							name="memberId" value="${loginedMemberId}"> <input
							type="hidden" name=id value="${allArticles.get(0).id}">
						<div class="form-row">
							<div class="label">전송</div>
							<div class="input">
								<input type="submit" value="전송" />
							</div>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test="${isLogined} == false">
				<div class="replyWrite-form-box con">
					<form action="../article/doReplyWrite" method="POST"
						class="replyWrite-form form1"
						onsubmit="submitReplyWriteForm(this); return false;">
						<div class="form-row">
							<div class="label">댓글 쓰기</div>
							<a href="../member/login?redirectUrl=${urlEncodedCurrentUrl}"
								onclick="if(confirm('로그인 후 댓글 작성이 가능합니다. 로그인하시겠습니까?')==false){return false}">
								<div class="input">
									<textarea name="body" placeholder="댓글은 로그인 후 작성하실 수 있습니다."></textarea>
								</div>
							</a>
						</div>
					</form>
				</div>
			</c:if>
			<div>
				<h2>댓글 리스트</h2>
				<c:forEach items="${articleReplies}" var="articleReply">
					<div class="reply-box">
						<span class="reply-writer">${articleReply.extra.get("writerName")}</span>
						<span class="reply-body">${articleReply.body}</span> <span
							class="reply-regDate">${articleReply.regDate}</span>
						<div class="reply-edit-box">
							<c:if test="${loginedMemberId == articleReply.memberId || loginedMember.level == 10}">
								<span class="reply-modify"><a
									href="${pageContext.request.contextPath}/s/article/replyModify?id=${articleReply.id}&redirectUrl=${urlEncodedCurrentUrl}">수정</a></span>
								<a
									href="${pageContext.request.contextPath}/s/article/doDeleteReply?id=${articleReply.id}&redirectUrl=${urlEncodedCurrentUrl}"
									class="reply-delete delete-button"
									onclick="if(confirm('삭제하시겠습니까?')==false){return false}">삭제</a>
							</c:if>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</nav>
</c:if>

<nav class="all-article-list-m visible-on-sm-down">
	<h2>전체 글 (${totalPage})</h2>
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