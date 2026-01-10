---
name: zod-task
description: Turn a domain summary markdown (e.g., .codex/design/money-wallet-domain.md) into an executable task breakdown. Use when the user wants tasks derived from domain docs, with feature-focused scope and implementation-ready steps saved back under .codex/design.
---

# zod-task

도메인 정리 문서를 기반으로 개발 작업(Task)을 도출해 정리하라. 기능 정의를 중심으로 작업을 분해하고, 실행 가능한 수준으로 구체화하라.

## 입력 확인

- 기본 입력은 `./.codex/design` 아래의 도메인 요약 문서다.
- 파일 경로가 주어지지 않으면, 가장 최근에 생성된 도메인 요약 문서를 찾거나 사용자에게 물어보라.
- 레거시 참고: `/home/routine/workspace/zod-app/app-old`
- 레거시 참고 코드가 있으면 반드시 확인하고 작업 분해에 반영하라.

## 작업 분해 원칙

- 기능 정의를 중심으로 Task를 나눈다.
- 각 Task는 “무엇을 만들지”가 명확해야 하며, 최소 단위는 기능 1개로 한다.
- 도메인 규칙/제약/비기능 요구는 관련 Task에 반드시 반영한다.
- 데이터 구조가 필요하면 별도 Task로 분리한다.
- 단일 스레드/이벤트 처리 등 기술 제약은 설계 Task로 명시한다.
- 코드/파일 생성이 필요할 때는 **처음 한 번만** 생성 여부를 묻고, 사용자가 생성하라고 하면 필요한 작업을 한 번에 진행하라.

## Task 포맷

Task는 아래 필드로 작성하라.

- ID: `TASK-001` 형식
- 제목: 짧고 명확하게
- 목적: 한 줄
- 범위: 포함/제외
- 의존성: 선행 Task ID
- 산출물: 코드/문서/테이블 등
- 검증: 테스트 또는 확인 방법

## 산출물 저장

- 산출물은 반드시 `./.codex/task` 아래에 Markdown으로 저장하라.
- 파일명은 도메인 요약 문서명에 `-task`를 붙여 생성하라.

## 결과 예시 구조

1) 개요
2) Task 목록
3) 의존성 맵(간단)
4) 오픈 질문/리스크
