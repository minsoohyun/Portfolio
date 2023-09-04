# PORTFOLIO
# :clipboard: 목차

- :books: <a href="#outline">개요</a>
- :wrench: <a href="#tech">기술스택</a>
- :family: <a href="#team">팀원소개</a>
- :bookmark_tabs: <a href="#function">기능구현</a>
  - 아이디 찾기/ 암호 변경시 이메일 인증
  - 관리자 로그인시 버튼 활성화 및 작품 및 리뷰관리
  - 나이에따른 관람가 제한
  - 1:1 문의게시판
  - 결제
  - 영상조회 및 필터링
  - 고객 통계에 따른 영상 추천
  - 프로필에 따른 리뷰,시청목록,리뷰에 따른 신고 및 좋아요
- <a href="#projectlink">프로젝트 링크</a> 


- :bulb: <a href="#result">결론</a>
- :mag_right: <a href="#fullfill">보완할점</a>

# :books: <a name="outline">개요</a>
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/2.png"> <br/><br/>

>**프로젝트**: OTT플랫폼 제작
>
>**기획 및 제작** : 민수현,천정우,김도형,장기웅,김세현,주세환
>
>**분류**: 팀프로젝트
>
>**제작 기간**: 2023.05.15 ~ 2023.06.09
>
>**담당 기능**: 1:1문의 게시판 글작성, 수정, 삭제, 관리자 답변 작성, 수정
# :wrench: <a name="tech">기술스택</a>
<h4>데이터베이스</h4>
<div align="left">
 	<img src="https://img.shields.io/badge/ORACLE-F80000?style=flat&logo=oracle&logoColor=white" />
	<img src="https://img.shields.io/badge/H2-232F3E?style=flat&logo=h2&logoColor=white" />
	<img src="https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white" />	
</div> 
<h4>백엔드</h4>
<div align="left">
 	<img src="https://img.shields.io/badge/JAVA-007396?style=flat&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=springsecurity&logoColor=white" />
  <img src="https://img.shields.io/badge/JPA-59666C?style=flat&logo=hibernate&logoColor=white" />
  <img src="https://img.shields.io/badge/MyBatis-232F3E?style=flat&logo=mybatis&logoColor=white" />
</div> 
<h4>프론트엔드</h4>
<div align="left">
	<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white" />
	<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white" />
  <img src="https://img.shields.io/badge/JAVASCRIPT-F7DF1E?style=flat&logo=javascript&logoColor=white" />
</div>
<h4>UI프로토타입</h4>
<div align="left">
	<img src="https://img.shields.io/badge/FIGMA-F24E1E?style=flat&logo=figma&logoColor=white" />
</div>
<h4>배포</h4>
<div align="left">
  <img src="https://img.shields.io/badge/Linux-FCC624?style=flat&logo=linux&logoColor=white" />
	<img src="https://img.shields.io/badge/AWS-232F3E?style=flat&logo=amazonaws&logoColor=white" />
	
	
</div>
<h4>협업도구</h4>
<div align="left">
	<img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=Notion&logoColor=white" />
	<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white" />
</div>

# :family: <a name="team">팀원소개</a>
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/1.png"> <br/><br/>
# :bookmark_tabs: <a name="function">기능구현</a>
**1.아이디 찾기/ 암호 변경시 이메일 인증**

<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/%EC%9D%B4%EB%A9%94%EC%9D%BC%EC%BD%94%EB%93%9C%EC%A0%84%EC%86%A1.png">

<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/%EC%9D%B4%EB%A9%94%EC%9D%BC1.png">

<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/%EC%9D%B4%EB%A9%94%EC%9D%BC%20%EC%9D%B8%EC%A6%9D%EC%99%84%EB%A3%8C1.png">

<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/%EC%95%84%EC%9D%B4%EB%94%94%EC%B0%BE%EA%B8%B0.png">


- Redis DB에 5분간 8자리 코드 저장후 이메일 전송

<br/>

**2. 관리자 로그인시 버튼 활성화 및 작품 및 리뷰관리**
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/%EC%9E%91%ED%92%88_%EA%B4%80%EB%A6%AC%EC%9E%90%ED%99%94%EB%A9%B4.png">
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/deactivatedbutton.png"> <br/><br/>
- 세션에 저장된 아이디의 권한을 입력받아 권한이 관리자 일때 버튼이 나오도록 활성화 및 수정 가능 
<br/>

**3. 나이에따른 관람가 제한**
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/agechk.gif"> <br/><br/>
- RESTful 웹 서비스를 구축하여 RestController로 주문상세목록 객체를 JSON으로 데이터 전송
- 버튼 클릭시 Modal에서 주문 상세 목록 출력
<br/>

**4. 1:1 문의게시판**
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/%EA%B2%8C%EC%8B%9C%ED%8C%90.png"> <br/><br/>

- JavaScript를 이용하여 일주일 내의 작성 날짜를 변경
- 문의 답변 완료시 답변 완료로 표시
<br/>

**5. 결제**
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/pay.png"> <br/><br/>
- 이니시스 결제 api 연동하여 토큰 이나 멤버쉽으로 결제가능
- 결제 정보에 따라 프로필 생성가능 수를 늘리거나 영상시청시 토큰이 차감되거나 멤버쉽으로 볼수 있게 함 
<br/>

**6. 영상조회**
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/filter.gif"> <br/><br/>
- 각각의 키워드로 영상을 검색하거나 영상 분류에 맞는 영상물 조회 및 분류 안에서 세부장르로 검색할 수 있게함

<br/>

**7. 고객 통계에 따른 영상 추천**
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/most.png"> <br/><br/>
- 시청기록 테이블에서 가장 많이 보거나 리뷰가 많이 쓰여진 영상물 조회 후 상위 5개 항목 출력
- 슬라이드 형식으로 구성
<br/>

**8. 프로필에 따른 리뷰,시청목록,리뷰에 따른 신고 및 좋아요**
<img src="https://raw.githubusercontent.com/J3ONG/Portfolio1/main/src/main/resources/static/images/portfolioimage/review.gif"> <br/><br/>
- 프로필에따라 시청기록있는 영상에 한해 리뷰가 가능함, 좋아요,신고 기능 
<br/>

# :bulb: <a name="result">결론</a>
이번 프로젝트를 진행하면서 회의,개발,연계등을 팀원간에 같이 하면서 팀프로젝트에서 필요한 책임감을 기를 수 있었으며, 처음 부터 설계도인 서비스부터 
설계하여 개발에 불필요한 과정을 줄일 수 있었습니다. 컨트롤러에 바로 구현하는것이 아니고 서비스로하는 것을 팀간의 연계에서 있어 불편함을 해소하는 것을
직접 경험 했고 Mybatis,Jpa 및 다양한 프레임워크, 툴을 사용함으로써 전반적인 개발 지식을 함양할 수 있었습니다.<br/><br/>

# :mag_right: <a name="fullfill">보완할점</a>
1. 전체 프로젝트
 - 완성도에 대한 아쉬움
   - 회의시간이 많지 않아 뒤늦게 추가했으면 하는 기능들이 생각이 남
   - footer의 부재
   - 사용시의 불친절함(접근 불가시 이유를 나타냈었더라면)
 - 모바일 환경에서 화면이 깨져 제대로 보여지지 않는부분(반응형 웹) 
 <br/>

# <a name="projectlink">프로젝트 링크 </a> 
 - 관리자 아이디(qwerty)_ 비밀번호 동일합니다
 - 고객 아이디 비밀번호(555555, 555555)
 - http://13.125.171.81:8080/streampark/member/main.do (AWS/팀용)
 
<!-- 
- http://34.22.85.174:8080/streampark (GCP/개인용/개선중)
<img src="https://img.shields.io/badge/Google Cloud Platform-4285F4?style=flat?&logo=googlecloud&logoColor=white" />

  개선 리스트
  1. urlFilter 추가 http://34.22.85.174:8080/streampark/ 까지 입력해도 메인으로 이동(완)
  2. 로그인 시 reCAPTCHA 추가(완)
  3. 멤버쉽 가입시 화면 깨짐 현상 해결(임시)
  4. 이메일 인증 코드 메일 문구 수정(완)
  5. 암호찾기 방식 추가 url 보내기

 -->
