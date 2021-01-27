<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<style>
/* lib */
.form1 {
	display: block;
	width: 100%;
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

.form1 .form-row>.input>input, .form1 .form-row>.input>textarea {
	display: block;
	width: 100%;
	box-sizing: border-box;
	padding: 10px;
}

.form1 .form-row>.input>select {
	padding: 10px;
}

.form1 .form-row>.input>textarea {
	height: 100px;
}

@media ( max-width : 700px ) {
	.form1 .form-row {
		display: block;
	}
}
/* cus */
.mail-form-box {
	margin-top: 30px;
}
</style>

<div class="mail-form-box con">
	<form action="doSend" method="POST" class="write-form form1"
		onsubmit="submitMailWriteForm(this); return false;">
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input name="title" type="text" placeholder="제목을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
			<textarea name="body"></textarea>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일 주소</div>
			<div class="input">
			<input name="email" type="text" placeholder="이메일을 입력해주세요." autocomplete="off" />
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
<%@ include file="/jsp/part/foot.jspf"%>