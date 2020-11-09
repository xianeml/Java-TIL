# 강의 신청하기 버튼 문서정리

강의 주문 페이지로 넘어가기 위해 조건이 필요하다.

- [진행예정] 상태의 강의일 것.
- [정원]이 남아있을 것.

1. 먼저 cCode가 5,6번인 진행예정 강의인 조건을 만족해야 한다.
2. 코스의 정원과 회원가입시 진행예정 강의를 선택한 인원수를 비교해서 정원이 남아있다면 신청하기 버튼을 띄운다.

```java
int cStudNum = courseDTO.getcStudNum();
```

리트리브에 CourseDTO 파싱한게 있어서 학생정원수 그대로 파싱

이제 멤버테이블에서 진행예정 강의 선택자 수를 가져와야한다.

3. db에 cCode 가져가서 멤버에서 해당 cCode를 선택한 레코드 갯수 가져오기

```java
int memberCNum = service.selectMCNum(cCode);
System.out.println("해당 강의듣는 인원수"+memberCNum);
System.out.println("cCode: " + cCode);
```

```java
public int selectMCNum(int cCode) {
		SqlSession session = OracleSqlSessionFactory.getSession();
		CourseDAO dao = new CourseDAO();
		int result = 0;
		try {
			result = dao.selectMCNum(session, cCode);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return result;

	}
```

```java
public int selectMCNum(SqlSession session, int cCode) {
		int result = session.selectOne("selectMCNum", cCode);
		return result;
	}
```

```java
<select id="selectMCNum" parameterType="int" resultType="int">
		select count(*)
		from member
		where cCode = #{cCode}
	</select>
```

```java
request.setAttribute("memberCNum", memberCNum);
```

마지막으로 가져온 거 리퀘스트에 저장.

```java
int memberCNum = (int)request.getAttribute("memberCNum");
```

리트리브.jsp에서 파싱.

```java
<%
if(cCode==5 || cCode ==6) {
	if(cStudNum > memberCNum) {
%>
	<input type="submit" value="신청하기">
<%
	}
}
%>
```

두 숫자 비교해서 조건에 부합하면 신청하기 버튼 띄워주기
