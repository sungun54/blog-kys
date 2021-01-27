<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
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
	<form action="doJoin" method="POST" class="join-form form1"
		onsubmit="submitJoinForm(this); return false;">
		<input type="hidden" name="loginPwReal">
		<div class="form-row">
			<div class="label">아이디</div>
			<div class="input">
				<input onkeyup="JoinForm__checkLoginIdValid__debounce(this);" name="loginId"
					type="text" placeholder="아이디를 입력해주세요." autocomplete="off" />
				<div class="message-msg"></div>
			</div>
		</div>

		<div class="form-row">
			<div class="label">비밀번호</div>
			<div class="input">
				<input type="password" name="loginPw" placeholder="비밀번호를 입력해주세요."></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">비밀번호확인</div>
			<div class="input">
				<input type="password" name="loginPwConfirm"
					placeholder="비밀번호확인을 입력해주세요."></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input name="name" placeholder="이름을 입력해주세요." autocomplete="off"></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">닉네임</div>
			<div class="input">
				<input onkeyup="JoinForm__checkNicknameValid__debounce(this);" name="nickname"
					type="text" placeholder="닉네임을 입력해주세요." autocomplete="off" />
				<div class="message-msg"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input onkeyup="JoinForm__checkEmailValid__debounce(this);" name="email"
					type="text" placeholder="이메일을 입력해주세요." autocomplete="off" />
				<div class="message-msg"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="회원가입" /> <a href="list"></a>
			</div>
		</div>
	</form>
</div>
<script>
	var JoinForm__checkLoginIdValid__debounce = _.debounce(JoinForm__checkLoginIdDup, 1000);
	var JoinForm__checkEmailValid__debounce = _.debounce(JoinForm__checkEmailDup, 1000);
	var JoinForm__checkNicknameValid__debounce = _.debounce(JoinForm__checkNicknameDup, 1000);
</script>
<%@ include file="/jsp/part/foot.jspf"%>