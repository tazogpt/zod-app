import type { FetchOptions } from 'ofetch'

declare module '#app' {
    interface NuxtApp {
        $api: <T>(
            request: Parameters<typeof $fetch<T>>[0],
            options?: FetchOptions
        ) => Promise<T>
    }
}

declare module 'vue' {
    interface ComponentCustomProperties {
        $api: <T>(
            request: Parameters<typeof $fetch<T>>[0],
            options?: FetchOptions
        ) => Promise<T>
    }
}

export {}
