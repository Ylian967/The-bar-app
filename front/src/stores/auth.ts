import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { authService } from '../services/auth'

/** État d'authentification du barmaker (persisté dans le localStorage). */
export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token'))
  const role = ref<string | null>(localStorage.getItem('role'))
  const nom = ref<string | null>(localStorage.getItem('nom'))

  const estConnecte = computed(() => !!token.value)
  const estBarmaker = computed(() => role.value === 'BARMAKER')

  async function login(email: string, motDePasse: string) {
    const reponse = await authService.login(email, motDePasse)
    token.value = reponse.token
    role.value = reponse.role
    nom.value = reponse.nom
    localStorage.setItem('token', reponse.token)
    localStorage.setItem('role', reponse.role)
    localStorage.setItem('nom', reponse.nom)
  }

  function logout() {
    token.value = null
    role.value = null
    nom.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    localStorage.removeItem('nom')
  }

  return { token, role, nom, estConnecte, estBarmaker, login, logout }
})
