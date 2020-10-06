package com.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;

import com.config.MySqlSessionFactory;
import com.dao.GoodsDAO;
import com.dao.MemberDAO;
import com.dto.GoodsDTO;
import com.dto.MemberDTO;

public class GoodsService {
	 
	  
	  public List<GoodsDTO> goodsList(String gCategory) {
		SqlSession session = MySqlSessionFactory.getSession();
		List<GoodsDTO> list = null;
		try {
			GoodsDAO dao = new GoodsDAO();
			list = dao.goodsList(session, gCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return list;
		}//end idCheck

	public GoodsDTO goodsRetrieve(String gCode) {
		SqlSession session = MySqlSessionFactory.getSession();
		GoodsDTO list = null;
		try {
			GoodsDAO dao = new GoodsDAO();
			list = dao.goodsRetrieve(session, gCode);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
}//end class
