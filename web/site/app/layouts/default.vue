<script setup lang="ts">
const colorMode = useColorMode()
const isDark = computed(() => colorMode.value === 'dark')
const toggleColorMode = () => {
    colorMode.preference = isDark.value ? 'light' : 'dark'
}
</script>

<template>
    <div class="min-h-screen bg-surface-50 text-surface-900 dark:bg-surface-950 dark:text-surface-100">
        <header class="border-b border-surface-200/80 bg-white/80 backdrop-blur dark:border-surface-800 dark:bg-surface-950/80">
            <div class="mx-auto flex w-full max-w-6xl items-center justify-between px-6 py-4">
                <div class="text-sm font-semibold uppercase tracking-[0.35em] text-surface-500 dark:text-surface-400">
                    ZOD Site
                </div>
                <div class="flex items-center gap-4">
                    <nav class="flex items-center gap-6 text-sm text-surface-600 dark:text-surface-300">
                        <a class="transition hover:text-surface-900 dark:hover:text-surface-50" href="#">
                            개요
                        </a>
                        <a class="transition hover:text-surface-900 dark:hover:text-surface-50" href="#">
                            기능
                        </a>
                        <a class="transition hover:text-surface-900 dark:hover:text-surface-50" href="#">
                            문의
                        </a>
                    </nav>
                    <ClientOnly>
                        <button
                            type="button"
                            class="rounded-full border border-surface-200 px-3 py-1 text-xs font-semibold text-surface-700 transition hover:border-surface-300 hover:text-surface-900 dark:border-surface-700 dark:text-surface-200 dark:hover:border-surface-500 dark:hover:text-surface-50"
                            @click="toggleColorMode"
                        >
                            {{ isDark ? '라이트 모드' : '다크 모드' }}
                        </button>
                        <template #fallback>
                            <span class="inline-flex h-7 w-20" />
                        </template>
                    </ClientOnly>
                </div>
            </div>
        </header>
        <slot />
        <footer class="border-t border-surface-200/80 bg-white/80 backdrop-blur dark:border-surface-800 dark:bg-surface-950/80">
            <div class="mx-auto flex w-full max-w-6xl flex-col gap-3 px-6 py-6 text-xs text-surface-500 dark:text-surface-400 md:flex-row md:items-center md:justify-between">
                <span>© 2025 ZOD Site. All rights reserved.</span>
                <span>문의: support@zod.app</span>
            </div>
        </footer>
    </div>
</template>
