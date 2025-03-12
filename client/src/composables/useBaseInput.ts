import { computed, ref, watch } from 'vue'

// Define supported input types
export type InputType = 'text' | 'email' | 'password' | 'number' | 'tel' | 'url' | 'date' | 'search'

// Define Bootstrap sizes
export type InputSize = 'sm' | 'lg' | 'md'

interface InputProps {
  modelValue: string
  type?: InputType
  size?: InputSize
  disabled?: boolean
}

// Extract input logic
export function useInput(
  props: InputProps,
  emit: (event: 'update:modelValue', value: string) => void,
) {
  const model = ref(props.modelValue)

  watch(model, (newValue) => {
    emit('update:modelValue', newValue)
  })

  const inputClass = computed(() => [
    'form-control',
    props.size ? `form-control-${props.size}` : '',
    { 'is-disabled': props.disabled },
  ])

  return { inputClass, model }
}
