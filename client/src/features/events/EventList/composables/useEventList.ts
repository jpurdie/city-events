import { ref, computed } from 'vue'

interface Event {
  eventId: string
  name: string
  description: string
  startingAt: string
  endingAt: string
}

export function useEventSorting(events: Event[]) {
  const sortBy = ref<'name' | 'startingAt'>('name')

  const sortOptions = [
    { label: 'Name', value: 'name' },
    { label: 'Start Date', value: 'startingAt' },
  ]

  const sortedEvents = computed(() => {
    return [...events].sort((a, b) => {
      if (sortBy.value === 'name') {
        return a.name.localeCompare(b.name)
      } else if (sortBy.value === 'startingAt') {
        return new Date(a.startingAt).getTime() - new Date(b.startingAt).getTime() // ✅ Explicit conversion to number
      }
      return 0
    })
  })

  return { sortBy, sortOptions, sortedEvents }
}
