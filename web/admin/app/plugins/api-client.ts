export default defineNuxtPlugin(() => {
    const AUTH_ERROR_CODES = new Set(['TOKEN_EXPIRED', 'UNAUTHORIZED'])
    let refreshPromise: Promise<boolean> | null = null

    const isAuthError = (data: unknown) =>
        !!data && typeof data === 'object' && 'code' in data
        && AUTH_ERROR_CODES.has(String((data as { code?: string }).code))

    const shouldAttemptRefresh = (request: Parameters<typeof $fetch>[0]) => {
        const url = typeof request === 'string'
            ? request
            : request instanceof URL
                ? request.toString()
                : request instanceof Request
                    ? request.url
                    : ''
        return !url.includes('/api/auth/refresh')
    }

    const refreshSession = async () => {
        if (refreshPromise) return refreshPromise
        refreshPromise = (async () => {
            const userStore = useUserStore()
            const refreshCookie = useCookie<string>('admin_refresh_token')
            const refreshToken = userStore.refreshToken || refreshCookie.value
            const userid = userStore.profile?.userid
            if (!refreshToken || !userid) {
                userStore.clearSession()
                if (process.client) {
                    navigateTo('/')
                }
                return false
            }

            try {
                const response = await $fetch<{ code: string; data?: { accessToken: string; refreshToken: string } }>(
                    '/api/auth/refresh',
                    {
                        method: 'POST',
                        body: {
                            refreshToken,
                            userid
                        }
                    }
                )
                if (response.code === 'SUCCESS' && response.data) {
                    userStore.setSession(response.data)
                    return true
                }
            } catch {
                // ignore and fall through to clear session
            }

            userStore.clearSession()
            return false
        })()

        try {
            return await refreshPromise
        } finally {
            refreshPromise = null
        }
    }

    const apiFetch = $fetch.create({
        onRequest({ options }) {
            const userStore = useUserStore()
            const accessCookie = useCookie<string>('admin_access_token')
            const token = userStore.accessToken || accessCookie.value
            if (!token) return
            const headers = new Headers(options.headers || {})
            headers.set('Authorization', `Bearer ${token}`)
            options.headers = headers
        }
    })

    return {
        provide: {
            api: async <T>(
                request: Parameters<typeof $fetch>[0],
                options?: Parameters<typeof $fetch>[1]
            ) => {
                try {
                    const result = await apiFetch<T>(request, options)
                    if (shouldAttemptRefresh(request) && isAuthError(result)) {
                        const refreshed = await refreshSession()
                        if (refreshed) {
                            return await apiFetch<T>(request, options)
                        }
                        if (process.client) {
                            navigateTo('/')
                        }
                    }
                    return result
                } catch (error) {
                    const responseData = (error as { response?: { _data?: unknown }; data?: unknown })?.response?._data
                        ?? (error as { data?: unknown }).data
                    if (shouldAttemptRefresh(request) && isAuthError(responseData)) {
                        const refreshed = await refreshSession()
                        if (refreshed) {
                            return await apiFetch<T>(request, options)
                        }
                        if (process.client) {
                            navigateTo('/')
                        }
                    }
                    if ((error as { response?: { status?: number } })?.response?.status === 401) {
                        const userStore = useUserStore()
                        userStore.clearSession()
                        if (process.client) {
                            navigateTo('/')
                        }
                    }
                    throw error
                }
            }
        }
    }
})
