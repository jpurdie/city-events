<template>
  <form :class="formClass" @submit.prevent="handleSubmit">
    <!-- Form Content -->
    <slot></slot>

    <!-- Default Submit Button (optional) -->
    <!-- Ensure the button is always rendered unless explicitly hidden -->
    <div v-if="!hideSubmitButton" class="mt-3">
      <BaseButton type="submit" :variant="submitVariant" :disabled="loading">
        <slot name="submit-text">{{ submitText || 'Submit' }}</slot>
      </BaseButton>
    </div>
  </form>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, computed } from 'vue'
import { useBaseForm } from '@/composables/useBaseForm'
import BaseButton from '@/components/BaseButton.vue'
import type { ButtonVariant } from '@/composables/useButton'

const props = defineProps<{
  loading?: boolean
  hideSubmitButton?: boolean
  submitText?: string
  submitVariant?: ButtonVariant
}>()

const emit = defineEmits<{ (e: 'submit'): void }>()

// Hook into composable for form logic
const { formClass, handleSubmit } = useBaseForm(emit)
</script>
