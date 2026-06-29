import http from './http'
import type { Categorie, Cocktail, Ingredient } from '../types/models'

/** Appels API liés à la carte (catégories, cocktails, ingrédients). */
export const catalogueService = {
  listerCategories() {
    return http.get<Categorie[]>('/api/categories').then((r) => r.data)
  },
  listerCocktails(categorieId?: number) {
    const params = categorieId ? { categorieId } : undefined
    return http.get<Cocktail[]>('/api/cocktails', { params }).then((r) => r.data)
  },
  trouverCocktail(id: number) {
    return http.get<Cocktail>(`/api/cocktails/${id}`).then((r) => r.data)
  },
  listerIngredients() {
    return http.get<Ingredient[]>('/api/ingredients').then((r) => r.data)
  },
}
