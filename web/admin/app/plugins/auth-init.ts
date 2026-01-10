export default defineNuxtPlugin(async () => {
    const userStore = useUserStore()
    const refreshCookie = useCookie<string>('admin_refresh_token')

    if (!refreshCookie.value) return

    try {
        const response = await $fetch<{ code: string; data?: { accessToken: string; refreshToken: string } }>(
            '/api/auth/refresh',
            {
                method: 'POST',
                body: {
                    refreshToken: refreshCookie.value
                }
            }
        )

        if (response.code === 'SUCCESS' && response.data) {
            userStore.setSession(response.data)
        } else {
            userStore.clearSession()
        }
    } catch {
        userStore.clearSession()
    }
})
