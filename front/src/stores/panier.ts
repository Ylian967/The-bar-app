import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import type { Cocktail, Taille } from '../types/models'

export interface ArticlePanier {
  cocktailId: number
  nom: string
  imageUrl: string | null
  taille: Taille
  prix: number
  quantite: number
}

/** Panier du client (en mémoire le temps de la session). */
export const usePanierStore = defineStore('panier', () => {
  const articles = ref<ArticlePanier[]>([])

  const total = computed(() => articles.value.reduce((s, a) => s + a.prix * a.quantite, 0))
  const nbArticles = computed(() => articles.value.reduce((s, a) => s + a.quantite, 0))

  function ajouter(cocktail: Cocktail, taille: Taille, prix: number, quantite = 1) {
    const existant = articles.value.find((a) => a.cocktailId === cocktail.id && a.taille === taille)
    if (existant) {
      existant.quantite += quantite
    } else {
      articles.value.push({
        cocktailId: cocktail.id,
        nom: cocktail.nom,
        imageUrl: cocktail.imageUrl,
        taille,
        prix,
        quantite,
      })
    }
  }

  function changerQuantite(index: number, delta: number) {
    const article = articles.value[index]
    if (!article) return
    article.quantite += delta
    if (article.quantite <= 0) articles.value.splice(index, 1)
  }

  function vider() {
    articles.value = []
  }

  return { articles, total, nbArticles, ajouter, changerQuantite, vider }
})
