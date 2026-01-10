- [ ] API 레이어에 `POST /api/site/mypage/password` 엔드포인트 추가하고 Principal의 `userid`와 요청 DTO(현재 비밀번호, 새 비밀번호)를 받아 Application Command로 위임한다.
- [ ] Application Command Use Case 추가: 기존 비밀번호 확인, 새 비밀번호 최소 4자, 기존 비밀번호 재사용 금지 검증을 수행한다.
- [ ] Application Port 정의/확장: 회원 조회(비밀번호 포함)와 비밀번호 업데이트를 위한 Query/Command Port를 분리한다.
- [ ] Infra Adapter 구현: 회원 조회/비밀번호 업데이트 JPA 어댑터 및 리포지토리 메서드 추가(필요 시).
- [ ] 테스트 추가: API 컨트롤러, Command 서비스(검증 로직, 업데이트 성공/실패 케이스) 중심으로 작성한다.

Task 1 Prompt: `/api/site/mypage/password`를 처리하는 컨트롤러를 추가하고, Spring Security Principal에서 `userid`를 가져와 요청 DTO(현재 비밀번호, 새 비밀번호)를 Command 서비스에 전달하도록 구현해줘. 성공 시 `ApiResponse` 메시지를 반환하도록 해.

Task 2 Prompt: 비밀번호 변경 Command 서비스를 만들고, 기존 비밀번호 일치 확인, 새 비밀번호 최소 4자, 기존 비밀번호 재사용 금지 규칙을 적용해줘. 실패 시 적절한 ErrorCode로 예외를 던져줘.

Task 3 Prompt: 비밀번호 변경에 필요한 Application Port를 정의/확장하고, 조회용(Query)과 변경용(Command)을 분리해줘. 필요한 메소드 시그니처를 제안해줘.

Task 4 Prompt: JPA 기반 어댑터에서 비밀번호 조회/업데이트를 구현하고, 필요한 Repository 메소드가 없으면 추가해줘.

Task 5 Prompt: 컨트롤러와 Command 서비스에 대한 테스트를 추가해줘. 성공 케이스, 기존 비밀번호 불일치, 새 비밀번호 길이 미달, 동일 비밀번호 재사용 금지 케이스를 포함해줘.
