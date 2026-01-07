# Site (Spring MVC + Thymeleaf) Guidelines

이 디렉터리는 Nuxt 애플리케이션이 아니라 Spring MVC + Thymeleaf 기반의 서버 렌더링 모듈입니다.

## 1. 기본 원칙
- 화면은 Thymeleaf 템플릿으로 렌더링합니다.
- Frontend 아키텍처(Nuxt 관례, composables 등)는 적용하지 않습니다.
- Backend 연동은 HTTP API 계약 또는 서버 내부 서비스 호출(프로젝트 정책에 맞게)로만 진행합니다.
- 정적 자산(JS/CSS)은 `war/webpack`의 빌드 산출물을 사용합니다.

## 2. 디렉터리 구조(예)
- src/main/resources/templates (Thymeleaf templates)
- src/main/resources/static (정적 리소스 mount 지점; webpack 산출물 포함)

## 3. 템플릿 규칙
- 공통 레이아웃/헤더/푸터 등등은 fragment로 관리합니다.
- 템플릿에서는 비즈니스 로직을 작성하지 않고, 조건 분기는 최소화합니다.
- 화면 데이터는 `app/src/main/kotlin/zod` Controller에서 ViewModel로 정제하여 내려줍니다.

## 4. 실행 및 빌드
- 정적 자산이 필요하면 먼저 `war/webpack`을 yarn build 한 뒤 실행합니다.

## 5. site/webpack 연동
- 번들 산출물 경로와 템플릿에서의 포함 규칙은 `war/webpack/AGENTS.md`를 따릅니다.
