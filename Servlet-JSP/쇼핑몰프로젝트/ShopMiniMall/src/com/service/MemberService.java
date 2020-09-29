package com.service;

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
	
}//end class