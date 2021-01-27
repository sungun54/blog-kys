<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/jsp/part/head.jspf"%>
<h2>비밀번호 수정</h2>
<style>
/* lib */
.form1 {
	display: block;
	width: 50%;
	background-color: white;
	margin-top: 30px;
	padding: 20px;
}

.form1 .form-row {
	align-items: center;
	display: flex;
	margin-bottom: 10px;
}

.form1
 
.form-row
:not
 
(
:first-child
 
){
margin-top
:
 
10
px
;


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
	<form action="doPwConfirm" method="POST" class="join-form form1"
		onsubmit="submitPwConfirmForm(this); return false;">
		<input type="hidden" name="id" value="${loginedMember.id}">
		<div class="form-row">
			<div class="label">비밀번호</div>
			<div class="input">
				<input type="password" name="loginPw" placeholder="기존 비밀번호를 입력해주세요."></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="전송" /> <a href="list"></a>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>