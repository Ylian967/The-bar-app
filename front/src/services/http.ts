import axios from 'axios'

/** URL de base de l'API (surchargée via VITE_API_URL en Docker). */
export const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080'

const http = axios.create({ baseURL: API_URL })

// Ajoute automatiquement le jeton JWT s'il est présent.
http.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

/** Construit l'URL complète d'une image servie par le back. */
export function imageUrl(chemin: string | null): string {
  if (!chemin) return ''
  return chemin.startsWith('http') ? chemin : API_URL + chemin
}

export default http
