<script setup lang="ts">
const userStore = useUserStore()
const colorMode = useColorMode()
const isDark = computed(() => colorMode.value === 'dark')
const toggleColorMode = () => {
    colorMode.preference = isDark.value ? 'light' : 'dark'
}
const { $api } = useNuxtApp()

const isLoggingOut = ref(false)

const logout = async () => {
    if (isLoggingOut.value) return
    isLoggingOut.value = true
    try {
        await $api('/api/auth/logout', {
            method: 'POST',
            body: {
                userid: userStore.profile?.userid || '',
                refreshToken: userStore.refreshToken
            }
        })
    } catch (error) {
        // Logout should still clear local session even if the API fails.
    } finally {
        userStore.clearSession()
        isLoggingOut.value = false
        await navigateTo('/?logout=1')
    }
}
</script>

<template>
    <div class="min-h-screen bg-surface-50 text-surface-900 dark:bg-surface-950 dark:text-surface-100">
        <div class="flex min-h-screen">
            <aside class="hidden w-64 flex-col border-r border-surface-200 bg-white px-5 py-6 dark:border-surface-800 dark:bg-surface-900 lg:flex">
                <div>
                    <p class="text-xs font-semibold uppercase tracking-[0.3em] text-surface-500 dark:text-surface-400">
                        Admin Suite
                    </p>
                    <p class="mt-2 text-lg font-semibold text-surface-900 dark:text-surface-50">
                        운영 대시보드
                    </p>
                </div>
                <nav class="mt-8 flex flex-1 flex-col gap-2 text-sm font-semibold text-surface-600 dark:text-surface-300">
                    <a class="rounded-xl bg-primary-50 px-4 py-3 text-primary-700 dark:bg-primary-500/15 dark:text-primary-200">
                        대시보드
                    </a>
                    <a class="rounded-xl px-4 py-3 transition hover:bg-surface-100 hover:text-surface-900 dark:hover:bg-surface-800/70 dark:hover:text-surface-50">
                        회원 관리
                    </a>
                    <a class="rounded-xl px-4 py-3 transition hover:bg-surface-100 hover:text-surface-900 dark:hover:bg-surface-800/70 dark:hover:text-surface-50">
                        파트너 승인
                    </a>
                    <a class="rounded-xl px-4 py-3 transition hover:bg-surface-100 hover:text-surface-900 dark:hover:bg-surface-800/70 dark:hover:text-surface-50">
                        정산 리포트
                    </a>
                    <a class="rounded-xl px-4 py-3 transition hover:bg-surface-100 hover:text-surface-900 dark:hover:bg-surface-800/70 dark:hover:text-surface-50">
                        설정
                    </a>
                </nav>
                <footer class="text-xs text-surface-400 dark:text-surface-500">
                    © 2025 Routine Admin
                </footer>
            </aside>

            <div class="flex min-h-screen flex-1 flex-col">
                <header class="sticky top-0 z-30 border-b border-surface-200 bg-white/80 px-6 py-4 backdrop-blur dark:border-surface-800 dark:bg-surface-950/80">
                    <div class="flex items-center justify-between">
                        <div class="flex items-center gap-3 lg:hidden">
                            <div class="h-10 w-10 rounded-2xl bg-primary-600" />
                            <span class="text-sm font-semibold">Admin</span>
                        </div>
                        <div class="hidden text-sm text-surface-500 dark:text-surface-400 lg:block">
                            {{ userStore.profile?.nickname ? `${userStore.profile.nickname}님, 환영합니다.` : '환영합니다.' }}
                        </div>
                        <div class="flex items-center gap-3">
                            <button
                                type="button"
                                class="rounded-full border border-surface-200 px-3 py-1 text-xs font-semibold text-surface-600 transition hover:border-surface-300 hover:text-surface-900 dark:border-surface-700 dark:text-surface-300 dark:hover:border-surface-500 dark:hover:text-surface-50"
                                @click="toggleColorMode"
                            >
                                {{ isDark ? '라이트' : '다크' }}
                            </button>
                            <button
                                type="button"
                                class="rounded-full border border-surface-200 px-3 py-1 text-xs font-semibold text-surface-600 transition hover:border-surface-300 hover:text-surface-900 disabled:cursor-not-allowed disabled:opacity-60 dark:border-surface-700 dark:text-surface-300 dark:hover:border-surface-500 dark:hover:text-surface-50"
                                :disabled="isLoggingOut"
                                @click="logout"
                            >
                                {{ isLoggingOut ? '로그아웃 중...' : '로그아웃' }}
                            </button>
                        </div>
                    </div>
                </header>

                <main class="flex-1">
                    <slot />
                </main>

                <footer class="border-t border-surface-200 px-6 py-4 text-xs text-surface-500 dark:border-surface-800 dark:text-surface-400">
                    운영센터 · 문의 admin@routine.io · 버전 1.0.0
                </footer>
            </div>
        </div>
    </div>
</template>
