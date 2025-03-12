import { computed } from 'vue'

// Define valid Bootstrap button variants
export type ButtonVariant =
  | 'primary'
  | 'secondary'
  | 'success'
  | 'danger'
  | 'warning'
  | 'info'
  | 'light'
  | 'dark'

// Define the button props structure
interface ButtonProps {
  variant?: ButtonVariant
  size?: 'sm' | 'lg'
  block?: boolean
  outline?: boolean
}

// Extract reusable button logic
export function useButton(props: ButtonProps) {
  const buttonClass = computed(() => [
    'btn',
    props.outline ? `btn-outline-${props.variant}` : `btn-${props.variant}`,
    props.size ? `btn-${props.size}` : '',
    props.block ? 'w-100' : '',
  ])

  return { buttonClass }
}
