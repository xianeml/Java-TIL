package com.service;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;

import com.config.MySqlSessionFactory;
import com.dao.MemberDAO;
import com.dto.MemberDTO;

public class MemberService {
	public int memberAdd(MemberDTO dto) {
		MemberDAO dao = new MemberDAO();
		SqlSession session = MySqlSessionFactory.getSession();
		int n = 0;
		try {
			n = dao.memberAdd(session, dto);
			session.commit();
		} finally {
			session.close();
		}
		return n;
	}

	public int idCheck(String userid) {
		MemberDAO dao = new MemberDAO();
		SqlSession session = MySqlSessionFactory.getSession();
		int n = 0;
		try {
			n = dao.idCheck(session, userid);
			session.commit();
		} finally {
			session.close();
		}
		return n;
	}

	public MemberDTO login(HashMap<String, String> map) {
		// map을 dao에 전달 db 검색 후 사용자 정보를 MemberDTO로 리턴
		MemberDAO dao = new MemberDAO();
		SqlSession session = MySqlSessionFactory.getSession();
		MemberDTO n;
		try {
			n = dao.login(session, map);
		} finally {
			session.close();
		}
		return n;
	}

	public MemberDTO mypage(String userid) {
		SqlSession session = MySqlSessionFactory.getSession();
		MemberDTO dto = null;
		try {
			MemberDAO dao = new MemberDAO();
			dto = dao.mypage(session, userid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return dto;
	}
}// end class