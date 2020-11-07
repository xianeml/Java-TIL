# 08Day_MVC2_RequestMapping

1.

```jsx
@RequestMapping(value = "/aaa", method = RequestMethod.GET)
	public String main() {
		System.out.println("aaa 호출");
		return "main";
	}
```

2.

```jsx
@RequestMapping(value = { "/aaa2", "/bbb2" }, method = RequestMethod.GET)
	public String aaa2() {
		System.out.println("aaa2 호출");
		return "main";
	}
```

매핑값이 여러개일 수 있다.

3.

```jsx
@RequestMapping(value = "/ccc*", method = RequestMethod.GET)
	public String ccc() {
		System.out.println("ccc 호출");
		return "main";
	}
```

ccc 뒤에 뭐가 붙든 다 매핑해줌

4.

```jsx
@RequestMapping(value = "/ddd/*", method = RequestMethod.GET)
	public String ddd() {
		System.out.println("ddd 호출");
		return "main";
	}
```

슬래시 뒤에 주소 하나 더붙어도 된다

5.

```jsx
// 5. **은 여러 경로 가능 eee/ddd/zzz, eee/ddd/bbb, eee/ddd/bbb/ccc
	@RequestMapping(value = "/eee/**", method = RequestMethod.GET)
	public String eee() {
		System.out.println("eee 호출");
		return "main";
	}
```

\*\* 두개는 뒤에 여러 슬래시 경로가 와도 된다.

6.fff/여러경로/ggg

```jsx
// 6. /fff와 /ggg 사이에 어떠한 주소가 와도 됨.
	@RequestMapping(value = "/fff/**/ggg", method = RequestMethod.GET)
	public String fff() {
		System.out.println("fff 호출");
		return "main";
	}
```

7.주소요청: /hhh/abcd/xxx/1111

```jsx
@RequestMapping(value = "/hhh/{userid}/xxx/{passwd}", method = RequestMethod.GET)
	public String hhh() {
		System.out.println("hhh 호출");
		return "main";
	}
```

# 08Day_MVC_Exception

error.jsp:

```jsx
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
```

isErrorPage="true" 반드시 설정

```jsx
<h1>error.jsp</h1>
<%
	out.print(exception.getMessage());
%>
```

TestController:

```jsx
@RequestMapping(value = "/aaa", method = RequestMethod.GET)
	public String main() {
		if(true)throw new ArithmeticException("error 발생1");
		return "main";
	}

	@RequestMapping(value = "/bbb", method = RequestMethod.GET)
	public String bbbb() {
		if(true)throw new NullPointerException("error 발생2");
		return "main";
	}
```

```jsx
@ExceptionHandler({ArithmeticException.class, NullPointerException.class})
	public String errorPage() {
		return "error";
	}
```

Exception.class 하면 두 에러를 모두 잡을 수 있다.

# 08Day_MVC_Exception2

servlet-context.xml:

```jsx
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
  <property name="exceptionMappings">
    <props>
      <prop key="ArithmeticException">error</prop>
      <prop key="NullPointerException">error</prop>
    </props>
  </property>
</bean>
```

```jsx
@RequestMapping(value = "/aaa", method = RequestMethod.GET)
	public String main() {
		if(true)throw new ArithmeticException("error 발생1");
		return "main";
	}

	@RequestMapping(value = "/bbb", method = RequestMethod.GET)
	public String bbbb() {
		if(true)throw new NullPointerException("error 발생2");
		return "main";
	}
```

exceptionhandler 없이 xml에 에러 객체 만들어주면 이렇게 똑같이 나온다.

# 08Day_MVC3_redirect_forward

결과출력필요

```jsx
<h1>main입니다</h1>
usderid: ${userid}<br>
```

```jsx
@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(Model m) {
		System.out.println("redirect 호출");
		m.addAttribute("userid","홍길동");
		return "redirect:main";
	}
```

리다이렉트로 넘어가서 main 에서 저 어트리뷰트가 사용된다.

url로 userid="홍길동"으로 데이터 넘기기는 가능한데 많은 데이터를 옮기긴 힘들다.

# 08Day_MVC3_redirect2_flash

```jsx
<h1>main입니다</h1>
userid: ${userid}<br>
```

```jsx
@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() {
		System.out.println("main 호출");
		return "main";
	}

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(RedirectAttributes m) {
		System.out.println("redirect 호출");
		m.addFlashAttribute("userid","홍길동");
		return "redirect:main";
	}
```

세션에 넣으면 아무페이지에서나 get 해서 사용할 수 있다. 공통으로 사용할 내용만 넣어주고. 잠깐 사용할거는 사용후 remove해주기

# 08Day_MVC5_namespace

```jsx
@Controller
public class LoginController {
	//servlet-context.xml 에서 매핑주소와 view페이지 지정
	//servlet-context.xml 에서 이미지 폴더를 사용하도록 resource 등록

}
```

```jsx
<h1>main입니다</h1>
<img src="image/a.jpg" width="200">
```

```jsx
<!-- 1. 직접 jsp요청 -->
	<mvc:view-controller path="/kkk" view-name="main"/>
	<!-- /main 주소 요청시 main.jsp 뷰로 사용 -->
	<!-- 2.  -->
	<mvc:view-resolvers>
		<mvc:bean-name>
			<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"></bean>
		</mvc:bean-name>
		<mvc:jsp prefix="/WEB-INF/views/" suffix=".jsp"/>
	</mvc:view-resolvers>

	<!-- 3. 리소스 설정 -->
	<mvc:resources location="/WEB-INF/image/" mapping="/image/**"/>
```

뷰 리졸버 태그를 저렇게 mvc 네임스페이스로 쓸수도있다.

프로젝트이름에서 오른쪽마우스 + f11 하면 바로 루트화면을 띄워준다.

# 08Day_MVC5_HandlerinterceptorAdapter

```jsx
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle========"+handler);
		return true;
	}
}
```

이 인터셉터 클래스는 인터셉터 어답터를 상속받는다. 그래서 메소드를 오버라이딩해서 구현할수있다.

```jsx
@Override
public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
	System.out.println("postHandle======="+handler);
}
```

```jsx
@Override
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {
	System.out.println("aftercompletion");
}
```

소스로 오버라이드 할수 있다.

pre는 앞에붙는거.테스트 컨트롤러의 main 함수 실행되기 전에 작동한다.

servlet-context.xml:

```jsx
<bean id="myInterceptor" class="com.interceptor.MyHandlerInterceptor"></bean>
```

```jsx
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
	<property name="interceptors">
		<list>
			<ref bean="myInterceptor"/>
		</list>
	</property>
</bean>
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"></bean>
```

앞으로 프리핸들 쪽에 로그인이 되었는지 검사하는 로직이 들어가면 된다.
