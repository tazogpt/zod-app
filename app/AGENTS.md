# Backend Architecture & AI Collaboration Guidelines

이 문서는 `app` 모듈의 핵심 설계 규칙만 정의한다.  
AI는 명시적 요청 없이는 코드 생성/수정을 하지 않는다.

## 프로젝트 전제

- Backend는 단일 모듈(`app`)이다.
- 도메인은 패키지 단위로 분리하며, 추후 모듈 분리 가능해야 한다.
- 전면 재설계 없이 점진적 리팩토링만 허용한다.

## 기본 설계 원칙

- 헥사고날 아키텍처와 CQRS를 따른다.
- 도메인 규칙/유스케이스 흐름을 임의로 변경하지 않는다.
- 도메인 전용 기술 구현은 `infra`, 공통 기술 구현은 `common`에 둔다.

## 패키지/레이어 규칙

- 최상위 패키지는 Bounded Context이며, 직접 참조를 최소화한다.
- 도메인 구조: `domain`(순수 규칙/모델), `application`(유스케이스), `infra`(기술 구현), `api`(컨트롤러).

## CQRS

- Command는 상태 변경, Query는 조회만 수행한다.
- Query는 Entity 반환/도메인 규칙 수행을 금지한다.

## 인증/Principal

- Spring Security Principal을 사용하며, userid/role/level만 포함한다.

## 도메인 이벤트

- 사실(Fact)을 표현하며 외부 도메인에 직접 노출하지 않는다.

## 테스트 기준

- 도메인 규칙은 domain 테스트, 유스케이스는 application 테스트로 검증한다.

## AI 행동 규칙

- 명시적 요청이 없는 한 파일 생성/수정 금지.
- 대규모 구조 변경/전면 재설계 금지.
- 모호하면 질문한다.

## 규칙 우선순위

1) `AGENTS.md`  
2) `app/AGENTS.md`  
3) 사용자 명시적 지시
