import { computed, ref, watch } from 'vue'

// Define Bootstrap sizes
export type SelectSize = 'sm' | 'lg' | 'md'

// Define option structure (supports both objects & strings)
export type SelectOption = string | { label: string; value: string }

interface SelectProps {
  modelValue: string
  size?: SelectSize
  disabled?: boolean
  options: SelectOption[]
}

// Extract select logic
export function useSelect(
  props: SelectProps,
  emit: (event: 'update:modelValue', value: string) => void,
) {
  const model = ref(props.modelValue)

  watch(model, (newValue) => {
    emit('update:modelValue', newValue)
  })

  const selectClass = computed(() => [
    'form-select',
    props.size ? `form-select-${props.size}` : '',
    { 'is-disabled': props.disabled },
  ])

  // Normalize options (support objects & strings)
  const optionValue = (option: SelectOption) => (typeof option === 'string' ? option : option.value)
  const optionLabel = (option: SelectOption) => (typeof option === 'string' ? option : option.label)

  return { model, selectClass, optionValue, optionLabel }
}

let idCounter = 0

export function useId(prefix = 'input') {
  idCounter++
  return `${prefix}-${idCounter}`
}
