import tailwindcss from "@tailwindcss/vite";

// https://nuxt.com/docs/api/configuration/nuxt-config
const apiUrl = process.env.NODE_ENV === 'development'
    ? 'http://localhost:18080'
    : 'http://localhost:33080'

export default defineNuxtConfig({
    compatibilityDate: '2025-07-15',
    modules: [
        '@pinia/nuxt',
        '@nuxtjs/color-mode'
    ],
    css: [
        '~/assets/css/style.css'
    ],
    colorMode: {
        preference: 'system',
        fallback: 'dark',
        storageKey: 'color-mode'
    },
    vite: {
        plugins: [
            tailwindcss(),
        ],
    },
    nitro: {
        routeRules: {
            '/api/auth/**': {
                proxy: `${apiUrl}/api/auth/**`
            },
            '/api/**': {
                proxy: `${apiUrl}/api/site/**`
            }
        }
    },
    devtools: {enabled: true}
})