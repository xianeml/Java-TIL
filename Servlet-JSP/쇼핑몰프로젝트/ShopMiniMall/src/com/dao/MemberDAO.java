package com.dao;

import org.apache.ibatis.session.SqlSession;

import com.dto.MemberDTO;

public class MemberDAO {

   public int memberAdd(SqlSession session,MemberDTO dto) {
	  int n = session.insert("MemberMapper.memberAdd", dto);
	  return n;
   }

public int idCheck(SqlSession session, String userid) {
	int n = session.selectOne("MemberMapper.idCheck", userid);
	return n;
}
}
