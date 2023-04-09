# ☘️ GREME: Green With Me!
> 친환경 챌린지 도전 <br/>
> 나의 일상 기록 다이어리 



<br/>

## 🔫 Team Shoot It

- 기획 : 박영선, 이지민
- 안드로이드 네이티브 : 정은정, 조예진
- 백엔드 : 김상인, 한채연 ([ 🔗 백엔드 Repo ](https://github.com/ChaeyeonHan/GREME))

<br/><br/>

## 📌 [@eunjjungg](https://github.com/eunjjungg) : Native 기술 스택

- Android, Kotlin
- 디자인 패턴 - MVVM + Repository Pattern
- Live Data, Data Binding
- Android Compose
- Android KTX
- Retrofit
- Material Design 3
- Custom View
- Glide
- 소셜 로그인 - Kakao, Naver API
- 이슈 관리 - Jira, Github Issue
- 형상 관리 전략 - Gitflow
- 애니메이션 - Lottie

<br/><br/>

## 💻 [@eunjjungg](https://github.com/eunjjungg) : Native 담당 부분

- 프로젝트 세팅
    - 프로젝트 생성
    - gitignore 생성
    - layer 단위 패키지 분리
    - base code 생성 (프래그먼트, 액티비티, Retrofit 관련)
    - 디자인 관련 코드 생성 (색상, 사이즈…)
    - 폰트 세팅 (사용하지 않는 텍스트들 제외하여 폰트 용량 줄이는 작업 포함)
    - 앱 아이콘 세팅
- 회원가입 관련 로직 (전체 로그인 흐름 함께 고려함)
- 카카오톡, 네이버 소셜 로그인
- 홈 화면 관련 
- 챌린지 관련 로직
- 다른 유저 프로필 관련 로직

<br/>

#### 📁 layer 단위 패키지 분리 방식

<img width="298" alt="image" src="https://user-images.githubusercontent.com/100047095/230750320-010d1c7b-e909-4d71-8072-14d9120ca168.png">

- base : boilerplate code를 줄이기 위해 재사용 가능한 클래스들 존재 
- model : model 관련 코드 분리
- network : 서버 연결 Interface 및 소셜 로그인 관련 로직
- repository : 서버 연결 Repository 
- ui : ui 관련 코드 분리
    - adapter : `RecyclerView` 관련 어댑터 클래스 분리
    - custom : custom view 관련 클래스 분리
    - fragment : `fragment` 관련 클래스 분리
    - interface : ui와 관련된 interface 분리
    - view : `activity` 관련 클래스 분리
        - compose : `activity` 중 컴포즈로 구현한 것들 모음 
- util : 전체 앱에서 사용되는 유틸 클래스, 함수 분리
- viewmodel : 뷰모델 클래스 분리

<br/><br/>

## 🌱 [@eunjjungg](https://github.com/eunjjungg) : 구현 화면 및 설명

### 🙋‍♀️ 회원가입 

#### 1. 소셜 로그인을 통한 회원가입 및 로그인
https://user-images.githubusercontent.com/100047095/230750091-add60a88-ee43-4b5a-8d19-c2d96d0c2f09.mp4

- 네이버, 카카오 API를 사용한 소셜 로그인 구현
- 웹뷰를 사용한 약관 동의 문구 노출
- 소셜 로그인에 필요한 노출되면 안 되는 정보 `local.properties`로 분리
- 토큰 기기내 저장은 `Encrypted SharedPreference` 사용

<br/>

#### 2. ID 입력
https://user-images.githubusercontent.com/100047095/230750097-25064b4b-a111-4bb6-a85d-9c084f288a45.mp4


- 아이디 유효성 검사 - 정규식
- 아이디 중복 검사 - 서버로부터 응답
    - 서버에 부하가 가지 않도록 사용자가 아이디 입력 후 1초 동안 더 이상의 입력이 없을 떄만 중복 검사 요청 후 로직 처리하도록 구현
- 디자인 - google material의 `TextInputLayout` 사용

<br/>

#### 3. 추가 정보 입력
https://user-images.githubusercontent.com/100047095/230750109-aca9daaa-22a4-4a9b-a169-41e258777cd7.mp4


- 각 `fragment` 모두 같은 `viewmodel` 사용하도록 구현
- 마지막 다음으로 넘어가기 버튼 클릭 시 회원가입 절차 완료되고 서버로 가입 요청하도록 구현
- 가입 도중 끊긴다면 카카오 로그인에서 회원 삭제하도록 구현

<br/>

### 🏆 챌린지

#### 1. 도움말
https://user-images.githubusercontent.com/100047095/230750118-946de992-7db1-4cd5-af15-39ec4bfc77e3.mp4

- Android Compose로 구현
- Lottie로 애니메이션 구현

<br/>

#### 2. 홈 화면 대표 챌린지, 참여 챌린지
https://user-images.githubusercontent.com/100047095/230749988-7d3cf7a5-b641-47cf-a2b8-c1473cfefd6a.mp4

https://user-images.githubusercontent.com/100047095/230750124-dee5c543-4a69-4e8b-8bab-dc1d08f2a4d7.mp4


- 각 대표 챌린지와 유저의 참여 챌린지 구현 
- 아래 동영상은 유저가 현재 참여중인 챌린지의 상태를 참여하지 않음으로 변경하였을 때 홈 화면의 모습

<br/>

#### 3. 챌린지 등록 & 제외
https://user-images.githubusercontent.com/100047095/230750142-f846d394-8d7c-487e-b216-9947156f4829.mp4


- Shared Element 사용하여 화면 전환 사용
- `fab` 사용
- `RecyclerView`로 구현해야 하는데 전체 화면이 스크롤 되어야 하므로 화면의 모든 구성 요소 각각 컴포넌트화함
- 재사용 가능하도록 일정 부분은 커스텀 뷰로 만듦
- 유저가 올린 이미지 정사각형으로 표시되도록 직접 사이즈 계산하여 크롭 (추가 라이브러리 사용 X)

