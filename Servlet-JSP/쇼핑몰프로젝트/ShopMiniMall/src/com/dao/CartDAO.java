package com.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.dto.CartDTO;
import com.dto.GoodsDTO;

public class CartDAO {

	public int cartAdd(SqlSession session, CartDTO dto) {
		int n = session.insert("CartMapper.cartAdd", dto);
		return n;
	}

}
