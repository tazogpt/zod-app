<template>
    <main
        class="min-h-screen bg-gradient-to-br from-surface-50 via-white to-surface-100 text-surface-900 dark:from-surface-950 dark:via-surface-900 dark:to-surface-800 dark:text-surface-50"
    >
        <div class="relative overflow-hidden">
            <div class="absolute left-1/2 top-[-220px] h-[420px] w-[420px] -translate-x-1/2 rounded-full bg-primary-300/40 blur-[120px] dark:bg-primary-500/30" />
            <div class="absolute right-[-120px] top-24 h-[280px] w-[280px] rounded-full bg-primary-200/40 blur-[120px] dark:bg-primary-400/20" />
            <div class="absolute bottom-[-160px] left-[-120px] h-[320px] w-[320px] rounded-full bg-surface-200/60 blur-[140px] dark:bg-surface-200/10" />
        </div>

        <div class="relative mx-auto flex w-full max-w-6xl flex-col gap-16 px-6 pb-20 pt-16">
            <header class="flex flex-col gap-8">
                <div class="flex items-center justify-between text-xs uppercase tracking-[0.4em] text-surface-500 dark:text-surface-300">
                    <span class="font-semibold">ZOD SITE</span>
                    <span class="text-surface-400 dark:text-surface-400">Preview</span>
                </div>
                <div class="max-w-3xl space-y-6">
                    <p class="text-sm font-medium text-primary-700 dark:text-primary-200">
                        새로운 파트너 경험을 위한 테스트 랜딩
                    </p>
                    <h1 class="text-4xl font-semibold leading-tight md:text-6xl">
                        한 화면에서 흐름을 정리하고,
                        팀의 실행 속도를 올립니다.
                    </h1>
                    <p class="text-base text-surface-600 dark:text-surface-200 md:text-lg">
                        사이트 모듈 전용 랜딩으로, 운영 영역과 분리된 디자인 언어를 실험합니다.
                        핵심 메시지, 전환 구조, 시각적 톤을 빠르게 검증할 수 있습니다.
                    </p>
                    <div class="flex flex-wrap gap-3">
                        <button
                            type="button"
                            class="rounded-full bg-primary-600 px-6 py-3 text-sm font-semibold text-white transition hover:bg-primary-500 dark:bg-primary-500 dark:text-surface-950 dark:hover:bg-primary-400"
                        >
                            데모 요청
                        </button>
                        <button
                            type="button"
                            class="rounded-full border border-surface-300 px-6 py-3 text-sm font-semibold text-surface-700 transition hover:border-surface-400 hover:text-surface-900 dark:border-surface-400/60 dark:text-surface-100 dark:hover:border-surface-200 dark:hover:text-surface-50"
                        >
                            기능 둘러보기
                        </button>
                    </div>
                </div>
            </header>

            <section class="grid gap-6 md:grid-cols-3">
                <article class="rounded-2xl border border-surface-200/70 bg-white/70 p-6 shadow-sm shadow-surface-900/5 dark:border-surface-700/60 dark:bg-surface-900/60 dark:shadow-none">
                    <p class="text-sm font-semibold text-primary-700 dark:text-primary-200">01</p>
                    <h2 class="mt-3 text-lg font-semibold">즉시 반영되는 메시지</h2>
                    <p class="mt-2 text-sm text-surface-600 dark:text-surface-300">
                        운영과 분리된 카피를 시험할 수 있도록 핵심 메시지를 분리합니다.
                    </p>
                </article>
                <article class="rounded-2xl border border-surface-200/70 bg-white/70 p-6 shadow-sm shadow-surface-900/5 dark:border-surface-700/60 dark:bg-surface-900/60 dark:shadow-none">
                    <p class="text-sm font-semibold text-primary-700 dark:text-primary-200">02</p>
                    <h2 class="mt-3 text-lg font-semibold">가벼운 화면 구성</h2>
                    <p class="mt-2 text-sm text-surface-600 dark:text-surface-300">
                        단일 화면으로 플로우를 검증하고, 개발 리소스를 집중합니다.
                    </p>
                </article>
                <article class="rounded-2xl border border-surface-200/70 bg-white/70 p-6 shadow-sm shadow-surface-900/5 dark:border-surface-700/60 dark:bg-surface-900/60 dark:shadow-none">
                    <p class="text-sm font-semibold text-primary-700 dark:text-primary-200">03</p>
                    <h2 class="mt-3 text-lg font-semibold">테스트 친화적</h2>
                    <p class="mt-2 text-sm text-surface-600 dark:text-surface-300">
                        QA와 공유 가능한 구조로 빠른 피드백 루프를 돕습니다.
                    </p>
                </article>
            </section>

            <section class="flex flex-col gap-6 rounded-3xl border border-surface-200/80 bg-white/80 p-8 shadow-sm shadow-surface-900/5 dark:border-surface-700/70 dark:bg-surface-900/70 dark:shadow-none md:flex-row md:items-center md:justify-between">
                <div class="space-y-3">
                    <h3 class="text-2xl font-semibold">지금 바로 흐름을 테스트해 보세요.</h3>
                    <p class="text-sm text-surface-600 dark:text-surface-300">
                        이 화면은 사이트 모듈 전용이며, admin/partner와 다른 톤을 유지합니다.
                    </p>
                </div>
                <button
                    type="button"
                    class="rounded-full bg-primary-600 px-6 py-3 text-sm font-semibold text-white transition hover:bg-primary-500 dark:bg-primary-500 dark:text-surface-950 dark:hover:bg-primary-400"
                >
                    시작하기
                </button>
            </section>
        </div>
    </main>
</template>
