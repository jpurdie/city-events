<template>
  <button :type="type" :class="buttonClass" :disabled="disabled" @click="emit('click', $event)">
    <slot></slot>
  </button>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, withDefaults } from 'vue'
import { useButton } from '@/composables/useButton'
import type { ButtonVariant } from '@/composables/useButton' // ✅ Type-only import

// Define props with type safety
const props = withDefaults(
  defineProps<{
    variant?: ButtonVariant
    size?: 'sm' | 'lg'
    type?: 'button' | 'submit' | 'reset'
    block?: boolean
    disabled?: boolean
    outline?: boolean
  }>(),
  {
    variant: 'primary',
    type: 'button',
    block: false,
    disabled: false,
    outline: false,
  },
)

// Use composable for class logic
const { buttonClass } = useButton(props)

// Define emits
const emit = defineEmits<{
  (e: 'click', event: MouseEvent): void
}>()
</script>
