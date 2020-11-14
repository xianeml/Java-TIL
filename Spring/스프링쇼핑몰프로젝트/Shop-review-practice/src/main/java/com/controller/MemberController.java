package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dto.MemberDTO;
import com.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	MemberService service;

	@RequestMapping(value = "/memberAdd")
	public String memberAdd(MemberDTO m, Model model) {
		service.memberAdd(m);
		model.addAttribute("success", "회원가입성공");
		return "main";
	}
	
	@RequestMapping(value = "/loginCheck/myPage")
	public String myPage(HttpSession session) {
		MemberDTO dto = (MemberDTO)session.getAttribute("login");
		String userid = dto.getUserid();
		MemberDTO mDTO = service.mypage(userid);
		return "mypage";
	}
}
