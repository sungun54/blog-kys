<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<style>
.profile-box{
	width : 1200px;
	background-color : white;
	padding : 50px;
	margin-top : 50px;
	
}

.profile-box .logo-box {
	text-align: left;
	width: 100%;
}

.profile-box .logo {
	position : relative;
	text-align : center;
	background-color: skyblue;
	width: 200px;
	height : 200px;
	border-radius: 100%;
	font-size: 4rem;
	color: darkblue;
	display: inline-block;
	margin-bottom: 15px;
}
.profile-box .logo .fa-paper-plane{
	position : absolute;
	font-size : 7rem;
	left : 50%;	
	top : 50%;
	transform : translate(-50%, -50%);
}
</style>


<nav class="profile-box con">
	<div class="logo-box">
		<div class="logo">
			<i class="far fa-paper-plane"></i>
		</div>
	</div>
	<div class ="name">이름 : 김용순</div>
	<div class="tel">연락처 : 010-2816-4871</div>
</nav>


<%@ include file="/jsp/part/foot.jspf"%>