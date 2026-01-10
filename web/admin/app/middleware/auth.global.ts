export default defineNuxtRouteMiddleware((to) => {
    if (to.path === '/') return
    const userStore = useUserStore()
    if (!userStore.accessToken) {
        userStore.hydrateFromCookie()
    }
    if (!userStore.accessToken) {
        return navigateTo('/')
    }
})
