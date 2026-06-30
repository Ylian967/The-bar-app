<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import { imageUrl } from '../../services/http'
import type { Categorie, Cocktail } from '../../types/models'
import BottomNav from '../../components/BottomNav.vue'

const categories = ref<Categorie[]>([])
const cocktails = ref<Cocktail[]>([])
const categorieActive = ref<number | null>(null)
const recherche = ref('')
const chargement = ref(true)

const liste = computed(() =>
  cocktails.value.filter((c) => c.nom.toLowerCase().includes(recherche.value.toLowerCase())),
)
const duJour = computed(() =>
  categorieActive.value === null && recherche.value === '' ? cocktails.value.filter((c) => c.favori) : [],
)

function prixMin(c: Cocktail): number {
  return Math.min(...c.tailles.map((t) => t.prix))
}

async function charger() {
  chargement.value = true
  cocktails.value = await catalogueService.listerCocktails(categorieActive.value ?? undefined, true)
  chargement.value = false
}

function filtrer(id: number | null) {
  categorieActive.value = id
  charger()
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

    <div class="search">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round">
        <circle cx="11" cy="11" r="7" /><path d="M20 20l-3.5-3.5" />
      </svg>
      <input v-model="recherche" type="text" placeholder="Rechercher un cocktail…" />
    </div>

    <div class="chips">
      <button class="chip" :class="{ on: categorieActive === null }" @click="filtrer(null)">Carte du jour</button>
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

    <template v-else>
      <section v-if="duJour.length">
        <div class="row-h"><h3>Cocktails du jour</h3></div>
        <div class="hcards">
          <RouterLink
            v-for="c in duJour"
            :key="c.id"
            :to="{ name: 'fiche', params: { id: c.id } }"
            class="cc"
          >
            <div class="pic"><img :src="imageUrl(c.imageUrl)" :alt="c.nom" /></div>
            <div class="body">
              <div class="nm">{{ c.nom }}</div>
              <div class="ac">{{ c.accroche ?? c.categorie }}</div>
              <div class="pr">{{ prixMin(c) }} €</div>
            </div>
          </RouterLink>
        </div>
      </section>

      <div class="row-h"><h3>Tous les cocktails</h3></div>
      <p v-if="liste.length === 0" class="etat">Aucun cocktail trouvé.</p>
      <div class="list">
        <RouterLink
          v-for="c in liste"
          :key="c.id"
          :to="{ name: 'fiche', params: { id: c.id } }"
          class="item"
        >
          <img class="av" :src="imageUrl(c.imageUrl)" :alt="c.nom" />
          <div class="info">
            <div class="nom">{{ c.nom }}</div>
            <div class="ac">{{ c.accroche ?? c.categorie }}</div>
          </div>
          <div class="prix">{{ prixMin(c) }} €<small>dès</small></div>
        </RouterLink>
      </div>
    </template>

    <BottomNav />
  </div>
</template>

<style scoped>
.screen { max-width: 480px; margin: 0 auto; min-height: 100vh; padding: 22px 18px 110px; }
.entete { margin-bottom: 16px; }
.bonsoir { color: var(--muted); font-size: 13px; font-weight: 500; }
.entete h1 { font-family: 'Sora', sans-serif; font-size: 30px; font-weight: 800; letter-spacing: -0.5px; }

.search { display: flex; align-items: center; gap: 11px; background: var(--sheen); border: 1px solid var(--stroke); backdrop-filter: blur(22px); box-shadow: var(--edge); border-radius: 18px; padding: 14px 16px; }
.search svg { width: 18px; height: 18px; color: var(--muted); flex-shrink: 0; }
.search input { border: none; outline: none; flex: 1; font-family: inherit; font-size: 14px; background: transparent; color: #fff; }
.search input::placeholder { color: var(--muted); }

.chips { display: flex; gap: 9px; overflow-x: auto; padding: 16px 0 4px; scrollbar-width: none; -ms-overflow-style: none; }
.chips::-webkit-scrollbar { display: none; }
.chip { flex-shrink: 0; }

.row-h { display: flex; justify-content: space-between; align-items: baseline; margin: 24px 2px 14px; }
.row-h h3 { font-family: 'Sora', sans-serif; font-size: 18px; font-weight: 700; }
.row-h a { color: var(--accent1); font-size: 13px; font-weight: 600; }

.hcards { display: flex; gap: 14px; overflow-x: auto; padding-bottom: 4px; scrollbar-width: none; -ms-overflow-style: none; }
.hcards::-webkit-scrollbar { display: none; }
.cc { min-width: 160px; flex: 0 0 160px; border-radius: 24px; overflow: hidden; background: var(--sheen); border: 1px solid var(--stroke); backdrop-filter: blur(24px) saturate(150%); box-shadow: var(--edge); }
.cc .pic { height: 120px; }
.cc .pic img { width: 100%; height: 100%; object-fit: cover; }
.cc .body { padding: 12px 14px 14px; }
.cc .body .nm { font-family: 'Sora', sans-serif; font-weight: 700; font-size: 15px; }
.cc .body .ac { font-size: 11px; color: var(--muted); margin-top: 2px; }
.cc .body .pr { margin-top: 9px; font-family: 'Sora', sans-serif; font-weight: 700; color: var(--gold); font-size: 15px; }

.list { display: flex; flex-direction: column; gap: 11px; }
.item { display: flex; align-items: center; gap: 14px; background: var(--sheen); border: 1px solid var(--stroke); backdrop-filter: blur(24px) saturate(150%); box-shadow: var(--edge); border-radius: 20px; padding: 11px; }
.item .av { width: 56px; height: 56px; border-radius: 15px; object-fit: cover; }
.info { flex: 1; }
.info .nom { font-family: 'Sora', sans-serif; font-weight: 600; font-size: 15px; }
.info .ac { color: var(--muted); font-size: 12px; }
.prix { font-family: 'Sora', sans-serif; font-weight: 700; color: var(--gold); font-size: 15px; text-align: right; }
.prix small { display: block; color: var(--muted2); font-weight: 500; font-size: 10px; }
</style>
