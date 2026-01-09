# AGENTS.md
# Backend Architecture & AI Collaboration Guidelines

이 문서는 `app` 모듈에 적용되는 **백엔드 아키텍처, 설계 규칙 및 AI 협업 규칙의 단일 기준 문서**이다.  
AI 에이전트(ChatGPT 등)는 이 문서를 **최우선 규칙**으로 준수해야 하며, 명시적인 요청이 없는 한 코드를 생성하거나 수정하지 않는다.

---

## 1. 프로젝트 전제

- Backend는 **단일 모듈(app)** 구조이다.
- 도메인 분리는 **패키지 단위(Bounded Context)** 로 수행한다.
- 각 도메인은 추후 **독립 모듈 또는 마이크로서비스로 분리 가능**하도록 설계한다.
- 점진적 리팩토링을 전제로 하며 **전면 재설계는 허용하지 않는다.**

---

## 2. 기본 설계 원칙

- 느슨한 **헥사고날 아키텍처(Hexagonal Architecture)** 를 따른다.
- **CQRS(Command / Query Responsibility Segregation)** 를 기본 설계 원칙으로 한다.
- 도메인 규칙과 유스케이스 흐름을 임의로 변경하지 않는다.
- 도메인 전용 기술 구현은 반드시 infra 계층에 위치해야 한다.
- 전역 기술 구현(보안, 설정, 공통 인프라 등)은 common에 위치할 수 있다.
- 기본 AI 동작은 다음에 한정한다.
  - 구조 검토
  - 설계 리뷰
  - 문제점 및 리스크 지적
  - 대안 제시
- 파일 생성/수정은 **명시적인 요청이 있을 때만 수행**한다.
- 설계 검토가 충분하지 않다고 판단되면 **반드시 추가 질문을 한다.**

---

## 3. 패키지 기반 도메인 분리

```text
app/src/main/kotlin
 └─ zod
     ├─ api
     ├─ member
     ├─ payment
     ├─ wallet
     └─ common
```

- 각 최상위 패키지는 하나의 Bounded Context를 의미한다.
- 도메인 간 직접 참조를 최소화한다.
- 공통 기술/유틸은 `common`에 위치한다.

---

## 4. 도메인 내부 헥사고날 구조

각 도메인 패키지는 다음 구조를 가질 수 있다.

```text
member
 ├─ domain
 ├─ application
 └─ infra
```

### 4.1 domain

- 비즈니스 규칙과 모델을 표현한다.
- 기술 및 프레임워크 의존을 금지한다.
- 포함 가능 요소:
  - Entity / Value Object
  - Domain Service
  - Domain Event
  - enum / model / port
- port는 adapter(infra)와 연결되는 **interface** 이다.
- 도메인 이벤트는 domain 내부에서만 생성/수집한다.

---

### 4.2 application

- 유스케이스 흐름(오케스트레이션)을 담당한다.
- Command / Query 를 명확히 분리한다.
- 외부 의존은 반드시 interface(port)로만 접근한다.
- 트랜잭션 경계를 정의할 수 있다.
- Request / Response DTO는 **application에 고정**한다.
- DTO 파일 수 증가 방지를 위해 관련 DTO는 하나의 XxxDto 아래 묶는다.

```kotlin
class AuthDto {

    data class LoginRequest(
        val username: String,
        val password: String,
    )

    data class RefreshRequest(
        val refreshToken: String,
    )
}
```

---

### 4.3 infra

- 모든 기술 구현이 위치한다.
  - JPA, QueryDSL, Redis, JWT, Security, WebClient, Scheduler 등
- application/domain에 정의된 port의 구현체만 위치한다.
- 구현체는 **기술이 드러나는 이름**을 사용한다.
  - 예: JpaMemberRepository, QueryDslMemberDao, RedisTokenStore
- adapter 하위에 spec 패키지를 두고 **adapter와 1:1 매칭**한다.
- QueryDSL 구성 요소는 다음 기준으로 분리한다.

```kotlin
object MemberQuerySpec {

    object Select {
        // projections (select)
    }

    object Where {
        // predicate
    }

    object OrderBy {
        // order by
    }
}
```

---

### 4.4 api

- Controller 및 Web Entry Point만 포함한다.
- 요청/응답 모델은 api에서 받고 application DTO로 변환한다.
- 비즈니스 로직을 포함하지 않는다.
- adapter 패키지는 사용하지 않고 infra를 사용한다.

---

## 5. CQRS 규칙

### Command

- 시스템 상태를 변경한다.
- `application.command` 하위에 위치한다.
- 저장/갱신은 **CommandPort** 를 통해 수행한다.
- Entity 또는 도메인 객체를 반환하지 않는다.

### Query

- 시스템 상태를 변경하지 않는다.
- 도메인을 직접 거치지 않아도 된다.
- DTO 또는 ReadModel만 반환한다.
- **QueryPort** 를 사용한다.

### 금지

- Query에서 Entity 반환
- Query에서 도메인 규칙 수행

※ exists / count 류 조회는 Query 성격이지만,  
Command 유스케이스 내부에서 **검증 용도**로 사용하는 것은 허용한다.

---

## 6. 인증 / 행위자(Principal) 규칙

- 인증된 행위자는 **Spring Security Principal** 을 사용한다.
- 별도의 Actor 모델을 중복 정의하지 않는다.
- Principal은 모든 도메인(board, bet, payment 등)에 공통으로 사용된다.
- Principal에 포함 가능한 정보는 다음 최소 집합으로 제한한다.
  - userid
  - role
  - level
- 다음 정보는 포함하지 않는다.
  - password / password hash
  - status
  - 표시용 정보(nickname 등)

---

## 7. 도메인 이벤트 규칙

- 도메인 이벤트는 발생한 사실(Fact)을 표현한다.
- 가능한 한 닫힌 집합으로 관리하는 것을 권장한다.
- Kotlin 사용 시 sealed interface 사용을 권장한다.
- 도메인 이벤트는 외부 도메인에 직접 노출하지 않는다.

---

## 8. 테스트 기준

- 도메인 규칙은 domain 테스트로 검증한다.
- 유스케이스는 application 테스트로 검증한다.
- Query는 DTO / ReadModel 결과 기준으로 테스트한다.

---

## 9. AI 행동 규칙

- 명시적인 요청이 없는 한 파일 생성/수정을 하지 않는다.
- 기존 설계를 존중하며 다음을 임의로 수행하지 않는다.
  - 전면 재설계
  - 대규모 구조 변경
- 판단이 모호한 경우 반드시 질문한다.

---

## 10. 규칙 우선순위
1. AGENTS.md  
2. 이 문서 (app/AGENTS.md)
3. 사용자 명시적 지시

위 규칙이 충돌할 경우 **상위 우선순위를 따른다.**
