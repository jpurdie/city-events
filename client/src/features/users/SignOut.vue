<template>
  <MainLayout title="Sign Out">
    <div class="d-flex justify-content-center mt-5">Signing out...</div>
  </MainLayout>
</template>

<script setup>
import MainLayout from '@/components/MainLayout.vue'
import { useSignOut } from './composables/useSignOut'

// Call the function immediately when the component is mounted
const { handleSignOut } = useSignOut()
handleSignOut()
</script>
