📝 게시판 RestAPI 백엔드
📖 소개
이 프로젝트는 게시판 REST API 백엔드의 기본 기능 구현을 목표로 개발되었습니다.

🎯 목표 기능
👤 회원가입 / 로그인 (완료)
이름, 이메일, 비밀번호 입력 후 회원가입
비밀번호는 org.springframework.security.crypto.password.PasswordEncoder로 암호화
🔐 사용자 인증 AOP 적용 (완료)
토큰 내의 이메일이 존재하는 이메일인지 점검
📄 게시글 CRUD (완료)
AOP 설정으로 JWT의 이메일이 존재하지 않는 이메일일 경우 거부
생성, 갱신, 삭제 시 토큰의 유저 ID와 게시물의 유저 ID 비교
🔑 JWT (완료)
HS256으로 이메일 암호화 후 웹 토큰으로 전송
💬 댓글 기능 (완료)
생성, 갱신, 삭제 시 토큰의 유저 ID와 댓글의 유저 ID 비교
🔗 대댓글 기능 (완료)
생성, 갱신, 삭제 시 토큰의 유저 ID와 댓글의 유저 ID 비교
👍 게시글 및 댓글 추천/비추천
기능 구현 예정

💬 웹 소켓 활용 채팅 시스템
기능 구현 예정

🛠 기술 스택
Category	Technology
Language	
Framework	
Database	
Security	
ORM	
📄 API Documentation
API 엔드포인트 및 사용 예제는 추후 추가 예정입니다.

🚀 시작하기
1. Clone the Repository
bash
코드 복사
git clone https://github.com/song6368/Spring-Backend-DB.git
2. Build and Run the Project
bash
코드 복사
cd Spring-Backend-DB
./mvnw spring-boot:run
3. Access the API
기본 URL: http://localhost:8080/api
