import http from './http'
import type { LoginResponse } from '../types/models'

/** Appel API d'authentification. */
export const authService = {
  login(email: string, motDePasse: string) {
    return http.post<LoginResponse>('/api/auth/login', { email, motDePasse }).then((r) => r.data)
  },
}
