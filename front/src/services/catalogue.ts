import http from './http'
import type {
  Categorie,
  CategorieRequest,
  Cocktail,
  CocktailExterne,
  CocktailRequest,
  ImportCocktailRequest,
  Ingredient,
  IngredientRequest,
} from '../types/models'

/** Appels API liés à la carte (catégories, cocktails, ingrédients). */
export const catalogueService = {
  // --- Catégories ---
  listerCategories() {
    return http.get<Categorie[]>('/api/categories').then((r) => r.data)
  },
  creerCategorie(body: CategorieRequest) {
    return http.post<Categorie>('/api/categories', body).then((r) => r.data)
  },
  modifierCategorie(id: number, body: CategorieRequest) {
    return http.put<Categorie>(`/api/categories/${id}`, body).then((r) => r.data)
  },
  supprimerCategorie(id: number) {
    return http.delete(`/api/categories/${id}`)
  },

  // --- Cocktails ---
  listerCocktails(categorieId?: number) {
    const params = categorieId ? { categorieId } : undefined
    return http.get<Cocktail[]>('/api/cocktails', { params }).then((r) => r.data)
  },
  trouverCocktail(id: number) {
    return http.get<Cocktail>(`/api/cocktails/${id}`).then((r) => r.data)
  },
  creerCocktail(body: CocktailRequest) {
    return http.post<Cocktail>('/api/cocktails', body).then((r) => r.data)
  },
  modifierCocktail(id: number, body: CocktailRequest) {
    return http.put<Cocktail>(`/api/cocktails/${id}`, body).then((r) => r.data)
  },
  supprimerCocktail(id: number) {
    return http.delete(`/api/cocktails/${id}`)
  },

  // --- Ingrédients ---
  listerIngredients() {
    return http.get<Ingredient[]>('/api/ingredients').then((r) => r.data)
  },
  creerIngredient(body: IngredientRequest) {
    return http.post<Ingredient>('/api/ingredients', body).then((r) => r.data)
  },
  modifierIngredient(id: number, body: IngredientRequest) {
    return http.put<Ingredient>(`/api/ingredients/${id}`, body).then((r) => r.data)
  },
  supprimerIngredient(id: number) {
    return http.delete(`/api/ingredients/${id}`)
  },

  // --- Catalogue externe (TheCocktailDB) ---
  suggestionsExterne() {
    return http.get<CocktailExterne[]>('/api/catalogue-externe/suggestions').then((r) => r.data)
  },
  rechercherExterne(q: string) {
    return http.get<CocktailExterne[]>('/api/catalogue-externe', { params: { q } }).then((r) => r.data)
  },
  importerExterne(body: ImportCocktailRequest) {
    return http.post<Cocktail>('/api/catalogue-externe/import', body).then((r) => r.data)
  },
}
