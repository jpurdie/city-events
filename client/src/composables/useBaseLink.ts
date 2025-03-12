import { computed } from 'vue'

// Define Bootstrap link variants
export type LinkVariant =
  | 'primary'
  | 'secondary'
  | 'success'
  | 'danger'
  | 'warning'
  | 'info'
  | 'light'
  | 'dark'

// Define the props structure
interface LinkProps {
  to: string
  variant?: LinkVariant
  size?: 'sm' | 'lg'
  block?: boolean
  outline?: boolean
}

// Extract reusable link logic
export function useLink(props: LinkProps) {
  const isExternal = computed(() => props.to.startsWith('http'))

  const linkClass = computed(() => [
    'btn',
    props.outline ? `btn-outline-${props.variant}` : `btn-${props.variant}`,
    props.size ? `btn-${props.size}` : '',
    props.block ? 'w-100' : '',
  ])

  return { isExternal, linkClass }
}
