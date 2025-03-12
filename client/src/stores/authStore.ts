import { defineStore } from 'pinia'
import { ref, onMounted } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const isAuthenticated = ref(false)
  const userId = ref<string | null>(null)

  // Load userId from sessionStorage when store initializes
  onMounted(() => {
    const storedUserId = sessionStorage.getItem('userId')
    if (storedUserId) {
      userId.value = storedUserId
      isAuthenticated.value = true
    }
  })

  const login = (id: string) => {
    userId.value = id
    isAuthenticated.value = true
    sessionStorage.setItem('userId', id) // Save userId to sessionStorage
  }

  const logout = () => {
    userId.value = null
    isAuthenticated.value = false
    sessionStorage.removeItem('userId') // Remove userId from sessionStorage
  }

  return { isAuthenticated, userId, login, logout }
})
