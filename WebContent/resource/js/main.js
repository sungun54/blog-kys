function MobileSideBar__init() {
	var $btnToggleMobileSideBar = $('.btn-toggle-mobile-side-bar');

	$btnToggleMobileSideBar.click(function() {
		if ($(this).hasClass('active')) {
			$(this).removeClass('active');
			$('.mobile-side-bar').removeClass('active');
		} else {
			$(this).addClass('active');
			$('.mobile-side-bar').addClass('active');
		}
	});
}

$(function() {
	MobileSideBar__init();
});
// 유튜브 플러그인 시작
function youtubePlugin() {
	toastui.Editor.codeBlockManager.setReplacer("youtube", function(youtubeId) {
		// Indentify multiple code blocks
		const wrapperId = "yt" + Math.random().toString(36).substr(2, 10);

		// Avoid sanitizing iframe tag
		setTimeout(renderYoutube.bind(null, wrapperId, youtubeId), 0);

		return '<div id="' + wrapperId + '"></div>';
	});
}

function renderYoutube(wrapperId, youtubeId) {
	const el = document.querySelector('#' + wrapperId);

	var urlParams = getUrlParams(youtubeId);

	var width = '100%';
	var height = '100%';

	if (urlParams.width) {
		width = urlParams.width;
	}

	if (urlParams.height) {
		height = urlParams.height;
	}

	var maxWidth = 500;

	if (urlParams['max-width']) {
		maxWidth = urlParams['max-width'];
	}

	var ratio = '16-9';

	if (urlParams['ratio']) {
		ratio = urlParams['ratio'];
	}

	var marginLeft = 'auto';

	if (urlParams['margin-left']) {
		marginLeft = urlParams['margin-left'];
	}

	var marginRight = 'auto';

	if (urlParams['margin-right']) {
		marginRight = urlParams['margin-right'];
	}

	if (youtubeId.indexOf('?') !== -1) {
		var pos = youtubeId.indexOf('?');
		youtubeId = youtubeId.substr(0, pos);
	}

	el.innerHTML = '<div style="max-width:'
			+ maxWidth
			+ 'px; margin-left:'
			+ marginLeft
			+ '; margin-right:'
			+ marginRight
			+ ';" class="ratio-'
			+ ratio
			+ ' relative"><iframe class="abs-full" width="'
			+ width
			+ '" height="'
			+ height
			+ '" src="https://www.youtube.com/embed/'
			+ youtubeId
			+ '" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>';
}
// 유튜브 플러그인 끝

// repl 플러그인 시작
function replPlugin() {
	toastui.Editor.codeBlockManager.setReplacer("repl", function(replUrl) {
		var postSharp = "";
		if (replUrl.indexOf('#') !== -1) {
			var pos = replUrl.indexOf('#');
			postSharp = replUrl.substr(pos);
			replUrl = replUrl.substr(0, pos);
		}

		if (replUrl.indexOf('?') === -1) {
			replUrl += "?dummy=1";
		}

		replUrl += "&lite=true";
		replUrl += postSharp;

		// Indentify multiple code blocks
		const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;

		// Avoid sanitizing iframe tag
		setTimeout(renderRepl.bind(null, wrapperId, replUrl), 0);

		return "<div id=\"" + wrapperId + "\"></div>";
	});
}

function renderRepl(wrapperId, replUrl) {
	const el = document.querySelector(`#${wrapperId}`);

	var urlParams = getUrlParams(replUrl);

	var height = 400;

	if (urlParams.height) {
		height = urlParams.height;
	}

	el.innerHTML = '<iframe height="'
			+ height
			+ 'px" width="100%" src="'
			+ replUrl
			+ '" scrolling="no" frameborder="no" allowtransparency="true" allowfullscreen="true" sandbox="allow-forms allow-pointer-lock allow-popups allow-same-origin allow-scripts allow-modals"></iframe>';
}
// repl 플러그인 끝

// codepen 플러그인 시작
function codepenPlugin() {
	toastui.Editor.codeBlockManager
			.setReplacer(
					"codepen",
					function(codepenUrl) {
						// Indentify multiple code blocks
						const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;

						// Avoid sanitizing iframe tag
						setTimeout(renderCodepen.bind(null, wrapperId,
								codepenUrl), 0);

						return '<div id="' + wrapperId + '"></div>';
					});
}

function renderCodepen(wrapperId, codepenUrl) {
	const el = document.querySelector(`#${wrapperId}`);

	var urlParams = getUrlParams(codepenUrl);

	var height = 400;

	if (urlParams.height) {
		height = urlParams.height;
	}

	var width = '100%';

	if (urlParams.width) {
		width = urlParams.width;
	}

	if (!isNaN(width)) {
		width += 'px';
	}

	if (codepenUrl.indexOf('#') !== -1) {
		var pos = codepenUrl.indexOf('#');
		codepenUrl = codepenUrl.substr(0, pos);
	}

	el.innerHTML = '<iframe height="'
			+ height
			+ '" style="width: '
			+ width
			+ ';" scrolling="no" title="" src="'
			+ codepenUrl
			+ '" frameborder="no" allowtransparency="true" allowfullscreen="true"></iframe>';
}
// repl 플러그인 끝

// lib 시작
String.prototype.replaceAll = function(org, dest) {
	return this.split(org).join(dest);
}

function getUrlParams(url) {
	url = url.trim();
	url = url.replaceAll('&amp;', '&');
	if (url.indexOf('#') !== -1) {
		var pos = url.indexOf('#');
		url = url.substr(0, pos);
	}

	var params = {};

	url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) {
		params[key] = value;
	});
	return params;
}
// lib 끝

var joinFormSubmitted = false;
var JoinForm__validLoginId = '';
function submitJoinForm(form) {

	if (joinFormSubmitted) {
		alert('처리 중입니다.');
		return;
	}

	
	form.loginId.value = form.loginId.value.trim();
	if (form.loginId.value.length == 0) {
		alert('아이디를 입력해주세요.');
		form.loginId.focus();

		return;
	}
	
	if (form.loginId.value != JoinForm__validLoginId) {
		alert('다른 아이디를 입력해주세요.');
		form.loginId.focus();
		return;
	}
	
	if (form.loginId.value.indexOf(' ') != -1) {
		alert('아이디를 영문소문자와 숫자의 조합으로 입력해주세요.')
		form.loginId.focus();

		return;
	}

	form.loginPw.value = form.loginPw.value.trim();
	if (form.loginPw.value.length == 0) {
		alert('비밀번호를 입력해주세요.');
		form.loginPw.focus();

		return;
	}

	form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
	if (form.loginPwConfirm.value.length == 0) {
		alert('비밀번호확인를 입력해주세요.');
		form.loginPwConfirm.focus();

		return;
	}

	if (form.loginPwConfirm.value != form.loginPw.value) {
		alert('비밀번호와 비밀번호확인이 일치하지않습니다.');
		form.loginPw.focus();

		return;
	}

	form.name.value = form.name.value.trim();
	if (form.name.value.length == 0) {
		alert('이름을 입력해주세요.');
		form.name.focus();

		return;
	}

	form.nickname.value = form.nickname.value.trim();
	if (form.nickname.value.length == 0) {
		alert('닉네임을 입력해주세요.');
		form.nickname.focus();

		return;
	}
	
	if (form.nickname.value.length > 8) {
		alert('닉네임은 8글자까지 가능합니다.');
		form.nickname.focus();

		return;
	}
	
	form.email.value = form.email.value.trim();
	if (form.email.value.length == 0) {
		alert('이메일을 입력해주세요.');
		form.email.focus();

		return;
	}

	form.loginPwReal.value = sha256(form.loginPw.value);

	form.loginPw.value = '';
	form.loginPwConfirm.value = '';

	form.submit();
	joinFormSubmitted = true;
}

function JoinForm__checkLoginIdDup(input) {
	var form = input.form;
	form.loginId.value = form.loginId.value.trim();
	if (form.loginId.value.length == 0) {
		return;
	}
	$.get('getLoginIdDup', {
		loginId : form.loginId.value
	}, function(data) {
		var $message = $(form.loginId).next();
		if (data.resultCode.substr(0, 2) == 'S-') {
			$message.empty().append(
					'<div style="color:green;">' + data.msg + '</div>');
			JoinForm__validLoginId = data.loginId;
		} else {
			$message.empty().append(
					'<div style="color:red;">' + data.msg + '</div>');
			JoinForm__validLoginId = '';
		}
	}, 'json');
}

function JoinForm__checkNicknameDup(input) {
	var form = input.form;
	form.nickname.value = form.nickname.value.trim();
	if (form.nickname.value.length == 0) {
		return;
	}
	$.get('getNicknameDup', {
		nickname : form.nickname.value
	}, function(data) {
		var $message = $(form.nickname).next();
		if (data.resultCode.substr(0, 2) == 'S-') {
			$message.empty().append(
					'<div style="color:green;">' + data.msg + '</div>');
			JoinForm__validNickname = data.nickname;
		} else {
			$message.empty().append(
					'<div style="color:red;">' + data.msg + '</div>');
			JoinForm__validNickname = '';
		}
	}, 'json');
}

function JoinForm__checkEmailDup(input) {
	var form = input.form;
	form.email.value = form.email.value.trim();
	if (form.email.value.length == 0) {
		return;
	}
	$.get('getEmailDup', {
		email : form.email.value
	}, function(data) {
		var $message = $(form.email).next();
		if (data.resultCode.substr(0, 2) == 'S-') {
			$message.empty().append(
					'<div style="color:green;">' + data.msg + '</div>');
			JoinForm__validEmail = data.email;
		} else {
			$message.empty().append(
					'<div style="color:red;">' + data.msg + '</div>');
			JoinForm__validEmail = '';
		}
	}, 'json');
}

function submitLoginForm(form) {
	form.loginId.value = form.loginId.value.trim();
	if (form.loginId.value.length == 0) {
		alert('아이디를 입력해주세요.');
		form.loginId.focus();

		return;
	}

	form.loginPw.value = form.loginPw.value.trim();
	if (form.loginPw.value.length == 0) {
		alert('비밀번호를 입력해주세요.');
		form.loginPw.focus();

		return;
	}
	form.loginPw.value = sha256(form.loginPw.value);
	
	form.submit();
}

var writeFormSubmitted = false;

function submitWriteForm(form) {

	if (writeFormSubmitted) {
		alert('처리 중입니다.');
		return;
	}

	form.title.value = form.title.value.trim();
	if (form.title.value.length == 0) {
		alert('제목을 입력해주세요.');
		form.title.focus();

		return;
	}
	
	var source = editor2.getMarkdown().trim();

	if (source.length == 0) {
		alert('내용을 입력해주세요.');
		editor2.focus();
		return;
	}

	form.body.value = source;
	removeOnBeforeUnload();
	form.submit();
	writeFormSubmitted = true;
	
}
var modifyFormSubmitted = false;

function submitModifyForm(form) {

	if (modifyFormSubmitted) {
		alert('처리 중입니다.');
		return;
	}

	form.title.value = form.title.value.trim();
	if (form.title.value.length == 0) {
		alert('제목을 입력해주세요.');
		form.title.focus();

		return;
	}
	
	var source = editor3.getMarkdown().trim();

	if (source.length == 0) {
		alert('내용을 입력해주세요.');
		editor3.focus();
		return;
	}

	form.body.value = source;
	
	if(confirm('수정하시겠습니까?') == false){
		return;
	}	
	
	form.submit();
	modifyFormSubmitted = true;
	
}

function submitReplyWriteForm(form){
	form.body.value = form.body.value.trim();
	if (form.body.value.length == 0) {
		alert('댓글을 입력해주세요.');
		form.body.focus();

		return;
	}
	removeOnBeforeUnload();
		
	form.submit();
}

function submitSearchForm(form){
	form.searchKeyword.value = form.searchKeyword.value.trim();
	if (form.searchKeyword.value.length < 2) {
		alert('검색어를 2자 이상 입력해주세요.');
		form.searchKeyword.focus();
		return;
	}
		
	form.submit();
}
function getBodyFromXTemplate(selector) {
	return $(selector).html().trim().replace(/<!--REPLACE:script-->/gi, 'script');
}

$('.replyLogin').click(function(){
	if(confirm('로그인 후 작성하시겠습니까?')){
		
	} else{
		return;
	}
});

var onBeforeUnloadSetted = false;
var onBeforeUnload = function(e) {
  return '떠나시겠습니까?';
};

function applyOnBeforeUnload() {
  if ( onBeforeUnloadSetted ) return;
  $(window).bind('beforeunload', onBeforeUnload);
  onBeforeUnloadSetted = true;
}

$(function() {
	$('.replyWrite-form textarea').keyup(function() {
		  applyOnBeforeUnload();
		})
	$('.write-form #editor2').keyup(function() {
	applyOnBeforeUnload();
	})
});

function removeOnBeforeUnload() {
  $(window).unbind('beforeunload', onBeforeUnload);
  onBeforeUnloadSetted = false;
}

function submitMailWriteForm(form) {

	form.title.value = form.title.value.trim();
	if (form.title.value.length == 0) {
		alert('제목을 입력해주세요.');
		form.title.focus();

		return;
	}
	
	form.body.value = form.body.value.trim();
	if (form.body.value.length == 0) {
		alert('내용을 입력해주세요.');
		form.body.focus();
		return;
	}
		
	form.email.value = form.email.value.trim();
	if (form.email.value.length == 0) {
		alert('이메일을 입력해주세요.');
		form.email.focus();
		return;
	}
	
	if(confirm('보내시겠습니까?') == false){
		return;
	}	
	
	form.submit();
	
}

function submitFindLoginIdForm(form) {

	form.name.value = form.name.value.trim();
	if (form.name.value.length == 0) {
		alert('이름을 입력해주세요.');
		form.name.focus();

		return;
	}
	
	form.email.value = form.email.value.trim();
	if (form.email.value.length == 0) {
		alert('이메일을 입력해주세요.');
		form.email.focus();
		return;
	}
	
	if(confirm('보내시겠습니까?') == false){
		return;
	}	
	
	form.submit();
}

function submitFindLoginPwForm(form) {
	
	form.loginId.value = form.loginId.value.trim();
	if (form.loginId.value.length == 0) {
		alert('아이디를 입력해주세요.');
		form.loginId.focus();

		return;
	}	
	
	form.name.value = form.name.value.trim();
	if (form.name.value.length == 0) {
		alert('이름을 입력해주세요.');
		form.name.focus();

		return;
	}
	
	form.email.value = form.email.value.trim();
	if (form.email.value.length == 0) {
		alert('이메일을 입력해주세요.');
		form.email.focus();
		return;
	}
	
	if(confirm('보내시겠습니까?') == false){
		return;
	}	
	
	form.submit();
}

function submitMemberModifyForm(form) {
	
	form.nickname.value = form.nickname.value.trim();
	if (form.nickname.value.length == 0) {
		alert('닉네임을 입력해주세요.');
		form.nickname.focus();

		return;
	}
	
	if (form.nickname.value.length > 8) {
		alert('닉네임은 8글자까지 가능합니다.');
		form.nickname.focus();

		return;
	}
	
	form.email.value = form.email.value.trim();
	if (form.email.value.length == 0) {
		alert('이메일을 입력해주세요.');
		form.email.focus();
		return;
	}
	
	
	
	if(confirm('수정하시겠습니까?') == false){
		return;
	}	
	
	form.submit();
	
}

function submitPwModifyForm(form) {
	
	form.loginPw.value = form.loginPw.value.trim();
	if (form.loginPw.value.length == 0) {
		alert('비밀번호를 입력해주세요.');
		form.loginPw.focus();

		return;
	}

	form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
	if (form.loginPwConfirm.value.length == 0) {
		alert('비밀번호확인를 입력해주세요.');
		form.loginPwConfirm.focus();

		return;
	}

	if (form.loginPwConfirm.value != form.loginPw.value) {
		alert('비밀번호와 비밀번호확인이 일치하지않습니다.');
		form.loginPw.focus();

		return;
	}
	
	form.loginPwReal.value = sha256(form.loginPw.value);

	form.loginPw.value = '';
	form.loginPwConfirm.value = '';
	
	form.submit();
	
}

function submitPwConfirmForm(form) {
	
	form.loginPw.value = form.loginPw.value.trim();
	if (form.loginPw.value.length == 0) {
		alert('비밀번호를 입력해주세요.');
		form.loginPw.focus();

		return;
	}

	form.loginPw.value = sha256(form.loginPw.value);

	form.submit();
	
}

if (!param.jsAction) {
	param.jsAction = '';
}

var jsActions = param.jsAction.split(',');

for ( var key in jsActions) {
	var jsActionFuncName = jsActions[key];

	$(function() {
		setTimeout(function() {
			if (window[jsActionFuncName]) {
				window[jsActionFuncName]();
			} else {
				console.error("jsAction으로 요청받은, " + jsActionFuncName
						+ " 함수는 존재하지 않아서 실행하지 못했습니다.");
			}
		}, 100);
	});
}
