package com.dao;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.dto.CartDTO;
import com.dto.GoodsDTO;
import com.dto.OrderDTO;

public class CartDAO {

	public int cartAdd(SqlSession session, CartDTO dto) {
		int n = session.insert("CartMapper.cartAdd", dto);
		return n;
	}

	public List<CartDTO> cartList(SqlSession session, String userid) {
		List<CartDTO> list = session.selectList("cartList", userid);
		return list;
	}
	
	public int cartDel(SqlSession session, int num) {
		int n = session.delete("CartMapper.cartDel", num);
		return n;
	}

	public void cartUpdate(SqlSession session, HashMap<String, Integer> map) {
		session.update("CartMapper.cartUpdate",map);
	}

	public int cartAllDel(SqlSession session, List<String> list) {
		int n = session.delete("CartMapper.cartAllDel", list);
		return n;
	}

	public CartDTO cartByNum(SqlSession session, int num) {
		CartDTO cdto = session.selectOne("CartMapper.cartbyNum", num);
		return cdto;
	}

	public int orderDone(SqlSession session, OrderDTO dto2) {
		int n = session.insert("OrderMapper.orderDone", dto2);
		System.out.println("dao:"+n);
		return n;
	}

}
