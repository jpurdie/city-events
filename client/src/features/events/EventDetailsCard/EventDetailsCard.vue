<template>
  <BaseCard :outlined="true" variant="dark">
    <!-- Event Image -->
    <template #header>
      <img :src="imageUrl" class="card-img-top" alt="image description" />
    </template>

    <!-- Event Details -->
    <h5 class="card-title">{{ event.name }}</h5>
    <h6 class="card-text">{{ event.description }}</h6>
    <h6 class="card-text text-muted">Starts: {{ formatDate(event.startingAt) }}</h6>
    <h6 class="card-text text-muted">Ends: {{ formatDate(event.endingAt) }}</h6>

    <!-- Registration Section -->
    <template #footer>
      <div v-if="loading" class="d-flex align-items-center">
        <div class="spinner-border text-primary me-2" role="status">
          <span class="visually-hidden">Processing...</span>
        </div>
        <span>Processing registration...</span>
      </div>

      <div v-else>
        <!-- Success Message -->
        <!-- <div v-if="success" class="alert alert-success py-2 mb-2" role="alert">
          {{ isRegistered ? 'Registration updated!' : 'Registration successful!' }}
        </div> -->

        <!-- Error Message -->
        <div v-if="error" class="alert alert-danger py-2 mb-2" role="alert">
          {{ error }}
        </div>

        <!-- Existing Registration Info -->
        <div v-if="eventRegistration" class="alert alert-info py-2 mb-2">
          <p>You're Registered!</p>
          <p><strong>Qty Attending:</strong> {{ selectedQuantity }}</p>
          <!-- Delete Registration Button with Confirmation -->
          <BaseButton type="button" @click="confirmDelete" variant="danger" :disabled="loading">
            Cancel Registration
          </BaseButton>
        </div>

        <div v-else>
          <!-- Register or Update Button -->
          <BaseForm @submit="handleRegistration" v-if="authStore.isAuthenticated" hideSubmitButton>
            <BaseSelect
              v-model="selectedQuantity"
              label="Qty Attending"
              :options="quantities"
              placeholder="Select Quantity"
            />
            <button type="submit" class="btn btn-primary">
              {{ isRegistered ? 'Update Registration' : 'Register' }}
            </button>
          </BaseForm>
        </div>

        <!-- Unauthenticated users are prompted to sign in -->
        <BaseLink v-if="!authStore.isAuthenticated" to="/signin" variant="secondary">
          Sign In to Register
        </BaseLink>
      </div>
    </template>
  </BaseCard>
</template>

<script setup lang="ts">
import { defineProps, onMounted, ref, computed } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import BaseCard from '@/components/BaseCard.vue'
import BaseForm from '@/components/BaseForm.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseSelect from '@/components/BaseSelect.vue'
import BaseLink from '@/components/BaseLink.vue'
import { useEventDetailsCard } from './composables/useEventDetailsCard'

const props = defineProps<{
  event: {
    eventId: string
    name: string
    description: string
    startingAt: string
    endingAt: string
  }
}>()
const authStore = useAuthStore()

const {
  imageUrl,
  formatDate,
  quantities,
  selectedQuantity,
  registerForEvent,
  getEventRegistrationById,
  deleteEventRegistration,
  eventRegistration,
  loading,
  error,
  success,
} = useEventDetailsCard(props.event)

// Check if the user is registered
const isRegistered = computed(() => eventRegistration.value !== null)

// Fetch registration when component mounts
onMounted(async () => {
  if (authStore.userId) {
    await getEventRegistrationById(props.event.eventId)
    if (eventRegistration.value) {
      selectedQuantity.value = eventRegistration.value.qtyAttending.toString()
    }
  }
})

// Handle registration or update
const handleRegistration = async () => {
  if (isRegistered.value && eventRegistration.value) {
    // Delete existing registration first
    await deleteEventRegistration(eventRegistration.value.eventRegistrationId)
  }
  // Register with new details
  await registerForEvent()
}

// Handle delete confirmation
const confirmDelete = () => {
  if (confirm('Are you sure you want to cancel your registration?')) {
    deleteRegistration()
  }
}

// Delete registration
const deleteRegistration = async () => {
  if (!eventRegistration.value || !eventRegistration.value.eventId) {
    error.value = 'Unable to delete registration. Registration ID is missing.'
    return
  }
  await deleteEventRegistration(eventRegistration.value.eventId)
}
</script>
