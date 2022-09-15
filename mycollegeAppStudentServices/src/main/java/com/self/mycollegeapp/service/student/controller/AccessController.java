package com.self.mycollegeapp.service.student.controller;

import java.util.Base64;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessController extends AbstractController {

	@RequestMapping(value = "/mycollegeapp/services/student/authenticate", method = RequestMethod.POST)
	public Hashtable getAuthenticate(HttpServletRequest request, HttpSession session) {
		System.out.println("request "+request.getParameter("login")+" "+request.getParameterMap().size());
		Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
    	//in actual below token should be process with real data e.g of user information from database, correct issue at time value. this just a mock
		String jwtHeader = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
		String jwtPayload = "{\"username\":\"admin1\",\"iat\":\"1620590325\"}";
		String encodedContent = Base64.getEncoder().encodeToString(jwtHeader.getBytes())+"."+Base64.getEncoder().encodeToString(jwtPayload.getBytes());
		byte[] jwtSignature = HmacUtils.hmacSha256("a1dd395c19f3a1f7ff0e6bfe3ee6028e4373b41085a6da776ab02a8baf129abb", encodedContent);
		String jwtToken = Base64.getEncoder().encodeToString(jwtHeader.getBytes())+"."+Base64.getEncoder().encodeToString(jwtPayload.getBytes())+"."+Base64.getEncoder().encodeToString(jwtSignature);
		hashtable.put("access_token", jwtToken);
		return successResponse(hashtable);
	}

}

