<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import { imageUrl } from '../../services/http'
import type { Categorie, CocktailExterne, Taille } from '../../types/models'

const emit = defineEmits<{ imported: []; cancel: [] }>()

const categories = ref<Categorie[]>([])
const recherche = ref('')
const resultats = ref<CocktailExterne[]>([])
const chargement = ref(false)
const selection = ref<CocktailExterne | null>(null)
const categorieId = ref(0)
const prix = ref<Record<Taille, number>>({ S: 0, M: 0, L: 0 })
const erreur = ref('')

async function chercher() {
  chargement.value = true
  erreur.value = ''
  resultats.value = recherche.value.trim()
    ? await catalogueService.rechercherExterne(recherche.value.trim())
    : await catalogueService.suggestionsExterne()
  chargement.value = false
}

function choisir(c: CocktailExterne) {
  selection.value = c
  prix.value = { S: 0, M: 0, L: 0 }
}

async function importer() {
  const tailles = (['S', 'M', 'L'] as Taille[])
    .filter((t) => prix.value[t] > 0)
    .map((t) => ({ taille: t, prix: prix.value[t] }))
  if (!selection.value || !categorieId.value || tailles.length === 0) {
    erreur.value = 'Choisissez une catégorie et au moins un prix.'
    return
  }
  await catalogueService.importerExterne({
    idExterne: selection.value.idExterne,
    categorieId: categorieId.value,
    tailles,
  })
  emit('imported')
}

onMounted(async () => {
  categories.value = await catalogueService.listerCategories()
  categorieId.value = categories.value[0]?.id ?? 0
  chargement.value = true
  resultats.value = await catalogueService.suggestionsExterne()
  chargement.value = false
})
</script>

<template>
  <div class="form">
    <h3>Importer depuis le catalogue</h3>

    <div class="search">
      <input v-model="recherche" placeholder="Rechercher (ex : margarita, gin…)" @keyup.enter="chercher" />
      <button class="go" @click="chercher">Rechercher</button>
    </div>

    <p v-if="chargement" class="info">Recherche…</p>

    <!-- Étape 1 : résultats -->
    <div v-if="!selection" class="grille">
      <button v-for="c in resultats" :key="c.idExterne" class="res" @click="choisir(c)">
        <img :src="imageUrl(c.imageUrl)" :alt="c.nom" />
        <span>{{ c.nom }}</span>
      </button>
      <p v-if="resultats.length === 0 && !chargement" class="info">Aucun cocktail trouvé.</p>
    </div>

    <!-- Étape 2 : configurer l'import -->
    <div v-else class="config">
      <div class="apercu">
        <img :src="imageUrl(selection.imageUrl)" :alt="selection.nom" />
        <div>
          <div class="nm">{{ selection.nom }}</div>
          <div v-if="selection.ingredients.length" class="ings">{{ selection.ingredients.join(' · ') }}</div>
        </div>
        <button class="chg" @click="selection = null">Changer</button>
      </div>

      <label>Catégorie
        <select v-model="categorieId">
          <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.nom }}</option>
        </select>
      </label>
      <div class="bloc">Tailles & prix
        <div class="prix">
          <span v-for="t in (['S', 'M', 'L'] as const)" :key="t">
            {{ t }} <input v-model.number="prix[t]" type="number" min="0" step="0.5" /> €
          </span>
        </div>
      </div>
      <p v-if="erreur" class="err">{{ erreur }}</p>
      <div class="actions">
        <button class="btn ghost" @click="emit('cancel')">Annuler</button>
        <button class="btn" @click="importer">Importer ce cocktail</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.form { background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); border-radius: 20px; box-shadow: var(--shadow-sm); padding: 20px; }
.form h3 { font-size: 16px; font-weight: 700; margin-bottom: 14px; }
.search { display: flex; gap: 10px; margin-bottom: 14px; }
.search input { flex: 1; background: var(--cream); border: 1.5px solid var(--line); border-radius: 12px; padding: 10px 12px; font-family: inherit; font-size: 14px; }
.go { background: var(--accent); color: #fff; font-family: 'Sora', sans-serif; font-weight: 700; font-size: 13px; padding: 0 16px; border-radius: 12px; }
.info { color: var(--ink-soft); font-size: 13px; text-align: center; padding: 10px; }
.grille { display: grid; grid-template-columns: repeat(auto-fill, minmax(110px, 1fr)); gap: 12px; max-height: 320px; overflow-y: auto; }
.res { background: var(--cream); border-radius: 16px; overflow: hidden; text-align: left; }
.res img { width: 100%; height: 90px; object-fit: cover; }
.res span { display: block; padding: 8px 10px; font-family: 'Sora', sans-serif; font-weight: 600; font-size: 13px; }
.config .apercu { display: flex; align-items: center; gap: 12px; background: var(--cream); border-radius: 16px; padding: 10px; margin-bottom: 14px; }
.apercu img { width: 54px; height: 54px; border-radius: 12px; object-fit: cover; }
.apercu .nm { font-family: 'Sora', sans-serif; font-weight: 700; font-size: 15px; }
.apercu .ings { font-size: 11px; color: var(--ink-soft); }
.chg { margin-left: auto; background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); border: 1px solid var(--line); border-radius: 10px; padding: 7px 10px; font-size: 12px; font-weight: 600; color: var(--ink-soft); }
label { display: block; font-size: 12px; font-weight: 600; color: var(--ink-soft); margin-bottom: 12px; }
select { display: block; width: 100%; margin-top: 6px; background: var(--cream); border: 1.5px solid var(--line); border-radius: 12px; padding: 10px 12px; font-family: inherit; font-size: 14px; }
.bloc { font-size: 12px; font-weight: 600; color: var(--ink-soft); margin-bottom: 14px; }
.prix { display: flex; gap: 10px; margin-top: 6px; align-items: center; }
.prix span { display: flex; align-items: center; gap: 4px; font-weight: 700; color: var(--ink); }
.prix input { width: 64px; background: var(--cream); border: 1.5px solid var(--line); border-radius: 12px; padding: 8px; font-family: inherit; }
.err { color: var(--coral-d); font-size: 13px; margin-bottom: 10px; }
.actions { display: flex; gap: 10px; }
.actions .btn { flex: 1; }
</style>
