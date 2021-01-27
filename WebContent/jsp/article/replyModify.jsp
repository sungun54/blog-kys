<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<%@ page import="com.sbs.java.blog.dto.ArticleReply"%>
<%
ArticleReply articleReply = (ArticleReply) request.getAttribute("articleReply");
%>


<div class="replyWrite-form-box con">
	<form action="replyDoModify" method="POST" class="replyWrite-form form1"
		onsubmit="submitReplyWriteForm(this); return false;">
		<input type="hidden" name="redirectUrl" value="${param.redirectUrl}">
		<div class="form-row">
			<div class="label">댓글 쓰기</div>
			<div class="input">
				<textarea name="body">${articleReply.body}</textarea>
			</div>
		</div>
		<input type="hidden" name="id" value="${param.id}">
		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="전송" />
			</div>
		</div>
	</form>
</div>
<%@ include file="/jsp/part/foot.jspf"%>