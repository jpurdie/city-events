<template>
  <div class="mb-3">
    <!-- Label -->
    <label v-if="label" :for="computedId" class="form-label">
      <slot name="label">{{ label }}</slot>
    </label>

    <!-- Select Dropdown -->
    <select
      :id="computedId"
      v-model="model"
      :class="selectClass"
      :disabled="disabled"
      @change="emit('update:modelValue', ($event.target as HTMLSelectElement).value)"
    >
      <option v-if="placeholder" disabled value="">{{ placeholder }}</option>
      <option v-for="option in options" :key="optionValue(option)" :value="optionValue(option)">
        {{ optionLabel(option) }}
      </option>
    </select>

    <!-- Validation/Error Message -->
    <small v-if="errorMessage" class="text-danger">
      <slot name="error">{{ errorMessage }}</slot>
    </small>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, computed, useId } from 'vue'
import { useSelect } from '@/composables/useBaseSelect'
import type { SelectSize, SelectOption } from '@/composables/useBaseSelect'

const props = defineProps<{
  modelValue: string
  id?: string
  label?: string
  size?: SelectSize
  placeholder?: string
  disabled?: boolean
  errorMessage?: string
  options: SelectOption[]
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
}>()

// ✅ Vue 3.3+ automatically generates a unique ID per component instance
const computedId = computed(() => props.id ?? useId())

const { model, selectClass, optionValue, optionLabel } = useSelect(props, emit)
</script>
