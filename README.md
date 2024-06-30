# Chapter 5-1 Review Assignment Feed

## 프로젝트 설명

이 프로젝트는 Spring Boot와 Kotlin을 사용하여 개발된 댓글 기능이 포함된 게시물 피드 애플리케이션입니다. 유저는 게시물에 댓글을 작성, 수정, 삭제할 수 있으며 댓글에 좋아요를 추가할 수 있습니다.

```
 ⚙ features : 필수 구현 기능
- 회원 가입 API
  - 닉네임, 비밀번호, 비밀번호 확인을 request에서 전달받기
  - 닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
  - 비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
  - 비밀번호 확인은 비밀번호와 정확하게 일치하기
  - 중복된 닉네임 입력 시 "중복된 닉네임입니다." 에러 메세지를 response에 포함하기
  - 닉네임 중복 확인 기능 제공

- 로그인 API
  - 닉네임, 비밀번호를 request에서 전달받기
  - 로그인 실패 시 "닉네임 또는 패스워드를 확인해주세요." 에러 메세지를 response에 포함하기
  - 로그인 성공 시 JWT를 활용하여 유저 정보를 Cookie로 전달하기

- 전체 게시글 목록 조회 API
  - 제목, 작성자명(nickname), 작성 날짜를 조회하기
  - 작성 날짜 기준으로 내림차순 정렬하기

- 게시글 작성 API
  - 토큰을 검사하여 유효한 토큰일 경우에만 게시글 작성 가능
  - 제목(최대 500자), 작성 내용(최대 5000자)을 입력하기

- 게시글 조회 API
  - 제목, 작성자명(nickname), 작성 날짜, 작성 내용을 조회하기

- 게시글 수정 API
  - 토큰을 검사하여 해당 사용자가 작성한 게시글만 수정 가능

- 게시글 삭제 API
  - 토큰을 검사하여 해당 사용자가 작성한 게시글만 삭제 가능

- 댓글 작성 API
  - 게시글과 연관된 댓글 작성
  - 토큰을 검사하여 유효한 토큰일 경우에만 댓글 작성 가능
  - 댓글 내용 입력하기

- 게시글과 댓글 목록 조회 API, 댓글 수정/삭제 API
  - 댓글 목록 조회
  - 게시글 조회 시 해당 게시글의 댓글 목록도 응답
  - 토큰을 검사하여 해당 사용자가 작성한 댓글만 수정/삭제 가능

 😆 features : 선택 구현 기능 (챌린지 과제)
- 회원 가입 API
  - 비밀번호를 단방향 암호화하여 저장
  - 회원 가입 시 이메일 또는 SNS로 인증 번호를 전달하고 검증하기

- 전체 게시글 목록 조회 API
  - 페이징 조회 기능
  - 페이징 + 커스텀 정렬 기능

- 게시글 작성 API
  - 이미지 업로드 기능

- 게시글 삭제 API
  - 수정된지 3시간이 지난 데이터를 자동으로 지우는 스케줄러 기능

- 게시글과 댓글 목록 조회 API, 댓글 수정/삭제 API
  - 페이징 조회 기능
  - 페이징 + 커스텀 정렬 기능
  - 게시글 삭제 시 연관된 댓글도 같이 삭제
```

## 기술 스택

- Kotlin
- Spring Boot
- Spring Data JPA
- Hibernate
- JWT (Json Web Token) Authentication
- H2 Database (기본 설정)
- Swagger

## 주요 기능

- 게시물에 댓글 작성
- 댓글 조회
- 댓글 수정
- 댓글 삭제
- 댓글 좋아요 추가
- 게시물 작성
- 게시물 조회
- 게시물 수정
- 게시물 삭제
- 닉네임 중복 확인
- 사용자 회원가입
- 사용자 로그인
- 만료된 게시물 삭제 (스케줄러)

## API 엔드포인트

### 댓글 작성

- **URL**: `/api/v1/comments/{feedId}`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "comment": "댓글 내용"
    }
    ```

### 댓글 조회

- **URL**: `/api/v1/comments/{feedId}`
- **Method**: `GET`

### 댓글 수정

- **URL**: `/api/v1/comments/{commentId}`
- **Method**: `PUT`
- **Request Body**:
    ```json
    {
        "comment": "수정된 댓글 내용"
    }
    ```

### 댓글 삭제

- **URL**: `/api/v1/comments/{commentId}`
- **Method**: `DELETE`

### 댓글 좋아요 추가

- **URL**: `/api/v1/comments/{commentId}/thumb-up`
- **Method**: `POST`

### 게시물 작성

- **URL**: `/api/v1/feeds`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "title": "게시물 제목",
        "content": "게시물 내용"
    }
    ```

### 게시물 조회

- **URL**: `/api/v1/feeds`
- **Method**: `GET`

### 특정 게시물 조회

- **URL**: `/api/v1/feeds/{id}`
- **Method**: `GET`

### 게시물 수정

- **URL**: `/api/v1/feeds/{id}`
- **Method**: `PUT`
- **Request Body**:
    ```json
    {
        "title": "수정된 게시물 제목",
        "content": "수정된 게시물 내용"
    }
    ```

### 게시물 삭제

- **URL**: `/api/v1/feeds/{id}`
- **Method**: `DELETE`

### 닉네임 중복 확인

- **URL**: `/api/v1/signup/check-nickname`
- **Method**: `GET`
- **Request Params**: `nickname` (중복 확인할 닉네임)

### 사용자 회원가입

- **URL**: `/api/v1/signup`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "nickname": "사용자 닉네임",
        "password": "사용자 비밀번호",
        "confirmPassword": "비밀번호 확인"
    }
    ```

### 사용자 로그인

- **URL**: `/api/v1/login`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "nickname": "사용자 닉네임",
        "password": "사용자 비밀번호"
    }
    ```

## 환경설정<br/>

Language : Kotlin<br/>
IDLE : IntelliJ community<br/>
JDK : temurin-17 <br/>
