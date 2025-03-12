<template>
  <div :class="cardClass">
    <!-- Card Header (if slot exists) -->
    <div v-if="$slots.header || title" class="card-header">
      <slot name="header">
        <h5 class="card-title">{{ title }}</h5>
      </slot>
    </div>
    <!-- Card Body -->
    <div class="card-body">
      <slot></slot>
    </div>
    <!-- Card Footer (if slot exists) -->
    <div v-if="$slots.footer" class="card-footer">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, withDefaults, useSlots } from 'vue'
import { useCard } from '@/composables/useBaseCard'
import type { CardVariant } from '@/composables/useBaseCard'

// Define Props
const props = withDefaults(
  defineProps<{
    title?: string
    variant?: CardVariant
    outlined?: boolean
  }>(),
  {
    variant: 'light',
    outlined: false,
  },
)

// Compute card classes
const { cardClass } = useCard(props)
</script>
