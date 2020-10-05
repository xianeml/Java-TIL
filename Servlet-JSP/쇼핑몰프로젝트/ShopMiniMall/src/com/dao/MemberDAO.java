package com.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

import com.dto.MemberDTO;

public class MemberDAO {

	public int memberAdd(SqlSession session, MemberDTO dto) {
		int n = session.insert("MemberMapper.memberAdd", dto);
		return n;
	}

	public int idCheck(SqlSession session, String userid) {
		int n = session.selectOne("MemberMapper.idCheck", userid);
		return n;
	}

	public MemberDTO login(SqlSession session, HashMap<String, String> map) {
		// "MemberMapper.login" 사용 정보 select MemberDTO생성
		MemberDTO n = session.selectOne("MemberMapper.login", map);
		return n;
	}

	public MemberDTO mypage(SqlSession session, String userid) {
		MemberDTO n = session.selectOne("MemberMapper.mypage", userid);
		System.out.println(n);
		return n;
	}
}
