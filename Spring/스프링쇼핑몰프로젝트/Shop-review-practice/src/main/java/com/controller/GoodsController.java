package com.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dto.CartDTO;
import com.dto.GoodsDTO;
import com.dto.MemberDTO;
import com.service.GoodsService;

@Controller
public class GoodsController {
	
	@Autowired
	GoodsService service;
	
	@RequestMapping(value = "/goodsList")
	public ModelAndView goodsList(@RequestParam("gCategory") String gCategory, HttpSession session) {
		if(gCategory==null) {
			gCategory = "top";
		}
		List<GoodsDTO> list = service.goodsList(gCategory);
		ModelAndView mav = new ModelAndView();
		mav.addObject("goodsList", list);
		mav.setViewName("main");
		return mav;
	}
	
	@RequestMapping(value = "/goodsRetrieve")
	@ModelAttribute("goodsRetrieve")
	public GoodsDTO goodsRetrieve(@RequestParam("gCode") String gCode) {
		GoodsDTO dto = service.goodsRetrieve(gCode);
		return dto;
	}
	
	@RequestMapping(value = "/loginCheck/cartAdd")
	public String cartAdd(CartDTO cart, HttpSession session) {
		MemberDTO mDTO = (MemberDTO)session.getAttribute("login");
		String userid = mDTO.getUserid();
		cart.setUserid(userid);
		session.setAttribute("mesg", cart.getgCode());
		service.cartAdd(cart);
		return "redirect:../goodsRetrieve?gCode="+cart.getgCode();
	}
	
	@RequestMapping(value = "/loginCheck/cartList")
	public String cartList(RedirectAttributes attr, HttpSession session) {
		MemberDTO mDTO = (MemberDTO) session.getAttribute("login");
		String userid = mDTO.getUserid();
		List<CartDTO> list = service.cartList(userid);
		attr.addFlashAttribute("cartList", list);
		return "redirect:../cartList";
	}
	
	@RequestMapping(value = "/loginCheck/cartUpdate")
	public String cartUpdate(@RequestParam Map<String, String> map) {
		System.out.println(map);
		service.cartUpdate(map);
		return "redirect:../cartList";
	}
}
