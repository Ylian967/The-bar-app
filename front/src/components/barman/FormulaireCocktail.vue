<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import type { Categorie, Cocktail, Ingredient, Taille } from '../../types/models'

const props = defineProps<{ cocktail?: Cocktail | null }>()
const emit = defineEmits<{ saved: []; cancel: [] }>()

const categories = ref<Categorie[]>([])
const ingredients = ref<Ingredient[]>([])
const erreur = ref('')
const rechercheIng = ref('')

const f = ref({
  nom: '',
  accroche: '',
  description: '',
  categorieId: 0,
  imageUrl: '',
  ingredientIds: [] as number[],
  prix: { S: 0, M: 0, L: 0 } as Record<Taille, number>,
})

const selectionnes = computed(() => ingredients.value.filter((i) => f.value.ingredientIds.includes(i.id)))
const proposes = computed(() => {
  const q = rechercheIng.value.trim().toLowerCase()
  return ingredients.value.filter((i) => !f.value.ingredientIds.includes(i.id) && i.nom.toLowerCase().includes(q))
})
const peutCreer = computed(() => {
  const q = rechercheIng.value.trim()
  return q.length > 0 && !ingredients.value.some((i) => i.nom.toLowerCase() === q.toLowerCase())
})

function basculer(id: number) {
  f.value.ingredientIds.push(id)
}
function retirer(id: number) {
  f.value.ingredientIds = f.value.ingredientIds.filter((x) => x !== id)
}
async function creerIngredient() {
  const nom = rechercheIng.value.trim()
  if (!nom) return
  const cree = await catalogueService.creerIngredient({ nom })
  ingredients.value.push(cree)
  f.value.ingredientIds.push(cree.id)
  rechercheIng.value = ''
}

async function enregistrer() {
  const tailles = (['S', 'M', 'L'] as Taille[])
    .filter((t) => f.value.prix[t] > 0)
    .map((t) => ({ taille: t, prix: f.value.prix[t] }))
  if (!f.value.nom.trim() || !f.value.categorieId || tailles.length === 0) {
    erreur.value = 'Nom, catégorie et au moins une taille avec prix sont requis.'
    return
  }
  const body = {
    nom: f.value.nom.trim(),
    accroche: f.value.accroche.trim() || null,
    description: f.value.description.trim() || null,
    imageUrl: f.value.imageUrl.trim() || null,
    categorieId: f.value.categorieId,
    tailles,
    ingredientIds: f.value.ingredientIds,
  }
  if (props.cocktail) await catalogueService.modifierCocktail(props.cocktail.id, body)
  else await catalogueService.creerCocktail(body)
  emit('saved')
}

async function supprimer() {
  if (!props.cocktail) return
  if (!confirm(`Supprimer « ${props.cocktail.nom} » ?`)) return
  await catalogueService.supprimerCocktail(props.cocktail.id)
  emit('saved')
}

onMounted(async () => {
  ;[categories.value, ingredients.value] = await Promise.all([
    catalogueService.listerCategories(),
    catalogueService.listerIngredients(),
  ])
  const c = props.cocktail
  if (c) {
    const prix = { S: 0, M: 0, L: 0 } as Record<Taille, number>
    c.tailles.forEach((t) => (prix[t.taille] = t.prix))
    f.value = {
      nom: c.nom,
      accroche: c.accroche ?? '',
      description: c.description ?? '',
      categorieId: c.categorieId,
      imageUrl: c.imageUrl ?? '',
      ingredientIds: ingredients.value.filter((i) => c.ingredients.includes(i.nom)).map((i) => i.id),
      prix,
    }
  } else {
    f.value.categorieId = categories.value[0]?.id ?? 0
  }
})
</script>

<template>
  <div class="form">
    <h3>{{ cocktail ? 'Modifier' : 'Nouveau' }} cocktail</h3>

    <label>Nom<input v-model="f.nom" placeholder="Ex : Mojito" /></label>
    <label>Accroche<input v-model="f.accroche" placeholder="Ex : Frais & mentholé" /></label>
    <label>Catégorie
      <select v-model="f.categorieId">
        <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.nom }}</option>
      </select>
    </label>
    <label>Description<textarea v-model="f.description" rows="2" /></label>
    <label>Image (URL)<input v-model="f.imageUrl" placeholder="/images/cocktails/mojito.jpg" /></label>

    <div class="bloc">Tailles & prix
      <div class="prix">
        <span v-for="t in (['S', 'M', 'L'] as const)" :key="t">
          {{ t }} <input v-model.number="f.prix[t]" type="number" min="0" step="0.5" /> €
        </span>
      </div>
    </div>

    <div class="bloc">Ingrédients
      <div class="puces">
        <button v-for="i in selectionnes" :key="i.id" class="puce on" @click="retirer(i.id)">
          {{ i.nom }} <em>×</em>
        </button>
        <span v-if="selectionnes.length === 0" class="vide">Aucun sélectionné</span>
      </div>
      <input v-model="rechercheIng" class="rech" placeholder="Rechercher / ajouter un ingrédient…" />
      <div class="options">
        <button v-for="i in proposes" :key="i.id" class="opt" @click="basculer(i.id)">{{ i.nom }}</button>
        <button v-if="peutCreer" class="opt creer" @click="creerIngredient">+ Créer « {{ rechercheIng.trim() }} »</button>
      </div>
    </div>

    <p v-if="erreur" class="err">{{ erreur }}</p>
    <div class="actions">
      <button v-if="cocktail" class="btn danger" @click="supprimer">Supprimer</button>
      <button class="btn ghost" @click="emit('cancel')">Annuler</button>
      <button class="btn" @click="enregistrer">Enregistrer</button>
    </div>
  </div>
</template>

<style scoped>
.form { background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); border-radius: 20px; box-shadow: var(--shadow-sm); padding: 20px; }
.form h3 { font-size: 16px; font-weight: 700; margin-bottom: 14px; }
label { display: block; font-size: 12px; font-weight: 600; color: var(--ink-soft); margin-bottom: 12px; }
input, select, textarea { display: block; width: 100%; margin-top: 6px; background: var(--cream); border: 1.5px solid var(--line); border-radius: 12px; padding: 10px 12px; font-family: inherit; font-size: 14px; }
.bloc { font-size: 12px; font-weight: 600; color: var(--ink-soft); margin-bottom: 14px; }
.prix { display: flex; gap: 10px; margin-top: 6px; align-items: center; }
.prix span { display: flex; align-items: center; gap: 4px; font-weight: 700; color: var(--ink); }
.prix input { width: 64px; margin: 0; }
.puces { display: flex; flex-wrap: wrap; gap: 7px; margin: 8px 0; min-height: 24px; }
.puce { background: var(--btn); border: 1px solid var(--btn-stroke); backdrop-filter: blur(14px) saturate(150%); -webkit-backdrop-filter: blur(14px) saturate(150%); color: #fff; border-radius: 999px; padding: 6px 12px; font-size: 12px; font-weight: 600; box-shadow: var(--btn-sheen); }
.puce em { font-style: normal; margin-left: 4px; opacity: 0.85; }
.vide { color: var(--ink-soft); font-size: 12px; font-weight: 500; }
.rech { margin-top: 4px; }
.options { display: flex; flex-wrap: wrap; gap: 7px; margin-top: 10px; max-height: 140px; overflow-y: auto; }
.opt { background: var(--cream); border: 1px solid var(--line); border-radius: 999px; padding: 6px 12px; font-size: 12px; font-weight: 500; color: var(--ink); }
.opt.creer { background: rgba(240,194,122,0.18); color: var(--gold); border-color: rgba(240,194,122,0.4); font-weight: 700; }
.err { color: var(--coral-d); font-size: 13px; margin-bottom: 10px; }
.actions { display: flex; gap: 10px; }
.actions .btn { flex: 1; }
.btn.danger { background: rgba(255,77,141,0.18); color: var(--coral-d); box-shadow: none; }
</style>
