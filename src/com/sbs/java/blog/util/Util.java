package com.sbs.java.blog.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
	public static boolean empty(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);

		return empty(paramValue);
	}

	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof String) {
			return ((String) obj).trim().length() == 0;
		}

		return true;
	}

	public static boolean isNum(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);

		return isNum(paramValue);
	}

	public static boolean isNum(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Long) {
			return true;
		} else if (obj instanceof Integer) {
			return true;
		} else if (obj instanceof String) {
			try {
				Integer.parseInt((String) obj);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		return false;
	}

	public static int getInt(HttpServletRequest req, String paramName) {
		return Integer.parseInt(req.getParameter(paramName));
	}

	public static void printEx(String errName, HttpServletResponse resp, Exception e) {
		try {
			resp.getWriter()
					.append("<h1 style='color:red; font-weight:bold; text-align:left;'>[에러 : " + errName + "]</h1>");

			resp.getWriter().append("<pre style='text-align:left; font-weight:bold; font-size:1.3rem;'>");
			e.printStackTrace(resp.getWriter());
			resp.getWriter().append("</pre>");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static int sendMail(String smtpServerId, String smtpServerPw, String from, String fromName, String to,
			String title, String body) {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "587");

		Authenticator auth = new MailAuth(smtpServerId, smtpServerPw);

		Session session = Session.getDefaultInstance(prop, auth);

		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setSentDate(new Date());

			msg.setFrom(new InternetAddress(from, fromName));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(title, "UTF-8");
			msg.setText(body, "UTF-8");

			Transport.send(msg);

		} catch (AddressException ae) {
			System.out.println("AddressException : " + ae.getMessage());
			return -1;
		} catch (MessagingException me) {
			System.out.println("MessagingException : " + me.getMessage());
			return -2;
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
			return -3;
		}
		
		return 1;
	}
	 public static String sha256(String msg) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(msg.getBytes());
	        
	        return bytesToHex(md.digest());
	   }

	    public static String bytesToHex(byte[] bytes) {
	        StringBuilder builder = new StringBuilder();
	        for (byte b: bytes) {
	          builder.append(String.format("%02x", b));
	        }
	        return builder.toString();
	    }

		public static void sendMail(MimeMessage mail) {
			try {
				Transport.send(mail);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		public static void cofirmMail(String email, int id, HttpServletRequest req, String key, String gmailId, String gmailPw) {
			Properties prop = System.getProperties();
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.port", "587");


			Authenticator auth = new MailAuth(gmailId, gmailPw);

			Session session = Session.getDefaultInstance(prop, auth);
			
			MimeMessage mail = new MimeMessage(session);
			
			String htmlStr = "<h2>안녕하세요</h2><br><br>" 
					+ "<p>인증하기 버튼을 누르시면 로그인을 하실 수 있습니다 : " 
					+ "<a href='https://kys.my.iu.gy/" + req.getContextPath() + "/s/member/doMailAuth?id="+ id +"&key="+key+"&email="+email+"'>인증하기</a></p>"
					+ "(혹시 잘못 전달된 메일이라면 이 이메일을 무시하셔도 됩니다)";
			
			try {
				mail.setSentDate(new Date());

				mail.setFrom(new InternetAddress(gmailId, "관리자"));
				mail.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
				mail.setSubject("[본인인증] 인증메일입니다.", "utf-8");
				mail.setText(htmlStr, "utf-8", "html");
				mail.addRecipient(RecipientType.TO, new InternetAddress(email));

				Transport.send(mail);

			} catch (AddressException ae) {
				System.out.println("AddressException : " + ae.getMessage());
			} catch (MessagingException me) {
				System.out.println("MessagingException : " + me.getMessage());
			} catch (UnsupportedEncodingException e) {
				System.out.println("UnsupportedEncodingException : " + e.getMessage());
			}		
		}
		public static boolean isSuccess(Map<String, Object> rs) {
			return ((String) rs.get("resultCode")).startsWith("S-");
		}

		public static String getNewUriRemoved(String uri, String paramName) {
			String deleteStrStarts = paramName + "=";
			int delStartPos = uri.indexOf(deleteStrStarts);

			if (delStartPos != -1) {
				int delEndPos = uri.indexOf("&", delStartPos);

				if (delEndPos != -1) {
					delEndPos++;
					uri = uri.substring(0, delStartPos) + uri.substring(delEndPos, uri.length());
				} else {
					uri = uri.substring(0, delStartPos);
				}
			}

			if (uri.charAt(uri.length() - 1) == '?') {
				uri = uri.substring(0, uri.length() - 1);
			}

			if (uri.charAt(uri.length() - 1) == '&') {
				uri = uri.substring(0, uri.length() - 1);
			}

			return uri;
		}

		public static String getNewUri(String uri, String paramName, String paramValue) {
			uri = getNewUriRemoved(uri, paramName);

			if (uri.contains("?")) {
				uri += "&" + paramName + "=" + paramValue;
			} else {
				uri += "?" + paramName + "=" + paramValue;
			}

			uri = uri.replace("?&", "?");

			return uri;
		}

		public static String getNewUriAndEncoded(String uri, String paramName, String pramValue) {
			return getUriEncoded(getNewUri(uri, paramName, pramValue));
		}
		
		public static String getString(HttpServletRequest req, String paramName) {
			return req.getParameter(paramName);
		}

		public static String getUriEncoded(String str) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return str;
			}
		}

		public static String getString(HttpServletRequest req, String paramName, String elseValue) {
			if (req.getParameter(paramName) == null) {
				return elseValue;
			}

			if (req.getParameter(paramName).trim().length() == 0) {
				return elseValue;
			}

			return getString(req, paramName);
		}


}