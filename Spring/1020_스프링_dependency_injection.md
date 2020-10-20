# 스프링

날짜: Oct 19, 2020
블로깅: No

### 01Day8_getBean

```jsx
Student stu = ctx.getBean("stu1",Student.class);
		System.out.println(stu);
```

xml 에 지정했던 아이디로 만든 빈불러오는거임 스튜던트 객체를 얻어와라.

출력결과: `Student [name=null, age=0]`

아이디를 잘못적으면 이런 에러가

`NoSuchBeanDefinitionException: No bean named 'stu1' available`

```jsx
Student stu3 = ctx.getBean("stu1", Student.class);
System.out.println(stu == stu3);  //True
```

얘네는 같은 아이디 쓰고있는 같은 객체라서 찍어보면 둘이 같다고 나옴.

stu.xml:

```jsx
<bean id="stu1" class="com.dto.Student" />
<bean id="stu2" class="com.dto.Student"></bean>
```

### 02Day_constructor_injection

com.dto.Student:

```jsx
private String name;
	//public Student(){} // 사용자가 생성자 작성시 기본생성자가 사라짐.
	public Student(String x) { //생성자를 작성
		System.out.println("인자있는 생성자");
		this.name = x;
	}

	public String getInfo() {
		return name;
	}
```

stu.xml:

```jsx
<bean id="firstStudent" class="com.dto.Student">
  <constructor-arg name="x" value="홍길동" />
</bean>
```

생성자를 호출해주는 태그

저 네임은 어디서 왔느냐. 생성자에 있는 인자에서 왔다.

생성자를 통해서 bean을 초기화시켜줌

TestStudent:

```jsx
Student test = ctx.getBean("firstStudent", Student.class);
System.out.println(test.getInfo());
```

홍길동이 잘 찍힘

### 02Day_constructor_injection2

```jsx
package com.dto;

public class Student {

	private String name;
	private int age;

	public Student() {}

	public Student(String name) { //생성자를 작성
		this.name = name;
		System.out.println("1");
	}

	public Student(int age) { //생성자를 작성
		this.age = age;
		System.out.println("2");
	}

	public Student(String name, int age) { //생성자를 작성
		this.name = name;
		this.age = age;
		System.out.println("3");
	}

	public String getInfo() {
		return name+"\t"+age;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
	}
}
```

여러개의 생성자를 만들었다. 네임만 들어간거, 나이만 들어간거, 둘다들어간거

stu.xml:

```jsx
<bean id="firstStudent" class="com.dto.Student">
	<constructor-arg name="name" value="홍길동" />
</bean>
<bean id="secondStudent" class="com.dto.Student">
	<constructor-arg name="age" value="20" />
</bean>
<bean id="thirdStudent" class="com.dto.Student">
	<constructor-arg name="name" value="유노윤호" />
	<constructor-arg name="age" value="20" />
</bean>
```

나이 스트링으로 써놔도 int로 자동 형변환되어 들어감. beans 객체 3개 만들었다. 생성자 인자 2개 받는건 저 태그도 2개들어갔음

TestStudent.java:

```jsx
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("stu.xml");

Student test = ctx.getBean("firstStudent", Student.class);
System.out.println(test.getInfo());

Student test2 = ctx.getBean("secondStudent", Student.class);
System.out.println(test2.getInfo());

Student test3 = ctx.getBean("thirdStudent", Student.class);
System.out.println(test3.getInfo());

System.out.println(test+"\t"+test2+"\t"+test3);
```

### 02Day_constructor_injection3

```jsx
package com.dto;

public class Cat {
	private String catName;
	private int catAge;

	public Cat(String catName, int catAge) {
		this.catName = catName;
		this.catAge = catAge;
	}

	public String getCatInfo() {
		return catName+"\t"+catAge;
	}
}
```

고양이 객체 생성

```jsx
public class Student {

	private String name;
	private int age;
	private Cat cat;

	public Student() {}

	public Student(String name, int age, Cat cat) { //생성자를 작성
		this.name = name;
		this.age = age;
		this.cat = cat;
	}
```

학생은 고양이도 한마리 갖고있는거다

```jsx
<bean id="pet01" class="com.dto.Cat">
	<constructor-arg name="catAge" value="3" />
	<constructor-arg name="catName" value="나비" />
</bean>

<bean id="firstStudent" class="com.dto.Student">
	<constructor-arg name="name" value="홍길동" />
	<constructor-arg name="age" value="30"></constructor-arg>
	<constructor-arg name="cat" ref="pet01"></constructor-arg>
</bean>
```

cat은 레퍼런스. 위에서만든 캣 빈즈 객체 사용.

```jsx
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("stu.xml");

Student stu = ctx.getBean("firstStudent", Student.class);
System.out.println(stu.getInfo());

Cat cat = ctx.getBean("pet01", Cat.class);
System.out.println(cat.getCatInfo());

Cat cat1 = stu.getCat();
System.out.println(cat1.getCatInfo());
```

# 꼭 이해!

### 02Day_constructor_injection4

dao는 생성자가 따로 없으니까 기본생성자만 갖고있는거다

DeptDAO.java:

```jsx
public class DeptDAO {
	public List<String> list(){
		return Arrays.asList("홍길동", "이순신", "유관순");
	}
}
```

DeptService.java:

```jsx
public class DeptService {
	DeptDAO dao; //new가 사라지고 .xml에서 주입

	public DeptService(DeptDAO dao) {
		this.dao = dao;
	}
	public List<String> list(){
		return dao.list();
	}
}
```

stu.xml:

```jsx
<bean id="deptdao" class="com.dao.DeptDAO"></bean>
<bean id="service" class="com.dao.DeptService">
	<constructor-arg name="dao" ref="deptdao"></constructor-arg>
</bean>
```

main:

```jsx
DeptService service = ctx.getBean("service", DeptService.class);
		List<String> list = service.list();
		for (String x : list) {
			System.out.println(x);
		}
```

new를 안해 xml로 처리해

메인에ㅓㅅ 제일먼저 xml을 읽어온다

dao 개겣 생성하고고 그다음 service 객체를 생성하는데 매개변수1개짜리 생성자를 생서하고있다

레퍼런스로 deptdao 빈즈 객체의 아이디 값을 넘겨주고있다/

서비스로 가면 생성자에 인자로 dao를 받고있다.서비스는 dao를 갖고있는 객체가된거야

### 02Day_constructor_injection_pr2

이거는 person 객체로 2명 생성해서 해보고

### 02Day_constructor_injection_pr3

이거는 person 객체에 cat 까지 가져와서 해보기
