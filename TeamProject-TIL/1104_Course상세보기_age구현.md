# 프로젝트 TIL

Course 상세보기 - 강의별 수강연령대 통계 구현

### 1. 디비에 나이대별로 데이터 여러개 넣기 20~40 (5단위)

### 2. 리트리브에서 cCode 히든으로 넘기기

```java
<input type="hidden" name="cCode" value="<%=cCode %>" >
```

**그 강의를 수강하는** 회원들의 age 정보를 파악해야 하니까 디비에 **cCode** 넘겨주기

### 3. 서블릿에서 int로 형변환 파싱

```java
//age 정보 가져오기 위해 cCode 파싱
Integer cCode = Integer.parseInt(request.getParameter("cCode"));
```

### 4. age 레코드만 셀렉해서 리스트에 저장

```java
CourseService service = new CourseService();
List<Integer> ageList = service.ageList(cCode);
```

List<Integer> 제너릭 필수

```java
public List<Integer> ageList(Integer cCode) {
		SqlSession session = OracleSqlSessionFactory.getSession();
		CourseDAO dao = new CourseDAO();
		List<Integer> list = null;
		try {
			list = dao.ageList(session, cCode);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return list;
	}
```

```java
public List<Integer> ageList(SqlSession session, Integer cCode) {
		List<Integer> list = session.selectList("ageList", cCode);
		return list;
	}
```

```java
<select id="ageList" parameterType="int" resultType="int">
		select age
		from member
		where cCode = #{cCode}
	</select>
```

셀렉한 내용을 result type=list로 넣어서 보내는건 안되나?

### 5. 리퀘스트 저장

```java
request.setAttribute("ageList", ageList);
```

리퀘저장 후 디스패쳐로 jsp에 보냄

### 6. 리퀘스트 파싱

```java
// request에서 ageList 받아오기
	List<Integer> ageList = (List<Integer>)request.getAttribute("ageList");
```

### 7. 변수 만들고 반복문으로 리스트 분해. 구간별로 요소 갯수만큼 ++증가.

```java
int age1 = 0;
int age2 = 0;
int age3 = 0;
int age4 = 0;
```

```java
for (Integer age : ageList){
		if(age >= 20 && age <=24) {
			age1++;
		}
		if(age >=25 && age <= 29 ) {
			age2++;
		}
		if(age >= 30 && age <= 34) {
			age3++;
		}
		if(age >= 35 && age <=40) {
			age4++;
		}
	}
```

### 8. 화면에 출력

```java
연령대:<br>
20~25: <%= age1 %>
25~30: <%= age2 %>
30~35: <%= age3 %>
34~40: <%= age4 %>
```
