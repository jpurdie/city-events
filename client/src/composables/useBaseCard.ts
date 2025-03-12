import { computed, toRefs, reactive } from 'vue'

// Define valid Bootstrap card variants
export type CardVariant =
  | 'primary'
  | 'secondary'
  | 'success'
  | 'danger'
  | 'warning'
  | 'info'
  | 'light'
  | 'dark'

// Define Card Props Interface
interface CardProps {
  variant?: CardVariant
  outlined?: boolean
}

// Extract reusable card logic
export function useCard(props: CardProps) {
  // ✅ Wrap props in `reactive()` before using `toRefs()`
  const state = reactive({
    variant: props.variant ?? 'light',
    outlined: props.outlined ?? false,
  })

  const { variant, outlined } = toRefs(state)

  const cardClass = computed(() => [
    'card',
    outlined.value ? `border-${variant.value} text-${variant.value}` : ``,
  ])

  return { cardClass }
}
