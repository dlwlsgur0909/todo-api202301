# 스프링 부트 프로젝트 초기 설정 

### start.spring.io
 - Dependencies 
   - Spring Boot Dev Tools
   - Lombok
   - Spring Web
   - Thymeleaf
   - Spring Data JPA
   - MariaDB Driver
   - Validation

### application.properties 설정 
- database connection datasure  
   - spring.datasource.url=jdbc:mariadb://127.0.0.1:3306/demodb  
   - spring.datasource.username=root  
   - spring.datasource.password=mariadb  

- JPA config  
   - spring.jpa.properties.hibernate.format_sql=true  
   - spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDB106Dialect  
   - spring.jpa.show-sql=true  
   - spring.jpa.hibernate.database=mysql  
   - spring.jpa.hibernate.generate-ddl=true  
   - spring.jpa.hibernate.ddl-auto=create  

- logging level config  
   - logging.level.org.hibernate=info

### 프로젝트 자동 빌드 설정 
- 파일 -> 설정 -> 빌드,실행,배포 -> 컴파일러 -> 프로젝트 자동 빌드 체크
- 파일 -> 설정 -> 고급 설정 -> 컴파일러 -> 프로젝트 실행중에도~~~ 체크

### 기본 파일 설정 
- HELP.MD -> README.MD 
- git.ignore -> README.MD 삭제 


### 프로젝트 빌드 순서
- com.example.todo -> todoapi 패키지 
- todoapi -> entity 패키지 -> TodoEntity 클래스
- todoapi -> repository 패키지 -> TodoRepository 인터페이스
- todoapi -> service 패키지 -> TodoService 클래스 
- todoapi -> dto 패키지
- 





