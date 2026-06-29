import { beforeEach, describe, expect, it } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { usePanierStore } from './panier'
import type { Cocktail } from '../types/models'

const mojito: Cocktail = {
  id: 1, nom: 'Mojito', accroche: null, description: null, imageUrl: null,
  categorieId: 1, categorie: 'Classiques', duJour: true, favori: false, realisable: true, tailles: [], ingredients: [],
}

describe('store panier', () => {
  beforeEach(() => setActivePinia(createPinia()))

  it('ajoute un article et calcule le total', () => {
    const p = usePanierStore()
    p.ajouter(mojito, 'M', 8, 2)
    expect(p.articles).toHaveLength(1)
    expect(p.nbArticles).toBe(2)
    expect(p.total).toBe(16)
  })

  it('incrémente la quantité si le même article est rajouté', () => {
    const p = usePanierStore()
    p.ajouter(mojito, 'M', 8)
    p.ajouter(mojito, 'M', 8)
    expect(p.articles).toHaveLength(1)
    expect(p.articles[0].quantite).toBe(2)
  })

  it("retire l'article quand la quantité tombe à 0", () => {
    const p = usePanierStore()
    p.ajouter(mojito, 'S', 7)
    p.changerQuantite(0, -1)
    expect(p.articles).toHaveLength(0)
  })

  it('vide le panier', () => {
    const p = usePanierStore()
    p.ajouter(mojito, 'L', 10)
    p.vider()
    expect(p.articles).toHaveLength(0)
  })
})
