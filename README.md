# search books
---

프로젝트
> 카카오, 네이버 open api를 이용한 책 검색 서비스 
  
### ENV
- gradle : 6.0.1 <br>
- java version : 1.8 <br>
- spring boot : 2.1.9 <br>
- port : 8080 <br>
- server : embedded tomcat <br>
- database : h2
 
### 프로젝트 실행 방법
####1. Git Clone
> $ git clone https://github.com/17billion/searchBooks.git <br>
> $ cd searchBooks/

####2. 프로젝트 실행
> $ gradle bootRun

####3.  Flow 테스트 (curl, 테스트 계정 : test(account) / test123 (pwd))
> $ ./curlTestFlow.sh

####4. 접속
> http://localhost:8080

### 외부 라이브러리
- lombok : annotation 사용
- Bootstrap 4-beta : Front-end 화면 구성을 위한 CSS 용
- httpclient : RestAPI 호출
- jstl : jsp 구성 지원