import { defineStore } from 'pinia'

type UserProfile = {
    userid: string
    nickname: string
    role: string
    level: number
}

type SessionPayload = {
    accessToken: string
    refreshToken: string
}

const decodeJwtPayload = (token: string) => {
    try {
        const payload = token.split('.')[1]
        if (!payload) return null
        const normalized = payload.replace(/-/g, '+').replace(/_/g, '/')
        const padded = normalized.padEnd(Math.ceil(normalized.length / 4) * 4, '=')
        const decoded = typeof window === 'undefined'
            ? Buffer.from(padded, 'base64').toString('utf-8')
            : atob(padded)
        return JSON.parse(decoded)
    } catch {
        return null
    }
}

export const useUserStore = defineStore('user', () => {
    const accessToken = ref('')
    const refreshToken = ref('')
    const profile = ref<UserProfile | null>(null)

    const hydrateFromCookie = () => {
        const accessCookie = useCookie<string>('admin_access_token')
        const refreshCookie = useCookie<string>('admin_refresh_token')
        if (accessCookie.value) {
            accessToken.value = accessCookie.value
            refreshToken.value = refreshCookie.value || ''
            const claims = decodeJwtPayload(accessCookie.value)
            if (claims) {
                profile.value = {
                    userid: String(claims.sub ?? ''),
                    nickname: String(claims.nickname ?? ''),
                    role: String(claims.role ?? ''),
                    level: Number(claims.level ?? 0)
                }
            }
        }
    }

    const setSession = (payload: SessionPayload) => {
        accessToken.value = payload.accessToken
        refreshToken.value = payload.refreshToken
        const accessCookie = useCookie<string>('admin_access_token', { sameSite: 'lax' })
        const refreshCookie = useCookie<string>('admin_refresh_token', { sameSite: 'lax' })
        accessCookie.value = payload.accessToken
        refreshCookie.value = payload.refreshToken
        const claims = decodeJwtPayload(payload.accessToken)
        if (claims) {
            profile.value = {
                userid: String(claims.sub ?? ''),
                nickname: String(claims.nickname ?? ''),
                role: String(claims.role ?? ''),
                level: Number(claims.level ?? 0)
            }
        }
    }

    const clearSession = () => {
        accessToken.value = ''
        refreshToken.value = ''
        profile.value = null
        const accessCookie = useCookie<string>('admin_access_token')
        const refreshCookie = useCookie<string>('admin_refresh_token')
        accessCookie.value = null
        refreshCookie.value = null
    }

    return {
        accessToken,
        refreshToken,
        profile,
        hydrateFromCookie,
        setSession,
        clearSession
    }
})
