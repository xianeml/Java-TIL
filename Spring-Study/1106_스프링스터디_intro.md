# intro to Spring

- 섹션 2~4

### 왜 스프링을 쓰냐

- enterprise앱 빌딩을 위함
- POJO 개발쉽게
- 느슨한 연결을 위한 의존성주입
- AOP
- 자바 보일러플레이트 코드 단축

### inversion of control(IoC)

객체의 관리를 아웃소싱하다

### 스프링 컨테이너의 역할

- 내 앱에 필요한 객체들을 configuration파일에 생성하고 관리함
- 객체의 의존성 주입

### 컨테이너 설정 방법

- xml은 올드한 방법이라함. 지금은 어노테이션 또는 소스코드 안에 설정

### 스프링 개발 과정

1. xml에 beans 설정 (객체)
2. 스프링 컨테이너 생성
   - ApplicationContext가 일반적으로 사용하는 스프링 컨테이너
   - 우리는 수업때 GenericWebApplicationContext를 주로 사용함
   - main에 이 컨테이너 객체 생성해서 사용
3. 스프링 컨테이너에서 beans 찾기
   - `context.getBean("빈즈이름", 클래스이름.class)`
