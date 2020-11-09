1. 리트리브 폼으로 감싸서 신청하기 클릭하면 CourseOrderServlet으로 이동

   ```java
   // 코스 dto 정보 세션 저장
   	session.setAttribute("courseDTO", courseDTO);
   ```

   리트리브 서블릿에서 넘어온 강의정보2개 담긴 리스트를 반복문 돌려서 조건에 해당하는 하나의 강의 정보를 courseDTO 변수에 저장했었다. 그걸 그대로 세션에 담아주면 된다.

   ```java
   <form action="CourseOrderServlet" method="post">
   ```

2. 코스오더 서블릿

   세션 로그인 확인→ 다음페이지 지정

   ```java
   HttpSession session = request.getSession();
   MemberDTO memberDTO = (MemberDTO)session.getAttribute("login");
   System.out.println(memberDTO);
   String nextPage = null;
   if(memberDTO!=null){
   	nextPage = "courseOrder.jsp";
   }else {
   	nextPage = "LoginUIServlet";
   	session.setAttribute("mesg", "로그인이 필요한 작업입니다.");
   }
   response.sendRedirect(nextPage);
   ```

   UI서블릿은 이름 주의

3. 오더 코스.jsp 필요한 정보 세션 꺼내서 출력

   ```java
   <%
   	CourseDTO courseDTO = (CourseDTO)session.getAttribute("courseDTO");
   	System.out.println(courseDTO);
   	String cImage = courseDTO.getcImage();
   	String cName = courseDTO.getcName();
   	String cStartDate = courseDTO.getcStartDate();
   	String cEndDate = courseDTO.getcEndDate();
   	int cTotalDate = courseDTO.getcTotalDate();
   	int cPrice = courseDTO.getcPrice();

   	MemberDTO memberDTO = (MemberDTO)session.getAttribute("login");
   	String userName = memberDTO.getUserName();
   	String phoneNum = memberDTO.getPhoneNum();
   	String email = memberDTO.getEmail1() +"@" + memberDTO.getEmail2();
   %>
   ```

   ```java
   <h3>강의정보 확인</h3>
   <table border="1" id="courseTable">
   	<thead>
   		<tr>
   			<th>사진</th>
   			<th>강의명</th>
   			<th>기간</th>
   			<th>가격</th>
   		</tr>
   	</thead>
   	<tbody>
   		<tr>
   			<th><img src="./img/<%= cImage %>" width="200px"></th>
   			<th><%=cName %></th>
   			<th><%=cTotalDate %>일</th>
   			<th><%=cPrice %>원</th>
   		</tr>
   	</tbody>
   </table>
   ```

   ```java
   <h3>고객정보 확인</h3><br>
   이름: <%=userName %> <br>
   연락처: <%= phoneNum %> <br>
   이메일: <%=email %> <br>

   <h3>결제수단</h3>
   <div>
   	<input type="radio" id="creditCard" name="payMethod" checked>신용카드<br>
   	<input type="radio" id="toss"  name="payMethod">계좌이체<br>
   	<input type="radio" id="bank"  name="payMethod">무통장입금<br>
   </div>
   <input type="submit" value="결제하기">
   <input type="button" value="취소">
   ```

   라디오는 같은 name으로 묶어줘야 한다. 밸류값은 밖에 써주기.

cstartdate enddate 형식 수정하면 좋을거같다. 타임까지 들어가있음
