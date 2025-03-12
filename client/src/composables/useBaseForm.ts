import { computed } from 'vue'

export function useBaseForm(emit: (event: 'submit') => void) {
  const formClass = computed(() => 'needs-validation')

  const handleSubmit = () => {
    emit('submit')
  }

  return { formClass, handleSubmit }
}
