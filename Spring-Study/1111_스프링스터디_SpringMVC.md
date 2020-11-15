# 스프링 MVC란?

- 자바로 웹 어플리케이션을 빌딩할 수 있는 프레임워크
- Model-View-Controller 디자인패턴을 따른다.
- 코어 스프링 프레임워크 기능(IoC, DI) 활용

공식문서 :

[Web on Servlet Stack](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)

# 스프링 MVC 장점

- 스프링 방식으로 웹앱 UI를 만들 수 있다
- 재사용가능한 UI 컴포넌트 활용
- 웹 요청을 위한 앱 상태관리 (세션트래킹 등)
- 폼 데이터 처리 (유효성, 변환 등)
- 뷰 계층을 위한 유연한 config설정 (jsp뿐만 아니라 다양한 기능 사용가능)

# 스프링 MVC 앱의 컴포넌트란?

- 웹페이지의 UI 컴포넌트 컬렉션
- 스프링 beans의 컬렉션이다(컨트롤러, 서비스..)
- 컨피그설정(xml, 어노테이션, 퓨어자바)

# Model-View-Controller

![%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20MVC%20481009e26b504641a15190ff0fb0e469/Untitled.png](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20MVC%20481009e26b504641a15190ff0fb0e469/Untitled.png)

웹브라우저 → 프론트 컨트롤러로 요청

### 프론트 컨트롤러

- DispatcherServlet이 담당. 스프링 빌트인
- 다른 객체나 요소에게 요청을 위임한다

### 컨트롤러

- 개발자가 만드는 코드
- 비즈니스 로직을 포함한다: 요청핸들, 데이터 저장과 검색(db), 데이터 모델
- 적절한 뷰 템플릿 보내기

### 모델

- 데이터 컨테이너
- 데이터를 백엔드 시스템을 통해 저장하고 검색(db,웹서비스..), 원한다면 스프링 빈 사용

### 뷰 템플릿

- jsp+jstl 흔하게 사용함
- 데이터를 보여줌

# ViewResolver 설정:

```java
<bean
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/" />
		<property name="suffix" value=".jsp" />
	</bean>
```

- 접두사를 붙이다
- 접미사를 붙이다

# mvc프로젝트 개발과정

1.  컨트롤러 클래스 생성
    - `@Controller`: @Component를 상속하고 있어서 컴포넌트 스캔이 얘까지 해줌.
2.  컨트롤러 메소드 정의
3.  컨트롤러 메소드에 요청주소 매핑 추가
    - `@RequestMapping("/")`
4.  뷰네임 리턴
    - `return "main";`
5.  뷰페이지 개발

# 개발 과정

1.  컨트롤러 클래스 생성
2.  html 뷰 보여주기 위한 컨트롤러 메소드 생성. → 뷰페이지 만들기
3.  폼 데이터 처리를 위한 컨트롤러 메소드 생성 → 데이터 갖고와서 찍어주는 뷰페이지 만들기

    `${params.stuName}` → stuName은 폼 인풋필드 name

# Model

- 모델은 앱 데이터의 컨테이너다
- 컨트롤러에서 아무거나 모델에 넣을 수 있다.
- 뷰페이지 jsp는 모델에 접근할 수 있다.

# 모델을 컨트롤러 인자에 패싱

폼데이터를 읽고싶을때 HttpServletRequest 패싱, 폼데이터 담는 컨테이너 용도의 Model

```java
@RequestMapping("/processForm")
public String processForm(HttpServletRequest req, Model model){
	String name = request.getParameter("name");
	name = name.toUpperCase();
	String result = 'Hi! " + name;
	model.addAttribute("mesg", result);
	return "hello";
}
```

모델에 데이터를 담았다.

hello.jsp에서는 model에 담긴 데이터에 접근하기 위해 `${ mesg }`를 사용한다.

# 요청 파라미터 바인딩

위에꺼처럼 request.getParameter() 파싱할 필요 없이 `@RequestParam("name") String name` 사용해서 인자에 패싱 가능

요청으로 넘어온 파라미터를 String name 변수에 바인딩해준다.

# 컨트롤러에 요청주소 매핑 추가하기

- 부모 매핑주소가 있고 그 안에 나머지애들은 다 연관성 있음. 폴더 디렉토리 구조같음
- 클래스 안에 각 메소드마다 매핑주소가 있는 모양

# 스프링 MVC 폼 태그

- 데이터 바인딩 제공: 자동으로 데이터를 자바 오브젝트나 빈으로부터 검색하고 세팅한다.
- html을 만들어준다.

- jsp페이지 안에 html 구조 안에 이 스프링 mvc 폼 태그를 사용할 수 있다.

```java
<%@ taglib prefix="form" uri="자동완성하기" %>
```

# 폼 보여주기

- 보여주기 전에 컨트롤러에서 모델 attribute를 추가해야한다: 데이터 바인딩을 위함

  (`@ModelAttribute` Stuent the Student) 인자에 패싱 (Student dto 클래스도 먼저 만들어줘야함. 그래야 get 해서 쓸 수 있다.

- `theStudent.getLastName()` 이렇게 패스를 get 해서 폼데이터를 꺼내올 수 있다.
- 확인페이지: `${student.firstName}` 속성이름과 패스명으로 데이터에 접근할 수 있다.
