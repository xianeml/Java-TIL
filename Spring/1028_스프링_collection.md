# 스프링 set 주입, util, collection map

그냥 빈 아이디 클래스하면 기본생성자 쓰는거였고

인자 있는 생성자는 constructor-args 태그 넣는거였지

그냥 찍어내는건 밸류사용하면되고 만들어진 객체 사용하는건 ref 쓰면 되는거였다

# 01Day8_getBean_scope

stu.xml

```jsx
<bean id="stu" class="com.dto.Student" scope="prototype" />
<bean id="stu2" class="com.dto.Student" scope="singleton" /> //하나의 객체만 쓰겠다
<bean id="scopeTest" class="com.dto.Student" />
```

bean 생성은 New 한다는거다

객체를 따로따로 만들지 않고 하나의 객체로 같이쓰게 하는게 싱글톤

싱글톤은 겟빈 할때마다 새로 만드는게 아니라 하나의 객체를 공유해서 쓰게된다.

첫번째 프로토타입은 겟빈할때마다 new를 새로 만들어줌. 새롭게 생성하게됨

```jsx
GenericXmlApplicationContext ctx =
				new GenericXmlApplicationContext("classpath:com/config/stu.xml");

		//scope = prototype
		Student stu1 = ctx.getBean("stu", Student.class);
		Student stu2 = ctx.getBean("stu", Student.class);
		System.out.println(stu1 == stu2 ); //false

		//scope = singleton
		Student stu3 = ctx.getBean("stu2", Student.class);
		Student stu4 = ctx.getBean("stu2", Student.class);
		System.out.println(stu3 == stu4); //true

		Student stu5 = ctx.getBean("scopeTest", Student.class);
		Student stu6 = ctx.getBean("scopeTest", Student.class);
		System.out.println(stu5 == stu6); //true
```

스코프 안적어준 마지막 객체가 둘다 같다고 나왔다는건

스코프를 지정하지 않을 경우 기본적으로 싱글톤 스코프를 사용한다는 뜻이다.

# 02Day2_setter_injection

```jsx
public class Student {

	// property (member랑 같은말)
	private String name;

	public Student() {
		System.out.println("기본생성자호출======");
	}  //기본생성자 명시적삽입

	public Student(String name) { //사용자 정의 생성자가 있으면 기본생성자 안넣어줌
		this.name = name;
	}

	public void setName(String name) {
		System.out.println("setName호출======");
		this.name = name;
	}

	public String getInfo() {
		return name;
	}

}
```

```jsx
GenericXmlApplicationContext ctx =
				new GenericXmlApplicationContext("stu.xml");

		Student stu = ctx.getBean("firstStudent", Student.class);
		System.out.println(stu.getInfo());
```

```jsx
<bean id="firstStudent" class="com.dto.Student">
  <property name="name" value="홍길동"></property>
</bean>
```

property 태그는 set 함수 호출하겠단 뜻

# 02Day2_setter_injection2

```jsx
public class Student {

	// property (member랑 같은말)
	private String name;
	private int age;

	//setter injection
	public void setAge(int age) {
		this.age = age;
	}

	public void setName(String name) {
		System.out.println("setName호출======");
		this.name = name;
	}

	public String getInfo() {
		return name + "\t" + age;
	}

}
```

```jsx
<bean id="firstStudent" class="com.dto.Student">
  <property name="name" value="홍길동"></property>
  <property name="age" value="20"></property>
</bean>
```

프로퍼티로 셋함수 호출해서 나이를 20으로 세팅해줬다.

# 02Day2_setter_injection3

```jsx
public class Student {

	// property (member랑 같은말)
	private String name;
	private int age;

	//setter injection
	public void setName(String name) {
		this.name = name;
	}

	//constructor injection
	public Student(int age) {
		this.age = age;
	}

	public String getInfo() {
		return name + "\t" + age;
	}

}
```

이번엔 set함수는 name에만 만들어주고 age는 생성자로 만들었음

그럼 xml Stu 객체에서는 property랑 constructor-arg 태그 둘다 필요하겠지

```jsx
<bean id="firstStudent" class="com.dto.Student">
  <property name="name" value="홍길동"></property>
  <constructor-arg name="age" value="20"></constructor-arg>
</bean>
```

# 02Day2_setter_injection4

cat dto 생성. 기본생성자 없고 셋함수만 있다

```jsx
public class Cat {
	private String catName;
	private int catAge;

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public void setCatAge(int catAge) {
		this.catAge = catAge;
	}

	public String getCatInfo() {
		return catName + "\t" + catAge;
	}
}
```

```jsx
<bean id="pet01" class="com.dto.Cat">
	<property name="catName" value="나비"></property>
	<property name="catAge" value="3"></property>
</bean>

<bean id="firstStudent" class="com.dto.Student">
	<property name="name" value="홍길동"></property>
	<property name="age" value="30"></property>
	<property name="cat" ref="pet01"></property>
</bean>
```

```jsx
GenericXmlApplicationContext ctx =
				new GenericXmlApplicationContext("stu.xml");

		Student stu = ctx.getBean("firstStudent", Student.class);
		System.out.println(stu.getInfo());
		Cat cat = ctx.getBean("pet01", Cat.class);
		System.out.println(cat.getCatInfo());
```

# 02Day2_setter_injection5_p

p 사용하기 위한 xml 네임스페이스 추가

<property>태그를 따로 안쓰고 bean 태그 안에 단축해서 사용할 수 있다.

```jsx
<bean id="firstStudent" class="com.dto.Student" p:name="홍길동" />
```

property 태그 안쓰고 bean 안에 간단하게 쓰기

# 02Day2_setter_injection5_p2

```jsx
<bean id="pet01" class="com.dto.Cat" p:catName="나비" p:catAge="2" />

<bean id="firstStudent" class="com.dto.Student" p:name="홍길동" p:age="20" p:cat-ref="pet01">
```

고양이 있던거 쭐이기

다른 bean 객체를 참조하던 cat도 name-ref 형식의 인라인으로 작성할 수 있다.

# Collection

---

- 컬렉션 원소가 객체인 경우에는 <ref> 태그를 이용한다.
- 컬렉션 원소가 기본타입인 경우에는 <value> 태그를 이용한다.

( type 속성을 이용한 타입지정 가능하며, 제네릭도 사용 가능하다. )

# 02Day3_collection_list <list>

```jsx
public class Student {

	// property (member랑 같은말)
	private String name;
	private int age;
	private List<Cat> listCat;

	//setter injection
	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Cat> getListCat() {
		return listCat;
	}

	public void setListCat(List<Cat> listCat) {
		this.listCat = listCat;
	}
```

```jsx
<!-- 고양이 bean 생성 -->
<bean id="pet01" class="com.dto.Cat" p:catName="나비" p:catAge="3" />
<bean id="pet02" class="com.dto.Cat" p:catName="하늘" p:catAge="2" />

<!-- 학생 bean 생성 -->
<bean id="firstStudent" class="com.dto.Student">
	<property name="name" value="홍길동" />
	<property name="age" value="20" />
	<property name="listCat">
		<list>
			<ref bean="pet01" />
			<ref bean="pet01" />
			<ref bean="pet02" />
			<ref bean="pet02" />
		</list>
	</property>
</bean>
```

리스트 만들기

setter함수에 있던 리스트를 property 태그로 가져옴, 프로퍼티 안에 <list>태그 사용. 각각의 리스트 요소들은 ref 태그로 다른 bean 객체를 참조하고 있음.

```jsx
GenericXmlApplicationContext ctx =
				new GenericXmlApplicationContext("stu.xml");

		Student stu = ctx.getBean("firstStudent", Student.class);
		System.out.println(stu.getInfo());
		List<Cat> list = stu.getListCat();
		for (Cat cat : list) {
			System.out.println(cat.getCatInfo());
		}
```

출력

리스트 출력하려면 일단 겟리스트캣 호출하고 리스트 캣 타입 변수에 담아서 Cat 타입 변수로 반복문.

getCatInfo 찍어주면 캣 정보를 볼 수 있다.

# 02Day3_collection_list2_util

```jsx
<!-- 고양이 bean 생성 -->
	<bean id="pet01" class="com.dto.Cat" p:catName="나비" p:catAge="3" />
	<bean id="pet02" class="com.dto.Cat" p:catName="하늘" p:catAge="2" />

	<!-- list 생성 -->
	<util:list id="petList">
		<ref bean="pet01" />
		<ref bean="pet01" />
		<ref bean="pet02" />
		<ref bean="pet02" />
	</util:list>

	<bean id="firstStudent" class="com.dto.Student">
		<property name="name" value="홍길동" />
		<property name="age" value="20" />
		<property name="listCat">
			<ref bean="petList"/>
		</property>
	</bean>
```

위에꺼를 좀더 간단하게 작성하기.

리스트를 외부에 만들어주고 리스트 캣 안에 ref로 불러와서 객체참조하고있음

독립형 컬렉션 구현 방법이다. 재사용 가능해짐.

# 02Day3_collection2_set

```jsx
<!-- 고양이 bean 생성 -->
	<bean id="pet01" class="com.dto.Cat" p:catName="나비" p:catAge="3" />
	<bean id="pet02" class="com.dto.Cat" p:catName="하늘" p:catAge="2" />

	<bean id="firstStudent" class="com.dto.Student">
		<property name="name" value="홍길동" />
		<property name="age" value="20" />
		<property name="setCat">
			<set>
				<ref bean="pet01" />
				<ref bean="pet01" />
				<ref bean="pet02" />
				<ref bean="pet02" />
			</set>
		</property>
	</bean>
```

```jsx
GenericXmlApplicationContext ctx =
				new GenericXmlApplicationContext("stu.xml");

		Student stu = ctx.getBean("firstStudent", Student.class);
		System.out.println(stu.getInfo());
		Set<Cat> set = stu.getSetCat();
		for (Cat cat : set) {
			System.out.println(cat.getCatInfo());
		}
```

리스트랑 모양 똑같음.

# 02Day3_collection2_set2_util

```jsx
<!-- 고양이 bean 생성 -->
	<bean id="pet01" class="com.dto.Cat" p:catName="나비" p:catAge="3" />
	<bean id="pet02" class="com.dto.Cat" p:catName="하늘" p:catAge="2" />

	<!-- set 생성 -->
	<util:set id="petSet">
		<ref bean="pet01" />
		<ref bean="pet01" />
		<ref bean="pet02" />
		<ref bean="pet02" />
	</util:set>

	<bean id="firstStudent" class="com.dto.Student">
		<property name="name" value="홍길동" />
		<property name="age" value="20" />
		<property name="setCat">
			<ref bean="petSet"/>
		</property>
	</bean>
```

이것도 util 사용해서 밖에 빼줄수 있다.

# 02Day3_collection3_map

key는 스트링 val은 cat

```jsx
public class Student {

	// property (member랑 같은말)
	private String name;
	private int age;
	private Map<String, Cat> mapCat;

	//setter injection
	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Map<String, Cat> getMapCat() {
		return mapCat;
	}

	public void setMapCat(Map<String, Cat> mapCat) {
		this.mapCat = mapCat;
	}

	public String getInfo() {
		return name + "\t" + age;
	}
```

```jsx
<!-- 고양이 bean 생성 -->
	<bean id="pet01" class="com.dto.Cat" p:catName="나비" p:catAge="3" />
	<bean id="pet02" class="com.dto.Cat" p:catName="하늘" p:catAge="2" />

	<bean id="firstStudent" class="com.dto.Student">
		<property name="name" value="홍길동" />
		<property name="age" value="20" />
		<property name="mapCat">
			<map>
				<entry key="one" value-ref="pet01" />
				<entry key="two">
					<ref bean="pet02"/>
				</entry>
			</map>
		</property>
	</bean>
```

<map> 태그는 키를 <entry> 태그로 만들어주고 밸류값 참조할 땐 value-ref나 ref 태그를 만들어서 사용한다.

```jsx
GenericXmlApplicationContext ctx =
				new GenericXmlApplicationContext("stu.xml");

		Student stu = ctx.getBean("firstStudent", Student.class);
		System.out.println(stu.getInfo());

		Map<String, Cat> map = stu.getMapCat();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key + "=" + map.get(key).getCatInfo());
			//key 하나를 이용하여 value 얻은 후 value 객체의 함수 호출
		}
```

맵 꺼내오기

getMapCat 호출해서 결과를 맵에 담아주고 map.keySet으로 셋타입으로 바꾼뒤 출력한다.

key값들이 지금 다른 bean 객체 참조하고 있으니까 그 키값의 객체의 캣인포를 출력해준다.

# 02Day3_collection3_map2

```jsx
<!-- 고양이 bean 생성 -->
	<bean id="pet01" class="com.dto.Cat" p:catName="나비" p:catAge="3" />
	<bean id="pet02" class="com.dto.Cat" p:catName="하늘" p:catAge="2" />

	<!-- Map 생성 -->
	<util:map id="mapPet">
		<entry key="one" value-ref="pet01" />
		<entry key="two">
			<ref bean="pet02"/>
		</entry>
	</util:map>


	<bean id="firstStudent" class="com.dto.Student">
		<property name="name" value="홍길동" />
		<property name="age" value="20" />
		<property name="mapCat">
			<ref bean="mapPet"/>
		</property>
	</bean>
```

```jsx
GenericXmlApplicationContext ctx =
				new GenericXmlApplicationContext("stu.xml");

		Student stu = ctx.getBean("firstStudent", Student.class);
		System.out.println(stu.getInfo());

		Map<String, Cat> map = stu.getMapCat();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key + "=" + map.get(key).getCatInfo());
			//key 하나를 이용하여 value 얻은 후 value 객체의 함수 호출
		}
```

util 사용해서 맵을 밖으로 꺼내주기
