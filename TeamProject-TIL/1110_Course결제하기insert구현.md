강의 주문 페이지에서 [결제] 버튼을 누르면

1.  OderC 테이블에 결제 정보가 insert된다.
2.  member의 cCode 정보도 수강신청한 강의로 업데이트된다.

courseOrder.jsp:

```java
<form action="CourseOrderDoneServlet" method="post">
	<h3>결제수단</h3>
	<div>
		<input type="radio" id="creditCard" name="payMethod" checked>신용카드<br>
		<input type="radio" id="toss"  name="payMethod">계좌이체<br>
		<input type="radio" id="bank"  name="payMethod">무통장입금<br>
	</div>
	<input type="submit" value="결제하기">
	<input type="button" value="취소">
</form>
```

CourseOrderDoneServlet.java:

```java
HttpSession session = request.getSession();
		MemberDTO dto = (MemberDTO) session.getAttribute("login");
		String nextPage = null;
		if(dto!=null) {
			CourseDTO courseDTO = (CourseDTO) session.getAttribute("courseDTO");
			String cName = courseDTO.getcName();
			int cCode = courseDTO.getcCode();
			int cPrice = courseDTO.getcPrice();
			String cImage = courseDTO.getcImage();
			String cSTARTDATE = courseDTO.getcStartDate();
			String cENDDATE = courseDTO.getcEndDate();
			int cTOTALDATE = courseDTO.getcTotalDate();
			String userId = dto.getUserID();
			String userName = dto.getUserName();
			String phoneNum = dto.getPhoneNum();
			String email1 = dto.getEmail1();
			String email2 = dto.getEmail2();
			String payMethod = request.getParameter("payMethod");

			OrderCDTO oDTO = new OrderCDTO(0, cName, cCode, cPrice, cImage, cSTARTDATE, cENDDATE, cTOTALDATE, userId,
					userName, phoneNum, email1, email2, payMethod, null);
```

세션 로그인 검사 후 OrderC 테이블에 넣어야 하는 값들 파싱. 코스와 멤버 둘 다 세션에 있어서 그대로 사용. 페이메소드만 파싱해옴. 전부 OrderCDTO에 담아서 객체 생성.

```java
CourseService cService = new CourseService();
			int cResult = cService.insertOrderC(oDTO);
			MemberService mService = new MemberService();
			int mResult = mService.updateCcode(oDTO);
			System.out.println("코스인서트:" + cResult);
			System.out.println("멤버업데이트:" + mResult);
			nextPage = "main";

		}else {
			nextPage = "LoginUIServlet";
		}
		response.sendRedirect(nextPage);
	}
```

두 개의 로직 필요. 코스 서비스와 멤버 서비스에 각각 오더dto를 보내준다. 다음페이지 처리까지.

→ 주문완료 알림이 더 들어가야함

Configuration.xml:

```java
<typeAlias type="com.dto.OrderCDTO" alias="OrderCDTO"/>
```

맵퍼 사용전에 컨피그에 알리아스 등록

주문정보 insert 로직:

```java
public int insertOrderC(OrderCDTO dto) {
		SqlSession session = OracleSqlSessionFactory.getSession();
		CourseDAO dao = new CourseDAO();
		int result = 0;
		try {
			result = dao.insertOrderC(session, dto);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
```

dml 커밋

```java
public int insertOrderC(SqlSession session, OrderCDTO dto) {
		int result = session.insert("insertOrderC", dto);
		return result;
	}
```

```java
<insert id="insertOrderC" parameterType="OrderCDTO">
	insert into orderc
	(orderNum, cName, cCode, cPrice, cImage ,cStartDate ,cEndDate, cTotalDate, userId ,userName , phoneNum , email1 , email2 ,payMethod , oDate)
 	values (orderC_seq.nextval, #{cName}, #{cCode},
 	#{cPrice}, #{cImage}, #{cStartDate}, #{cEndDate}, #{cTotalDate}, #{userId}, #{userName}, #{phoneNum}, #{email1}, #{email2}, #{payMethod}, sysdate)
</insert>
```

에러해결

1.  시퀀스 이름 확인
2.  cName 사이즈 변경
3.  date 컬럼 varchar 타입으로 변경, 사이즈도 크게 변경

```java
alter table orderc modify(cName varchar2(100));
alter table orderc modify CSTARTDATE varchar2(30);
alter table orderc modify CENDDATE varchar2(30);
```

```java
TO_DATE(#{cStartDate},'YYYYMMDD'), TO_DATE(#{cEndDate},'YYYYMMDD')
```

이건 안되더라 `literal does not match format string`

멤버 cCode 업데이트 로직:

```java
public int updateCcode(OrderCDTO dto) {
		SqlSession session = OracleSqlSessionFactory.getSession();
		MemberDAO dao = new MemberDAO();
		int result = 0;
		try {
			result = dao.updateCcode(session, dto);
			session.commit();
		}finally {
			session.close();
		}
		return result;
	}
```

```java
public int updateCcode(SqlSession session, OrderCDTO dto) {
		int result = session.update("updateCcode", dto);
		return result;
	}
```

```java
<update id="updateCcode" parameterType="OrderCDTO">
	update member
	set cCode = #{cCode}
	where userid = #{userId}
</update>
```

userid 정보로 해당멤버 cCode 업뎃

→ 근데 계속 업뎃될 수 없도록 cCode=0인 멤버만 수강신청할 수 있게 변경해야할듯
