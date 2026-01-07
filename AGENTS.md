# Repository Structure Guidelines

이 문서는 본 저장소의 **프로젝트 구성과 책임 분리 기준**만을 정의합니다.

구현 규칙, 아키텍처, 프레임워크별 정책은  
각 영역의 `AGENTS.md`를 따릅니다.

답변은 특별한 요청이 없으면 한글로 답변합니다.

---

## 1. 프로젝트 구성

본 저장소는 다음과 같은 구조를 가집니다.

### Backend
- `app`
  - 단일 백엔드 모듈
  - 모든 서버 코드는 `app/src` 하위에 위치
  - 도메인 분리는 **패키지 단위**로 수행
- `war`
  - tomcat war 배포 모듈


### Frontend
- `web/admin`
- `web/partner`
- `web/site`

---

## 2. 책임 분리 원칙

- Root에는 비즈니스 로직이나 아키텍처 규칙을 두지 않습니다.
- Backend와 Frontend는 서로의 내부 규칙을 공유하지 않습니다.
- 패키지 단위로 분리된 Backend 코드는
  추후 모듈 또는 마이크로서비스로 분리 가능해야 합니다.

---

## 3. 문서 적용 범위

- Backend 규칙은 `app/AGENTS.md`를 따릅니다.
- War 배포 규칙은 `war/AGENTS.md`를 따릅니다.
- Frontend 규칙은 `web/AGENTS.md`를 따릅니다.
