# Project Title

**IrumOrder**

---

## Project Scope

서울시립대학교 컴퓨터과학부 2024년 소프트웨어공학 프로젝트로, Software Development Life-Cycle을 기반으로 객체지향 소프트웨어공학 방법론을 적용하여 교내 카페 모바일 주문을 도와주는 모바일 앱 개발.

---

## Project Duration

2024년 2학기

---

## Project Values/Motivations

수업 전후, 점심시간 등 교내 카페에 인원이 많이 몰릴 때 효율적인 시간 관리를 위한 비대면 오더를 목표로 함.

### 주요 기능:
- **자유로운 픽업 시간**: 원하는 시간대를 선택하여 교내 카페의 붐비는 시간에도 픽업 예약 가능.
- **루틴 설정 기능**: 매일 같은 메뉴를 설정하고 알림을 받을 수 있는 맞춤형 주문 기능.

---

## Highlighted Features

- **픽업 예약**: 메뉴 선택, 주문, 픽업 시간 예약을 간편히 수행.
- **주문 루틴 설정**: 반복적인 주문에 대해 요일, 시간별 알림을 제공해 사용자 경험 최적화.
- **알림 기능**: 주문 상태 변경, 픽업 준비 완료 및 실시간 푸시 알림 제공.

---

## Constraints

- 전농관 및 학관 카페와 앱을 직접 연결할 수 없어 임의로 DB를 제작하여 연동.
- 사업자 등록이 되어있지 않아 카카오페이 API를 이용한 실제 결제를 모의 결제로 대체.

---

## High-Level Architecture
### 구성 요소 및 데이터 흐름
![poster](./artifacts/Architecture_diagram/System_Integration_Overview.png)
1. **Frontend (React)**:
    - React를 사용한 사용자 인터페이스 레이어
    - 사용자의 입력 데이터를 처리하고, REST API를 통해 Backend와 상호작용합니다.
    - Local Storage를 통해 클라이언트 측 데이터를 관리합니다.
    - Feature-based Directory Structure

![mvc](./artifacts/Architecture_diagram/High_level_FeatureBased.png)

2. **Backend (Spring Framework)**:
    - Spring Framework 기반으로 구현된 애플리케이션 서버
    - REST API를 통해 Frontend와 통신하며, 데이터 요청 및 응답을 처리합니다.
    - 외부 API와 통신, 데이터베이스와 통신합니다.
    - MVC 구조
   
![mvc](./artifacts/Architecture_diagram/High_level_MVC.png)

---

### 데이터 흐름

1. **사용자 요청**:
    - 사용자가 Frontend(React)에서 요청을 보냅니다.
    - REST API를 통해 요청이 Backend(Spring)로 전달됩니다.

2. **Backend 처리**:
    - 요청에 따라 데이터를 데이터베이스에 저장하거나 조회합니다 (JDBC Access).
    - 또는 외부 API(Kakao Pay 등)와 상호작용하여 필요한 결과를 가져옵니다.

3. **응답 반환**:
    - Backend는 처리된 결과를 REST API로 Frontend에 반환합니다.
    - Frontend는 데이터를 사용자에게 표시합니다.

---

### 주요 특징
- **모듈화된 아키텍처**:
    - Frontend와 Backend가 명확히 분리되어 있으며, REST API를 통해 통신합니다.
- **확장성**:
    - 외부 API와의 통합을 통해 다양한 서비스 확장이 가능합니다.
- **유지보수성**:
    - Spring Framework 및 JDBC를 사용하여 코드의 유지보수가 용이합니다.
---

## Tech Stack

- **FrontEnd**: React.js
- **BackEnd**: Spring, MySQL
- **API**: Firebase, KakaoPay

---

## Installation

### FrontEnd
```bash
git clone https://github.com/ej9374/Software-Engineering
npm i 
npm i react-router-dom
npm i firebase
npm start
```

### BackEnd

1. **IntelliJ IDE 설치**
3. **JDK 설정**
    - JDK 17 버전으로 프로젝트를 설정.
4. **Gradle 빌드**
    - `build.gradle` 실행:
      ```bash
      ./gradlew build
      ```
5. **Spring Boot 애플리케이션 실행**
    - `IrumOrderApplication` 실행:
      ```bash
      ./gradlew bootRun
      ```

---

### Database

1. **MySQL Workbench 설치**
    - 데이터베이스 관리를 위해 MySQL Workbench를 설치.
2. **DB 초기화**
    - `IrumOrder_DB` 폴더의 SQL 스크립트를 실행해 데이터베이스 초기화.
3. **프로퍼티 파일 수정**
    - `application.properties` 파일에서 다음 항목 수정:
        - `spring.datasource.username`: MySQL 사용자 이름
        - `spring.datasource.password`: MySQL 비밀번호
        - `spring.datasource.url`: MySQL 데이터베이스 URL
4. **IntelliJ와 데이터베이스 연결**
    - IntelliJ에서 MySQL 데이터베이스 연결 설정 완료.

---

## Project Deliverables
(링크 업데이트 예정)
### 요구사항 분석 명세서
- [Final Version](#)

### Architecture & Design Documentation
- [Software Architecture](https://linktodocumentation)
- [Software Design](https://www.figma.com/design/x51yUm7qlKHmJsBmILMI7w/Irum-order?node-id=0-1&node-type=canvas&t=b9dXi4WOvpVvjXLQ-0)
- [UI Design](https://www.figma.com/design/x51yUm7qlKHmJsBmILMI7w/Irum-order?node-id=0-1&node-type=canvas&t=b9dXi4WOvpVvjXLQ-0)

### Coding Standard
- [Coding Standard](./artifacts/Coding%20Standard,%20Repository%20Management%20and%20Review%20Process%20-%20이룸핑.docx)

---

## Test Cases and Results

- **테스트 위치**: `IrumOrder_BackEnd/src/test/java`
- **테스트 대상**:
    - Controller
    - Service
    - Validator
- **테스트 결과**:
    - 결과 파일은 `IrumOrder/artifacts/testResult`(임시) 폴더에 저장.

---

## Repository Structure

### Backend (`IrumOrder_BackEnd`)

#### Main Codebase
```bash
├─main
│  ├─java
│  │  └─Irumping
│  │      └─IrumOrder
│  │          ├─config             
│  │          ├─controller           
│  │          ├─dto
│  │          ├─entity
│  │          ├─event
│  │          ├─exception
│  │          │  └─CustomExceptions
│  │          ├─listener
│  │          ├─repository
│  │          ├─scheduler
│  │          ├─service
│  │          └─validator
│  └─resources
│      ├─docs
│      └─firebase
└─test
    └─java
        └─Irumping
            └─IrumOrder
                ├─controller
                ├─listener
                ├─scheduler
                ├─service
                └─validator
```
---
- **`config`**
    - 애플리케이션의 설정 파일을 포함합니다.
    - `WebConfig`, `SecurityConfig`, `FirebaseConfig`

- **`controller`**
    - HTTP 요청 및 응답을 처리하는 API 컨트롤러를 포함합니다.
    - `OrderController`, `AuthController` 등

- **`dto`**
    - 계층 간 데이터 전달을 위한 데이터 전송 객체를 정의합니다.
    - `LoginDto`, `OrderRequestDto` 등

- **`entity`**
    - 데이터베이스 테이블과 매핑되는 엔터티 클래스를 정의합니다.
    - `UserEntity`, `OrderEntity` 등

- **`event`**
    - 비동기 프로세스에 사용되는 애플리케이션 이벤트를 정의합니다.
    - `PickUpAlarmEvent`

- **`exception`**
    - 커스텀 예외 및 전역 에러를 처리합니다. 
    - `GlobalExceptionHandler`, `ApiError`
    - **`CustomExceptions`**:
        - 비즈니스 로직에 사용되는 특정 커스텀 예외 클래스.
        - `InvalidInputException`, `PayErrorException` 등

- **`listener`**
    - 애플리케이션 이벤트에 반응하는 이벤트 리스너를 포함합니다.
    - `PickUpAlarmEventListener`

- **`repository`**
    - JPA 또는 기타 데이터베이스 접근 방식을 사용한 데이터베이스 상호작용 인터페이스를 포함합니다.
    - `UserRepository`, `OrderRepository`

- **`scheduler`**
    - 정기적인 백그라운드 작업을 처리합니다.
    - `RoutineAlarmScheduler`

- **`service`**
    - 애플리케이션의 비즈니스 로직을 구현합니다.
    - `AuthService`, `OrderService`

- **`validator`**
    - 사용자 입력 또는 비즈니스 규칙을 검증합니다.
    - `UserValidator`
---


#### `main/resources`

- **`docs`**
    - Swagger YAML 파일과 같은 API 문서를 포함합니다.
    - 예: `swagger-order.yaml`, `swagger-payment.yaml`.

- **`firebase`**
    - Firebase 관련 설정 파일을 포함합니다.
    - 예: `serviceAccount.json`.

---

#### `test/java/Irumping/IrumOrder`

- **`controller`**
    - API 컨트롤러에 대한 테스트 케이스입니다.
    - `OrderControllerTest`, `PayControllerTest` 등

- **`listener`**
    - 이벤트 리스너에 대한 테스트 케이스입니다.
    - `PickUpAlarmEventListenerTest`

- **`scheduler`**
    - 정기 작업 및 백그라운드 작업에 대한 테스트 케이스입니다.
    - `RoutineAlarmSchedulerTest`

- **`service`**
    - 서비스 클래스의 비즈니스 로직에 대한 단위 테스트입니다.
    - `AuthServiceTest`, `OrderServiceTest` 등

- **`validator`**
    - 입력 데이터 처리와 관련된 검증 로직에 대한 테스트 케이스입니다.
    - `UserValidatorTest`

---

### Frontend (`IrumOrder_FrontEnd`)

#### Main Codebase
```bash
irumorder_fe
├── src
│   ├── features
│   │   ├── cart
│   │   │   ├── CartView.css
│   │   │   ├── CartView.js
│   │   │   ├── Toolbar.css
│   │   │   └── Toolbar.js
│   │   ├── check
│   │   │   ├── CheckView.css
│   │   │   ├── CheckView.js
│   │   ├── menu
│   │   │   ├── Category.css
│   │   │   ├── Category.js
│   │   │   ├── MenuGrid.css
│   │   │   ├── MenuGrid.js
│   │   │   ├── MenuView.css
│   │   │   └── MenuView.js
│   │   ├── option
│   │   │   ├── OptionView.css
│   │   │   └── OptionView.js
│   │   ├── payment
│   │   │   ├── PickupReserv.css
│   │   │   ├── PickupReserv.js
│   │   │   ├── PayView.css
│   │   │   ├── PayView.js
│   │   │   ├── Paymentcomplete.css
│   │   │   ├── Paymentcomplete.js
│   │   │   ├── Toolbar.css
│   │   │   └── Toolbar.js
│   │   ├── store
│   │   │   ├── StoreSelection.css
│   │   │   ├── StoreSelection.js
│   │   ├── routine
│   │   │   ├── RoutineDetail.css
│   │   │   ├── RoutineDetail.js
│   │   │   ├── RoutineList.css
│   │   │   └── RoutineList.js
│   │   ├── user
│   │   │   ├── management
│   │   │   │   ├── Login.css
│   │   │   │   └── Login.js
│   │   │   ├── registration
│   │   │   │   ├── Signup.css
│   │   │   │   ├── Signup.js
│   │   │   │   ├── SignupComplete.css
│   │   │   │   ├── SignupComplete.js
│   │   │   │   ├── SignupStart.css
│   │   │   │   └── SignupStart.js
│   ├── shared
│   │   ├── context
│   │   │   ├── OrderOrRoutine.js
│   │   │   ├── RoutineContext.js
│   │   │   ├── RoutineModify.js
│   │   │   └── userStorage.js
│   │   ├── ui
│   │   │   ├── Main.css
│   │   │   └── Main.js
├── App.js
├── index.js
└── setupProxy.js
```
#### **`irumorder_fe`**
- React 프로젝트의 최상위 디렉터리.
- 프로젝트 설정과 엔트리 파일을 포함.

---

#### **`src`**
- 애플리케이션 소스 코드가 포함된 메인 디렉터리.

---

### **`features`**
애플리케이션의 주요 기능 단위를 담당하는 디렉터리. 각 하위 폴더는 특정 기능을 나타냄.

#### **`cart`**
- **`CartView.js`**: 장바구니 화면 컴포넌트.
- **`CartView.css`**: 장바구니 화면 스타일링.
- **`Toolbar.js`**: 장바구니에서 사용되는 툴바 컴포넌트.
- **`Toolbar.css`**: 툴바 스타일링 파일.

#### **`check`**
- **`CheckView.js`**: 주문 확인 화면 컴포넌트.
- **`CheckView.css`**: 주문 확인 화면 스타일링.

#### **`menu`**
- **`Category.js`**: 메뉴 카테고리 표시 컴포넌트.
- **`Category.css`**: 카테고리 스타일링 파일.
- **`MenuGrid.js`**: 메뉴 목록을 격자로 보여주는 컴포넌트.
- **`MenuGrid.css`**: 메뉴 목록 스타일링 파일.
- **`MenuView.js`**: 메뉴 선택 화면 컴포넌트.
- **`MenuView.css`**: 메뉴 선택 화면 스타일링.

#### **`option`**
- **`OptionView.js`**: 메뉴 옵션 선택 화면 컴포넌트.
- **`OptionView.css`**: 옵션 선택 화면 스타일링.

#### **`payment`**
- **`PickupReserv.js`**: 픽업 시간 예약 화면 컴포넌트.
- **`PickupReserv.css`**: 픽업 시간 예약 화면 스타일링.
- **`PayView.js`**: 결제 화면 컴포넌트.
- **`PayView.css`**: 결제 화면 스타일링.
- **`Paymentcomplete.js`**: 결제 완료 화면 컴포넌트.
- **`Paymentcomplete.css`**: 결제 완료 화면 스타일링.
- **`Toolbar.js`**: 결제 툴바 컴포넌트.
- **`Toolbar.css`**: 결제 툴바 스타일링.

#### **`store`**
- **`StoreSelection.js`**: 매장 선택 화면 컴포넌트.
- **`StoreSelection.css`**: 매장 선택 화면 스타일링.

#### **`routine`**
- **`RoutineDetail.js`**: 루틴 세부 정보 관리 컴포넌트.
- **`RoutineDetail.css`**: 루틴 세부 정보 스타일링.
- **`RoutineList.js`**: 루틴 목록 화면 컴포넌트.
- **`RoutineList.css`**: 루틴 목록 스타일링.

#### **`user`**
- **`management`**
    - **`Login.js`**: 사용자 로그인 화면 컴포넌트.
    - **`Login.css`**: 로그인 화면 스타일링.
- **`registration`**
    - **`Signup.js`**: 회원가입 화면 컴포넌트.
    - **`Signup.css`**: 회원가입 화면 스타일링.
    - **`SignupComplete.js`**: 회원가입 완료 화면 컴포넌트.
    - **`SignupComplete.css`**: 회원가입 완료 화면 스타일링.
    - **`SignupStart.js`**: 회원가입 시작 화면 컴포넌트.
    - **`SignupStart.css`**: 회원가입 시작 화면 스타일링.

---

### **`shared`**
공통으로 사용되는 코드와 유틸리티를 포함.

#### **`context`**
- **`OrderOrRoutine.js`**: 주문 및 루틴 관리 상태.
- **`RoutineContext.js`**: 루틴 관련 데이터 관리 컨텍스트.
- **`RoutineModify.js`**: 루틴 수정 관련 유틸리티.
- **`userStorage.js`**: 사용자 정보를 로컬 스토리지에 저장 및 가져오는 유틸리티.

#### **`ui`**
- **`Main.js`**: 메인 화면 컴포넌트.
- **`Main.css`**: 메인 화면 스타일링.

---

### **프로젝트 루트**

#### **`App.js`**
- 애플리케이션의 루트 컴포넌트.
- 모든 라우트를 정의하고 최상위 레이아웃을 설정.

#### **`index.js`**
- 애플리케이션의 진입점.
- ReactDOM을 사용해 루트 컴포넌트를 DOM에 렌더링.

#### **`setupProxy.js`**
- API 프록시 설정 파일.
- 클라이언트와 서버 간의 원활한 통신을 지원.



---

## Project Team Members

- **박수빈** ([@aon0303](https://www.github.com/aon0303)) - **Frontend, 기획**
- **양나슬** ([@yns030506](https://www.github.com/yns030506)) - **Backend**
- **김은지** ([@ej9374](https://www.github.com/ej9374)) - **Backend**
- **최진영** ([@glebungle](https://www.github.com/glebungle)) - **Frontend, Design**
- **이희진** ([@22huijin](https://www.github.com/22huijin)) - **Frontend, 기획**
- **주영은** ([@ye0314](https://www.github.com/ye0314)) - **Backend**
