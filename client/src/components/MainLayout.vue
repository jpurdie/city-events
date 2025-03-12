<!-- src/components/MainLayout.vue -->
<template>
  <div>
    <!-- Header with Navbar -->
    <header>
      <Navbar />
    </header>

    <!-- Main Content Section -->
    <main class="container py-4">
      <h1 v-if="title" class="mb-4">{{ title }}</h1>
      <slot />
    </main>

    <!-- Footer -->
    <Footer />

    <!-- Back to Top Button -->
    <button v-if="showBackToTop" @click="scrollToTop" class="btn btn-primary back-to-top">
      ↑ Back to Top
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import Navbar from '@/components/NavBar.vue'
import Footer from '@/components/Footer.vue'

// Props: Allows setting a page title dynamically
defineProps({
  title: String,
})

// Scroll-to-top functionality
const showBackToTop = ref(false)

const handleScroll = () => {
  showBackToTop.value = window.scrollY > 300
}

const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

// Attach scroll listener
onMounted(() => window.addEventListener('scroll', handleScroll))
onUnmounted(() => window.removeEventListener('scroll', handleScroll))
</script>

<style scoped>
.back-to-top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
  display: inline-block;
}
</style>
