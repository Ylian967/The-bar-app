<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import { imageUrl } from '../../services/http'
import type { Categorie, Cocktail } from '../../types/models'
import BottomNav from '../../components/BottomNav.vue'

const categories = ref<Categorie[]>([])
const cocktails = ref<Cocktail[]>([])
const categorieActive = ref<number | null>(null)
const chargement = ref(true)

async function charger() {
  chargement.value = true
  cocktails.value = await catalogueService.listerCocktails(categorieActive.value ?? undefined)
  chargement.value = false
}

function filtrer(id: number | null) {
  categorieActive.value = id
  charger()
}

function prixMin(c: Cocktail): number {
  return Math.min(...c.tailles.map((t) => t.prix))
}

onMounted(async () => {
  categories.value = await catalogueService.listerCategories()
  await charger()
})
</script>

<template>
  <div class="screen">
    <header class="entete">
      <p class="bonsoir">Bonsoir</p>
      <h1>La Carte</h1>
    </header>

    <div class="chips">
      <button class="chip" :class="{ on: categorieActive === null }" @click="filtrer(null)">Tout</button>
      <button
        v-for="cat in categories"
        :key="cat.id"
        class="chip"
        :class="{ on: categorieActive === cat.id }"
        @click="filtrer(cat.id)"
      >
        {{ cat.nom }}
      </button>
    </div>

    <p v-if="chargement" class="etat">Chargement de la carte…</p>
    <p v-else-if="cocktails.length === 0" class="etat">Aucun cocktail dans cette catégorie.</p>

    <div v-else class="liste">
      <RouterLink
        v-for="c in cocktails"
        :key="c.id"
        :to="{ name: 'fiche', params: { id: c.id } }"
        class="item"
      >
        <img :src="imageUrl(c.imageUrl)" :alt="c.nom" />
        <div class="info">
          <div class="nom">{{ c.nom }}</div>
          <div class="cat">{{ c.categorie }}</div>
        </div>
        <div class="prix">{{ prixMin(c) }} €<small>dès</small></div>
      </RouterLink>
    </div>

    <BottomNav />
  </div>
</template>

<style scoped>
.screen {
  max-width: 480px;
  margin: 0 auto;
  min-height: 100vh;
  background: var(--cream);
  padding: 22px 18px 100px;
}
.entete { margin-bottom: 16px; }
.bonsoir { color: var(--ink-soft); font-size: 13px; font-weight: 500; }
.entete h1 { font-size: 28px; font-weight: 800; }
.chips { display: flex; gap: 9px; overflow-x: auto; padding-bottom: 4px; }
.chip { flex-shrink: 0; }
.liste { margin-top: 18px; display: flex; flex-direction: column; gap: 11px; }
.item {
  display: flex;
  align-items: center;
  gap: 14px;
  background: #fff;
  border-radius: 20px;
  padding: 12px;
  box-shadow: var(--shadow-sm);
}
.item img { width: 58px; height: 58px; border-radius: 16px; object-fit: cover; }
.info { flex: 1; }
.info .nom { font-family: 'Poppins', sans-serif; font-weight: 600; font-size: 15px; }
.info .cat { color: var(--ink-soft); font-size: 12px; }
.prix { font-family: 'Poppins', sans-serif; font-weight: 700; color: var(--coral); font-size: 15px; text-align: right; }
.prix small { display: block; color: var(--ink-soft); font-weight: 500; font-size: 10px; }
</style>
