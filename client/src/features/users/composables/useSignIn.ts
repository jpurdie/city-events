import { ref } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import { useRouter } from 'vue-router'

export function useSignIn() {
  const router = useRouter()
  const authStore = useAuthStore()

  const email = ref('foo@example.com')
  const password = ref('Mock123456!')
  const loading = ref(false)

  const handleSignIn = () => {
    loading.value = true

    setTimeout(() => {
      const fakeUserId = '1234567890' // Simulated user ID from authn system
      authStore.login(fakeUserId)
      loading.value = false
      router.push('/events')
    }, 1000)
  }

  return { email, password, loading, handleSignIn }
}
