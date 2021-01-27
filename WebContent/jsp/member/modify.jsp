<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>
<h2>개인정보 수정</h2>
<style>
/* lib */
.form1 {
	display: block;
	width: 50%;
	background-color: white;
	margin-top: 30px;
	padding : 20px;
}

.form1 .form-row {
	align-items: center;
	display: flex;
	margin-bottom : 10px;
}

.form1 .form-row:not (:first-child ){
	margin-top: 10px;
}

.form1 .form-row>.label {
	width: 100px;
}

.form1 .form-row>.input {
	flex-grow: 1;
}

.form1 .form-row>.input>input {
	display: block;
	width: 100%;
	box-sizing: border-box;
	pading: 20px 0;
}

.form1 .form-row>.input>select {
	padding: 10px;
}

@media ( max-width : 700px ) {
	.form1 .form-row {
		display: block;
	}
}

</style>

<div class="join-form-box con">
	<form action="doMemberModify" method="POST" class="join-form form1"
		onsubmit="submitMemberModifyForm(this); return false;">
		<input type="hidden" name="id" value="${loginedMember.id}">
		<div class="form-row">
			<div class="label">아이디</div>
			<div class="input">
				<input value="${loginedMember.loginId}" name="loginId" type="text" placeholder="아이디를 입력해주세요." disabled />
			</div>
		</div>

		<div class="form-row">
			<div class="label">비밀번호</div>
			<div class="input">
				<input type="password" name="loginPw" disabled></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input value="${loginedMember.name} " name="name" disabled ></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">닉네임</div>
			<div class="input">
				<input value="${loginedMember.nickname}" name="nickname" placeholder="닉네임을 입력해주세요."></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input value="${loginedMember.email}" type ="email" name="email" placeholder="이메일을 입력해주세요." disabled ></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="수정" /> <a href="list"></a>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>