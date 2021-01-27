<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="login-form-box con">
		<form action="doFindLoginId" method="POST" class="login-form form1"
			onsubmit="submitFindLoginIdForm(this); return false;">
			<div class="form-row">
				<div class="label">이름</div>
				<div class="input">
					<input name="name" type="text" placeholder="이름을 입력해주세요."  autocomplete="off"/>
				</div>
			</div>
			<div class="form-row">
				<div class="label">이메일</div>
				<div class="input">
					<input type="email" name="email" placeholder="이메일을 입력해주세요." autocomplete="off"></input>
				</div>
			</div>
			<div class="form-row">
				<div class="label">전송</div>
				<div class="input">
					<input type="submit" value="전송" />
				</div>
			</div>
		</form>
	</div>
</body>
</html>