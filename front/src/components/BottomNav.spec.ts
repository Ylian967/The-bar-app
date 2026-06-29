import { beforeEach, describe, expect, it } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { mount } from '@vue/test-utils'
import BottomNav from './BottomNav.vue'
import { usePanierStore } from '../stores/panier'
import type { Cocktail } from '../types/models'

const stubs = { RouterLink: { template: '<a><slot /></a>' } }
const cocktail: Cocktail = {
  id: 1, nom: 'X', accroche: null, description: null, imageUrl: null,
  categorieId: 1, categorie: 'C', tailles: [], ingredients: [],
}

describe('BottomNav', () => {
  beforeEach(() => setActivePinia(createPinia()))

  it('affiche les trois onglets', () => {
    const w = mount(BottomNav, { global: { stubs } })
    expect(w.text()).toContain('Accueil')
    expect(w.text()).toContain('Panier')
    expect(w.text()).toContain('Commandes')
  })

  it('affiche le nombre d\'articles du panier', () => {
    usePanierStore().ajouter(cocktail, 'M', 8, 3)
    const w = mount(BottomNav, { global: { stubs } })
    expect(w.text()).toContain('3')
  })
})
