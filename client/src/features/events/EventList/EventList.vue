<template>
  <div class="container">
    <h2 class="mb-4">Upcoming Events</h2>

    <!-- Sorting Dropdown using BaseSelect -->
    <div class="mb-3 d-flex align-items-center">
      <BaseSelect
        v-model="sortBy"
        :options="sortOptions"
        size="sm"
        class="w-auto"
        label="Sort By"
      />
    </div>

    <!-- Event Cards -->
    <div v-if="sortedEvents.length > 0" class="row">
      <div v-for="event in sortedEvents" :key="event.eventId" class="col-md-6 col-lg-3 mb-4">
        <EventDetailsCard :event="event" />
      </div>
    </div>
    <p v-else class="text-muted text-center">No events available.</p>
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue'
import EventDetailsCard from '@/features/events/EventDetailsCard/EventDetailsCard.vue'
import BaseSelect from '@/components/BaseSelect.vue'
import { useEventSorting } from './composables/useEventList'

const props = defineProps<{
  events: {
    eventId: string
    name: string
    description: string
    startingAt: string
    endingAt: string
  }[]
}>()

// Call the composable function correctly
const { sortBy, sortOptions, sortedEvents } = useEventSorting(props.events)
</script>
