# 05_Day5_Spring_tx_annotation

```jsx
public class OrderDTO {
	int num;
	String pcode;
	int quantity;
```

```jsx
public class ProductDTO {
	String pcode;
	String pname;
	int price;
	int quantity;
```

### TestDAO.java:

```jsx
public class TestDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
```

dataSource 사용하는 코드는 빠졌다. xml에서 만들어줄 것이다

생성자 빠졌으니 프로퍼티 써서 넣어주게 된다.

```jsx
public List<ProductDTO> selectProduct() {
		String query = "select * from t_product order by pcode";
		return jdbcTemplate.query(query, new RowMapper<ProductDTO>() {

			public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductDTO dto = new ProductDTO();
				dto.setPcode(rs.getString("pcode"));
				dto.setPname(rs.getString("pname"));
				dto.setPrice(rs.getInt("price"));
				dto.setQuantity(rs.getInt("quantity"));
				return dto;
			}
		});
	}
```

### ProductService.java:

```jsx
public class ProductService {

	ProductDAO dao;

	public ProductService() {
		super();
	}

	public ProductDAO getDao() {
		return dao;
	}

	public void setDao(ProductDAO dao) {
		this.dao = dao;
	}

	public List<ProductDTO> selectProduct(){
		return dao.selectProduct();
	}
}
```

메인은 이 서비스를 쓸 건데 서비스는 dao 사용한다. 여기서 직접 생성하지 않고 setDAO 메소드를 이용해 주입받아서 사용할 것.

1. 프로퍼티스 읽어오기
2. 그걸로 데이터소스 만들기
3. jdbc템플릿만들기
4. 그거 dao에 넣기
5. 서비스에 넣기
6. 메인에서 getbean으로 호출

### dept.xml:

```jsx
<context:annotation-config></context:annotation-config>

<context:property-placeholder location="classpath:com/config/db.properties" />
<bean id="myDataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
	<property name="driverClassName" value="${jdbc.driver}"></property>
	<property name="url" value="${jdbc.url}"></property>
	<property name="username" value="${jdbc.userid}"></property>
	<property name="password" value="${jdbc.passwd}"></property>
</bean>

<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	<property name="dataSource" ref="myDataSource"></property>
</bean>

<bean id="DeptDAO" class="com.dao.ProductDAO">
	<property name="jdbcTemplate" ref="jdbcTemplate"></property>
</bean>

<bean id="service" class="com.service.ProductService">
	<property name="dao" ref="DeptDAO"></property>
</bean>
```

main:

```jsx
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:com/config/dept.xml");
		ProductService service = ctx.getBean("service", ProductService.class);
		System.out.println(service.selectProduct());
```

# 주문정보 select

ProductDAO.java:

```jsx
public List<OrderDTO> selectOrder() {
		String query = "select * from t_order order by pcode";
		return jdbcTemplate.query(query, new RowMapper<OrderDTO>() {
			public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderDTO dto = new OrderDTO();
				dto.setNum(rs.getInt("num"));
				dto.setPcode(rs.getString("pcode"));
				dto.setQuantity(rs.getInt("quantity"));
				return dto;
			}
		});
	}
```

ProductService.java:

```jsx
public List<OrderDTO> selectOrder() {
		return dao.selectOrder();
	}
```

# 주문정보 업데이트

### ProductService.java:

```jsx
@Transactional
	public void addOrder(String pcode,int quantity) throws Exception {
		dao.addOrder(pcode, quantity);
	}
```

dao의 addOrder 작업을 트랜잭션으로 묶음

### ProductDAO.java:

```jsx
public void addOrder(String pcode, int quantity) throws Exception {
		String sql1 = "update t_product set quantity = quantity - ? where pcode = ?";
		int n = jdbcTemplate.update(sql1, quantity, pcode);
		System.out.println("update t_product=============" + n);
		String sql2 = "in into t_order (num, pcode, quantity) values (t_order_seq.nextval, ?,?)";
		int n2 = jdbcTemplate.update(sql2, quantity, pcode);
		System.out.println("update t_product=============" + n2);
	}
```

제품이 5개 팔리면 업데이트 해주고. 주문정보도 업데이트. 쿼리가 2개

두번째로 주문테이블에 이 주문정보가 들어가야한다. sql2 쿼리문은 일부러 틀리게 넣음. 반영 안되고 롤백이 되도록. 트랜잭션 처리 코드는 안들어가있는데 아까 서비스에서 어노테이션 썼던걸로 함수 통째로 트랜잭션 묶어줌.

sql1: 상품테이블에 있는 하나의 상품 총수량에서 받아온 퀀티티값을 빼준다.

sql2:주문테이블에 상품코드만 insert하는거다.

jdbc템플릿 사용해서 쿼리문실행할 땐 첫번째 인자에 쿼리문, 나머지 인자엔 받아올 파라미터를 적는다.

### dept.xml:

```jsx
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="dataSource"></property>
</bean>
<tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="false"/>
```

트랜잭션 어노테이션을 <`tx:annotation-driven>` 얘가 관리한다.

### TestMain.java:

```jsx
System.out.println("상품 5개 주문~!");
		try {
			service.addOrder("p01", 5);
		} catch (Exception e) {
			System.out.println("에러발생 후 롤백처리");

		}
```

상품 5개 주문하기. trycatch로 에러잡기

# 스프링 설정문서

오라클 드라이버, 데이터소스 사용위한 dbcp2

```jsx
<!-- dependncy 추가 부분 -->
		<!-- https://mvnrepository.com/artifact/com.jslsolucoes/ojdbc6 -->
		<dependency>
			<groupId>com.jslsolucoes</groupId>
			<artifactId>ojdbc6</artifactId>
			<version>11.2.0.1.0</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>3.1.4.RELEASE</version>
		</dependency>

		<!-- 아파치 제공 커넥션 연결, 관리해주는 DataSource -->
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-dbcp2</artifactId>
			<version>2.1</version>
		</dependency>
```

xe용 오라클 디펜던시:

```jsx
<dependency>
  <groupId>org.testcontainers</groupId>
  <artifactId>oracle-xe</artifactId>
  <version>1.11.2</version>
</dependency>
```

에스펙트 사용 위한 aop 디펜던시:

```xml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
</dependency>
```

트랜잭션:

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-tx -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>4.3.20.RELEASE</version>
</dependency>
```

마이바티스:

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.6</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>1.3.2</version>
</dependency>
```

모아보기:

```xml
<!-- https://mvnrepository.com/artifact/com.jslsolucoes/ojdbc6 -->
		<dependency>
			<groupId>org.testcontainers</groupId>
			<artifactId>oracle-xe</artifactId>
			<version>1.11.2</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>3.1.4.RELEASE</version>
		</dependency>

		<!-- 아파치 제공 커넥션 연결, 관리해주는 DataSource -->
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-dbcp2</artifactId>
			<version>2.1</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
		<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjweaver</artifactId>
			<version>1.9.4</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.4.6</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.3.2</version>
		</dependency>
```

xml:

```xml
<context:annotation-config />

<context:property-placeholder location="classpath:com/config/db.properties" />

<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
	<property name="driverClassName" value="${jdbc.driver}"></property>
	<property name="url" value="${jdbc.url}"></property>
	<property name="username" value="${jdbc.userid}"></property>
	<property name="password" value="${jdbc.passwd}"></property>
</bean>

<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	<property name="dataSource" ref="dataSource"></property>
</bean>
```

```xml
<tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="false"/>
```

```xml
<context:component-scan base-package="com.service" />
```

이 설정 바탕으로 **마이바티스** 시작할 것

# 05Day2_Mybatis

MySqlSessionFactory 클래스 생각해보면 여기서 만들어놓은 세션을 우리가 사용하기만함

이걸 지금 똑같이 적용. 세션팩토리에서 세션 만들어서 계속 사용할거임

```java
oracle.jdbc=oracle.jdbc.driver.OracleDriver
oracle.url=jdbc:oracle:thin:@localhost:59162:xe
oracle.userid = scott
oracle.passwd = tiger
```

변수만 다르게했을 뿐 원래 쓰던거 사용해도 된다.

```java
@Alias("Dept") //맵퍼에서 사용하는 모델클래스의 alias를 어노테이션으로 설정
public class DeptDTO {

	private int deptno;
	private String dname;
	private String loc;
```

```java
public class DeptService {

	@Autowired
	DeptDAO dao;

	public List<DeptDTO> selectAll(){
		return dao.selectAll();
	}
}
```

```java
public class DeptDAO {

	@Autowired
	SqlSessionTemplate template;

	public List<DeptDTO> selectAll() {
		return template.selectList("DeptMapper.selectAll");
	}

}
```

DeptMapper.xml:

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="DeptMapper">
   <select id="selectAll" resultType="Dept">
      select deptno, dname, loc
      from dept
      order by deptno
   </select>
</mapper>
```

원래 쇼핑몰에서 쓰던 형식 가져옴. dept 테이블 전체 정보 셀렉트

```java
<context:annotation-config></context:annotation-config>
<context:component-scan base-package="com."></context:component-scan>

<context:property-placeholder location="classpath:com/config/jdbc.properties"/>
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
	<property name="driverClassName" value="${oracle.jdbc}"></property>
	<property name="url" value="${oracle.url}"></property>
	<property name="username" value="${oracle.userid}"></property>
	<property name="password" value="${oracle.passwd}"></property>
</bean>

<bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
	<property name="dataSource" ref="dataSource"></property>
	<property name="mapperLocations">
		<list>
			<value>classpath:com/config/DeptMapper.xml</value>
		</list>
	</property>
	<property name="typeAliases">
		<list>
			<value>com.dto.DeptDTO</value> <!-- 실제 alias는 각 클래스에서 어노테이션 지정 -->
		</list>
	</property>
	</bean>

	<bean id="sessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg name="sqlSessionFactory" ref="sessionFactory" />
	</bean>

	<bean id="dao" class="com.dao.DeptDAO"></bean>
	<bean id="service" class="com.service.DeptService"></bean>
```

```java
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:com/config/dept.xml");
		DeptService service = ctx.getBean("service", DeptService.class);
		System.out.println(service.selectAll());
```
