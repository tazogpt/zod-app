# war/webpack Guidelines (Bundler)

이 디렉터리는 Thymeleaf 기반 사이트에서 사용하는 정적 자산(JS/CSS) 번들을 생성하는 Webpack 모듈입니다.
Nuxt 애플리케이션이 아니며, output은 Spring static 경로에 배치됩니다.

## 1. 기본 원칙
- TypeScript는 사용하지 않습니다.
- 번들은 UMD 모드로 빌드하여 Thymeleaf 템플릿에서 import 없이 사용합니다.
- Tailwind CSS + PostCSS를 사용합니다.
- alpinejs 를 적극 활용합니다.
- alpinejs 로 구현이 불가능하거나 복잡한 것은 vue 를 사용합니다.
- 엔트리 파일을 채널별로 분리합니다:
    - admin.js
    - partner.js
    - site.js
    - mobile.js
- 공통 로직은 bundle.js로 구성합니다.

## 2. 디렉터리 구조(예)
- src/ (엔트리 및 공통 스크립트)
- tailwind.config.js
- postcss.config.js
- webpack.config.js
- dist/ (또는 build 산출물)
- (산출물은 최종적으로 Spring static 경로로 복사/출력)

## 3. 실행 및 빌드
```bash
pnpm install
pnpm dev
pnpm build
```

## 4. 산출물/연동 규칙
- 산출물은 Spring의 static 경로에서 제공되어야 합니다.
- Thymeleaf 템플릿에서는 `<script src="...">` 방식으로 번들을 로드합니다.
- 파일명/경로 규칙을 변경할 경우 war 템플릿 포함 코드도 함께 수정합니다.