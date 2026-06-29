import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // --- Client (mobile) ---
    { path: '/', name: 'carte', component: () => import('../views/client/CartePage.vue') },
    { path: '/cocktail/:id', name: 'fiche', component: () => import('../views/client/FicheCocktail.vue') },
    { path: '/panier', name: 'panier', component: () => import('../views/client/PanierPage.vue') },
    { path: '/mes-commandes', name: 'mes-commandes', component: () => import('../views/client/MesCommandes.vue') },

    // --- Barman (/admin, tablette) ---
    { path: '/admin', name: 'login', component: () => import('../views/barman/LoginAdmin.vue') },
    {
      path: '/admin/commandes',
      name: 'commandes-a-traiter',
      component: () => import('../views/barman/CommandesATraiter.vue'),
      meta: { barmaker: true },
    },
    {
      path: '/admin/commandes/:id',
      name: 'detail-commande',
      component: () => import('../views/barman/DetailCommande.vue'),
      meta: { barmaker: true },
    },
    {
      path: '/admin/carte',
      name: 'gestion-carte',
      component: () => import('../views/barman/GestionCarte.vue'),
      meta: { barmaker: true },
    },
  ],
})

// Protège les pages barman : redirige vers le login si non connecté.
router.beforeEach((to) => {
  if (to.meta.barmaker) {
    const auth = useAuthStore()
    if (!auth.estBarmaker) return { name: 'login' }
  }
  return true
})

export default router
