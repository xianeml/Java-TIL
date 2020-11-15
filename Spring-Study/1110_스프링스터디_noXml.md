# 자바코드 설정

- xml에서 스프링 컨테이너 설정하는 대신 자바 코드를 통해 설정할 수 있다

# 스프링 컨테이너 설정 3가지 방법

1.  xml 파일에 모든 설정
2.  컴포넌트 어노테이션 넣고 xml 파일에는 컴포넌트 스캔 태그만 작성
3.  자바 설정 클래스(no xml!)

# 스프링 컨테이너를 자바코드에 설정하는 개발 과정

1.  자바 클래스를 만들고 @Configuration 어노테이션 지정
2.  @ComponentScan 지정 (옵션)

    ```java
    @Configuration
    @ComponentScan("com.luv2code.springdemo")
    public class SportConfig {

    }
    ```

3.  스프링 자바 config 클래스 읽기

    ```java
    AnnotationConfigApplicationContext context =
    				new AnnotationConfigApplicationContext(SportConfig.class);
    ```

4.  스프링 컨테이너 bean 탐색

    ```java
    Coach theCoach = context.getBean("tennisCoach", Coach.class);
    ```

# 빈즈를 자바코드에 설정하는 개발 과정

1.  bean을 노출하기 위한 메소드 정의

    ```java
    @Bean
    public Coach wimCoach() {
    	SwimCoach mySwimCoach = new SwimCoach();
    	return mySwimCoach;
    }
    ```

    - 메소드 이름은 빈 아이디가 된다.
    - 컴포넌트 스캔은 없고, 각각의 빈은 이 클래스 안에서 정의된다.

2.  의존성 bean 주입

3.  스프링 자바 컨피그 클래스 읽기

    ```java
    AnnotationConfigApplicationContext context =
    				new AnnotationConfigApplicationContext(SportConfig.class);
    ```

4.  스프링 컨테이너에서 빈 찾기

    ```java
    Coach theCoach = context.getBean("swimCoach", Coach.class);
    ```

# 외부 프로퍼티스 파일 내용 주입하기

1. 프로퍼티스 파일 생성
2. 스프링 컨피그에 로드하기

   ```java
   @Configuration
   @PropertySource("classpath:sport.properties")
   public class SportConfig{

   }
   ```

3. 프로퍼티스 파일의 value값 참조하기

   ```java
   public class SwimCoach implements Coach{
   	@Value("${foo.email}")
   	private String email;

   	@Value("${foo.team}")
   	privte String team;
   }
   ```
