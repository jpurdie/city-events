<template>
  <div class="mb-3">
    <!-- Label -->
    <label v-if="label" :for="id" class="form-label">
      <slot name="label">{{ label }}</slot>
    </label>

    <!-- Input Field -->
    <input
      :id="id"
      v-model="model"
      :type="type"
      :class="inputClass"
      :placeholder="placeholder"
      :disabled="disabled"
      @input="emit('update:modelValue', ($event.target as HTMLInputElement).value)"
    />

    <!-- Validation/Error Message -->
    <small v-if="errorMessage" class="text-danger">
      <slot name="error">{{ errorMessage }}</slot>
    </small>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, computed } from 'vue'
import { useInput } from '@/composables/useBaseInput'
import type { InputType, InputSize } from '@/composables/useBaseInput'

const props = defineProps<{
  modelValue: string
  id?: string
  label?: string
  type?: InputType
  size?: InputSize
  placeholder?: string
  disabled?: boolean
  errorMessage?: string
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
}>()

const { inputClass, model } = useInput(props, emit)
</script>
