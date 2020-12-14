package org.zerock.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Test for Controller
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardControllerTests {

	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	
	// 가짜 mvc. URL과 파라미터 등을 브라우저에서 사용하는 것처럼 만들어 Controller 테스트
	private MockMvc mockMvc;
	
	// 모든 테스트 전에 매번 실행됨
	@org.junit.Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testList() throws Exception{
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn()
				.getModelAndView()
				.getModelMap());  // 모델에 담겨있는 데이터 확인
	}
	
	@Test
	public void testRegister() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				// 전송할 파라미터 지정 가능
				.param("title", "테스트 새 글제목")
				.param("content", "테스트 새 글내용")
				.param("writer", "user00"))
				.andReturn()
				.getModelAndView()
				.getViewName();
		log.info(resultPage);
	}

	@Test
	public void testGet() throws Exception{
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
				.param("bno", "2"))
				.andReturn()
				.getModelAndView()
				.getModelMap());
	}
	
	@Test
	public void testModify() throws Exception{
		String resultPage = mockMvc
				.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("bno", "10")
				.param("title", "수정된 테스트 새 글제목")
				.param("content", "수정된 테스트 새 글내용")
				.param("writer", "user00"))
				.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}

	@Test
	public void testRemove() throws Exception{
		// 삭제전 db bno 확인
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "25")
				).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	@Test
	public void testListPaging() throws Exception {
		log.info(mockMvc.perform(
				MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "2")
				.param("amount", "50"))
				.andReturn().getModelAndView().getModelMap());
	}
}
