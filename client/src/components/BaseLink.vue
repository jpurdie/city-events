<template>
  <component
    :is="isExternal ? 'a' : 'router-link'"
    :href="isExternal ? to : undefined"
    :to="isExternal ? undefined : to"
    :target="isExternal ? '_blank' : undefined"
    :rel="isExternal ? 'noopener noreferrer' : undefined"
    :class="linkClass"
    @click="emit('click', $event)"
  >
    <slot></slot>
  </component>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, computed } from 'vue'
import { useLink } from '@/composables/useBaseLink'
import type { LinkVariant } from '@/composables/useBaseLink'

const props = defineProps<{
  to: string
  variant?: LinkVariant
  size?: 'sm' | 'lg'
  block?: boolean
  outline?: boolean
}>()

const emit = defineEmits<{ (e: 'click', event: MouseEvent): void }>()

const { isExternal, linkClass } = useLink(props)
</script>
