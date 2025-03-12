<template>
  <MainLayout title="Events">
    <div v-if="loading">Loading events...</div>
    <div v-else-if="error" class="text-danger">{{ error }}</div>
    <EventList v-else :events="events" />
  </MainLayout>
</template>

<script setup>
import { ref } from 'vue'
import MainLayout from '@/components/MainLayout.vue'
import EventList from '@/features/events/EventList/EventList.vue'
import { useEvents } from '@/features/events/composables/useEvents'

// Fetch events from API
const { events, loading, error } = useEvents()
const selectedEventId = ref(null)

// Handle signup
const handleSignup = (data) => {
  alert(`Signed up for event ID: ${data.eventId}`)
}
</script>
