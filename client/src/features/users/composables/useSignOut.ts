import { useAuthStore } from '@/stores/authStore'
import { useRouter } from 'vue-router'

export function useSignOut() {
  const authStore = useAuthStore()
  const router = useRouter()

  const handleSignOut = () => {
    setTimeout(() => {
      authStore.logout() // Clears sessionStorage and resets state
      router.push('/signin')
    }, 1000)
  }

  return { handleSignOut }
}
