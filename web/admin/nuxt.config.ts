// https://nuxt.com/docs/api/configuration/nuxt-config
const apiUrl =
    process.env.NODE_ENV === 'development'
        ? 'http://localhost:18080'
        : 'http://localhost:33080'

export default defineNuxtConfig({
    compatibilityDate: '2025-07-15',
    modules: [
        '@pinia/nuxt',
        '@nuxtjs/color-mode'
    ],
    nitro: {
        routeRules: {
            '/api/auth/**': {
                proxy: `${apiUrl}/api/auth/**`
            },
            '/api/**': {
                proxy: `${apiUrl}/api/admin/**`
            }
        }
    },
    devtools: {
        enabled: true
    }
})
