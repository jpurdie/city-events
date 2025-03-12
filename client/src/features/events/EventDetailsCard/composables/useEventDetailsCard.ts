import { computed, ref } from 'vue'
import { useAuthStore } from '@/stores/authStore'

export function useEventDetailsCard(event: { eventId: string }) {
  const authStore = useAuthStore()
  const imageUrl = computed(() => `https://imageplaceholder.net/200`)

  const quantities = computed(() =>
    Array.from({ length: 10 }, (_, i) => ({ label: `${i + 1}`, value: `${i + 1}` })),
  )

  const selectedQuantity = ref('1')
  const loading = ref(false)
  const error = ref<string | null>(null)
  const success = ref<boolean>(false)
  const eventRegistration = ref<any | null>(null)

  const lambda_registration_events_url =
    'https://p26w3r3iws7brbzce46wj3s6em0fnygc.lambda-url.us-west-2.on.aws'
  // Fetch single event registration
  const getEventRegistrationById = async (eventId: string) => {
    loading.value = true
    error.value = null

    try {
      const response = await fetch(`${lambda_registration_events_url}/${eventId}`)

      if (response.status === 404) {
        eventRegistration.value = null // Expected case, don't log
        return
      }

      if (!response.ok) throw new Error('Failed to fetch event registration.')

      eventRegistration.value = await response.json()
    } catch (err: any) {
      if (err.message !== 'Failed to fetch event registration.') {
        console.error(err) // Log only unexpected errors
      }
      error.value = err.message
    } finally {
      loading.value = false
    }
  }

  // Delete existing registration
  const deleteEventRegistration = async (eventId: string) => {
    loading.value = true
    error.value = null
    try {
      const response = await fetch(`${lambda_registration_events_url}/${eventId}`, {
        method: 'DELETE',
      })
      if (!response.ok) throw new Error('Failed to delete registration.')
      eventRegistration.value = null
    } catch (err: any) {
      error.value = err.message
    } finally {
      loading.value = false
    }
  }

  // Register for an event (after deletion)
  const registerForEvent = async () => {
    if (!authStore.userId) {
      error.value = 'You must be signed in to register.'
      return
    }

    loading.value = true
    error.value = null
    success.value = false

    try {
      const response = await fetch(lambda_registration_events_url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          eventId: event.eventId,
          userId: authStore.userId,
          qtyAttending: selectedQuantity.value,
          registrationStatus: 'GOING',
        }),
      })

      if (!response.ok) {
        const responseData = await response.json()
        throw new Error(responseData.message || 'Failed to register for event.')
      }

      success.value = true
      await getEventRegistrationById(event.eventId) // Refresh registration data
    } catch (err: any) {
      error.value = err.message
    } finally {
      loading.value = false
    }
  }
  const formatDate = (isoString: string) => {
    return new Date(isoString).toLocaleDateString('en-US', {
      weekday: 'short',
      month: 'short',
      day: 'numeric',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    })
  }
  return {
    imageUrl,
    formatDate,
    quantities,
    selectedQuantity,
    registerForEvent,
    deleteEventRegistration,
    getEventRegistrationById,
    eventRegistration,
    loading,
    error,
    success,
  }
}
