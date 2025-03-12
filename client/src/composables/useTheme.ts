import { computed } from 'vue'

export function useTheme(variant: string, outline: boolean) {
  return computed(() => (outline ? `btn-outline-${variant}` : `btn-${variant}`))
}
