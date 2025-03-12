import { createRouter, createWebHistory } from 'vue-router'

// Import feature-based routes
import homeRoutes from '@/features/home/homeRoutes'
import eventRoutes from '@/features/events/eventRoutes'
//import registrationRoutes from '@/features/registrations/registrationRoutes'
import userRoutes from '@/features/users/userRoutes'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    ...homeRoutes,
    ...eventRoutes,
    //  ...registrationRoutes,
    ...userRoutes,
    { path: '/:pathMatch(.*)*', redirect: '/' }, // Redirect unknown routes to Home
  ],
})

export default router
