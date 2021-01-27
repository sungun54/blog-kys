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
	padding : 20px;
}
.form1 .form-row {
	align-items: center;
	display: flex;
}

.form1 .form-row:not(:first-child){
margin-top : 10px;
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
	pading : 20px 0;
}

.form1 .form-row>.input>select {
	padding: 10px;
}

@media ( max-width : 700px ) {
	.form1 .form-row {
		display: block;
	}
}
/* cus */
.join-form-box {
	background-color : white;
	margin-top: 30px;
}
</style>

<div class="login-form-box con">
	<form action="doLogin" method="POST" class="login-form form1" onsubmit="submitLoginForm(this); return false;">
		<input type="hidden" name="redirectUrl" value="${param.redirectUrl}">
		<input type="hidden" name=cateItemId value="${param.cateItemId}">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-row">
			<div class="label">아이디</div>
			<div class="input">
				<input name="loginId" type="text" placeholder="아이디를 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">비밀번호</div>
			<div class="input">
				<input type="password" name="loginPw" placeholder="비밀번호를 입력해주세요."></input>
			</div>
		</div>
		<div class="form-row">
			<div class="label">전송</div>
			<div class="input">
				<input type="submit" value="로그인" />
			</div>
		</div>
	</form>
	<div><span><a href="javascript:popupOpen();" >아이디 찾기</a></span><span><a href="javascript:popupOpen2();" >비밀번호 찾기</a></span></div>
</div>

<script type="text/javascript">

function popupOpen(){
	var popUrl = "${pageContext.request.contextPath}/s/member/findLoginId";	//팝업창에 출력될 페이지 URL
	var popOption = "width=500, height=500, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)
		window.open(popUrl,"",popOption);
	}
function popupOpen2(){
	var popUrl = "${pageContext.request.contextPath}/s/member/findLoginPw";	//팝업창에 출력될 페이지 URL
	var popOption = "width=500, height=500, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)
		window.open(popUrl,"",popOption);
	}
</script>


<%@ include file="/jsp/part/foot.jspf"%>