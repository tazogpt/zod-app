# Frontend (Nuxt) Guidelines

이 문서는 `web/admin`, `web/partner`, `web/site`에 공통 적용되는
Nuxt 프론트엔드 규칙을 정의합니다.

---

## 1. 기본 원칙

- 각 디렉터리는 독립적인 Nuxt 애플리케이션입니다.
- Backend 아키텍처(헥사고날, CQRS)는 적용하지 않습니다.
- Backend와의 통신은 HTTP API 계약을 통해서만 이루어집니다.

---

## 2. 디렉터리 구조

- Nuxt 기본 관례를 따릅니다.
  - `app/pages`
  - `app/components`
  - `app/composables`
  - `app/assets`
  - `public`

- API 호출 로직은 `composables` 또는 `services` 디렉터리에 모아 관리합니다.

---

## 3. 환경 설정

- 환경 변수는 `.env` 또는 Nuxt `runtimeConfig`를 사용합니다.
- 민감 정보는 커밋하지 않습니다.

---

## 4. 실행 및 빌드

각 모듈은 Node.js 기반으로 실행합니다.

```bash
pnpm install
pnpm dev
pnpm build
```

---

## 5. Backend 연동 규칙

- API 스펙 변경은 Backend와 사전 합의 후 진행합니다.
- 프론트엔드는 Backend 내부 패키지 구조를 가정하지 않습니다.
