<template>
  <MainLayout title="Sign In">
    <div class="d-flex justify-content-center mt-5">
      <BaseCard class="p-4" variant="">
        <template #header>
          <h3 class="text-center">Sign In</h3>
        </template>

        <BaseForm @submit="handleSignIn" :loading="loading" submitText="Sign In">
          <BaseInput v-model="email" type="email" label="Email Address" placeholder="Enter email" />
          <BaseInput
            v-model="password"
            type="password"
            label="Password"
            placeholder="Enter password"
          />
          <template #submit-text>Sign In</template>
        </BaseForm>
      </BaseCard>
    </div>
  </MainLayout>
</template>

<script setup>
import MainLayout from '@/components/MainLayout.vue'
import BaseForm from '@/components/BaseForm.vue'
import BaseInput from '@/components/BaseInput.vue'
import BaseCard from '@/components/BaseCard.vue'
import { useSignIn } from './composables/useSignIn'

const { email, password, loading, handleSignIn } = useSignIn()
</script>
