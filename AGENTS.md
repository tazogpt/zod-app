# Repository Structure Guidelines

이 문서는 **프로젝트 구성과 책임 분리 기준**만 정의합니다.  
세부 구현 규칙은 각 영역의 `AGENTS.md`를 따릅니다.

## 프로젝트 구성

- Backend: `app`(단일 모듈, 서버 코드는 `app/src`), `war`(tomcat 배포)
- Frontend: `web/admin`, `web/partner`, `web/site`

## 책임 분리

- Root에는 비즈니스 로직/아키텍처 규칙을 두지 않는다.
- Backend/Frontend는 내부 규칙을 공유하지 않는다.
- Backend 도메인은 패키지 단위로 분리하며 추후 모듈 분리 가능해야 한다.

## 문서 적용 범위

- Backend: `app/AGENTS.md`
- War: `war/AGENTS.md`
- Frontend: `web/AGENTS.md`

## 변경 승인 규칙

- 편집/수정 여부는 최초 1회만 묻고, 사용자가 승인하면 이후 관련 변경은 자동으로 진행한다.
