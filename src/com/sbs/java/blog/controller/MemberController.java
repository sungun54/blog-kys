package com.sbs.java.blog.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.service.MailService;
import com.sbs.java.blog.util.Util;

public class MemberController extends Controller {
	private String gmailId;
	private String gmailPw;

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp, String gmailId, String gmailPw) {
		super(dbConn, actionMethodName, req, resp);
		this.gmailId = gmailId;
		this.gmailPw = gmailPw;
	}

	@Override
	public String getControllerName() {
		return "member";
	}

	public void beforeAction() {
		super.beforeAction();
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "login":
			return actionLogin();
		case "doLogin":
			return actionDoLogin();
		case "doLogout":
			return actionDoLogout();
		case "join":
			return actionJoin();
		case "doJoin":
			return actionDoJoin();
		case "findLoginId":
			return actionFindLoginId();
		case "doFindLoginId":
			return actionDoFindLoginId();
		case "findLoginPw":
			return actionFindLoginPw();
		case "doFindLoginPw":
			return actionDoFindLoginPw();
		case "myPage":
			return actionMyPage();
		case "memberModify":
			return actionMemberModify();
		case "doMemberModify":
			return actionDoMemberModify();
		case "pwConfirm":
			return actionMemberPwModifyConfirm();
		case "doPwConfirm":
			return actionDoMemberPwModifyConfirm();
		case "pwModify":
			return actionMemberPwModify();
		case "doPwModify":
			return actionDoMemberPwModify();
		case "doMailAuth":
			return actionDoMailAuth();
		case "getLoginIdDup":
			return actionGetLoginIdDup();
		case "getNicknameDup":
			return actionGetNicknameDup();
		case "getEmailDup":
			return actionGetEmailDup();

		}

		return "";
	}

	private String actionDoMailAuth() {
		int id = Integer.parseInt(req.getParameter("id"));
		String key = req.getParameter("key");
		String email = req.getParameter("email");

		String value = attrService.getValue("member__" + id + "__extra__emailAuthCode");

		if (!value.equals(key)) {
			return "html:<script> alert('인증이 실패했습니다.');location.replace('../home/main'); </script>";
		}

		attrService.setValue("member__" + id + "__extra__emailAuthed", email);

		return "html:<script> alert('인증이 완료됐습니다.');location.replace('../member/login'); </script>";
	}

	private String actionDoMemberPwModifyConfirm() {
		int id = Integer.parseInt(req.getParameter("id"));
		String loginPw = req.getParameter("loginPw");

		boolean check = memberService.pwConfirm(id, loginPw);

		if (check == false) {
			return "html:<script> alert('비밀번호가 일치하지않습니다.'); history.back(); </script>";
		}

		return "html:<script>location.replace('pwModify'); </script>";
	}

	private String actionMemberPwModifyConfirm() {
		return "member/pwConfirm.jsp";
	}

	private String actionDoMemberPwModify() {
		int id = Integer.parseInt(req.getParameter("id"));
		String loginPw = req.getParameter("loginPwReal");

		memberService.pwModify(id, loginPw);
		memberService.update(id);
		attrService.remove("member__" + id + "__extra__useTempPassword");
		return "html:<script> alert('비밀번호가 변경되었습니다.'); location.replace('../home/main'); </script>";
	}

	private String actionMemberPwModify() {
		return "member/pwModify.jsp";
	}

	private String actionMemberModify() {
		return "member/modify.jsp";
	}

	private String actionDoMemberModify() {
		int id = Integer.parseInt(req.getParameter("id"));
		String nickname = req.getParameter("nickname");
		// String email = req.getParameter("email");

		boolean check = memberService.nicknameCheck(nickname);

		if (check == false) {
			return "html:<script> alert('이미 사용 중인 닉네임입니다.'); history.back();</script>";
		}

		// memberService.Modify(nickname, email, id);
		memberService.Modify(nickname, id);
		return "html:<script> alert('수정되었습니다.'); location.replace('../home/main'); </script>";
	}

	private String actionMyPage() {
		return "member/modify.jsp";
	}

	private String actionDoFindLoginPw() {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		Map<String, Object> member = memberService.findLoginPw(name, email, loginId);

		if (member.size() == 0) {
			return "html:<script> alert('일치하는 계정이 없습니다.'); history.back();</script>";
		}

		String uuid = "" + "";

		for (int i = 0; i < 5; i++) {
			uuid = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다.
			uuid = uuid.substring(0, 10); // uuid를 앞에서부터 10자리 잘라줌.
		}

		int id = (int) member.get("id");
		String uuidReal = uuid;
		try {
			uuid = Util.sha256(uuid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		memberService.temporaryPw(id, uuid);
		MailService mailService = new MailService(gmailId, gmailPw, gmailId, "관리자", dbConn, attrService);
		mailService.send(email, "임시비빌번호입니다.", uuidReal);
		attrService.setValue("member__" + id + "__extra__useTempPassword", "1");
		return "html:<script> alert('메일로 임시비밀번호를 발송하였습니다.'); window.close(); </script>";
	}

	private String actionFindLoginPw() {
		return "member/findLoginPw.jsp";
	}

	private String actionDoFindLoginId() {
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		Map<String, Object> member = memberService.findLoginId(name, email);

		if (member.size() == 0) {
			return "html:<script> alert('일치하는 아이디가 없습니다.'); window.close();</script>";
		}

		return "html:<script> alert('아이디는 " + member.get("loginId") + "'); window.close();</script>";
	}

	private String actionFindLoginId() {
		return "member/findLoginId.jsp";
	}

	private String actionDoLogout() {
		String redirectUrl = req.getParameter("redirectUrl");
//		String cateItemId = "";
//		if(req.getParameter("cateItemId") != null) {
//			cateItemId = req.getParameter("cateItemId");
//		}
//		String page = "";
//		if(req.getParameter("page") != null) {
//			page = req.getParameter("page");
//		}
//		
//		if(!cateItemId.equals("") && page.equals("")) {
//			redirectUrl+= "&cateItemId=" + cateItemId; 
//		}		
//		if(!page.equals("") && cateItemId.equals("")) {
//			redirectUrl+= "&page=" + page;
//		}
//		if(!cateItemId.equals("") && !page.equals("")) {
//			redirectUrl+= "&cateItemId=" + cateItemId + "&page=" + page; 
//		}	

		session.removeAttribute("loginedMemberId");

		return "html:<script> alert('로그아웃되었습니다.'); location.replace('../home/main'); </script>";
	}

	private String actionDoJoin() {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPwReal");
		String email = req.getParameter("email");

		boolean getRegDateMember = memberService.getRegDateMember(loginId);

		if (getRegDateMember == true) {
			memberService.deleteMember(loginId);
		}

		boolean isJoinableLoginId = memberService.isJoinableLoginId(loginId);

		if (isJoinableLoginId == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 아이디 입니다.'); history.back(); </script>", loginId);
		}

		boolean isJoinableNickname = memberService.isJoinableNickname(nickname);

		if (isJoinableNickname == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 닉네임 입니다.'); history.back(); </script>", nickname);
		}

		boolean isJoinableEmail = memberService.isJoinableEmail(email);

		if (isJoinableEmail == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 이메일 입니다.'); history.back(); </script>", email);
		}

		int id = memberService.join(loginId, name, nickname, loginPw, email);

		MailService mailService = new MailService(gmailId, gmailPw, gmailId, "관리자", dbConn, attrService);

		mailService.mailSendWithUserKey(email, id, req);

		// mailService.send(email, "회원가입을 축하드립니다.", "반갑습니다.");

		// return "html:<script> alert('" + id + "번 회원이 생성되었습니다.');
		// location.replace('../home/main'); </script>";

//		Timer t = new Timer(true);
//		
//		TimerTask task = new TimeTask(loginId, dbConn);
//						
//		t.schedule(task, 1000);		

		return "html:<script> alert('이메일 인증이 발송되었습니다. 10분 내로 인증해주세요. 인증 실패 시 계정이 삭제됩니다'); location.replace('../home/main'); </script>";
//		
//		return "html:<script> alert('이메일 인증에 실패하였습니다.'); location.replace('../home/main'); </script>";
	}

	private String actionJoin() {
		return "member/join.jsp";
	}

	private String actionDoLogin() {
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPw");
		String redirectUrl = req.getParameter("redirectUrl");
//		String cateItemId = "";
//		if(req.getParameter("cateItemId") != null) {
//			cateItemId = req.getParameter("cateItemId");
//		}
//		String page = "";
//		if(req.getParameter("page") != null) {
//			page = req.getParameter("page");
//		}
//		
//		if(!cateItemId.equals("") && page.equals("")) {
//			redirectUrl+= "&cateItemId=" + cateItemId; 
//		}		
//		if(!page.equals("") && cateItemId.equals("")) {
//			redirectUrl+= "&page=" + page;
//		}
//		if(!cateItemId.equals("") && !page.equals("")) {
//			redirectUrl+= "&cateItemId=" + cateItemId + "&page=" + page; 
//		}
		Map<String, Object> member = memberService.login(loginId, loginPw);
		if (member.size() == 0) {
			return "html:<script> alert('아이디 혹은 비밀번호가 일치하지않습니다.'); history.back(); </script>";
		}

		if (member != null) {
			boolean emailAuthed = attrService.getValue("member__" + member.get("id") + "__extra__emailAuthed")
					.length() > 0;
			if(emailAuthed == false) {
				return "html:<script> alert('이메일인증을 먼저 해주세요.'); history.back(); </script>";				
			}			
		}


		session.setAttribute("loginedMemberId", member.get("id"));
		
		
		String code = attrService.getValue("member__" + member.get("id") + "__extra__useTempPassword");
		
		boolean PwDate = memberService.getRegDateMemberYear(loginId);
		
		
		if(redirectUrl.equals("")&&PwDate == true) {
			return "html:<script>alert('오래된 비밀번호입니다. 비밀번호를 변경해주세요.'); alert('" + member.get("nickname")
			+ "님 어서오세요.'); location.replace('../home/main'); </script>";
		} else if(!redirectUrl.equals("")&&PwDate == true) {
			return "html:<script>alert('오래된 비밀번호입니다. 비밀번호를 변경해주세요.'); alert('" + member.get("nickname")
			+ "님 어서오세요.'); location.replace('" + redirectUrl+ "'); </script>";
		}
		
		if(code.equals("1")&&redirectUrl.equals("")&&PwDate == true) {
			return "html:<script>alert('오래된 비밀번호입니다. 비밀번호를 변경해주세요.'); alert('현재 임시 비밀번호를 사용 중 입니다.'); alert('" + member.get("nickname")
			+ "님 어서오세요.'); location.replace('../home/main'); </script>";
		} 		
		if(code.equals("1")&&redirectUrl.equals("")) {
			return "html:<script> alert('현재 임시 비밀번호를 사용 중 입니다.'); alert('" + member.get("nickname")
			+ "님 어서오세요.'); location.replace('../home/main'); </script>";
		} else if(code.equals("1")&&!redirectUrl.equals("")) {
			return "html:<script> alert('현재 임시 비밀번호를 사용 중 입니다.'); alert('" + member.get("nickname") + "님 어서오세요.'); location.replace('" + redirectUrl
					+ "'); </script>";
		}
		
		if (redirectUrl == "") {
			return "html:<script> alert('" + member.get("nickname")
					+ "님 어서오세요.'); location.replace('../home/main'); </script>";
		}
		
		return "html:<script> alert('" + member.get("nickname") + "님 어서오세요.'); location.replace('" + redirectUrl
				+ "'); </script>";
	}

	private String actionLogin() {
		return "member/login.jsp";
	}

	private String actionGetLoginIdDup() {
		String loginId = req.getParameter("loginId");

		Member member = memberService.getMemberByLoginId(loginId);
		boolean isJoinableLoginId = true;
		if (member != null) {
			int id = member.getId();
			boolean emailAuthed = attrService.getValue("member__" + id + "__extra__emailAuthed").length() > 0;
			isJoinableLoginId = false;
			if (emailAuthed == false && memberService.getRegDateMember(loginId)) {
				memberService.deleteMember(loginId);
				isJoinableLoginId = true;
			}
		}

		if (isJoinableLoginId) {
			return "json:{\"msg\":\"사용할 수 있는 아이디 입니다.\", \"resultCode\": \"S-1\", \"loginId\":\"" + loginId + "\"}";
		} else {
			return "json:{\"msg\":\"사용할 수 없는 아이디 입니다.\", \"resultCode\": \"F-1\", \"loginId\":\"" + loginId + "\"}";
		}
	}
	
	private String actionGetNicknameDup() {
		String nickname = req.getParameter("nickname");

		boolean isJoinableNickname = memberService.isJoinableNickname(nickname);

		if (isJoinableNickname) {
			return "json:{\"msg\":\"사용할 수 있는 닉네임 입니다.\", \"resultCode\": \"S-1\", \"loginId\":\"" + nickname + "\"}";
		} else {
			return "json:{\"msg\":\"사용할 수 없는 닉네임 입니다.\", \"resultCode\": \"F-1\", \"loginId\":\"" + nickname + "\"}";
		}
	}
	
	private String actionGetEmailDup() {
		String email = req.getParameter("email");

		boolean isJoinableEmail = memberService.isJoinableEmail(email);

		if (isJoinableEmail) {
			return "json:{\"msg\":\"사용할 수 있는 이메일 입니다.\", \"resultCode\": \"S-1\", \"loginId\":\"" + email + "\"}";
		} else {
			return "json:{\"msg\":\"사용할 수 없는 이메일 입니다.\", \"resultCode\": \"F-1\", \"loginId\":\"" + email + "\"}";
		}
	}
}
