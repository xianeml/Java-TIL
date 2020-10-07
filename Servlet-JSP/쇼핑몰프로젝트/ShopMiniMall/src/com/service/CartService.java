package com.service;

import org.apache.ibatis.session.SqlSession;

import com.config.MySqlSessionFactory;
import com.dao.CartDAO;
import com.dto.CartDTO;

public class CartService {

	public int cartAdd(CartDTO dto) {
		CartDAO dao = new CartDAO();
		SqlSession session = MySqlSessionFactory.getSession();
		int n = 0;
		try {
			n = dao.cartAdd(session, dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return n;
	}

}// end class
