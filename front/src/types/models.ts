// Types partagés, alignés sur les DTO de l'API.

export type Taille = 'S' | 'M' | 'L'
export type StatutCommande = 'COMMANDEE' | 'EN_PREPARATION' | 'TERMINEE'
export type StatutPreparation = 'PREPARATION_INGREDIENTS' | 'ASSEMBLAGE' | 'DRESSAGE' | 'TERMINEE'

export interface Categorie {
  id: number
  nom: string
}

export interface Ingredient {
  id: number
  nom: string
}

export interface TaillePrix {
  taille: Taille
  prix: number
}

export interface Cocktail {
  id: number
  nom: string
  description: string | null
  imageUrl: string | null
  categorieId: number
  categorie: string
  tailles: TaillePrix[]
  ingredients: string[]
}

export interface LigneCommande {
  id: number
  cocktailId: number
  cocktailNom: string
  taille: Taille
  prix: number
  quantite: number
  statutPreparation: StatutPreparation
}

export interface Commande {
  id: number
  clientNom: string
  statut: StatutCommande
  dateCreation: string
  lignes: LigneCommande[]
  total: number
}

// --- Corps de requêtes ---

export interface LigneCommandeRequest {
  cocktailId: number
  taille: Taille
  quantite: number
}

export interface CommandeRequest {
  clientNom: string
  lignes: LigneCommandeRequest[]
}

export interface LoginResponse {
  token: string
  email: string
  role: string
  nom: string
}
