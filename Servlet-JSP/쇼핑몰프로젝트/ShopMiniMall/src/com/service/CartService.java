package com.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;

import com.config.MySqlSessionFactory;
import com.dao.CartDAO;
import com.dto.CartDTO;
import com.dto.OrderDTO;

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

	public List<CartDTO> cartList(String userid) {
		SqlSession session = MySqlSessionFactory.getSession();
		CartDAO dao = new CartDAO();
		List<CartDTO> list = null;
		try {
			list = dao.cartList(session, userid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
	
	public int cartDel(int num) {
		SqlSession session = MySqlSessionFactory.getSession();
		CartDAO dao = new CartDAO();
		int n = 0;
		try {
			n = dao.cartDel(session, num);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return n;
	}

	public void cartUpdate(HashMap<String, Integer> map) {
		SqlSession session = MySqlSessionFactory.getSession();
		CartDAO dao = new CartDAO();
		try {
			dao.cartUpdate(session, map);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	public int cartAllDel(List<String> list) {
		SqlSession session = MySqlSessionFactory.getSession();
		CartDAO dao = new CartDAO();
		int n = 0;
		try {
			n = dao.cartAllDel(session, list);
			session.commit();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return n;
	}

	public CartDTO cartByNum(int num) {
		SqlSession session = MySqlSessionFactory.getSession();
		CartDAO dao = new CartDAO();
		CartDTO cdto = null;
		try {
			cdto = dao.cartByNum(session, num);
			session.commit();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return cdto;
	}

	public int orderDone(OrderDTO dto2, String orderNum) {
		SqlSession session = MySqlSessionFactory.getSession();
		CartDAO dao = new CartDAO();
		int n = 0;
		try {
			n = dao.orderDone(session, dto2);
			n = dao.cartDel(session, Integer.parseInt(orderNum));
			System.out.println(n);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}
		return n;
	}
	
	
	
}// end class
