# 어노테이션이란?

클래스에 대한 메타데이터(라벨을 붙인다)

컴파일 타임, 런타임에 처리된다.

컴파일 타임에는 실제로 그게 라벨대로 처리되었는지 검사함

# 스프링 설정을 왜 어노테이션으로 할까?

- xml 설정은 장황하다 → 어노테이션은 xml 설정을 최소화할 수 있다

# 컴포넌트 클래스 스캔

- 스프링은 특정 어노테이션 달린 클래스를 스캔하게 된다
- 그래서 자동으로 빈을 스프링 컨테이너에 등록해준다.

# 개발과정

1.  컴포넌트 스캔을 스프링 configuration 파일에 활성화시킴
    - 스캔을 원하는 베이스 패키지만 정해주면 자동으로 탐색
    - `<context:component-scan base-package="com.service" />`
2.  클래스에 @Component 어노테이션 추가
    - `@Component("Bean 아이디")`
3.  스프링 컨테이너에서 빈 검색
    - `context.getBean("Bean 아이디", 클래스.class);`

# Default Bean ID

컴포넌트 어노테이션에 아이디를 따로 지정해주지 않을 경우,

스프링이 자동으로 만들어주는 빈 아이디

클래스 이름에서 첫글자를 소문자로 따온다.

`TennisCoach` → `tennisCoach`

\*특이케이스: 첫번째 글자, 두번째 글자 모두 대문자면 자동변환 규칙이 적용안됨.

`RESTFul` → `RESTFul`

# 어노테이션 의존성 주입 - 오토와이어링

- 스프링은 자동으로 필요한 의존 객체를 연결해준다.
- 프로퍼티에 매치되는 클래스를 찾는다.
- 찾으면 자동주입

# 오토와이어링 주입 타입

- 생성자 주입
- setter 주입
- 필드 주입

이 스타일 중 하나를 정해서 프로젝트 안에서 하나의 스타일을 유지할 것.

# 생성자주입

1.  의존성 클래스나 인터페이스를 정의한다
2.  클래스에 주입을 위한 생성자를 만든다.
3.  생성자에 `@Autowired` 어노테이션으로 의존성을 설정한다.

# setter 주입

1.  setter 메소드를 클래스에 생성한다.
2.  setter메소드에 `@Autowired` 어노테이션으로 의존성을 설정한다.

# 메소드 주입

setter 메소드 대신 어떤 메소드 다 사용 가능.

# 필드 주입(멤버 주입)

```java
@Autowired
private DeptDAO dao;
```

1.  클래스 멤버에 오토와이어 어노테이션으로 의존성 주입 바로 적용(private 변수일지라도 가능)
2.  setter 메소드 필요없음

# @Qualifier

오토와이어가 여러개일때 자동주입 제대로 받는 방법

```java
@Autowired
@Qualifier("Bean 아이디")
private DeptDAO dao;
```

사용하고 싶은 빈 아이디를 정해준다.

모든 주입 타입에 사용 가능

# 어노테이션 Bean Scopes

클래스에 어노테이션 붙이기

```java
@Component
@Scope("singleton")
public class TennisCoach {
```

`@Scope("prototype")`

# 라이프사이클 메소드

`@PostConstruct` : 이 이닛 메소드는 생성자가 실행된 뒤, 의존성 주입이 완료된 후 실행될 것이다.

`@PreDestroy` : 이 디스트로이 메소드는 빈 객체가 사라질 때 실행될 것이다.

\*주의할점: 이 어노테이션 붙는 메소드는 인자를 가질 수 없다.

\*prototype scope일 경우: 스프링은 `@PostConstruct` 을 호출하지 않는다.
