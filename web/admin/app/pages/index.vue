<script setup lang="ts">
definePageMeta({
    layout: false
})

const userid = ref('')
const password = ref('')
const isSubmitting = ref(false)
const colorMode = useColorMode()
const isDark = computed(() => colorMode.value === 'dark')
const toggleColorMode = () => {
    colorMode.preference = isDark.value ? 'light' : 'dark'
}

const submitLogin = async () => {
    if (!userid.value || !password.value) {
        alert('아이디와 비밀번호를 입력해 주세요.')
        return
    }

    isSubmitting.value = true
    try {
        await $fetch('/api/auth/login', {
            method: 'POST',
            body: {
                userid: userid.value,
                password: password.value
            }
        })
        alert('로그인에 성공했습니다.')
    } catch (error) {
        alert('로그인에 실패했습니다. 다시 시도해 주세요.')
    } finally {
        isSubmitting.value = false
    }
}
</script>

<template>
    <main
        class="relative min-h-screen bg-gradient-to-br from-surface-50 to-surface-100 text-surface-900 dark:from-surface-900 dark:to-surface-950 dark:text-surface-100"
    >
        <ClientOnly>
            <button
                type="button"
                class="absolute right-4 top-4 z-10 rounded-full border border-surface-200 px-3 py-1 text-xs font-semibold text-surface-700 transition hover:border-surface-300 hover:text-surface-900 dark:border-surface-700 dark:text-surface-200 dark:hover:border-surface-500 dark:hover:text-surface-50"
                @click="toggleColorMode"
            >
                {{ isDark ? '라이트 모드' : '다크 모드' }}
            </button>
            <template #fallback>
                <span class="absolute right-4 top-4 inline-flex h-7 w-20" />
            </template>
        </ClientOnly>
        <section class="mx-auto flex min-h-screen w-full max-w-md items-center px-5 py-12">
            <div
                class="relative w-full rounded-2xl bg-white p-8 shadow-xl shadow-surface-900/10 dark:bg-surface-900/80 dark:shadow-black/40">
                <header class="flex items-start justify-between gap-6">
                    <div>
                        <p class="text-xs font-semibold uppercase tracking-[0.2em] text-surface-500 dark:text-surface-400">
                            Admin Console
                        </p>
                        <h1 class="mt-2 text-2xl font-semibold text-surface-900 dark:text-surface-50">
                            로그인
                        </h1>
                        <p class="mt-1 text-sm text-surface-500 dark:text-surface-400">
                            관리자 계정으로 접속해 주세요.
                        </p>
                    </div>
                </header>

                <form class="mt-6 flex flex-col gap-4" @submit.prevent="submitLogin">
                    <label class="flex flex-col gap-2 text-sm font-medium text-surface-700 dark:text-surface-200">
                        <span>아이디</span>
                        <input
                            v-model="userid"
                            type="text"
                            autocomplete="userid"
                            placeholder="아이디를 입력하세요"
                            class="rounded-xl border border-surface-200 bg-white px-4 py-3 text-base text-surface-900 placeholder:text-surface-400 focus:border-primary-500 focus:ring-2 focus:ring-primary-200 dark:border-surface-700 dark:bg-surface-900 dark:text-surface-50 dark:placeholder:text-surface-500 dark:focus:border-primary-400 dark:focus:ring-primary-500/40"
                        />
                    </label>
                    <label class="flex flex-col gap-2 text-sm font-medium text-surface-700 dark:text-surface-200">
                        <span>비밀번호</span>
                        <input
                            v-model="password"
                            type="password"
                            autocomplete="current-password"
                            placeholder="비밀번호를 입력하세요"
                            class="rounded-xl border border-surface-200 bg-white px-4 py-3 text-base text-surface-900 placeholder:text-surface-400 focus:border-primary-500 focus:ring-2 focus:ring-primary-200 dark:border-surface-700 dark:bg-surface-900 dark:text-surface-50 dark:placeholder:text-surface-500 dark:focus:border-primary-400 dark:focus:ring-primary-500/40"
                        />
                    </label>

                    <button
                        class="mt-2 w-full rounded-full bg-primary-600 px-4 py-3 text-sm font-semibold text-white transition hover:bg-primary-700 disabled:cursor-not-allowed disabled:opacity-60 dark:bg-primary-500 dark:text-surface-950 dark:hover:bg-primary-400"
                        type="submit"
                        :disabled="isSubmitting"
                    >
                        {{ isSubmitting ? '로그인 중...' : '로그인' }}
                    </button>
                </form>
            </div>
        </section>
    </main>
</template>
