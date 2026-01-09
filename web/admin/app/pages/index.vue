<script setup lang="ts">
import { ref } from 'vue'

const username = ref('')
const password = ref('')
const isSubmitting = ref(false)

const submitLogin = async () => {
    if (!username.value || !password.value) {
        alert('아이디와 비밀번호를 입력해 주세요.')
        return
    }

    isSubmitting.value = true
    try {
        await $fetch('/api/auth/login', {
            method: 'POST',
            body: {
                username: username.value,
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
    <main class="login">
        <section class="card">
            <header class="header">
                <p class="eyebrow">Admin Console</p>
                <h1>로그인</h1>
                <p class="sub">관리자 계정으로 접속해 주세요.</p>
            </header>

            <form class="form" @submit.prevent="submitLogin">
                <label class="field">
                    <span>아이디</span>
                    <input
                        v-model="username"
                        type="text"
                        autocomplete="username"
                        placeholder="아이디를 입력하세요"
                    />
                </label>
                <label class="field">
                    <span>비밀번호</span>
                    <input
                        v-model="password"
                        type="password"
                        autocomplete="current-password"
                        placeholder="비밀번호를 입력하세요"
                    />
                </label>

                <button class="submit" type="submit" :disabled="isSubmitting">
                    {{ isSubmitting ? '로그인 중...' : '로그인' }}
                </button>
            </form>
        </section>
    </main>
</template>

<style scoped>
.login {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 48px 20px;
    background: linear-gradient(145deg, #f4f6f9, #e8eef4);
    color: #1f2a37;
}

.card {
    width: min(420px, 100%);
    padding: 32px;
    border-radius: 18px;
    background: #ffffff;
    box-shadow: 0 22px 45px rgba(15, 23, 42, 0.12);
}

.header h1 {
    margin: 8px 0 4px;
    font-size: 28px;
}

.eyebrow {
    text-transform: uppercase;
    letter-spacing: 0.08em;
    font-size: 12px;
    color: #6b7280;
}

.sub {
    margin: 0;
    color: #6b7280;
}

.form {
    margin-top: 24px;
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.field {
    display: flex;
    flex-direction: column;
    gap: 8px;
    font-size: 14px;
    color: #374151;
}

.field input {
    padding: 12px 14px;
    border-radius: 10px;
    border: 1px solid #d1d5db;
    font-size: 15px;
}

.field input:focus {
    outline: 2px solid #2563eb;
    border-color: transparent;
}

.submit {
    margin-top: 8px;
    padding: 12px 16px;
    border-radius: 999px;
    border: none;
    background: #111827;
    color: #fff;
    font-weight: 600;
    cursor: pointer;
}

.submit:disabled {
    cursor: not-allowed;
    opacity: 0.6;
}
</style>
