# Backend Architecture Guidelines

이 문서는 `app` 모듈에 적용되는
백엔드 아키텍처 및 설계 규칙을 정의합니다.

현재 Backend는 단일 모듈 구조이며,
도메인 분리는 **패키지 단위**로 수행합니다.

---

## 1. 기본 원칙

- 느슨한 헥사고날 아키텍처를 사용합니다.
- CQRS(Command / Query Responsibility Segregation)를 기본으로 합니다.
- 자동 생성/수정은 요청이 있을 때만 수행합니다.
- 기본 동작은 설계 검토, 리뷰, 대안 제시에 한정합니다.
- 완벽한 설계 검토가 될때까지 필요한 사항은 다시 질문 합니다.

---

## 2. 패키지 기반 도메인 분리

Backend는 다음과 같이 **도메인 중심 패키지 구조**를 가집니다.

```text
app/src/main/kotlin
 └─ zod
     ├─ api
     ├─ member
     ├─ payment
     ├─ wallet
     └─ common
```

- 각 최상위 패키지는 하나의 바운디드 컨텍스트를 의미합니다.
- 패키지는 추후 독립 모듈 또는 MS로 분리 가능한 단위로 설계합니다.
- 도메인 간 직접 참조를 최소화합니다.

---

## 3. 헥사고날 아키텍처 규칙

각 도메인 패키지 내부는 다음 계층을 가질 수 있습니다.

```text
member
 ├─ domain
 ├─ application
 └─ infra
```

### 계층 책임
- domain  
  - 비즈니스 규칙과 모델  
  - 기술 및 프레임워크 의존 금지
  - 도메인 이벤트는 domain 내부에서 생성/수집할 수 있습니다.

- application
  - 유스케이스 흐름 (오케스트레이션)
  - Command / Query 분리
  - 외부 의존은 interface로만 접근.
  - interface 이름은 Port 대신 의미 기반 DDD 명칭을 사용합니다.
    - 저장소: *Repository, *Store
    - 조회: *Dao, *Reader
    - 외부 연동: *Client, *Gateway, *Fetcher
  - Request/Response DTO는 application 으로 고정하고, 혼용하지 않습니다.

- infra
  - 기술 구현(JPA, QueryDSL, WebClient, Scheduler 등)
  - application에 정의된 interface(Repository/Dao/Client 등)의 구현체만 위치합니다.
  - 구현체는 기술이 드러나는 이름을 사용합니다.
    - 예: JpaMemberRepository, QueryDslMemberDao, WebClientOddsClient, RedisTokenStore

- api 
  - Controller 및 web entrypoint
  - 요청/응답 모델은 api에서 받고 application으로 변환해 전달합니다.
  - adapter 패키지 대신 infra를 사용합니다.

### 패키지 크기 제한
- 각 패키지는 바운디드 컨텍스트 기준으로 분리합니다.
- 패키지당 파일 수는 적정하게 유지하며, 최대 10개를 넘지 않도록 합니다.
- 파일 수가 증가하거나 의미가 분화되면 하위 패키지 분리를 검토합니다.  
  - 예: member/application/command, member/application/query, member/infra/jpa, member/infra/dao

---

## 4. CQRS 규칙

### Command
- 도메인 상태를 변경합니다.
- application.command
- 저장 및 갱신은 **CommandRepository** 를 사용합니다.

### Query
- 도메인을 거치지 않아도 됩니다.
- DTO 또는 ReadModel을 반환합니다.
- **ReadRepository** 를 사용합니다.

### 금지:
- Query에서 Entity 반환
- Query에서 도메인 규칙 수행

---

## 5. 도메인 이벤트 규칙

- 도메인 이벤트는 해당 도메인에서 발생한 사실(Fact)을 표현합니다.
- 도메인 이벤트는 가능한 한 닫힌 집합으로 관리하는 것을 권장합니다.
- Kotlin 사용 시 sealed interface 사용을 권장합니다.
- 도메인 이벤트는 외부 도메인에 직접 노출하지 않습니다.

---

## 6. 테스트 기준

- 도메인 규칙은 domain 테스트로 검증합니다.
- 유스케이스는 application 테스트로 검증합니다.
- Query는 DTO 결과 기준으로 테스트합니다.

---