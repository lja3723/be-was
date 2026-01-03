be-was-2025: 코드스쿼드 백엔드 교육용 WAS 2025 개정판

# 1. Week1 과제 수행 프로그래스

##  1.1. Step1 - index.html 응답

### 1.1.1. 학습 내용

- [x] HTTP 프로토콜 복습
- [x] 자바 스레드 모델과 변천사에 대한 학습
- [x] Concurrent 패키지 학습
- [x] 프로젝트 동작원리 분석을 위한 학습 진행
  - [x] ServerSocket, Socket의 개념 및 원리
  - [x] InputStream, OutputStream의 개념 및 사용법
    - [ ] (TODO) Stream과 StreamReader의 정확한 차이점 공부
  - [x] reflect 패키지에 대한 간단한 사용 예시 공부
    - private field, static field를 가지는 클래스를 테스트하기 위하여 리플렉션 개념을 활용
  - [x] 프로젝트의 resources/static에 어떤 파일이 있고, 어떤 구조로 배치되어 있는지 분석하기
- [ ] (TODO) 로거의 사용법 학습
- [ ] (TODO) WAS, 웹서버의 전반적 및 포괄적인 개념

### 1.1.2. 구현 내용

- [x] 일부 객체(Database, User)에 대한 테스트케이스 작성
  - 나머지 두 객체는 추후 리팩토링 과정에서 테스트케이스 작성 예정
- [x] Thread로 구현된 웹서버를 Concurrent 패키지를 활용하여 리팩토링
  - 무한적으로 생성되는 Thread는 매우 비효율적이므로 Concurrent 패키지의 Thread Pool을 활용하여 리팩토링 예정
- [x] HTTP Request 내용을 파싱하고 로거로 출력하는 기능
- [x] localhost:8080/index.html 요청 시 index.html 파일을 읽어서 응답하는 기능 구현
  - [ ] (TODO) requestbody를 유연하게 조립하는 방법을 고민하기
- [x] OOP 원칙에 따라 클래스의 책임을 최대한 줄이고, 단위 테스트가 가능하도록 코드 구조 고민하기