<script setup lang="ts">
const userStore = useUserStore()

const kpis = [
    { label: '오늘 신규 가입', value: '128', delta: '+12.4%' },
    { label: '활성 세션', value: '3,482', delta: '+5.1%' },
    { label: '결제 성공률', value: '98.6%', delta: '+0.8%' },
    { label: '미처리 문의', value: '24', delta: '-3.2%' }
]

const alerts = [
    { title: '주요 서버 점검 예정', time: '오늘 23:00', tone: 'bg-amber-100 text-amber-700' },
    { title: '신규 파트너 승인 대기', time: '8건', tone: 'bg-emerald-100 text-emerald-700' },
    { title: '결제 오류 알림', time: '2건', tone: 'bg-rose-100 text-rose-700' }
]

const tasks = [
    { title: '주간 리포트 공유', status: '진행 중', owner: '기획팀' },
    { title: '신규 공지 등록', status: '검토 필요', owner: '운영팀' },
    { title: '파트너 온보딩 완료', status: '완료', owner: '세일즈팀' }
]
</script>

<template>
    <div class="relative overflow-hidden">
        <div class="pointer-events-none absolute -top-24 right-0 h-64 w-64 rounded-full bg-primary-200/40 blur-3xl dark:bg-primary-500/20" />
        <div class="pointer-events-none absolute -bottom-32 left-0 h-72 w-72 rounded-full bg-surface-200/70 blur-3xl dark:bg-surface-800/50" />

        <div class="mx-auto flex w-full max-w-6xl flex-col gap-8 px-6 py-10">
            <header class="rounded-3xl bg-white/80 p-8 shadow-xl shadow-surface-900/10 backdrop-blur dark:bg-surface-900/70">
                <div class="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
                    <div>
                        <p class="text-xs font-semibold uppercase tracking-[0.35em] text-surface-500 dark:text-surface-400">
                            Admin Dashboard
                        </p>
                        <h1 class="mt-3 text-3xl font-semibold text-surface-900 dark:text-surface-50">
                            {{ userStore.profile?.nickname ? `${userStore.profile.nickname}님` : '관리자님' }} 오늘의 운영 현황
                        </h1>
                        <p class="mt-2 text-sm text-surface-500 dark:text-surface-400">
                            핵심 지표와 우선순위 업무를 한눈에 확인하세요.
                        </p>
                    </div>
                    <div class="flex flex-wrap gap-3">
                        <button class="rounded-full border border-surface-200 px-4 py-2 text-xs font-semibold text-surface-600 transition hover:border-surface-300 hover:text-surface-900 dark:border-surface-700 dark:text-surface-300 dark:hover:border-surface-500 dark:hover:text-surface-50">
                            리포트 내보내기
                        </button>
                        <button class="rounded-full bg-primary-600 px-4 py-2 text-xs font-semibold text-white transition hover:bg-primary-700 dark:bg-primary-500 dark:text-surface-950 dark:hover:bg-primary-400">
                            알림 센터 열기
                        </button>
                    </div>
                </div>
            </header>

            <section class="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
                <article
                    v-for="kpi in kpis"
                    :key="kpi.label"
                    class="rounded-2xl border border-surface-200 bg-white p-5 shadow-sm shadow-surface-900/5 dark:border-surface-800 dark:bg-surface-900/80"
                >
                    <p class="text-xs font-semibold uppercase tracking-[0.2em] text-surface-500 dark:text-surface-400">
                        {{ kpi.label }}
                    </p>
                    <div class="mt-4 flex items-baseline justify-between">
                        <p class="text-2xl font-semibold text-surface-900 dark:text-surface-50">{{ kpi.value }}</p>
                        <span class="rounded-full bg-emerald-100 px-2 py-1 text-xs font-semibold text-emerald-700 dark:bg-emerald-500/20 dark:text-emerald-200">
                            {{ kpi.delta }}
                        </span>
                    </div>
                </article>
            </section>

            <section class="grid gap-4 md:grid-cols-3">
                <div class="rounded-2xl border border-surface-200 bg-white p-5 text-sm text-surface-600 shadow-sm shadow-surface-900/5 dark:border-surface-800 dark:bg-surface-900/80 dark:text-surface-300">
                    <p class="text-xs font-semibold uppercase tracking-[0.2em] text-surface-400">아이디</p>
                    <p class="mt-3 text-lg font-semibold text-surface-900 dark:text-surface-50">
                        {{ userStore.profile?.userid || 'admin' }}
                    </p>
                </div>
                <div class="rounded-2xl border border-surface-200 bg-white p-5 text-sm text-surface-600 shadow-sm shadow-surface-900/5 dark:border-surface-800 dark:bg-surface-900/80 dark:text-surface-300">
                    <p class="text-xs font-semibold uppercase tracking-[0.2em] text-surface-400">권한</p>
                    <p class="mt-3 text-lg font-semibold text-surface-900 dark:text-surface-50">
                        {{ userStore.profile?.role || 'GOD' }}
                    </p>
                </div>
                <div class="rounded-2xl border border-surface-200 bg-white p-5 text-sm text-surface-600 shadow-sm shadow-surface-900/5 dark:border-surface-800 dark:bg-surface-900/80 dark:text-surface-300">
                    <p class="text-xs font-semibold uppercase tracking-[0.2em] text-surface-400">레벨</p>
                    <p class="mt-3 text-lg font-semibold text-surface-900 dark:text-surface-50">
                        Lv.{{ userStore.profile?.level ?? 0 }}
                    </p>
                </div>
            </section>

            <section class="grid gap-6 lg:grid-cols-[2fr,1fr]">
                <div class="rounded-3xl border border-surface-200 bg-white p-6 shadow-lg shadow-surface-900/10 dark:border-surface-800 dark:bg-surface-900/80">
                    <div class="flex items-center justify-between">
                        <h2 class="text-lg font-semibold text-surface-900 dark:text-surface-50">트래픽 트렌드</h2>
                        <span class="text-xs font-semibold text-surface-500 dark:text-surface-400">최근 7일</span>
                    </div>
                    <div class="mt-6 grid gap-4 md:grid-cols-3">
                        <div class="rounded-2xl bg-surface-100 px-4 py-5 text-sm text-surface-600 dark:bg-surface-800/70 dark:text-surface-300">
                            <p class="text-xs font-semibold uppercase tracking-[0.2em] text-surface-400">방문자</p>
                            <p class="mt-3 text-2xl font-semibold text-surface-900 dark:text-surface-50">42.1K</p>
                            <div class="mt-4 h-2 w-full rounded-full bg-surface-200 dark:bg-surface-700">
                                <div class="h-2 w-4/5 rounded-full bg-primary-500" />
                            </div>
                        </div>
                        <div class="rounded-2xl bg-surface-100 px-4 py-5 text-sm text-surface-600 dark:bg-surface-800/70 dark:text-surface-300">
                            <p class="text-xs font-semibold uppercase tracking-[0.2em] text-surface-400">전환율</p>
                            <p class="mt-3 text-2xl font-semibold text-surface-900 dark:text-surface-50">6.8%</p>
                            <div class="mt-4 h-2 w-full rounded-full bg-surface-200 dark:bg-surface-700">
                                <div class="h-2 w-2/3 rounded-full bg-emerald-500" />
                            </div>
                        </div>
                        <div class="rounded-2xl bg-surface-100 px-4 py-5 text-sm text-surface-600 dark:bg-surface-800/70 dark:text-surface-300">
                            <p class="text-xs font-semibold uppercase tracking-[0.2em] text-surface-400">매출</p>
                            <p class="mt-3 text-2xl font-semibold text-surface-900 dark:text-surface-50">₩128M</p>
                            <div class="mt-4 h-2 w-full rounded-full bg-surface-200 dark:bg-surface-700">
                                <div class="h-2 w-1/2 rounded-full bg-amber-500" />
                            </div>
                        </div>
                    </div>
                    <div class="mt-6 rounded-2xl border border-dashed border-surface-200 px-6 py-10 text-center text-sm text-surface-400 dark:border-surface-700 dark:text-surface-500">
                        차트 컴포넌트를 연결하면 실시간 그래프가 표시됩니다.
                    </div>
                </div>

                <div class="flex flex-col gap-6">
                    <div class="rounded-3xl border border-surface-200 bg-white p-6 shadow-lg shadow-surface-900/10 dark:border-surface-800 dark:bg-surface-900/80">
                        <h2 class="text-lg font-semibold text-surface-900 dark:text-surface-50">알림 요약</h2>
                        <div class="mt-4 flex flex-col gap-3">
                            <div
                                v-for="alert in alerts"
                                :key="alert.title"
                                class="flex items-center justify-between rounded-2xl border border-surface-100 px-4 py-3 text-sm dark:border-surface-800"
                            >
                                <div>
                                    <p class="font-semibold text-surface-900 dark:text-surface-50">{{ alert.title }}</p>
                                    <p class="text-xs text-surface-500 dark:text-surface-400">{{ alert.time }}</p>
                                </div>
                                <span :class="['rounded-full px-3 py-1 text-xs font-semibold', alert.tone]">
                                    확인
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="rounded-3xl border border-surface-200 bg-white p-6 shadow-lg shadow-surface-900/10 dark:border-surface-800 dark:bg-surface-900/80">
                        <h2 class="text-lg font-semibold text-surface-900 dark:text-surface-50">오늘의 작업</h2>
                        <div class="mt-4 flex flex-col gap-3">
                            <div
                                v-for="task in tasks"
                                :key="task.title"
                                class="rounded-2xl bg-surface-100 px-4 py-3 text-sm text-surface-700 dark:bg-surface-800/70 dark:text-surface-200"
                            >
                                <p class="font-semibold text-surface-900 dark:text-surface-50">{{ task.title }}</p>
                                <div class="mt-2 flex items-center justify-between text-xs text-surface-500 dark:text-surface-400">
                                    <span>{{ task.status }}</span>
                                    <span>{{ task.owner }}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</template>
