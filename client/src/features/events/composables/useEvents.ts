import { ref, onMounted } from 'vue'

const lambda_events_url = 'https://qwvgjzgqgrnoxtvpnoxglg55fe0xlxek.lambda-url.us-west-2.on.aws'

export function useEvents() {
  const events = ref([])
  const loading = ref(true)
  const error = ref(null)

  const numEvents = 10

  async function fetchEvents() {
    loading.value = true
    error.value = null
    try {
      const response = await fetch(lambda_events_url + '?count=' + numEvents)
      if (!response.ok) throw new Error('Failed to fetch events.')
      const allEvents = await response.json()
      events.value = allEvents
    } catch (err: any) {
      error.value = err.message
    } finally {
      loading.value = false
    }
  }

  onMounted(fetchEvents) // Auto-fetch when component mounts

  return { events, loading, error, fetchEvents }
}
