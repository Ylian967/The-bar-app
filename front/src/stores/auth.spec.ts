import { beforeEach, describe, expect, it, vi } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { useAuthStore } from './auth'
import { authService } from '../services/auth'

vi.mock('../services/auth', () => ({
  authService: { login: vi.fn() },
}))

describe('store auth', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    localStorage.clear()
  })

  it('non connecté par défaut', () => {
    const a = useAuthStore()
    expect(a.estConnecte).toBe(false)
    expect(a.estBarmaker).toBe(false)
  })

  it('login stocke le jeton et le rôle barmaker', async () => {
    vi.mocked(authService.login).mockResolvedValue({ token: 'tok', email: 'b@b.fr', role: 'BARMAKER', nom: 'Ylian' })
    const a = useAuthStore()
    await a.login('b@b.fr', 'pwd')
    expect(a.estConnecte).toBe(true)
    expect(a.estBarmaker).toBe(true)
    expect(localStorage.getItem('token')).toBe('tok')
  })

  it('logout efface le jeton', async () => {
    vi.mocked(authService.login).mockResolvedValue({ token: 'tok', email: 'b', role: 'BARMAKER', nom: 'Y' })
    const a = useAuthStore()
    await a.login('b', 'p')
    a.logout()
    expect(a.estConnecte).toBe(false)
    expect(localStorage.getItem('token')).toBeNull()
  })
})
