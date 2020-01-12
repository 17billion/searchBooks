# search books
---

프로젝트
> 카카오, 네이버 Open API를 이용한 책 검색 서비스 
  
### ENV
- gradle : 6.0.1 <br>
- java version : 1.8 <br>
- spring boot : 2.1.9 <br>
- port : 8080 <br>
- server : embedded tomcat <br>
- database : h2
 
### 프로젝트 실행 방법
#### 1. Git Clone
> $ git clone https://github.com/17billion/searchBooks.git <br>
> $ cd searchBooks/

#### 2. 프로젝트 실행
> $ gradle bootRun
```
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.9.RELEASE)
 ...
 ..
 .
020-01-12 21:29:10.914  INFO 1268 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2020-01-12 21:29:10.914  INFO 1268 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-01-12 21:29:10.921  INFO 1268 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 7 ms
<=========----> 75% EXECUTING
```
#### 3.  Flow 테스트 (curl, 테스트 계정 : test(account) / test123 (pwd))
> $ chmod 755 curlTestFlow.sh && ./curlTestFlow.sh
```
##################
#####API TEST#####
account : test
pwd : test123
url : localhost:8080
##################
1. 로그인(200, 302 성공)
200
2-1. 검색어 조회 (Spring security 2회, 200 성공)
200
200
2-2. 검색어 조회 (Spring boot 3회, 200 성공)
200
200
200
3. 검색어 히스토리 조회
									검색어: spring boot
									검색어: spring boot
									검색어: spring boot
									검색어: spring security
									검색어: spring security
4. 랭킹조회 (오름차순)
[{"search_word":"spring boot","count":3},{"search_word":"spring security","count":2}]
5. 로그아웃(200, 302 성공)
302
```

#### 4. 접속
> http://localhost:8080

### 외부 라이브러리
- lombok : annotation 사용
- Bootstrap 4-beta : Front-end 화면 구성을 위한 CSS 용
- httpclient : RestAPI 호출
- jstl : jsp 구성 지원
